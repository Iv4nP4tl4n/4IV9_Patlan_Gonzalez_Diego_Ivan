
/**
 *
 * @author ivanp
 */
package modelo;

public class PruebaActualizarProducto {

    public static void main(String[] args) {

        ProductoDAO dao = new ProductoDAO();

        Producto p = dao.buscarProducto(1);

        if (p != null) {

            p.setNombre("Croquetas Pro Plan Premium");
            p.setPrecioVenta(700);

            if (dao.actualizarProducto(p)) {

                System.out.println("Producto actualizado");

            } else {

                System.out.println("Error al actualizar");
            }

        } else {

            System.out.println("Producto no encontrado");
        }
    }
}
