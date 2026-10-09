package Ejer_4;

public class Producto {
    private int id;
    private String nombre;
    private double precio;

    public Producto(int id, String nombre, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setPrecio(double precio) { this.precio = precio; }

    public String toLinea() { return id + ";" + nombre + ";" + precio; }

    public static Producto desdeLinea(String linea) {
        String[] p = linea.split(";");
        if (p.length != 3) throw new IllegalArgumentException("Linea invalida: " + linea);
        return new Producto(Integer.parseInt(p[0].trim()), p[1].trim(), Double.parseDouble(p[2].trim()));
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Nombre: %s | Precio: S/ %.2f", id, nombre, precio);
    }
}
