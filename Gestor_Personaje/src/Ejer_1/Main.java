package Ejer_1;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Gestor gestor = new Gestor("personajes.txt");
        int op;
        do {
            System.out.println("\n=== GESTOR DE PERSONAJES ===");
            System.out.println("1. Anadir   2. Mostrar   3. Modificar   4. Borrar");
            System.out.println("5. Actualizar un atributo   6. Ordenar por atributo");
            System.out.println("7. Estadisticas   8. Cargar personajes predefinidos   0. Salir");
            op = leerEntero("Opcion: ");
            try {
                switch (op) {
                    case 1: anadir(gestor); break;
                    case 2: gestor.mostrar(); break;
                    case 3: modificar(gestor); break;
                    case 4: System.out.println(gestor.borrar(leerTexto("Nombre: ")) ? "Borrado." : "No existe ese personaje."); break;
                    case 5: actualizarUno(gestor); break;
                    case 6: gestor.mostrarOrdenadoPor(leerAtributo()); break;
                    case 7: gestor.mostrarEstadisticas(); break;
                    case 8: System.out.println("Personajes anadidos: " + gestor.cargarPredefinidos()); break;
                    case 0: System.out.println("Hasta luego."); break;
                    default: System.out.println("Opcion invalida.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (op != 0);
    }

    private static void anadir(Gestor g) {
        Personaje p = new Personaje(leerTexto("Nombre: "), leerEntero("Vida: "),
                leerEntero("Ataque: "), leerEntero("Defensa: "), leerEntero("Alcance: "));
        System.out.println(g.anadir(p) ? "Personaje anadido." : "Ya existe un personaje con ese nombre.");
    }

    private static void modificar(Gestor g) {
        String n = leerTexto("Nombre a modificar: ");
        boolean ok = g.modificar(n, leerEntero("Nueva vida: "), leerEntero("Nuevo ataque: "),
                leerEntero("Nueva defensa: "), leerEntero("Nuevo alcance: "));
        System.out.println(ok ? "Modificado." : "No existe ese personaje.");
    }

    private static void actualizarUno(Gestor g) {
        String n = leerTexto("Nombre: ");
        Gestor.Atributo a = leerAtributo();
        boolean ok = g.actualizarAtributo(n, a, leerEntero("Nuevo valor: "));
        System.out.println(ok ? "Atributo actualizado." : "No existe ese personaje.");
    }

    private static Gestor.Atributo leerAtributo() {
        while (true) {
            String t = leerTexto("Atributo (vida/ataque/defensa/alcance): ").toUpperCase();
            try { return Gestor.Atributo.valueOf(t); }
            catch (IllegalArgumentException e) { System.out.println("Atributo no valido."); }
        }
    }

    private static String leerTexto(String msg) {
        System.out.print(msg);
        return sc.nextLine();
    }

    private static int leerEntero(String msg) {
        while (true) {
            try { return Integer.parseInt(leerTexto(msg).trim()); }
            catch (NumberFormatException e) { System.out.println("Ingresa un numero entero."); }
        }
    }
}