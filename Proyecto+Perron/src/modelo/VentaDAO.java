
/**
 *
 * @author ivanp
 */
package modelo;
import java.sql.*;
import java.util.*;

public class VentaDAO {

    public List<Venta> listarVentas() {
        List<Venta> lista = new ArrayList<>();
        String sql = """
            SELECT v.*, e.nombre AS nombre_empleado, m.nombre AS nombre_metodo
            FROM venta v
            LEFT JOIN empleado e ON v.id_empleado = e.id_empleado
            LEFT JOIN metodo_pago m ON v.id_metodo = m.id_metodo
        """;
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Venta v = new Venta();
                v.setIdVenta(rs.getInt("id_venta"));
                v.setFecha(rs.getDate("fecha"));
                v.setHora(rs.getTime("hora"));
                v.setSubtotal(rs.getDouble("subtotal"));
                v.setIva(rs.getDouble("iva"));
                v.setTotal(rs.getDouble("total"));
                v.setObservaciones(rs.getString("observaciones"));
                v.setEstado(rs.getBoolean("estado"));
                v.setIdEmpleado(rs.getInt("id_empleado"));
                v.setIdMetodo(rs.getInt("id_metodo"));
                v.setNombreEmpleado(rs.getString("nombre_empleado"));
                v.setNombreMetodo(rs.getString("nombre_metodo"));
                lista.add(v);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }

    public int insertarVenta(Venta v) {
        String sql = "INSERT INTO venta(fecha, hora, subtotal, iva, total, observaciones, estado, id_empleado, id_metodo) VALUES(?,?,?,?,?,?,?,?,?)";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setDate(1, v.getFecha());
            ps.setTime(2, v.getHora());
            ps.setDouble(3, v.getSubtotal());
            ps.setDouble(4, v.getIva());
            ps.setDouble(5, v.getTotal());
            ps.setString(6, v.getObservaciones());
            ps.setBoolean(7, v.isEstado());
            ps.setInt(8, v.getIdEmpleado());
            ps.setInt(9, v.getIdMetodo());
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) return keys.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return -1;
    }

    public boolean insertarDetalleVenta(int idVenta, int idProducto, int cantidad, double precio) {
        String sql = "INSERT INTO detalle_venta(id_venta, id_producto, cantidad, precio_unitario, importe) VALUES(?,?,?,?,?)";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idVenta);
            ps.setInt(2, idProducto);
            ps.setInt(3, cantidad);
            ps.setDouble(4, precio);
            ps.setDouble(5, precio * cantidad);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public boolean cancelarVenta(int idVenta) {
        String sql = "UPDATE venta SET estado=FALSE WHERE id_venta=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idVenta);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }
}
