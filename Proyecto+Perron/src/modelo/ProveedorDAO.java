
/**
 *
 * @author ivanp
 */
package modelo;
import java.sql.*;
import java.util.*;

public class ProveedorDAO {

    public List<Proveedor> listarProveedores() {
        List<Proveedor> lista = new ArrayList<>();
        String sql = "SELECT p.*, t.nombre AS nombre_tipo FROM proveedor p LEFT JOIN tipo_producto_proveedor t ON p.id_tipo = t.id_tipo";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Proveedor p = new Proveedor();
                p.setIdProveedor(rs.getInt("id_proveedor"));
                p.setNombre(rs.getString("nombre"));
                p.setTelefono(rs.getString("telefono"));
                p.setDireccion(rs.getString("direccion"));
                p.setEstado(rs.getBoolean("estado"));
                p.setIdTipo(rs.getInt("id_tipo"));
                p.setNombreTipo(rs.getString("nombre_tipo"));
                lista.add(p);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }

    public boolean insertarProveedor(Proveedor p) {
        String sql = "INSERT INTO proveedor(nombre, telefono, direccion, estado, id_tipo) VALUES(?,?,?,?,?)";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getTelefono());
            ps.setString(3, p.getDireccion());
            ps.setBoolean(4, p.isEstado());
            ps.setInt(5, p.getIdTipo());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public boolean actualizarProveedor(Proveedor p) {
        String sql = "UPDATE proveedor SET nombre=?, telefono=?, direccion=?, estado=?, id_tipo=? WHERE id_proveedor=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getTelefono());
            ps.setString(3, p.getDireccion());
            ps.setBoolean(4, p.isEstado());
            ps.setInt(5, p.getIdTipo());
            ps.setInt(6, p.getIdProveedor());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public boolean eliminarProveedor(int id) {
        String sql = "DELETE FROM proveedor WHERE id_proveedor=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }
}