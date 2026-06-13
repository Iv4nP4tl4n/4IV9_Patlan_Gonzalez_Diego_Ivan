
/**
 *
 * @author ivanp
 */
package modelo;
import java.sql.*;
import java.util.*;

public class TicketDAO {

    public List<Ticket> listarTickets() {
        List<Ticket> lista = new ArrayList<>();
        String sql = """
            SELECT t.*, e.nombre AS nombre_empleado, m.nombre AS nombre_metodo
            FROM ticket t
            LEFT JOIN empleado e ON t.id_empleado = e.id_empleado
            LEFT JOIN metodo_pago m ON t.id_metodo = m.id_metodo
        """;
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Ticket t = new Ticket();
                t.setIdTicket(rs.getInt("id_ticket"));
                t.setNumeroTicket(rs.getString("numero_ticket"));
                t.setFechaHora(rs.getTimestamp("fecha_hora"));
                t.setSubtotal(rs.getDouble("subtotal"));
                t.setIva(rs.getDouble("iva"));
                t.setTotal(rs.getDouble("total"));
                t.setIdVenta(rs.getInt("id_venta"));
                t.setIdEmpleado(rs.getInt("id_empleado"));
                t.setIdMetodo(rs.getInt("id_metodo"));
                t.setNombreEmpleado(rs.getString("nombre_empleado"));
                t.setNombreMetodo(rs.getString("nombre_metodo"));
                lista.add(t);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }

    public boolean insertarTicket(Ticket t) {
        String sql = "INSERT INTO ticket(numero_ticket, fecha_hora, subtotal, iva, total, id_venta, id_empleado, id_metodo) VALUES(?,?,?,?,?,?,?,?)";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getNumeroTicket());
            ps.setTimestamp(2, t.getFechaHora());
            ps.setDouble(3, t.getSubtotal());
            ps.setDouble(4, t.getIva());
            ps.setDouble(5, t.getTotal());
            ps.setInt(6, t.getIdVenta());
            ps.setInt(7, t.getIdEmpleado());
            ps.setInt(8, t.getIdMetodo());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }
}