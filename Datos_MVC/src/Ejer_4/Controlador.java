package Ejer_4;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Controlador {
    private final Vista vista;
    private final File archivo;
    private final List<Producto> registros = new ArrayList<>();

    public Controlador(Vista vista, String rutaArchivo) {
        this.vista = vista;
        this.archivo = new File(rutaArchivo);
    }

    public void iniciar() {
        leerRegistros();
        int op;
        do {
            op = vista.mostrarMenu();
            switch (op) {
                case 1: listar(); break;
                case 2: agregarRegistro(); break;
                case 3: buscarRegistro(); break;
                case 4: eliminarRegistro(); break;
                case 5: vista.mostrarMensaje("Hasta luego."); break;
                default: vista.mostrarError("Opcion invalida.");
            }
        } while (op != 5);
    }

    public void leerRegistros() {
        registros.clear();
        if (!archivo.exists()) {
            vista.mostrarMensaje("Archivo no encontrado; se creara al agregar el primer registro.");
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                try { registros.add(Producto.desdeLinea(linea)); }
                catch (IllegalArgumentException e) { vista.mostrarError("Linea ignorada: " + e.getMessage()); }
            }
            vista.mostrarExito("Registros cargados: " + registros.size());
            listar();
        } catch (IOException e) {
            vista.mostrarError("No se pudo leer el archivo: " + e.getMessage());
        }
    }

    private void listar() {
        if (registros.isEmpty()) { vista.mostrarMensaje("(No hay registros)"); return; }
        for (Producto p : registros) vista.mostrarMensaje(p.toString());
    }

    public void agregarRegistro() {
        int id = vista.pedirEntero("ID: ");
        if (buscarPorId(id) != null) { vista.mostrarError("Ya existe un registro con ID " + id); return; }
        String nombre = vista.pedirTexto("Nombre: ");
        if (nombre.isEmpty() || nombre.contains(";")) {
            vista.mostrarError("El nombre no puede estar vacio ni contener ';'.");
            return;
        }
        double precio = vista.pedirDouble("Precio: ");
        if (precio < 0) { vista.mostrarError("El precio no puede ser negativo."); return; }

        registros.add(new Producto(id, nombre, precio));
        if (guardar()) vista.mostrarExito("Registro agregado.");
        else registros.remove(registros.size() - 1);
    }

    public void buscarRegistro() {
        Producto p = buscarPorId(vista.pedirEntero("ID a buscar: "));
        if (p == null) vista.mostrarError("No se encontro el registro.");
        else vista.mostrarMensaje(p.toString());
    }

    public void eliminarRegistro() {
        Producto p = buscarPorId(vista.pedirEntero("ID a eliminar: "));
        if (p == null) { vista.mostrarError("No se encontro el registro."); return; }
        int pos = registros.indexOf(p);
        registros.remove(p);
        if (guardar()) vista.mostrarExito("Registro eliminado.");
        else registros.add(pos, p);     }

    private Producto buscarPorId(int id) {
        for (Producto p : registros) if (p.getId() == id) return p;
        return null;
    }

    private boolean guardar() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            for (Producto p : registros) pw.println(p.toLinea());
            return true;
        } catch (IOException e) {
            vista.mostrarError("No se pudo guardar el archivo: " + e.getMessage());
            return false;
        }
    }
}