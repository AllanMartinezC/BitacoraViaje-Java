package Model;

import java.text.SimpleDateFormat;

import java.util.Date;


public class Entrada {
    private String texto;
    private Date fecha;
    private String categoria;
    private static final SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public Entrada(String texto, String categoria) {
        this.texto = texto;
        this.fecha = new Date();
    }

    public String getTexto() {
        return texto;
    }

    public Date getFecha() {
        return fecha;
    }

      public String getCategoria() {
        return categoria;
    }

    @Override
    public String toString() {
        return formato.format(fecha) + " - " + texto;
    }
}