package Controlador;

/**
 *
 * @author ivanp
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import Modelo.ConexionDB;
import Modelo.Producto;
import Modelo.ProductoDAO;
import Vista.ProductoVista;

public class ProductoControlador {

    private ProductoDAO dao;
    private ProductoVista vista;

    public ProductoControlador(
            ProductoDAO dao,
            ProductoVista vista) {

        this.dao = dao;
        this.vista = vista;

        inicializarEventos();
        cargarTabla();
    }

    private void inicializarEventos() {

        vista.getBtnAgregar()
                .addActionListener(e -> agregar());
    }

    private void agregar() {

        try {

            Producto producto
                    = construirProductoFormulario();

            if (producto == null) {
                return;
            }

            dao.agregar(producto);

            JOptionPane.showMessageDialog(
                    vista,
                    "Producto agregado correctamente");

            cargarTabla();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    vista,
                    "Error: " + e.getMessage());
        }
    }

    private Producto construirProductoFormulario() {

        try {

            int id = Integer.parseInt(
                    vista.getTxtId().getText());

            String nombre =
                    vista.getTxtNombre().getText();

            double precio = Double.parseDouble(
                    vista.getTxtPrecio().getText());

            int cantidad = Integer.parseInt(
                    vista.getTxtCantidad().getText());

            String categoria =
                    vista.getTxtCategoria().getText();

            return new Producto(
                    id,
                    nombre,
                    precio,
                    cantidad,
                    categoria);

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    vista,
                    "Datos invalidos");

            return null;
        }
    }

    private void cargarTabla() {

        DefaultTableModel modelo
                = vista.getModeloTabla();

        modelo.setRowCount(0);

        try {

            Connection con
                    = ConexionDB.getConexion();

            Statement st
                    = con.createStatement();

            ResultSet rs
                    = st.executeQuery(
                            "SELECT * FROM producto");

            while (rs.next()) {

                modelo.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getDouble("precio"),
                    rs.getInt("cantidad"),
                    rs.getString("categoria")
                });
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    vista,
                    "Error al cargar tabla");
        }
    }
}
