package Ejer_3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Estadisticas {
    private int totalLineas;
    private int totalPalabras;
    private long totalCaracteres;
    private final Map<String, Integer> frecuencias = new HashMap<>();

    public Estadisticas(List<String> lineas) {
        for (String linea : lineas) {
            totalLineas++;
            totalCaracteres += linea.length();
            contarPalabras(linea);
        }
    }

    private void contarPalabras(String linea) {
        StringBuilder palabra = new StringBuilder();
        for (int i = 0; i <= linea.length(); i++) {
            if (i < linea.length() && Character.isLetterOrDigit(linea.charAt(i))) {
                palabra.append(linea.charAt(i));
            } else if (palabra.length() > 0) {
                frecuencias.merge(palabra.toString().toLowerCase(), 1, Integer::sum);
                totalPalabras++;
                palabra.setLength(0);
            }
        }
    }

    public int getTotalLineas() { return totalLineas; }
    public int getTotalPalabras() { return totalPalabras; }
    public long getTotalCaracteres() { return totalCaracteres; }

    public double getPromedioPalabrasPorLinea() {
        return totalLineas == 0 ? 0 : (double) totalPalabras / totalLineas;
    }

    public List<Map.Entry<String, Integer>> getMasFrecuentes(int limite) {
        List<Map.Entry<String, Integer>> lista = new ArrayList<>(frecuencias.entrySet());
        lista.sort((a, b) -> {
            int cmp = Integer.compare(b.getValue(), a.getValue());
            return cmp != 0 ? cmp : a.getKey().compareTo(b.getKey());
        });
        return lista.subList(0, Math.min(limite, lista.size()));
    }
}