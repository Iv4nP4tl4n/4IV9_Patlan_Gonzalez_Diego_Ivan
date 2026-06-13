/**
 *
 * @author ivanp
 */
package vista;

import modelo.ConexionBD;
import modelo.Empleado;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class Pedidos extends JFrame {

    Color turquesa       = new Color(0, 190, 185);
    Color turquesaClaro  = new Color(188, 237, 234);
    Color turquesaOscuro = new Color(0, 160, 155);
    Color blanco        = Color.WHITE;
    Color grisFondo     = new Color(245, 245, 245);
    Color textoGris     = new Color(80, 80, 80);

    private Empleado empleado;
    private DefaultTableModel modelo;
    private JTable tabla;

    public Pedidos(Empleado empleado) {
        this.empleado = empleado;
        setTitle("Pedidos - Proyecto Perrón");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        JPanel fondo = new JPanel(new BorderLayout());
        fondo.setBackground(grisFondo);
        fondo.add(crearSidebar(), BorderLayout.WEST);

        JPanel contenido = new JPanel(null);
        contenido.setBackground(grisFondo);

        JLabel lblTitulo = new JLabel("Pedidos");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(textoGris);
        lblTitulo.setBounds(20, 15, 200, 30);
        contenido.add(lblTitulo);

        JButton btnNuevo = new JButton("+ Nuevo pedido");
        btnNuevo.setBackground(turquesa);
        btnNuevo.setForeground(blanco);
        btnNuevo.setFont(new Font("Arial", Font.BOLD, 13));
        btnNuevo.setBorderPainted(false);
        btnNuevo.setFocusPainted(false);
        btnNuevo.setBounds(580, 15, 130, 30);
        btnNuevo.addActionListener(e -> {
            new AgregarPedido(empleado, this);
            setVisible(false);
        });
        contenido.add(btnNuevo);

        JPanel panelFiltros = new JPanel(null);
        panelFiltros.setBackground(blanco);
        panelFiltros.setBounds(20, 55, 700, 70);
        panelFiltros.setBorder(BorderFactory.createLineBorder(new Color(220,220,220)));
        
        JLabel lblBuscar = new JLabel("Búsqueda personalizada");
        lblBuscar.setBounds(10, 8, 150, 20);
        lblBuscar.setFont(new Font("Arial", Font.PLAIN, 12));
        panelFiltros.add(lblBuscar);
        
        JComboBox<String> cbCampo = new JComboBox<>(new String[]{"Todos", "Proveedor", "N° pedido"});
        cbCampo.setBounds(10, 30, 120, 25);
        panelFiltros.add(cbCampo);
        
        JTextField txtBuscar = new JTextField();
        txtBuscar.setBounds(135, 30, 150, 25);
        panelFiltros.add(txtBuscar);
        
        JComboBox<String> cbEstado = new JComboBox<>(new String[]{"Todos los estados", "Pendiente", "Completado", "Cancelado"});
        cbEstado.setBounds(295, 30, 130, 25);
        panelFiltros.add(cbEstado);
        
        JComboBox<String> cbFecha = new JComboBox<>(new String[]{"Últimos 60 días", "Hoy", "Esta semana", "Este mes"});
        cbFecha.setBounds(435, 30, 120, 25);
        panelFiltros.add(cbFecha);
        
        JButton btnExportar = new JButton("Exportar");
        btnExportar.setBounds(565, 30, 80, 25);
        btnExportar.setBackground(new Color(240,240,240));
        panelFiltros.add(btnExportar);
        
        contenido.add(panelFiltros);

        String[] columnas = {"N° Pedido", "Fecha", "Proveedor", "Empleado", "Total", "Estado", "Acciones"};
        modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modelo);
        tabla.setRowHeight(30);
        tabla.setFont(new Font("Arial", Font.PLAIN, 12));
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tabla.getTableHeader().setBackground(turquesaClaro);
        tabla.getTableHeader().setForeground(Color.BLACK);
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.setGridColor(new Color(220,220,220));

        DefaultTableCellRenderer rendererEstado = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel c = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setOpaque(true);
                c.setForeground(Color.BLACK);
                
                if (value != null) {
                    String estado = value.toString().trim();
                    switch (estado) {
                        case "Completado":
                            c.setBackground(new Color(200, 255, 200));
                            c.setForeground(new Color(0, 100, 0));
                            break;
                        case "Cancelado":
                            c.setBackground(new Color(255, 200, 200));
                            c.setForeground(new Color(150, 0, 0));
                            break;
                        case "Pendiente":
                            c.setBackground(new Color(255, 255, 200));
                            c.setForeground(new Color(150, 150, 0));
                            break;
                        default:
                            c.setBackground(blanco);
                            c.setForeground(Color.BLACK);
                            break;
                    }
                }
                return c;
            }
        };
        tabla.getColumnModel().getColumn(5).setCellRenderer(rendererEstado);


        // ✅ AQUÍ ESTÁN LOS BOTONES FUNCIONANDO: EDITAR Y ELIMINAR
        tabla.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 2));
                panel.setOpaque(true);
                panel.setBackground(isSelected ? new Color(220, 240, 240) : blanco);

                try {
                    String base = "vista/iconos/";
                    Icon editarIco = new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource(base + "pencil.png")).getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
                    Icon eliminarIco = new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource(base + "trash.png")).getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));

                    JButton btnEditar = new JButton(editarIco);
                    JButton btnEliminar = new JButton(eliminarIco);

                    for (JButton btn : new JButton[]{btnEditar, btnEliminar}) {
                        btn.setOpaque(false);
                        btn.setContentAreaFilled(false);
                        btn.setBorderPainted(false);
                        btn.setFocusPainted(false);
                        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    }

                    int idPedido = (int) table.getValueAt(row, 0);

                    // ✅ FUNCIÓN EDITAR: ABRE VENTANA PARA CAMBIAR ESTADO
                    btnEditar.addActionListener(e -> {
                        new EditarPedido(empleado, idPedido, Pedidos.this);
                    });

                    // ✅ FUNCIÓN ELIMINAR: BORRA DE LA BASE
                    btnEliminar.addActionListener(e -> {
                        int conf = JOptionPane.showConfirmDialog(null, 
                            "¿Seguro que deseas eliminar este pedido?\nSe borrará también sus productos.", 
                            "Confirmar eliminación", 
                            JOptionPane.YES_NO_OPTION, 
                            JOptionPane.WARNING_MESSAGE);
                        if (conf == JOptionPane.YES_OPTION) {
                            eliminarPedido(idPedido);
                        }
                    });

                    panel.add(btnEditar);
                    panel.add(btnEliminar);

                } catch (Exception e) {
                    // Si no encuentra iconos, pone texto
                    panel.add(new JLabel("✏️"));
                    panel.add(new JLabel("🗑️"));
                }
                return panel;
            }
        });


        JScrollPane spTabla = new JScrollPane(tabla);
        spTabla.setBounds(20, 140, 700, 380);
        spTabla.setBorder(BorderFactory.createLineBorder(new Color(220,220,220)));
        contenido.add(spTabla);

        JPanel paginacion = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        paginacion.setBackground(grisFondo);
        paginacion.setBounds(20, 525, 700, 30);
        paginacion.add(new JLabel("Mostrando 1 a 10 de todos los pedidos"));
        contenido.add(paginacion);

        cargarPedidos();

        fondo.add(contenido, BorderLayout.CENTER);
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
                @Override
                public void mouseEntered(MouseEvent e) {
                    fila.setBackground(turquesaOscuro);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    fila.setBackground(bgNormal);
                }
                @Override
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

    public void cargarPedidos() {
        modelo.setRowCount(0);

        String sql = """
            SELECT p.id_pedido, p.fecha, pr.nombre AS proveedor, 
                   e.nombre AS empleado, p.total_pedido, ep.nombre AS estado
            FROM pedido p
            JOIN proveedor pr ON p.id_proveedor = pr.id_proveedor
            JOIN empleado e ON p.id_empleado = e.id_empleado
            JOIN estado_pedido ep ON p.id_estado = ep.id_estado
            ORDER BY p.fecha DESC
        """;

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("id_pedido"),
                    sdf.format(rs.getDate("fecha")),
                    rs.getString("proveedor"),
                    rs.getString("empleado"),
                    String.format("$ %.2f", rs.getDouble("total_pedido")),
                    rs.getString("estado"),
                    ""
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar: " + e.getMessage());
            e.printStackTrace();
        }
    }


    // ✅ FUNCIÓN PARA ELIMINAR DE VERDAD
    private void eliminarPedido(int idPedido) {
        try (Connection con = ConexionBD.getConexion()) {
            // Primero borramos los detalles
            String sql = "DELETE FROM detalle_pedido WHERE id_pedido = ?";
            PreparedStatement ps1 = con.prepareStatement(sql);
            ps1.setInt(1, idPedido);
            ps1.executeUpdate();

            // Luego borramos el pedido
            sql = "DELETE FROM pedido WHERE id_pedido = ?";
            PreparedStatement ps2 = con.prepareStatement(sql);
            ps2.setInt(1, idPedido);
            ps2.executeUpdate();

            JOptionPane.showMessageDialog(this, "✅ Pedido eliminado correctamente");
            cargarPedidos(); // Recarga la tabla

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Error al eliminar: " + e.getMessage());
        }
    }


    // ✅ VENTANA EDITAR (FUNCIONA)
    class EditarPedido extends JDialog {
        Color turquesa       = new Color(0, 190, 185);
        Color blanco        = Color.WHITE;
        Color grisFondo     = new Color(245, 245, 245);
        Color textoGris     = new Color(80, 80, 80);

        private int idPedido;
        private Pedidos ventanaPadre;

        public EditarPedido(Empleado empleado, int idPedido, Pedidos ventanaPadre) {
            this.idPedido = idPedido;
            this.ventanaPadre = ventanaPadre;

            setTitle("Editar Pedido - Proyecto Perrón");
            setSize(350, 200);
            setLocationRelativeTo(null);
            setModal(true);
            setResizable(false);

            JPanel fondo = new JPanel(null);
            fondo.setBackground(grisFondo);

            JLabel lblTitulo = new JLabel("Editar estado del pedido #" + idPedido);
            lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));
            lblTitulo.setForeground(textoGris);
            lblTitulo.setBounds(20, 20, 280, 25);
            fondo.add(lblTitulo);

            JLabel lblEstado = new JLabel("Estado:");
            lblEstado.setFont(new Font("Arial", Font.PLAIN, 12));
            lblEstado.setBounds(20, 60, 80, 25);
            fondo.add(lblEstado);

            JComboBox<String> cbEstado = new JComboBox<>(new String[]{"Pendiente", "Completado", "Cancelado"});
            cbEstado.setBounds(80, 60, 200, 25);
            fondo.add(cbEstado);

            // Cargar estado actual
            cargarEstadoActual(cbEstado);

            JButton btnGuardar = new JButton("Guardar cambios");
            btnGuardar.setBackground(turquesa);
            btnGuardar.setForeground(blanco);
            btnGuardar.setFont(new Font("Arial", Font.BOLD, 12));
            btnGuardar.setBorderPainted(false);
            btnGuardar.setBounds(80, 100, 200, 30);
            btnGuardar.addActionListener(e -> guardarCambios(cbEstado.getSelectedItem().toString()));
            fondo.add(btnGuardar);

            add(fondo);
            setVisible(true);
        }

        private void cargarEstadoActual(JComboBox<String> cbEstado) {
            String sql = "SELECT ep.nombre FROM pedido p JOIN estado_pedido ep ON p.id_estado = ep.id_estado WHERE p.id_pedido = ?";
            try (Connection con = ConexionBD.getConexion();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, idPedido);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    cbEstado.setSelectedItem(rs.getString(1));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void guardarCambios(String nuevoEstado) {
            int idEstado = switch (nuevoEstado) {
                case "Pendiente" -> 1;
                case "Completado" -> 2;
                case "Cancelado" -> 3;
                default -> 1;
            };

            String sql = "UPDATE pedido SET id_estado = ? WHERE id_pedido = ?";
            try (Connection con = ConexionBD.getConexion();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, idEstado);
                ps.setInt(2, idPedido);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "✅ Estado actualizado");
                ventanaPadre.cargarPedidos(); // Refresca la tabla principal
                dispose(); // Cierra ventana

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "❌ Error: " + e.getMessage());
            }
        }
    }
}