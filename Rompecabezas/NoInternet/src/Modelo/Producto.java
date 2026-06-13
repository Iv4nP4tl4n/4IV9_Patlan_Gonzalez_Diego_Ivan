package Modelo;

/**
 * Modelo de un rompecabezas con datos de la base rompecabeza.
 */
public class Producto {
    private int id;
    private String nombre;
    private double precio; // repuesto: piezas
    private int cantidad; // repuesto: avance percent
    private String categoria;
    private String dificultad;
    private String estado;

    public Producto() {
        this.id = 0;
        this.nombre = "";
        this.precio = 0.0;
        this.cantidad = 0;
        this.categoria = "";
        this.dificultad = "";
        this.estado = "";
    }

    public Producto(int id, String nombre) {
        this();
        this.id = id;
        this.nombre = nombre;
    }

    public Producto(int id, String nombre, int piezas, String dificultad, String estado, String categoria, int avance) {
        this.id = id;
        this.nombre = nombre;
        this.precio = piezas;
        this.cantidad = avance;
        this.categoria = categoria;
        this.dificultad = dificultad;
        this.estado = estado;
    }

    public Producto(int id, String nombre, double precio, int cantidad, String categoria) {
        this(id, nombre, (int) precio, "", "", categoria, cantidad);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        if (precio >= 0) this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        if (cantidad >= 0) this.cantidad = cantidad;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getPiezas() {
        return (int) precio;
    }

    public void setPiezas(int piezas) {
        this.precio = piezas;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getAvance() {
        return cantidad;
    }

    public void setAvance(int avance) {
        this.cantidad = avance;
    }

    public String mostrarDetalle() {
        return String.format("ID: %d | Nombre: %s | Piezas: %d | Dificultad: %s | Estado: %s | Categoría: %s | Avance: %d%%",
                id, nombre, getPiezas(), dificultad, estado, categoria, getAvance());
    }

    @Override
    public String toString() {
        return mostrarDetalle();
    }
}
