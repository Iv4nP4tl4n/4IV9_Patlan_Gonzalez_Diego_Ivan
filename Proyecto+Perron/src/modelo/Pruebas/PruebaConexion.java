/**
 *
 * @author ivanp
 */
package modelo.Pruebas;

public class PruebaConexion {

    public static void main(String[] args) {
        try {
            if (modelo.ConexionBD.probarConexion()) {
                System.out.println("Conexion exitosa");
            } else {
                System.out.println("Fallo la conexión");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}