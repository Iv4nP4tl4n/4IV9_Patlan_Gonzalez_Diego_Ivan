
/**
 *
 * @author ivanp
 */
package modelo;

public class PruebaEliminarProducto {

    public static void main(String[] args) {

        ProductoDAO dao = new ProductoDAO();

        if (dao.eliminarProducto(1)) {

            System.out.println("Producto eliminado");

        } else {

            System.out.println("Error al eliminar");
        }
    }
}
