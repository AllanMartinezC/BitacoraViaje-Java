package Model;

import java.util.List;

public class Estadisticas {
    private int totalEntradas;
    private int totalCaracteres;
    private String primeraFecha;
    private String ultimaFecha;
    private double promedio;

    public Estadisticas(List<Entrada> entradas) {
        if (entradas.isEmpty()) return;

        totalEntradas = entradas.size();
        totalCaracteres = entradas.stream().mapToInt(e -> e.getTexto().length()).sum();
        primeraFecha = entradas.get(0).getFecha().toString();
        ultimaFecha = entradas.get(totalEntradas - 1).getFecha().toString();
        promedio = totalCaracteres / (double) totalEntradas;
    }

    public String resumen() {
        return "📊 Estadísticas de tu Bitácora:\n\n" +
               "🔢 Total de entradas: " + totalEntradas + "\n" +
               "🕰️ Desde: " + primeraFecha + "\n" +
               "📅 Hasta: " + ultimaFecha + "\n" +
               "✍️ Promedio de caracteres por entrada: " + String.format("%.2f", promedio);
    }
}

