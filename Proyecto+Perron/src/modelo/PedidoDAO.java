
/**
 *
 * @author ivanp
 */
package modelo;
import java.sql.*;
import java.util.*;

public class PedidoDAO {

    public List<Pedido> listarPedidos() {
        List<Pedido> lista = new ArrayList<>();
        String sql = """
            SELECT p.*, pr.nombre AS nombre_proveedor,
            ep.nombre AS nombre_estado, e.nombre AS nombre_empleado
            FROM pedido p
            LEFT JOIN proveedor pr ON p.id_proveedor = pr.id_proveedor
            LEFT JOIN estado_pedido ep ON p.id_estado = ep.id_estado
            LEFT JOIN empleado e ON p.id_empleado = e.id_empleado
        """;
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Pedido p = new Pedido();
                p.setIdPedido(rs.getInt("id_pedido"));
                p.setFecha(rs.getDate("fecha"));
                p.setTotalPedido(rs.getDouble("total_pedido"));
                p.setIdProveedor(rs.getInt("id_proveedor"));
                p.setIdEmpleado(rs.getInt("id_empleado"));
                p.setIdEstado(rs.getInt("id_estado"));
                p.setNombreProveedor(rs.getString("nombre_proveedor"));
                p.setNombreEstado(rs.getString("nombre_estado"));
                p.setNombreEmpleado(rs.getString("nombre_empleado"));
                lista.add(p);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }

    public boolean insertarPedido(Pedido p) {
        String sql = "INSERT INTO pedido(fecha, total_pedido, id_proveedor, id_empleado, id_estado) VALUES(?,?,?,?,?)";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, p.getFecha());
            ps.setDouble(2, p.getTotalPedido());
            ps.setInt(3, p.getIdProveedor());
            ps.setInt(4, p.getIdEmpleado());
            ps.setInt(5, p.getIdEstado());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public boolean actualizarPedido(Pedido p) {
        String sql = "UPDATE pedido SET fecha=?, total_pedido=?, id_proveedor=?, id_empleado=?, id_estado=? WHERE id_pedido=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, p.getFecha());
            ps.setDouble(2, p.getTotalPedido());
            ps.setInt(3, p.getIdProveedor());
            ps.setInt(4, p.getIdEmpleado());
            ps.setInt(5, p.getIdEstado());
            ps.setInt(6, p.getIdPedido());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public boolean eliminarPedido(int id) {
        String sql = "DELETE FROM pedido WHERE id_pedido=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }
}
