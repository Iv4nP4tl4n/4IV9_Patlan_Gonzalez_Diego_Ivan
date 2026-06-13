package modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {

    public Empleado login(String usuario, String contrasena) {
        Empleado emp = null;
        
        String sql = "SELECT e.id_empleado, e.nombre, e.telefono, e.usuario, e.contraseña, e.estado, e.id_rol, r.nombre AS nombre_rol " +
                     "FROM empleado e " +
                     "INNER JOIN rol r ON e.id_rol = r.id_rol " +
                     "WHERE e.usuario = ? AND e.estado = 1";
        
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (con == null) {
                return null;
            }

            ps.setString(1, usuario);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String passGuardada = rs.getString("contraseña");
                if (passGuardada != null && passGuardada.equals(contrasena)) {
                    emp = new Empleado();
                    emp.setIdEmpleado(rs.getInt("id_empleado"));
                    emp.setNombre(rs.getString("nombre"));
                    emp.setTelefono(rs.getString("telefono"));
                    emp.setUsuario(rs.getString("usuario"));
                    emp.setContraseña(passGuardada);
                    emp.setEstado(rs.getBoolean("estado"));
                    emp.setIdRol(rs.getInt("id_rol"));
                    emp.setNombreRol(rs.getString("nombre_rol"));
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return emp;
    }

    public List<Empleado> listar() {
        List<Empleado> lista = new ArrayList<>();
        String sql = "SELECT e.*, r.nombre AS nombre_rol FROM empleado e INNER JOIN rol r ON e.id_rol = r.id_rol";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Empleado emp = new Empleado();
                emp.setIdEmpleado(rs.getInt("id_empleado"));
                emp.setNombre(rs.getString("nombre"));
                emp.setTelefono(rs.getString("telefono"));
                emp.setUsuario(rs.getString("usuario"));
                emp.setContraseña(rs.getString("contraseña"));
                emp.setEstado(rs.getBoolean("estado"));
                emp.setIdRol(rs.getInt("id_rol"));
                emp.setNombreRol(rs.getString("nombre_rol"));
                lista.add(emp);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return lista;
    }

    public boolean agregar(Empleado emp) {
        boolean guardado = false;
        String sql = "INSERT INTO empleado (nombre, telefono, usuario, contraseña, estado, id_rol) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, emp.getNombre());
            ps.setString(2, emp.getTelefono());
            ps.setString(3, emp.getUsuario());
            ps.setString(4, emp.getContraseña());
            ps.setBoolean(5, emp.isEstado());
            ps.setInt(6, emp.getIdRol());

            if (ps.executeUpdate() > 0) {
                guardado = true;
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return guardado;
    }

    public boolean modificar(Empleado emp) {
        boolean modificado = false;
        String sql = "UPDATE empleado SET nombre=?, telefono=?, usuario=?, contraseña=?, estado=?, id_rol=? WHERE id_empleado=?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, emp.getNombre());
            ps.setString(2, emp.getTelefono());
            ps.setString(3, emp.getUsuario());
            ps.setString(4, emp.getContraseña());
            ps.setBoolean(5, emp.isEstado());
            ps.setInt(6, emp.getIdRol());
            ps.setInt(7, emp.getIdEmpleado());

            if (ps.executeUpdate() > 0) {
                modificado = true;
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return modificado;
    }

    public boolean eliminar(int id) {
        boolean eliminado = false;
        String sql = "UPDATE empleado SET estado = 0 WHERE id_empleado = ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            if (ps.executeUpdate() > 0) {
                eliminado = true;
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return eliminado;
    }

    public Empleado buscarPorId(int id) {
        Empleado emp = null;
        String sql = "SELECT e.*, r.nombre AS nombre_rol FROM empleado e INNER JOIN rol r ON e.id_rol = r.id_rol WHERE e.id_empleado = ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                emp = new Empleado();
                emp.setIdEmpleado(rs.getInt("id_empleado"));
                emp.setNombre(rs.getString("nombre"));
                emp.setTelefono(rs.getString("telefono"));
                emp.setUsuario(rs.getString("usuario"));
                emp.setContraseña(rs.getString("contraseña"));
                emp.setEstado(rs.getBoolean("estado"));
                emp.setIdRol(rs.getInt("id_rol"));
                emp.setNombreRol(rs.getString("nombre_rol"));
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return emp;
    }
}