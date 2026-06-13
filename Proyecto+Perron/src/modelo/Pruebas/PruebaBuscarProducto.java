/**
 *
 * @author ivanp
 */
package modelo;

public class PruebaBuscarProducto {

    public static void main(String[] args) {

        ProductoDAO dao = new ProductoDAO();

        Producto p = dao.buscarProducto(1);

        if (p != null) {

            System.out.println("ID: " + p.getIdProducto());
            System.out.println("Nombre: " + p.getNombre());
            System.out.println("Precio: " + p.getPrecioVenta());

        } else {

            System.out.println("Producto no encontrado");
        }
    }
}