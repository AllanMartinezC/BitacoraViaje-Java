import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.stream.Collectors;
import Model.Bitacora;
import Model.Entrada;
import Model.Estadisticas;
import Model.Exportar;

public class BitacoraApp extends JFrame {
    private JTextArea entradaTexto;
    private JComboBox<String> comboCategoria;
    private JTextField campoBusqueda;
    private DefaultListModel<String> listaModel;
    private JList<String> listaEntradas;
    private Bitacora bitacora;
    private JPanel panelCentral;
    private JPanel barraLateral;
    private boolean barraVisible = false;
    private boolean modoOscuro = false;

    private Color claroFondo = Color.WHITE;
    private Color claroTexto = Color.BLACK;
    private Color oscuroFondo = new Color(45, 45, 45);
    private Color oscuroTexto = Color.LIGHT_GRAY;

    public BitacoraApp() {
        bitacora = new Bitacora();

        setTitle("📝 Bitácora Interior");
        setSize(550, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton botonMenu = new JButton("📂 Menú");
        botonMenu.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        botonMenu.setAlignmentX(Component.LEFT_ALIGNMENT);
        botonMenu.setMaximumSize(new Dimension(100, 30));
        botonMenu.setBackground(new Color(180, 200, 240));
        botonMenu.setFocusPainted(false);
        botonMenu.setBorderPainted(false);
        botonMenu.addActionListener(e -> toggleBarraLateral());
        panelCentral.add(botonMenu);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel titulo = new JLabel("Bitácora de Viaje");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentral.add(titulo);

        JLabel instruccion = new JLabel("Escribe aquí tu reflexión/observación del día:");
        instruccion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        instruccion.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentral.add(instruccion);

        entradaTexto = new JTextArea(7, 40);
        entradaTexto.setLineWrap(true);
        entradaTexto.setWrapStyleWord(true);
        JScrollPane scrollEntrada = new JScrollPane(entradaTexto);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 5)));
        panelCentral.add(scrollEntrada);

        JLabel etiquetaCategoria = new JLabel("Categoría:");
        etiquetaCategoria.setAlignmentX(Component.LEFT_ALIGNMENT);

        comboCategoria = new JComboBox<>(new String[] {
            "Personal", "Estudios", "Trabajo", "Espiritualidad", "Ideas", "Reflexiones", "Otro"
        });
        comboCategoria.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));

        panelCentral.add(Box.createRigidArea(new Dimension(0, 5)));
        panelCentral.add(etiquetaCategoria);
        panelCentral.add(comboCategoria);

        JButton botonGuardar = new JButton("Guardar entrada");
        botonGuardar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonGuardar.setMaximumSize(new Dimension(150, 40));
        botonGuardar.addActionListener(e -> guardarEntrada());
        panelCentral.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentral.add(botonGuardar);

        JLabel listaTitulo = new JLabel("Buscar entrada:");
        listaTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        listaTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 20)));
        panelCentral.add(listaTitulo);

        campoBusqueda = new JTextField();
        campoBusqueda.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        campoBusqueda.setToolTipText("Buscar por palabra clave o categoría");
        JButton botonBuscar = new JButton("Buscar");
        botonBuscar.setMaximumSize(new Dimension(100, 30));
        botonBuscar.addActionListener(e -> buscarEntradas());
        panelCentral.add(campoBusqueda);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 5)));
        panelCentral.add(botonBuscar);

        listaModel = new DefaultListModel<>();
        listaEntradas = new JList<>(listaModel);
        JScrollPane scrollLista = new JScrollPane(listaEntradas);
        scrollLista.setPreferredSize(new Dimension(500, 250));
        panelCentral.add(Box.createRigidArea(new Dimension(0, 5)));
        panelCentral.add(scrollLista);

        JButton botonBorrar = new JButton("Borrar entrada seleccionada");
        botonBorrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonBorrar.setMaximumSize(new Dimension(220, 35));
        botonBorrar.addActionListener(e -> borrarEntrada());
        panelCentral.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentral.add(botonBorrar);

        add(panelCentral, BorderLayout.CENTER);

        barraLateral = new JPanel();
        barraLateral.setPreferredSize(new Dimension(0, getHeight()));
        barraLateral.setBackground(new Color(30, 30, 30));
        barraLateral.setLayout(new BoxLayout(barraLateral, BoxLayout.Y_AXIS));

        JLabel tituloLateral = new JLabel("⚙ Opciones");
        tituloLateral.setForeground(Color.WHITE);
        tituloLateral.setFont(new Font("Segoe UI", Font.BOLD, 18));
        tituloLateral.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JButton botonTema = new JButton("Cambiar tema");
        JButton botonExportar = new JButton("Exportar");
        JButton botonEstadisticas = new JButton("Ver estadísticas");

        botonTema.setAlignmentX(Component.LEFT_ALIGNMENT);
        botonExportar.setAlignmentX(Component.LEFT_ALIGNMENT);
        botonEstadisticas.setAlignmentX(Component.LEFT_ALIGNMENT);

        botonTema.setMaximumSize(new Dimension(180, 30));
        botonExportar.setMaximumSize(new Dimension(180, 30));
        botonEstadisticas.setMaximumSize(new Dimension(180, 30));

        botonTema.addActionListener(e -> alternarTema());
        botonExportar.addActionListener(e -> exportarEntradas());
        botonEstadisticas.addActionListener(e -> mostrarEstadisticas());

        for (JButton b : new JButton[]{botonTema, botonExportar, botonEstadisticas}) {
            b.setBackground(new Color(60, 60, 60));
            b.setForeground(Color.WHITE);
            b.setBorderPainted(false);
            b.setFocusPainted(false);
        }

        barraLateral.add(tituloLateral);
        barraLateral.add(Box.createRigidArea(new Dimension(0, 10)));
        barraLateral.add(botonTema);
        barraLateral.add(Box.createRigidArea(new Dimension(0, 10)));
        barraLateral.add(botonExportar);
        barraLateral.add(Box.createRigidArea(new Dimension(0, 10)));
        barraLateral.add(botonEstadisticas);

        add(barraLateral, BorderLayout.WEST);
        aplicarTema();
    }

