package Model;

import java.util.ArrayList;



public class Bitacora {
    private ArrayList<Entrada> entradas;

    public Bitacora() {
        entradas = new ArrayList<>();
    }

    public void agregarEntrada(String texto, String categoria) {
        entradas.add(new Entrada(texto, categoria));
    }

    public void borrarEntrada(int indice) {
        if (indice >= 0 && indice < entradas.size()) {
            entradas.remove(indice);
        }
    }

    public ArrayList<Entrada> getEntradas() {
        return entradas;
    }
}