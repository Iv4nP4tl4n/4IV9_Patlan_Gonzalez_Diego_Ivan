/**
 *
 * @author ivanp
 */
package modelo;

public class PruebaProducto {

    public static void main(String[] args) {

        System.out.println("ENTRO AL MAIN");

        try {

            ProductoDAO dao = new ProductoDAO();

            System.out.println("DAO creado");

            System.out.println("Productos encontrados: "
                    + dao.listarProductos().size());

            for (Producto p : dao.listarProductos()) {

                System.out.println(
                        p.getIdProducto() + " | "
                        + p.getNombre() + " | "
                        + p.getPrecioVenta()
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}