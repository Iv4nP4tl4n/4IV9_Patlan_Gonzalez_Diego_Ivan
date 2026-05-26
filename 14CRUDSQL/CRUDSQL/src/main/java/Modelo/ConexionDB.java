package Modelo;

/**
 *
 * @author ivanp
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    private static final String URL
            = "jdbc:mysql://localhost:3306/productos_sql";

    private static final String USUARIO = "root";
    private static final String PASSWORD = "n0m3l0";

    public static Connection getConexion() throws Exception {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException e) {

            throw new SQLException("Driver no encontrado");
        }

        return DriverManager.getConnection(
                URL,
                USUARIO,
                PASSWORD);
    }

    public static boolean probarConexion() throws Exception {

        try {

            Connection con = getConexion();

            System.out.println("Si se conecto");

            return con != null && !con.isClosed();

        } catch (SQLException e) {

            System.out.println(
                    "Error de conexion: "
                    + e.getMessage());

            return false;
        }
    }
}