private void buscarEntradas() {
    String termino = campoBusqueda.getText().toLowerCase();
    List<Entrada> resultados = bitacora.getEntradas().stream()
        .filter(e -> e.getTexto().toLowerCase().contains(termino)
            || (e.getCategoria() != null && e.getCategoria().toLowerCase().contains(termino)))
        .collect(Collectors.toList());

    listaModel.clear();
    for (Entrada e : resultados) {
        listaModel.addElement(e.toString());
    }
}

    private void guardarEntrada() {
        String texto = entradaTexto.getText().trim();
        String categoria = (String) comboCategoria.getSelectedItem();
        if (!texto.isEmpty()) {
            bitacora.agregarEntrada(texto, categoria);
            actualizarLista();
            entradaTexto.setText("");
            efectoFlash(Color.GREEN.brighter());
        } else {
            JOptionPane.showMessageDialog(this, "La entrada está vacía", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void alternarTema() {
        modoOscuro = !modoOscuro;
        aplicarTema();
    }

    private void exportarEntradas() {
        boolean exito = Exportar.exportar(bitacora.getEntradas(), "bitacora.txt");
        if (exito) {
            JOptionPane.showMessageDialog(this, "Entradas exportadas con éxito a bitacora.txt");
        } else {
            JOptionPane.showMessageDialog(this, "Error al exportar entradas");
        }
    }

    private void mostrarEstadisticas() {
        Estadisticas stats = new Estadisticas(bitacora.getEntradas());
        JOptionPane.showMessageDialog(this, stats.resumen(), "📈 Estadísticas", JOptionPane.INFORMATION_MESSAGE);
    }

    private void toggleBarraLateral() {
        int inicio = barraVisible ? 150 : 0;
        int fin = barraVisible ? 0 : 150;
        barraVisible = !barraVisible;

        Timer timer = new Timer(5, null);
        timer.addActionListener(new ActionListener() {
            int ancho = inicio;

            public void actionPerformed(ActionEvent e) {
                ancho += (barraVisible ? 10 : -10);
                barraLateral.setPreferredSize(new Dimension(ancho, getHeight()));
                barraLateral.revalidate();
                if ((barraVisible && ancho >= fin) || (!barraVisible && ancho <= fin)) {
                    barraLateral.setPreferredSize(new Dimension(fin, getHeight()));
                    barraLateral.revalidate();
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        timer.start();
    }

    private void borrarEntrada() {
        int indice = listaEntradas.getSelectedIndex();
        if (indice != -1) {
            bitacora.borrarEntrada(indice);
            actualizarLista();
            efectoFlash(Color.RED.brighter());
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona una entrada para borrar", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void actualizarLista() {
        listaModel.clear();
        for (Entrada e : bitacora.getEntradas()) {
            listaModel.addElement(e.toString());
        }
        if (!listaModel.isEmpty()) {
            listaEntradas.ensureIndexIsVisible(listaModel.size() - 1);
        }
    }

    private void aplicarTema() {
        Color fondo = modoOscuro ? oscuroFondo : claroFondo;
        Color texto = modoOscuro ? oscuroTexto : claroTexto;

        panelCentral.setBackground(fondo);
        entradaTexto.setBackground(fondo);
        entradaTexto.setForeground(texto);
        listaEntradas.setBackground(fondo);
        listaEntradas.setForeground(texto);
        comboCategoria.setBackground(fondo);
        comboCategoria.setForeground(texto);
        campoBusqueda.setBackground(fondo);
        campoBusqueda.setForeground(texto);

        for (Component c : panelCentral.getComponents()) {
            if (c instanceof JLabel) c.setForeground(texto);
            if (c instanceof JButton) {
                c.setBackground(modoOscuro ? Color.DARK_GRAY : new Color(200, 230, 255));
                c.setForeground(texto);
            }
        }
    }

    private void efectoFlash(Color color) {
        Color original = panelCentral.getBackground();
        panelCentral.setBackground(color);
        Timer timer = new Timer(150, e -> panelCentral.setBackground(original));
        timer.setRepeats(false);
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BitacoraApp app = new BitacoraApp();
            app.setVisible(true);
        });
    }
}
