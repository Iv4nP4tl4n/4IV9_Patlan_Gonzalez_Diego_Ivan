/**
 *
 * @author ivanp
 */
package modelo.Pruebas;

public class PruebaLogin {

    public static void main(String[] args) {

        modelo.EmpleadoDAO dao = new modelo.EmpleadoDAO();

        modelo.Empleado emp =
                dao.login("admin", "admin123");

        if (emp != null) {

            System.out.println(
                    "Bienvenido "
                    + emp.getNombre());

        } else {

            System.out.println(
                    "Usuario o contraseña incorrectos");
        }
    }
}
