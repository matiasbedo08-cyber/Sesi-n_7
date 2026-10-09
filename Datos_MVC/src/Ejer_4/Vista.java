package Ejer_4;

import java.util.Scanner;

public class Vista {
    private final Scanner sc = new Scanner(System.in);

    public int mostrarMenu() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1. Listar todos los registros");
        System.out.println("2. Agregar un nuevo registro");
        System.out.println("3. Buscar un registro por ID");
        System.out.println("4. Eliminar un registro por ID");
        System.out.println("5. Salir");
        return pedirEntero("Opcion: ");
    }

    public String pedirTexto(String msg) {
        System.out.print(msg);
        return sc.nextLine().trim();
    }

    public int pedirEntero(String msg) {
        while (true) {
            try { return Integer.parseInt(pedirTexto(msg)); }
            catch (NumberFormatException e) { mostrarError("Ingresa un numero entero valido."); }
        }
    }

    public double pedirDouble(String msg) {
        while (true) {
            try { return Double.parseDouble(pedirTexto(msg).replace(',', '.')); }
            catch (NumberFormatException e) { mostrarError("Ingresa un numero valido."); }
        }
    }

    public void mostrarMensaje(String msg) { System.out.println(msg); }
    public void mostrarExito(String msg) { System.out.println("[OK] " + msg); }
    public void mostrarError(String msg) { System.out.println("[ERROR] " + msg); }
}