package Model;

import java.io.*;
import java.util.List;

public class Exportar{
    public static boolean exportar(List<Entrada> entradas, String nombreArchivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            for (Entrada e : entradas) {
                writer.write(e.getFecha() + " - " + e.getTexto());
                writer.newLine();
                writer.write("------------------------------------------------");
                writer.newLine();
            }
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}

