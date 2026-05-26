
package Vista;

/**
 *
 * @author ivanp
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ProductoVista extends JFrame {

    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;

    private JTextField txtId,
            txtNombre,
            txtPrecio,
            txtCantidad,
            txtCategoria;

    private JComboBox<String> cmbTipoProducto;

    private JButton btnAgregar,
            btnActualizar,
            btnEliminar,
            btnBuscar,
            btnLimpiar;

    public ProductoVista() {

        setTitle("CRUD de Productos");
        setSize(950, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        inicializarComponentes();
    }

    private void inicializarComponentes() {

        setLayout(new BorderLayout(10, 10));

        JPanel panelTitulo = new JPanel();

        panelTitulo.setBackground(
                new Color(39, 174, 96));

        JLabel lblTitulo = new JLabel(
                "Sistema de Gestión de Productos");

        lblTitulo.setFont(
                new Font("Arial",
                        Font.BOLD,
                        20));

        lblTitulo.setForeground(Color.WHITE);

        panelTitulo.add(lblTitulo);

        add(panelTitulo, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(
                new String[]{
                    "ID",
                    "Nombre",
                    "Precio",
                    "Cantidad",
                    "Categoria"
                }, 0) {

            @Override
            public boolean isCellEditable(
                    int row,
                    int column) {

                return false;
            }
        };

        tablaProductos = new JTable(modeloTabla);

        JScrollPane scroll
                = new JScrollPane(tablaProductos);

        add(scroll, BorderLayout.CENTER);

        JPanel panelFormulario
                = new JPanel(
                        new GridLayout(6, 2, 10, 10));

        panelFormulario.setBorder(
                BorderFactory.createTitledBorder(
                        "Datos del Producto"));

        txtId = new JTextField();
        txtNombre = new JTextField();
        txtPrecio = new JTextField();
        txtCantidad = new JTextField();
        txtCategoria = new JTextField();

        cmbTipoProducto = new JComboBox<>(
                new String[]{
                    "General",
                    "Electronico",
                    "Alimento"
                });

        panelFormulario.add(new JLabel("ID"));
        panelFormulario.add(txtId);

        panelFormulario.add(new JLabel("Nombre"));
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Precio"));
        panelFormulario.add(txtPrecio);

        panelFormulario.add(new JLabel("Cantidad"));
        panelFormulario.add(txtCantidad);

        panelFormulario.add(new JLabel("Categoria"));
        panelFormulario.add(txtCategoria);

        panelFormulario.add(new JLabel("Tipo"));
        panelFormulario.add(cmbTipoProducto);

        JPanel panelBotones = new JPanel();

        btnAgregar = new JButton("Agregar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnBuscar = new JButton("Buscar");
        btnLimpiar = new JButton("Limpiar");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnLimpiar);

        JPanel panelInferior
                = new JPanel(new BorderLayout());

        panelInferior.add(
                panelFormulario,
                BorderLayout.CENTER);

        panelInferior.add(
                panelBotones,
                BorderLayout.SOUTH);

        add(panelInferior, BorderLayout.SOUTH);
    }

    public JTextField getTxtId() {
        return txtId;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    public JTextField getTxtCantidad() {
        return txtCantidad;
    }

    public JTextField getTxtCategoria() {
        return txtCategoria;
    }

    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }

    public JButton getBtnAgregar() {
        return btnAgregar;
    }
}