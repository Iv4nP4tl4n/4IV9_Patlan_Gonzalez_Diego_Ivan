
package Modelo;

/**
 *
 * @author ivanp
 */
public class Producto {

    private int id;
    private String nombre;
    private double precio;
    private int cantidad;
    private String categoria;

    public Producto() {

        this.id = 0;
        this.nombre = "";
        this.precio = 0.0;
        this.cantidad = 0;
        this.categoria = "";
    }

    public Producto(int id, String nombre,
            double precio, int cantidad,
            String categoria) {

        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.categoria = categoria;
    }

    public Producto(int id, String nombre) {

        this.id = id;
        this.nombre = nombre;
        this.precio = 0.0;
        this.cantidad = 0;
        this.categoria = "Sin categoria";
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

        if (precio >= 0) {
            this.precio = precio;
        }
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {

        if (cantidad >= 0) {
            this.cantidad = cantidad;
        }
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String mostrarDetalle() {

        return String.format(
                "ID: %d | Nombre: %s | Precio: %.2f | Cantidad: %d | Categoria: %s",
                id, nombre, precio, cantidad, categoria);
    }

    public double calcularValorInventario() {
        return precio * cantidad;
    }

    public double calcularValorInventario(double porcentajeDescuento) {

        double total = precio * cantidad;
        double descuento = total * (porcentajeDescuento / 100);

        return total - descuento;
    }

    @Override
    public String toString() {
        return mostrarDetalle();
    }
}