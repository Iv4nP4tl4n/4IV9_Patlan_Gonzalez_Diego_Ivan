/**
 *
 * @author ivanp
 */
package Modelo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
 * ============================================================================
 * CAPA: MODELO — RompecabezasDAO (Data Access Object)
 * ============================================================================
 * Su ÚNICA responsabilidad es comunicarse con la base de datos rompecabeza.
 * Lanza excepciones en lugar de mostrar mensajes — el Controlador decide
 * qué hacer con los errores.
 *
 * FLUJO MVC:
 *   Vista (botón click) → Controlador → DAO (ejecuta SQL) → Controlador → Vista
 * ============================================================================
 */
public class RompecabezasDAO {

    // CREATE — Agregar rompecabezas
    public void agregar(RompecabezasModelo r) throws SQLException, Exception {
        String sql = "INSERT INTO rompecabezas (nombre, piezas, dificultad, estado, id_categoria) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, r.getNombre());
            ps.setInt(2, r.getPiezas());
            ps.setString(3, r.getDificultad());
            ps.setString(4, r.getEstado());
            ps.setInt(5, r.getIdCategoria());
            ps.executeUpdate();
        }
    }

    // READ — Buscar por ID (con JOIN para obtener nombre de categoría)
    public RompecabezasModelo buscarPorId(int id) throws SQLException, Exception {
        String sql = "SELECT r.*, c.nombre_categoria FROM rompecabezas r " +
                     "INNER JOIN categorias c ON r.id_categoria = c.id_categoria " +
                     "WHERE r.id_rompecabezas = ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return construirDesdeResultSet(rs);
                }
            }
        }
        return null;
    }

    // READ — Listar todos (con JOIN para nombre de categoría)
    public List<RompecabezasModelo> listarTodos() throws SQLException, Exception {
        List<RompecabezasModelo> lista = new ArrayList<>();
        String sql = "SELECT r.*, c.nombre_categoria FROM rompecabezas r " +
                     "INNER JOIN categorias c ON r.id_categoria = c.id_categoria";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(construirDesdeResultSet(rs));
            }
        }
        return lista;
    }

    // UPDATE — Actualizar rompecabezas existente
    public void actualizar(RompecabezasModelo r) throws SQLException, Exception {
        String sql = "UPDATE rompecabezas SET nombre = ?, piezas = ?, dificultad = ?, " +
                     "estado = ?, id_categoria = ? WHERE id_rompecabezas = ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, r.getNombre());
            ps.setInt(2, r.getPiezas());
            ps.setString(3, r.getDificultad());
            ps.setString(4, r.getEstado());
            ps.setInt(5, r.getIdCategoria());
            ps.setInt(6, r.getIdRompecabezas());
            ps.executeUpdate();
        }
    }

    // DELETE — Eliminar por ID
    public void eliminar(int id) throws SQLException, Exception {
        // Primero eliminar avances relacionados (integridad referencial)
        String sqlAvance = "DELETE FROM avance WHERE id_rompecabezas = ?";
        String sqlRomp   = "DELETE FROM rompecabezas WHERE id_rompecabezas = ?";

        try (Connection conn = ConexionBD.getConexion()) {
            try (PreparedStatement ps = conn.prepareStatement(sqlAvance)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
            try (PreparedStatement ps = conn.prepareStatement(sqlRomp)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
        }
    }

    // READ — Listar todas las categorías (para el ComboBox)
    public List<Categorias> listarCategorias() throws SQLException, Exception {
        List<Categorias> lista = new ArrayList<>();
        String sql = "SELECT * FROM categorias";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Categorias(
                    rs.getInt("id_categoria"),
                    rs.getString("nombre_categoria")
                ));
            }
        }
        return lista;
    }

    // Método auxiliar: construye un objeto desde el ResultSet
    private RompecabezasModelo construirDesdeResultSet(ResultSet rs) throws SQLException {
        RompecabezasModelo r = new RompecabezasModelo(
            rs.getInt("id_rompecabezas"),
            rs.getString("nombre"),
            rs.getInt("piezas"),
            rs.getString("dificultad"),
            rs.getString("estado"),
            rs.getInt("id_categoria")
        );
        r.setNombreCategoria(rs.getString("nombre_categoria"));
        return r;
    }
}