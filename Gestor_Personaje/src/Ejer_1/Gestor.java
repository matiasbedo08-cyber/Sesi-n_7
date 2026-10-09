package Ejer_1;

import java.io.*;
import java.util.*;


public class Gestor {
    public enum Atributo { VIDA, ATAQUE, DEFENSA, ALCANCE }

    private final List<Personaje> personajes = new ArrayList<>();
    private final File archivo;

    public Gestor(String rutaArchivo) {
        this.archivo = new File(rutaArchivo);
        cargar();
    }


    private void cargar() {
        if (!archivo.exists()) return; 
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                try {
                    Personaje p = Personaje.desdeLinea(linea);
                    if (buscar(p.getNombre()) == null) personajes.add(p);
                } catch (IllegalArgumentException e) {
                    System.out.println("Linea ignorada: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    private void guardar() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            for (Personaje p : personajes) pw.println(p.toLinea());
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo: " + e.getMessage());
        }
    }


    public Personaje buscar(String nombre) {
        for (Personaje p : personajes) {
            if (p.getNombre().equalsIgnoreCase(nombre.trim())) return p;
        }
        return null;
    }

    public boolean anadir(Personaje nuevo) {
        if (buscar(nuevo.getNombre()) != null) return false;
        personajes.add(nuevo);
        guardar();
        return true;
    }

    public void mostrar() {
        if (personajes.isEmpty()) {
            System.out.println("(No hay personajes registrados)");
            return;
        }
        for (Personaje p : personajes) System.out.println(p);
    }

    public boolean modificar(String nombre, int vida, int ataque, int defensa, int alcance) {
        Personaje p = buscar(nombre);
        if (p == null) return false;
        Personaje prueba = new Personaje(p.getNombre(), vida, ataque, defensa, alcance);
        p.setVida(prueba.getVida());
        p.setAtaque(prueba.getAtaque());
        p.setDefensa(prueba.getDefensa());
        p.setAlcance(prueba.getAlcance());
        guardar();
        return true;
    }

    public boolean borrar(String nombre) {
        Personaje p = buscar(nombre);
        if (p == null) return false;
        personajes.remove(p);
        guardar();
        return true;
    }

    public boolean actualizarAtributo(String nombre, Atributo attr, int valor) {
        Personaje p = buscar(nombre);
        if (p == null) return false;
        switch (attr) {
            case VIDA: p.setVida(valor); break;
            case ATAQUE: p.setAtaque(valor); break;
            case DEFENSA: p.setDefensa(valor); break;
            case ALCANCE: p.setAlcance(valor); break;
        }
        guardar();
        return true;
    }

    public void mostrarOrdenadoPor(Atributo attr) {
        List<Personaje> copia = new ArrayList<>(personajes);
        copia.sort((a, b) -> Integer.compare(valor(b, attr), valor(a, attr)));
        if (copia.isEmpty()) System.out.println("(No hay personajes registrados)");
        for (Personaje p : copia) System.out.println(p);
    }

    private int valor(Personaje p, Atributo attr) {
        switch (attr) {
            case VIDA: return p.getVida();
            case ATAQUE: return p.getAtaque();
            case DEFENSA: return p.getDefensa();
            default: return p.getAlcance();
        }
    }

    public void mostrarEstadisticas() {
        int n = personajes.size();
        System.out.println("Total de personajes: " + n);
        if (n == 0) return;
        for (Atributo a : Atributo.values()) {
            double suma = 0;
            for (Personaje p : personajes) suma += valor(p, a);
            System.out.printf("Promedio de %s: %.2f%n", a.name().toLowerCase(), suma / n);
        }
    }

    public int cargarPredefinidos() {
        Personaje[] base =
        {
            new Personaje("Caballero", 4, 2, 4, 1), 
            new Personaje("Guerrero", 3, 4, 2, 4), 
            new Personaje("Arquero", 2, 4, 1, 8)   
        };
        int añadidos = 0;
        for (Personaje p : base) if (anadir(p)) añadidos++;
        return añadidos;
    }
}