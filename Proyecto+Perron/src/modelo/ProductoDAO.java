/**
 *
 * @author ivanp
 */
package modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

   public List<Producto> listarProductos() {

    List<Producto> lista = new ArrayList<>();

        try {

            Connection con = ConexionBD.getConexion();

            String sql = "SELECT * FROM producto";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Producto p = new Producto();

                p.setIdProducto(rs.getInt("id_producto"));
                p.setNombre(rs.getString("nombre"));
                p.setPrecioVenta(rs.getDouble("precio_venta"));
                p.setCosto(rs.getDouble("costo"));
                p.setStock(rs.getInt("stock"));
                p.setStockMinimo(rs.getInt("stock_minimo"));
                p.setCodigoBarras(rs.getString("codigo_barras"));
                p.setEstado(rs.getBoolean("estado"));
                p.setIdCategoria(rs.getInt("id_categoria"));
                p.setIdTipoMascota(rs.getInt("id_tipo_mascota"));

                lista.add(p);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
   
   public boolean insertarProducto(Producto p) {

        try {

            Connection con = ConexionBD.getConexion();

            String sql = "INSERT INTO producto "
                    + "(nombre, precio_venta, costo, stock, stock_minimo, "
                    + "codigo_barras, estado, id_categoria, id_tipo_mascota) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, p.getNombre());
            ps.setDouble(2, p.getPrecioVenta());
            ps.setDouble(3, p.getCosto());
            ps.setInt(4, p.getStock());
            ps.setInt(5, p.getStockMinimo());
            ps.setString(6, p.getCodigoBarras());
            ps.setBoolean(7, p.isEstado());
            ps.setInt(8, p.getIdCategoria());
            ps.setInt(9, p.getIdTipoMascota());

            int filas = ps.executeUpdate();

            ps.close();
            con.close();

            return filas > 0;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }
   
   public Producto buscarProducto(int id) {

        Producto p = null;

        try {

            Connection con = ConexionBD.getConexion();

            String sql =
                    "SELECT * FROM producto "
                    + "WHERE id_producto = ?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                p = new Producto();

                p.setIdProducto(rs.getInt("id_producto"));
                p.setNombre(rs.getString("nombre"));
                p.setPrecioVenta(rs.getDouble("precio_venta"));
                p.setCosto(rs.getDouble("costo"));
                p.setStock(rs.getInt("stock"));
                p.setStockMinimo(rs.getInt("stock_minimo"));
                p.setCodigoBarras(rs.getString("codigo_barras"));
                p.setEstado(rs.getBoolean("estado"));
                p.setIdCategoria(rs.getInt("id_categoria"));
                p.setIdTipoMascota(rs.getInt("id_tipo_mascota"));
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return p;
    }
   
   public boolean actualizarProducto(Producto p) {

        try {

            Connection con = ConexionBD.getConexion();

            String sql =
                    "UPDATE producto SET "
                    + "nombre = ?, "
                    + "precio_venta = ?, "
                    + "costo = ?, "
                    + "stock = ?, "
                    + "stock_minimo = ?, "
                    + "codigo_barras = ?, "
                    + "estado = ?, "
                    + "id_categoria = ?, "
                    + "id_tipo_mascota = ? "
                    + "WHERE id_producto = ?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, p.getNombre());
            ps.setDouble(2, p.getPrecioVenta());
            ps.setDouble(3, p.getCosto());
            ps.setInt(4, p.getStock());
            ps.setInt(5, p.getStockMinimo());
            ps.setString(6, p.getCodigoBarras());
            ps.setBoolean(7, p.isEstado());
            ps.setInt(8, p.getIdCategoria());
            ps.setInt(9, p.getIdTipoMascota());
            ps.setInt(10, p.getIdProducto());

            int filas = ps.executeUpdate();

            ps.close();
            con.close();

            return filas > 0;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }
   
   public boolean eliminarProducto(int id) {

        try {

            Connection con = ConexionBD.getConexion();

            String sql =
                    "DELETE FROM producto "
                    + "WHERE id_producto = ?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, id);

            int filas = ps.executeUpdate();

            ps.close();
            con.close();

            return filas > 0;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }
}