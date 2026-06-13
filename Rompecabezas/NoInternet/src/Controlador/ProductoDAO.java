package Controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Modelo.ConexionBD;
import Modelo.Producto;

public class ProductoDAO {

    public List<Producto> listarTodos() throws SQLException, Exception {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT r.id_rompecabezas, r.nombre, r.piezas, r.dificultad, r.estado, "
                + "c.nombre_categoria, COALESCE(a.porcentaje, 0) AS avance "
                + "FROM rompecabezas r "
                + "LEFT JOIN categorias c ON r.id_categoria = c.id_categoria "
                + "LEFT JOIN ("
                + "  SELECT av.id_rompecabezas, av.porcentaje "
                + "  FROM avance av "
                + "  INNER JOIN (SELECT id_rompecabezas, MAX(fecha) AS maxfecha FROM avance GROUP BY id_rompecabezas) ult "
                + "  ON av.id_rompecabezas = ult.id_rompecabezas AND av.fecha = ult.maxfecha"
                + ") a ON a.id_rompecabezas = r.id_rompecabezas";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                productos.add(construirProductoDesdeResultSet(rs));
            }
        }
        return productos;
    }

    public Producto buscarPorId(int id) throws SQLException, Exception {
        String sql = "SELECT r.id_rompecabezas, r.nombre, r.piezas, r.dificultad, r.estado, "
                + "c.nombre_categoria, COALESCE(a.porcentaje, 0) AS avance "
                + "FROM rompecabezas r "
                + "LEFT JOIN categorias c ON r.id_categoria = c.id_categoria "
                + "LEFT JOIN ("
                + "  SELECT av.id_rompecabezas, av.porcentaje "
                + "  FROM avance av "
                + "  INNER JOIN (SELECT id_rompecabezas, MAX(fecha) AS maxfecha FROM avance GROUP BY id_rompecabezas) ult "
                + "  ON av.id_rompecabezas = ult.id_rompecabezas AND av.fecha = ult.maxfecha"
                + ") a ON a.id_rompecabezas = r.id_rompecabezas "
                + "WHERE r.id_rompecabezas = ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return construirProductoDesdeResultSet(rs);
                }
            }
        }
        return null;
    }

    private Producto construirProductoDesdeResultSet(ResultSet rs) throws SQLException {
        return new Producto(
                rs.getInt("id_rompecabezas"),
                rs.getString("nombre"),
                rs.getInt("piezas"),
                rs.getString("dificultad"),
                rs.getString("estado"),
                rs.getString("nombre_categoria"),
                rs.getInt("avance")
        );
    }

    // CRUD stubs para mantener compatibilidad con el controlador.
    // Estas operaciones no están soportadas en la versión "NoInternet" (solo lectura).
    public void agregar(Producto p) throws SQLException {
        throw new SQLException("Operación no soportada: solo lectura para rompecabezas");
    }

    public void actualizar(Producto p) throws SQLException {
        throw new SQLException("Operación no soportada: solo lectura para rompecabezas");
    }

    public void eliminar(int id) throws SQLException {
        throw new SQLException("Operación no soportada: solo lectura para rompecabezas");
    }
}
