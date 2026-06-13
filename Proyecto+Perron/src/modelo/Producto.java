/**
 *
 * @author ivanp
 */
package modelo;

public class Producto {

    private int idProducto;
    private String nombre;
    private double precioVenta;
    private double costo;
    private int stock;
    private int stockMinimo;
    private String codigoBarras;
    private boolean estado;
    private int idCategoria;
    private int idTipoMascota;

    public Producto() {
    }

    public Producto(int idProducto, String nombre,
            double precioVenta, double costo,
            int stock, int stockMinimo,
            String codigoBarras, boolean estado,
            int idCategoria, int idTipoMascota) {

        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precioVenta = precioVenta;
        this.costo = costo;
        this.stock = stock;
        this.stockMinimo = stockMinimo;
        this.codigoBarras = codigoBarras;
        this.estado = estado;
        this.idCategoria = idCategoria;
        this.idTipoMascota = idTipoMascota;
    }

    // Getters 
    public int getIdProducto() {
        return idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public double getCosto() {
        return costo;
    }

    public int getStock() {
        return stock;
    }

    public int getStockMinimo() {
        return stockMinimo;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public boolean isEstado() {
        return estado;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public int getIdTipoMascota() {
        return idTipoMascota;
    }
    
    //Setters
    
    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public void setIdTipoMascota(int idTipoMascota) {
        this.idTipoMascota = idTipoMascota;
    }

    // --- NUEVOS MÉTODOS RECOMENDADOS ---

    // Calcular ganancia unitaria
    public double calcularGanancia() {
        return this.precioVenta - this.costo;
    }

    // Verificar si hay bajo stock
    public boolean tieneBajoStock() {
        return this.stock <= this.stockMinimo;
    }

    // Representación en texto del producto
    @Override
    public String toString() {
        return "Producto{" +
                "idProducto=" + idProducto +
                ", nombre='" + nombre + '\'' +
                ", precioVenta=" + precioVenta +
                ", costo=" + costo +
                ", stock=" + stock +
                ", stockMinimo=" + stockMinimo +
                ", codigoBarras='" + codigoBarras + '\'' +
                ", estado=" + estado +
                ", idCategoria=" + idCategoria +
                ", idTipoMascota=" + idTipoMascota +
                '}';
    }

    // Comparar productos por su ID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return idProducto == producto.idProducto;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(idProducto);
    }
}