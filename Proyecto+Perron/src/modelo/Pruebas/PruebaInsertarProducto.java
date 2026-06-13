/**
 *
 * @author ivanp
 */
package modelo;

public class PruebaInsertarProducto {

    public static void main(String[] args) {

        Producto p = new Producto();

        p.setNombre("Shampoo Canino");
        p.setPrecioVenta(120);
        p.setCosto(80);
        p.setStock(10);
        p.setStockMinimo(2);
        p.setCodigoBarras("750100000099");
        p.setEstado(true);
        p.setIdCategoria(5);
        p.setIdTipoMascota(1);

        ProductoDAO dao = new ProductoDAO();

        if (dao.insertarProducto(p)) {
            System.out.println("Producto insertado");
        } else {
            System.out.println("Error al insertar");
        }
    }
}