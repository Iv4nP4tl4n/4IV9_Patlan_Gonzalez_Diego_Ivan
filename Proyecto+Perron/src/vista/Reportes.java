
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
import java.text.SimpleDateFormat;
import java.util.Date;

public class Reportes extends JFrame {

    // COLORES EXACTOS DE TU IMAGEN
    Color verdeMenu = new Color(0, 120, 110);
    Color verdeActivo = new Color(0, 170, 160);
    Color celesteFondo = new Color(230, 245, 250);
    Color blanco = Color.WHITE;
    Color negroTexto = new Color(20, 20, 20);
    Color grisBoton = new Color(200, 200, 200);
    Color azulBoton = new Color(0, 170, 160);
    Color rojoBoton = new Color(220, 30, 30);

    private Empleado empleado;
    private JTextField txtBuscarProducto, txtTituloReporte;
    private JComboBox<String> cboTipoIncidencia;
    private JTextArea txtDescripcion;
    private JTable tablaReportes;
    private DefaultTableModel modeloTabla;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public Reportes(Empleado empleado) {
        this.empleado = empleado;
        setTitle("Reportes - Registro de Incidencias");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        JPanel fondo = new JPanel(new BorderLayout());
        fondo.setBackground(blanco);
        fondo.add(crearSidebar(), BorderLayout.WEST);
        fondo.add(crearContenido(), BorderLayout.CENTER);

        add(fondo);
        setVisible(true);

        // Cargar reportes existentes al iniciar
        cargarReportesRegistrados();
    }

    // BARRA LATERAL IGUAL A TODAS LAS PANTALLAS
    private JPanel crearSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(verdeMenu);
        sidebar.setPreferredSize(new Dimension(180, 600));

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
            String nombreIcono = item[0];
            String texto = item[1];
            boolean esActivo = texto.equals("REPORTES");

            JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 12));
            fila.setMaximumSize(new Dimension(180, 55));
            fila.setCursor(new Cursor(Cursor.HAND_CURSOR));
            fila.setBackground(esActivo ? verdeActivo : verdeMenu);
            fila.setOpaque(true);

            try {
                ImageIcon icono = new ImageIcon(getClass().getResource("/vista/iconos/" + nombreIcono));
                JLabel lblIcono = new JLabel(new ImageIcon(icono.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
                fila.add(lblIcono);
            } catch (Exception e) {
                JLabel lblError = new JLabel("•");
                lblError.setForeground(blanco);
                fila.add(lblError);
            }

            JLabel lblTexto = new JLabel(texto);
            lblTexto.setForeground(blanco);
            lblTexto.setFont(new Font("Arial", Font.BOLD, 13));
            fila.add(lblTexto);

            fila.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    switch (texto) {
                        case "INICIO": new Inicio(empleado); dispose(); break;
                        case "VENTAS": new Ventas(empleado); dispose(); break;
                        case "PRODUCTOS": new Productos(empleado); dispose(); break;
                        case "PROVEEDORES": new Proveedores(empleado); dispose(); break;
                        case "PEDIDOS": new Pedidos(empleado); dispose(); break;
                        case "REPORTES": new Reportes(empleado); dispose(); break;
                        case "USUARIOS": new Usuarios(empleado); dispose(); break;
                        case "TICKETS": new Tickets(empleado); dispose(); break;
                        case "REGRESAR": new Inicio_sesion(); dispose(); break;
                    }
                }
            });
            sidebar.add(fila);
        }
        return sidebar;
    }

    // CONTENIDO PRINCIPAL: FORMULARIO DE INCIDENCIAS + TABLA
    private JPanel crearContenido() {
        JPanel contenido = new JPanel(null);
        contenido.setBackground(blanco);

        // TITULO
        JLabel lblTitulo = new JLabel("REGISTRAR REPORTE / INCIDENCIA");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setForeground(verdeMenu);
        lblTitulo.setBounds(20, 15, 350, 25);
        contenido.add(lblTitulo);

        // SECCIÓN SUPERIOR: DATOS DEL REPORTE
        JPanel panelFormulario = new JPanel(null);
        panelFormulario.setBackground(celesteFondo);
        panelFormulario.setBounds(20, 45, 700, 180);
        panelFormulario.setBorder(BorderFactory.createLineBorder(verdeActivo, 1));

        // BUSCAR PRODUCTO
        JLabel lblBuscarProd = new JLabel("Producto con incidencia:");
        lblBuscarProd.setFont(new Font("Arial", Font.BOLD, 12));
        lblBuscarProd.setBounds(15, 15, 150, 20);
        panelFormulario.add(lblBuscarProd);

        txtBuscarProducto = new JTextField("Buscar por código o nombre...");
        txtBuscarProducto.setBounds(15, 38, 320, 25);
        panelFormulario.add(txtBuscarProducto);

        JButton btnBuscar = new JButton("🔍");
        btnBuscar.setBounds(340, 38, 40, 25);
        btnBuscar.addActionListener(e -> buscarProducto());
        panelFormulario.add(btnBuscar);

        // TIPO DE INCIDENCIA
        JLabel lblTipo = new JLabel("Tipo de incidencia:");
        lblTipo.setFont(new Font("Arial", Font.BOLD, 12));
        lblTipo.setBounds(400, 15, 120, 20);
        panelFormulario.add(lblTipo);

        cboTipoIncidencia = new JComboBox<>(new String[]{
            "Producto dañado", "Caducado", "Falta de stock", "Error de precio", 
            "Defecto de fábrica", "Devolución", "Otro"
        });
        cboTipoIncidencia.setBounds(400, 38, 250, 25);
        panelFormulario.add(cboTipoIncidencia);

        // TITULO DEL REPORTE
        JLabel lblTituloRep = new JLabel("Título del reporte:");
        lblTituloRep.setFont(new Font("Arial", Font.BOLD, 12));
        lblTituloRep.setBounds(15, 70, 120, 20);
        panelFormulario.add(lblTituloRep);

        txtTituloReporte = new JTextField();
        txtTituloReporte.setBounds(15, 93, 365, 25);
        panelFormulario.add(txtTituloReporte);

        // DESCRIPCIÓN DETALLADA
        JLabel lblDescripcion = new JLabel("Descripción detallada:");
        lblDescripcion.setFont(new Font("Arial", Font.BOLD, 12));
        lblDescripcion.setBounds(400, 70, 150, 20);
        panelFormulario.add(lblDescripcion);

        txtDescripcion = new JTextArea("Escribe aquí todos los detalles, observaciones o comentarios...");
        txtDescripcion.setBounds(400, 93, 250, 70);
        txtDescripcion.setBorder(BorderFactory.createLineBorder(grisBoton));
        txtDescripcion.setLineWrap(true);
        panelFormulario.add(txtDescripcion);

        contenido.add(panelFormulario);

        // BOTONES DE ACCIÓN
        JButton btnGuardar = new JButton("GUARDAR REPORTE");
        btnGuardar.setBackground(azulBoton);
        btnGuardar.setForeground(blanco);
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 12));
        btnGuardar.setBounds(20, 235, 150, 30);
        btnGuardar.setBorderPainted(false);
        btnGuardar.addActionListener(e -> guardarReporte());
        contenido.add(btnGuardar);

        JButton btnLimpiar = new JButton("LIMPIAR");
        btnLimpiar.setBackground(grisBoton);
        btnLimpiar.setFont(new Font("Arial", Font.BOLD, 12));
        btnLimpiar.setBounds(180, 235, 100, 30);
        btnLimpiar.setBorderPainted(false);
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        contenido.add(btnLimpiar);

        // TABLA: REPORTES REGISTRADOS
        JLabel lblTabla = new JLabel("Reportes e incidencias registradas");
        lblTabla.setFont(new Font("Arial", Font.BOLD, 14));
        lblTabla.setBounds(20, 280, 300, 25);
        contenido.add(lblTabla);

        String[] columnas = {"Fecha", "Producto", "Tipo", "Título", "Registró", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaReportes = new JTable(modeloTabla);
        tablaReportes.setRowHeight(28);
        tablaReportes.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaReportes.getTableHeader().setBackground(celesteFondo);
        tablaReportes.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        JScrollPane sp = new JScrollPane(tablaReportes);
        sp.setBounds(20, 310, 700, 250);
        sp.setBorder(BorderFactory.createLineBorder(verdeActivo, 2));
        contenido.add(sp);

        return contenido;
    }

    // ✅ BUSCAR PRODUCTO EN LA BASE DE DATOS
    private void buscarProducto() {
        String criterio = txtBuscarProducto.getText().trim();
        if (criterio.isEmpty() || criterio.equals("Buscar por código o nombre...")) {
            JOptionPane.showMessageDialog(this, "Escribe un código o nombre de producto");
            return;
        }

        try (Connection con = ConexionBD.getConexion()) {
            String sql = "SELECT nombre FROM producto WHERE (id_producto = ? OR nombre LIKE ?) AND estado = 1";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, criterio);
            ps.setString(2, "%" + criterio + "%");
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                txtBuscarProducto.setText(rs.getString("nombre"));
            } else {
                JOptionPane.showMessageDialog(this, "Producto no encontrado");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al buscar: " + e.getMessage());
        }
    }

    // GUARDAR REPORTE EN LA BASE DE DATOS
    private void guardarReporte() {
        // Validar campos
        if (txtBuscarProducto.getText().trim().isEmpty() || txtTituloReporte.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completa los campos obligatorios (Producto y Título)");
            return;
        }

        try (Connection con = ConexionBD.getConexion()) {
            // Asegúrate de tener esta tabla creada en tu BD
            String sql = "INSERT INTO reporte_incidencias " +
                         "(fecha, id_empleado, producto, tipo_incidencia, titulo, descripcion, estado) " +
                         "VALUES (?, ?, ?, ?, ?, ?, 'Pendiente')";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setTimestamp(1, new Timestamp(new Date().getTime()));
            ps.setInt(2, empleado.getIdEmpleado());
            ps.setString(3, txtBuscarProducto.getText());
            ps.setString(4, cboTipoIncidencia.getSelectedItem().toString());
            ps.setString(5, txtTituloReporte.getText());
            ps.setString(6, txtDescripcion.getText());

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "✅ Reporte guardado correctamente");

            limpiarFormulario();
            cargarReportesRegistrados(); // Actualizar tabla

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Error al guardar: " + e.getMessage() + 
                "\n\nNota: Si es la primera vez, asegúrate de crear la tabla 'reporte_incidencias' en tu base de datos.");
        }
    }

    //  CARGAR TODOS LOS REPORTES EN LA TABLA
    private void cargarReportesRegistrados() {
        modeloTabla.setRowCount(0);

        try (Connection con = ConexionBD.getConexion()) {
            String sql = "SELECT r.fecha, r.producto, r.tipo_incidencia, r.titulo, e.nombre AS empleado, r.estado " +
                         "FROM reporte_incidencias r " +
                         "JOIN empleado e ON r.id_empleado = e.id_empleado " +
                         "ORDER BY r.fecha DESC";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                modeloTabla.addRow(new Object[]{
                    sdf.format(rs.getTimestamp("fecha")),
                    rs.getString("producto"),
                    rs.getString("tipo_incidencia"),
                    rs.getString("titulo"),
                    rs.getString("empleado"),
                    rs.getString("estado")
                });
            }

        } catch (Exception e) {
            // Si la tabla no existe, mostrar mensaje una sola vez
            if (modeloTabla.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Tabla de reportes no creada aún.\nUsa este código en tu base de datos:\n\n" +
                    "CREATE TABLE reporte_incidencias (\n" +
                    "  id_reporte INT PRIMARY KEY AUTO_INCREMENT,\n" +
                    "  fecha DATETIME,\n" +
                    "  id_empleado INT,\n" +
                    "  producto VARCHAR(150),\n" +
                    "  tipo_incidencia VARCHAR(100),\n" +
                    "  titulo VARCHAR(200),\n" +
                    "  descripcion TEXT,\n" +
                    "  estado VARCHAR(50) DEFAULT 'Pendiente',\n" +
                    "  FOREIGN KEY (id_empleado) REFERENCES empleado(id_empleado)\n" +
                    ");");
            }
        }
    }

    // LIMPIAR FORMULARIO
    private void limpiarFormulario() {
        txtBuscarProducto.setText("Buscar por código o nombre...");
        txtTituloReporte.setText("");
        txtDescripcion.setText("Escribe aquí todos los detalles, observaciones o comentarios...");
        cboTipoIncidencia.setSelectedIndex(0);
    }
}