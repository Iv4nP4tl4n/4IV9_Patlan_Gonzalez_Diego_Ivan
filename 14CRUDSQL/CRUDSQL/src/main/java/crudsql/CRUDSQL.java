

package crudsql;

/**
 *
 * @author ivanp
 */

import Controlador.ProductoControlador;
import Modelo.ProductoDAO;
import Vista.ProductoVista;

public class CRUDSQL {

    public static void main(String[] args) {

        ProductoVista vista
                = new ProductoVista();

        ProductoDAO dao
                = new ProductoDAO();

        ProductoControlador controlador
                = new ProductoControlador(
                        dao,
                        vista);

        vista.setVisible(true);
    }
}