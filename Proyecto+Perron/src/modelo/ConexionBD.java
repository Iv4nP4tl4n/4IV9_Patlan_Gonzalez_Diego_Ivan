/**
 *
 * @author ivanp
 */
package modelo;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/proyecto_perron?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String CONTRASEÑA = "Iv4nP4tl4n";

    public static Connection getConexion() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return con;
    }

   
    public static boolean probarConexion() {
        Connection con = getConexion();
        if (con != null) {
            try {
                con.close();
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }
}