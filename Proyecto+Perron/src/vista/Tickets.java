package vista;

import modelo.ConexionBD;
import modelo.Empleado;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tickets extends JFrame {

    Color verdeMenu = new Color(0, 120, 110);
    Color verdeActivo = new Color(0, 170, 160);
    Color celesteFondo = new Color(230, 245, 250);
    Color grisBorde = new Color(200, 220, 225);
    Color blanco = Color.WHITE;
    Color azulBoton = new Color(0, 170, 160);

    private Empleado empleado;
    private JTable tablaTickets;
    private DefaultTableModel modeloTabla;
    private DecimalFormat df = new DecimalFormat("$000,000.00");
    private SimpleDateFormat sdfFecha = new SimpleDateFormat("yyyy-MM-dd");

    // 🔹 COMPONENTES DE BÚSQUEDA
    private JTextField txtFechaInicio, txtFechaFin;
    private JComboBox<String> cboEmpleados;
    private JButton btnBuscar, btnLimpiar;

    public Tickets(Empleado empleado) {
        this.empleado = empleado;
        setTitle("Tickets - Proyecto Perrón");
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
    }

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
            boolean esActivo = texto.equals("TICKETS");

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

    private JPanel crearContenido() {
        JPanel contenido = new JPanel(null);
        contenido.setBackground(blanco);

        JLabel lblTitulo = new JLabel("Listado de Tickets");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setForeground(verdeMenu);
        lblTitulo.setBounds(20, 15, 200, 25);
        contenido.add(lblTitulo);

        //  PANEL DE FILTROS
        JPanel panelFiltros = new JPanel(null);
        panelFiltros.setBackground(celesteFondo);
        panelFiltros.setBounds(20, 45, 710, 80);
        panelFiltros.setBorder(BorderFactory.createLineBorder(grisBorde));

        // Fecha Inicio
        JLabel lblFechaInicio = new JLabel("Fecha Inicio (AAAA-MM-DD):");
        lblFechaInicio.setFont(new Font("Arial", Font.PLAIN, 12));
        lblFechaInicio.setBounds(15, 10, 160, 20);
        panelFiltros.add(lblFechaInicio);
        txtFechaInicio = new JTextField();
        txtFechaInicio.setBounds(15, 32, 130, 22);
        panelFiltros.add(txtFechaInicio);

        // Fecha Fin
        JLabel lblFechaFin = new JLabel("Fecha Fin (AAAA-MM-DD):");
        lblFechaFin.setFont(new Font("Arial", Font.PLAIN, 12));
        lblFechaFin.setBounds(160, 10, 140, 20);
        panelFiltros.add(lblFechaFin);
        txtFechaFin = new JTextField();
        txtFechaFin.setBounds(160, 32, 130, 22);
        panelFiltros.add(txtFechaFin);

        // Empleado
        JLabel lblEmpleado = new JLabel("Atendido por:");
        lblEmpleado.setFont(new Font("Arial", Font.PLAIN, 12));
        lblEmpleado.setBounds(300, 10, 100, 20);
        panelFiltros.add(lblEmpleado);
        cboEmpleados = new JComboBox<>();
        cboEmpleados.setBounds(300, 32, 180, 22);
        panelFiltros.add(cboEmpleados);

        // Botones
        btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(azulBoton);
        btnBuscar.setForeground(blanco);
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 12));
        btnBuscar.setBounds(500, 30, 90, 25);
        btnBuscar.setBorderPainted(false);
        btnBuscar.addActionListener(e -> buscarTickets());
        panelFiltros.add(btnBuscar);

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBackground(Color.GRAY);
        btnLimpiar.setForeground(blanco);
        btnLimpiar.setFont(new Font("Arial", Font.BOLD, 12));
        btnLimpiar.setBounds(600, 30, 90, 25);
        btnLimpiar.setBorderPainted(false);
        btnLimpiar.addActionListener(e -> {
            txtFechaInicio.setText("");
            txtFechaFin.setText("");
            cboEmpleados.setSelectedIndex(0);
            cargarTickets();
        });
        panelFiltros.add(btnLimpiar);

        contenido.add(panelFiltros);

        //  TABLA
        String[] columnas = {"N° Ticket", "Fecha y Hora", "Empleado", "Total"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaTickets = new JTable(modeloTabla);
        tablaTickets.setRowHeight(25);
        tablaTickets.getTableHeader().setBackground(celesteFondo);

        JScrollPane sp = new JScrollPane(tablaTickets);
        sp.setBounds(20, 135, 710, 400);
        contenido.add(sp);

        // Cargar lista de empleados al iniciar
        cargarEmpleados();
        // Cargar todos los tickets al abrir
        cargarTickets();

        return contenido;
    }

    // 📌 Cargar empleados en el combo
    private void cargarEmpleados() {
        cboEmpleados.removeAllItems();
        cboEmpleados.addItem("Todos los empleados");

        try (Connection con = ConexionBD.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT id_empleado, nombre FROM empleado WHERE estado = 1 ORDER BY nombre")) {

            while (rs.next()) {
                cboEmpleados.addItem(rs.getInt("id_empleado") + " - " + rs.getString("nombre"));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar empleados: " + e.getMessage());
        }
    }

    //  Cargar TODOS los tickets (sin filtro)
    private void cargarTickets() {
        modeloTabla.setRowCount(0);
        try (Connection con = ConexionBD.getConexion()) {
            String sql = "SELECT t.numero_ticket, t.fecha_hora, e.nombre, t.total " +
                         "FROM ticket t " +
                         "JOIN empleado e ON t.id_empleado = e.id_empleado " +
                         "ORDER BY t.fecha_hora DESC";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                modeloTabla.addRow(new Object[]{
                    rs.getString("numero_ticket"),
                    rs.getTimestamp("fecha_hora"),
                    rs.getString("nombre"),
                    df.format(rs.getDouble("total"))
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar tickets: " + e.getMessage());
        }
    }

    //  BUSCAR CON FILTROS
    private void buscarTickets() {
        modeloTabla.setRowCount(0);

        String fechaInicio = txtFechaInicio.getText().trim();
        String fechaFin = txtFechaFin.getText().trim();
        String empleadoSel = (String) cboEmpleados.getSelectedItem();

        // Validar fechas si se escribieron
        if (!fechaInicio.isEmpty() || !fechaFin.isEmpty()) {
            try {
                if (!fechaInicio.isEmpty()) sdfFecha.parse(fechaInicio);
                if (!fechaFin.isEmpty()) sdfFecha.parse(fechaFin);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Usa AAAA-MM-DD");
                return;
            }
        }

        try (Connection con = ConexionBD.getConexion()) {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT t.numero_ticket, t.fecha_hora, e.nombre, t.total ")
               .append("FROM ticket t ")
               .append("JOIN empleado e ON t.id_empleado = e.id_empleado ")
               .append("WHERE 1=1 ");

            // Filtro fecha
            if (!fechaInicio.isEmpty()) sql.append("AND DATE(t.fecha_hora) >= ? ");
            if (!fechaFin.isEmpty()) sql.append("AND DATE(t.fecha_hora) <= ? ");

            // Filtro empleado
            if (empleadoSel != null && !empleadoSel.equals("Todos los empleados")) {
                String idEmp = empleadoSel.split(" - ")[0];
                sql.append("AND t.id_empleado = ? ");
            }

            sql.append("ORDER BY t.fecha_hora DESC");

            PreparedStatement ps = con.prepareStatement(sql.toString());
            int param = 1;

            if (!fechaInicio.isEmpty()) ps.setString(param++, fechaInicio);
            if (!fechaFin.isEmpty()) ps.setString(param++, fechaFin);
            if (empleadoSel != null && !empleadoSel.equals("Todos los empleados")) {
                String idEmp = empleadoSel.split(" - ")[0];
                ps.setInt(param++, Integer.parseInt(idEmp));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                modeloTabla.addRow(new Object[]{
                    rs.getString("numero_ticket"),
                    rs.getTimestamp("fecha_hora"),
                    rs.getString("nombre"),
                    df.format(rs.getDouble("total"))
                });
            }

            if (modeloTabla.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No se encontraron tickets con esos filtros");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al buscar: " + e.getMessage());
        }
    }
}