/**
 *
 * @author ivanp
 */
package vista;

import modelo.ConexionBD;
import modelo.Empleado;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AgregarPedido extends JFrame {

    Color turquesa       = new Color(0, 190, 185);
    Color turquesaClaro  = new Color(188, 237, 234);
    Color turquesaOscuro = new Color(0, 160, 155);
    Color blanco         = Color.WHITE;
    Color grisFondo      = new Color(245, 245, 245);
    Color grisClaro      = new Color(230, 230, 230);
    Color textoGris      = new Color(80, 80, 80);
    Color rojoBoton      = new Color(200, 30, 30);

    private Empleado empleado;
    private JFrame ventanaAnterior;
    private JComboBox<String> cbProveedor, cbProducto;
    private JTextField txtCantidad, txtCosto, txtTotal;
    private DefaultTableModel modeloDetalles;
    private double totalGeneral = 0.0;
    private JLabel lblTotalGeneral;

    public AgregarPedido(Empleado empleado, JFrame ventanaAnterior) {
        this.empleado = empleado;
        this.ventanaAnterior = ventanaAnterior;

        setTitle("Agregar Pedido");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ventanaAnterior.setVisible(true);
            }
        });

        JPanel fondo = new JPanel(new BorderLayout());
        fondo.setBackground(grisFondo);
        fondo.add(crearSidebar(), BorderLayout.WEST);
        fondo.add(crearContenido(), BorderLayout.CENTER);

        add(fondo);
        setVisible(true);
    }

    private JPanel crearSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(turquesa);
        sidebar.setPreferredSize(new Dimension(200, 600));

        String[][] items = {
            {"home.png",             "INICIO"},
            {"shopping-cart.png",    "VENTAS"},
            {"cube.png",             "PRODUCTOS"},
            {"users-alt.png",        "PROVEEDORES"},
            {"truck-side.png",       "PEDIDOS"},
            {"book-alt.png",         "REPORTES"},
            {"users.png",            "USUARIOS"},
            {"search-alt.png",       "TICKETS"},
            {"sign-out-alt.png",     "REGRESAR"}
        };

        for (String[] item : items) {
            String nombreImagen = item[0];
            String texto = item[1];
            boolean esActivo = texto.equals("PEDIDOS");

            JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 12));
            fila.setMaximumSize(new Dimension(200, 55));
            fila.setCursor(new Cursor(Cursor.HAND_CURSOR));
            fila.setBackground(esActivo ? turquesaOscuro : turquesa);
            fila.setOpaque(true);

            try {
                java.net.URL urlIcono = getClass().getClassLoader().getResource("vista/iconos/" + nombreImagen);
                JLabel icono = new JLabel(new ImageIcon(new ImageIcon(urlIcono).getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
                fila.add(icono);
            } catch (Exception e) {
                fila.add(new JLabel(""));
            }

            JLabel lbl = new JLabel(texto);
            lbl.setForeground(blanco);
            lbl.setFont(new Font("Arial", Font.BOLD, 13));
            fila.add(lbl);

            Color bgNormal = esActivo ? turquesaOscuro : turquesa;
            fila.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { fila.setBackground(turquesaOscuro); }
                public void mouseExited(MouseEvent e) { fila.setBackground(bgNormal); }
                public void mouseClicked(MouseEvent e) {
                    switch (texto) {
                        case "INICIO"      -> { new Inicio(empleado); dispose(); }
                        case "VENTAS"      -> { new Ventas(empleado); dispose(); }
                        case "PRODUCTOS"   -> { new Productos(empleado); dispose(); }
                        case "PROVEEDORES" -> { new Proveedores(empleado); dispose(); }
                        case "PEDIDOS"     -> { new Pedidos(empleado); dispose(); }
                        case "REPORTES"    -> { new Reportes(empleado); dispose(); }
                        case "USUARIOS"    -> { new Usuarios(empleado); dispose(); }
                        case "TICKETS"     -> { new Tickets(empleado); dispose(); }
                        case "REGRESAR"    -> { new Inicio_sesion(); dispose(); }
                    }
                }
            });
            sidebar.add(fila);
        }
        return sidebar;
    }

    private JPanel crearContenido() {
        JPanel contenido = new JPanel(null);
        contenido.setBackground(grisFondo);

        JLabel lblTitulo = new JLabel("AGREGAR NUEVO PEDIDO");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(textoGris);
        lblTitulo.setBounds(20, 15, 300, 30);
        contenido.add(lblTitulo);

        JPanel panelDatos = new JPanel(null);
        panelDatos.setBackground(blanco);
        panelDatos.setBounds(20, 50, 700, 120);
        panelDatos.setBorder(BorderFactory.createLineBorder(new Color(220,220,220)));

        JLabel lblProv = new JLabel("Proveedor:");
        lblProv.setBounds(20, 20, 100, 25);
        panelDatos.add(lblProv);
        cbProveedor = new JComboBox<>();
        cbProveedor.setBounds(100, 20, 200, 25);
        panelDatos.add(cbProveedor);

        JLabel lblProducto = new JLabel("Producto:");
        lblProducto.setBounds(330, 20, 100, 25);
        panelDatos.add(lblProducto);
        cbProducto = new JComboBox<>();
        cbProducto.setBounds(410, 20, 200, 25);
        panelDatos.add(cbProducto);

        JLabel lblCant = new JLabel("Cantidad:");
        lblCant.setBounds(20, 60, 100, 25);
        panelDatos.add(lblCant);
        txtCantidad = new JTextField();
        txtCantidad.setBounds(100, 60, 80, 25);
        panelDatos.add(txtCantidad);

        JLabel lblCosto = new JLabel("Costo Unitario:");
        lblCosto.setBounds(200, 60, 100, 25);
        panelDatos.add(lblCosto);
        txtCosto = new JTextField();
        txtCosto.setBounds(300, 60, 80, 25);
        panelDatos.add(txtCosto);

        JLabel lblTotal = new JLabel("Subtotal:");
        lblTotal.setBounds(400, 60, 100, 25);
        panelDatos.add(lblTotal);
        txtTotal = new JTextField();
        txtTotal.setBounds(450, 60, 80, 25);
        txtTotal.setEditable(false);
        panelDatos.add(txtTotal);

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.setBackground(turquesa);
        btnAgregar.setForeground(blanco);
        btnAgregar.setFont(new Font("Arial", Font.BOLD, 12));
        btnAgregar.setBorderPainted(false);
        btnAgregar.setBounds(550, 60, 100, 25);
        btnAgregar.addActionListener(e -> agregarDetalle());
        panelDatos.add(btnAgregar);

        contenido.add(panelDatos);

        JLabel lblTabla = new JLabel("Detalles del Pedido");
        lblTabla.setFont(new Font("Arial", Font.BOLD, 14));
        lblTabla.setBounds(20, 180, 200, 25);
        contenido.add(lblTabla);

        String[] columnas = {"Producto", "Cantidad", "Costo Unitario", "Subtotal"};
        modeloDetalles = new DefaultTableModel(columnas, 0);
        JTable tablaDetalles = new JTable(modeloDetalles);
        tablaDetalles.setRowHeight(28);
        tablaDetalles.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaDetalles.getTableHeader().setBackground(turquesaClaro);
        tablaDetalles.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        JScrollPane spTabla = new JScrollPane(tablaDetalles);
        spTabla.setBounds(20, 210, 700, 250);
        spTabla.setBorder(BorderFactory.createLineBorder(new Color(220,220,220)));
        contenido.add(spTabla);

        lblTotalGeneral = new JLabel("TOTAL GENERAL: $ 0.00");
        lblTotalGeneral.setFont(new Font("Arial", Font.BOLD, 16));
        lblTotalGeneral.setForeground(textoGris);
        lblTotalGeneral.setBounds(20, 470, 300, 30);
        contenido.add(lblTotalGeneral);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        panelBotones.setBounds(350, 470, 370, 40);
        panelBotones.setBackground(grisFondo);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(grisClaro);
        btnCancelar.setForeground(textoGris);
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 13));
        btnCancelar.setBorderPainted(false);
        btnCancelar.addActionListener(e -> {
            ventanaAnterior.setVisible(true);
            dispose();
        });
        panelBotones.add(btnCancelar);

        JButton btnGuardar = new JButton("GUARDAR PEDIDO");
        btnGuardar.setBackground(turquesaOscuro);
        btnGuardar.setForeground(blanco);
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 13));
        btnGuardar.setBorderPainted(false);
        btnGuardar.addActionListener(e -> guardarPedido());
        panelBotones.add(btnGuardar);

        contenido.add(panelBotones);

        cargarProveedores();
        cargarProductos();

        txtCantidad.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) { calcularSubtotal(); }
        });
        txtCosto.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) { calcularSubtotal(); }
        });

        return contenido;
    }

    private void guardarPedido() {
        if (modeloDetalles.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Agrega al menos un producto al pedido", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Connection con = null;
        try {
            con = ConexionBD.getConexion();
            con.setAutoCommit(false);

            String sqlPedido = "INSERT INTO pedido(fecha, total_pedido, id_proveedor, id_empleado, id_estado) VALUES(?, ?, ?, ?, ?)";
            PreparedStatement psP = con.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS);
            
            psP.setDate(1, new java.sql.Date(System.currentTimeMillis()));
            psP.setDouble(2, totalGeneral);

            String provSel = cbProveedor.getSelectedItem().toString();
            int idProv = Integer.parseInt(provSel.split(" - ")[0]);
            psP.setInt(3, idProv);

            psP.setInt(4, empleado.getIdEmpleado());
            psP.setInt(5, 1);

            psP.executeUpdate();

            ResultSet rs = psP.getGeneratedKeys();
            int idPedido = 0;
            if (rs.next()) idPedido = rs.getInt(1);

            String sqlDetalle = "INSERT INTO detalle_pedido(id_pedido, id_producto, cantidad, costo_unitario, subtotal) VALUES(?, ?, ?, ?, ?)";
            PreparedStatement psD = con.prepareStatement(sqlDetalle);

            for (int i = 0; i < modeloDetalles.getRowCount(); i++) {
                String prodSel = modeloDetalles.getValueAt(i, 0).toString();
                int idProd = Integer.parseInt(prodSel.split(" - ")[0]);
                int cant = Integer.parseInt(modeloDetalles.getValueAt(i, 1).toString());
                double costo = Double.parseDouble(modeloDetalles.getValueAt(i, 2).toString());
                double subtotal = Double.parseDouble(modeloDetalles.getValueAt(i, 3).toString());

                psD.setInt(1, idPedido);
                psD.setInt(2, idProd);
                psD.setInt(3, cant);
                psD.setDouble(4, costo);
                psD.setDouble(5, subtotal);
                psD.executeUpdate();
            }

            con.commit();

            JOptionPane.showMessageDialog(this, "✅ Pedido guardado correctamente");
            
            ventanaAnterior.setVisible(true);
            ((Pedidos) ventanaAnterior).cargarPedidos();
            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Error al guardar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            try {
                if (con != null) con.rollback();
            } catch (SQLException ex) { 
                ex.printStackTrace(); 
            }
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException ex) { 
                ex.printStackTrace(); 
            }
        }
    }

    private void cargarProveedores() {
        try (Connection con = ConexionBD.getConexion();
             ResultSet rs = con.createStatement().executeQuery("SELECT id_proveedor, nombre FROM proveedor ORDER BY nombre")) {
            while (rs.next()) {
                cbProveedor.addItem(rs.getInt("id_proveedor") + " - " + rs.getString("nombre"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando proveedores: " + e.getMessage());
        }
    }

    private void cargarProductos() {
        try (Connection con = ConexionBD.getConexion();
             ResultSet rs = con.createStatement().executeQuery("SELECT id_producto, nombre FROM producto ORDER BY nombre")) {
            while (rs.next()) {
                cbProducto.addItem(rs.getInt("id_producto") + " - " + rs.getString("nombre"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando productos: " + e.getMessage());
        }
    }

    private void calcularSubtotal() {
        try {
            if (txtCantidad.getText().isBlank() || txtCosto.getText().isBlank()) {
                txtTotal.setText("0.00");
                return;
            }
            int cantidad = Integer.parseInt(txtCantidad.getText());
            double costo = Double.parseDouble(txtCosto.getText());
            double subtotal = cantidad * costo;
            txtTotal.setText(String.format("%.2f", subtotal));
        } catch (NumberFormatException e) {
            txtTotal.setText("0.00");
        }
    }

    private void agregarDetalle() {
        try {
            if (txtCantidad.getText().isBlank() || txtCosto.getText().isBlank() || txtTotal.getText().equals("0.00")) {
                JOptionPane.showMessageDialog(this, "Ingresa cantidad y costo válidos");
                return;
            }

            String producto = cbProducto.getSelectedItem().toString();
            int cantidad = Integer.parseInt(txtCantidad.getText());
            double costo = Double.parseDouble(txtCosto.getText());
            double subtotal = Double.parseDouble(txtTotal.getText());

            modeloDetalles.addRow(new Object[]{producto, cantidad, costo, subtotal});

            totalGeneral += subtotal;
            lblTotalGeneral.setText(String.format("TOTAL GENERAL: $ %.2f", totalGeneral));

            txtCantidad.setText("");
            txtCosto.setText("");
            txtTotal.setText("0.00");
            txtCantidad.requestFocus();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Datos inválidos: " + e.getMessage());
        }
    }
}