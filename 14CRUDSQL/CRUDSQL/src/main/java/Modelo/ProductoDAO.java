package Modelo;

/**
 *
 * @author ivanp
 */

import java.sql.*;

public class ProductoDAO {

    public void agregar(Producto prod)
            throws SQLException {

        String sql = "INSERT INTO producto "
                + "(id, nombre, precio, cantidad, categoria) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (
                Connection con = ConexionDB.getConexion();
                PreparedStatement ps
                = con.prepareStatement(sql)) {

            establecerParametrosComunes(ps, prod);

            ps.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private void establecerParametrosComunes(
            PreparedStatement ps,
            Producto prod) throws Exception {

        ps.setInt(1, prod.getId());
        ps.setString(2, prod.getNombre());
        ps.setDouble(3, prod.getPrecio());
        ps.setInt(4, prod.getCantidad());
        ps.setString(5, prod.getCategoria());
    }
}