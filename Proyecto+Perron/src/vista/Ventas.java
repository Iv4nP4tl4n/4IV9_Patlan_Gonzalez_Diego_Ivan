package vista;

import modelo.ConexionBD;
import modelo.Empleado;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.Date;
import java.time.LocalTime;

public class Ventas extends JFrame {

    Color verdeMenu = new Color(0, 120, 110);
    Color verdeActivo = new Color(0, 170, 160);
    Color celesteFondo = new Color(230, 245, 250);
    Color grisTabla = new Color(230, 230, 230);
    Color blanco = Color.WHITE;
    Color rojoBoton = new Color(220, 30, 30);
    Color azulBoton = new Color(0, 170, 160);
    Color grisBoton = new Color(200, 200, 200);
    Color grisBorde = new Color(200, 220, 225);

    private Empleado empleado;
    private JTextField txtBuscar;
    private JComboBox<String> cboMetodoPago;
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    private JTextArea txtObservaciones;
    private JLabel lblSubtotal, lblIva, lblTotal;
    private double subtotalGeneral = 0.0;
    private final double IVA = 0.16;
    private DecimalFormat df = new DecimalFormat("$000,000.00");

    private java.util.Map<String, Integer> metodosPago = new java.util.HashMap<>();

    public Ventas(Empleado empleado) {
        this.empleado = empleado;
        setTitle("Ventas - Proyecto Perrón");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        cargarMetodosPago();

        JPanel fondo = new JPanel(new BorderLayout());
        fondo.setBackground(blanco);
        fondo.add(crearSidebar(), BorderLayout.WEST);
        fondo.add(crearContenido(), BorderLayout.CENTER);

        add(fondo);
        setVisible(true);
    }

    private void cargarMetodosPago() {
        metodosPago.clear();
        try (Connection con = ConexionBD.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT id_metodo, nombre FROM metodo_pago")) {

            while (rs.next()) {
                metodosPago.put(rs.getString("nombre"), rs.getInt("id_metodo"));
            }

        } catch (Exception e) {
            metodosPago.put("Efectivo", 1);
            metodosPago.put("Transferencia", 2);
        }
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
            boolean esActivo = texto.equals("VENTAS");

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

        JLabel lblTitulo = new JLabel("Nueva venta");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setForeground(verdeMenu);
        lblTitulo.setBounds(20, 15, 150, 25);
        contenido.add(lblTitulo);

        JPanel panelBuscar = new JPanel(null);
        panelBuscar.setBackground(celesteFondo);
        panelBuscar.setBounds(20, 45, 450, 60);
        panelBuscar.setBorder(BorderFactory.createLineBorder(grisBorde));

        JLabel lblBuscar = new JLabel("Buscar producto");
        lblBuscar.setFont(new Font("Arial", Font.PLAIN, 12));
        lblBuscar.setBounds(10, 8, 120, 20);
        panelBuscar.add(lblBuscar);

        txtBuscar = new JTextField();
        txtBuscar.setBounds(10, 30, 380, 22);
        txtBuscar.setBorder(BorderFactory.createEmptyBorder());
        panelBuscar.add(txtBuscar);

        JButton btnBuscar = new JButton("Q");
        btnBuscar.setBounds(395, 30, 40, 22);
        btnBuscar.setBorder(BorderFactory.createEmptyBorder());
        btnBuscar.setBackground(blanco);
        btnBuscar.addActionListener(e -> mostrarListaProductos());
        panelBuscar.add(btnBuscar);

        contenido.add(panelBuscar);

        JPanel panelPago = new JPanel(null);
        panelPago.setBackground(celesteFondo);
        panelPago.setBounds(480, 45, 250, 60);
        panelPago.setBorder(BorderFactory.createLineBorder(grisBorde));

        JLabel lblPago = new JLabel("Método de pago");
        lblPago.setFont(new Font("Arial", Font.PLAIN, 12));
        lblPago.setBounds(10, 8, 120, 20);
        panelPago.add(lblPago);

        cboMetodoPago = new JComboBox<>(metodosPago.keySet().toArray(new String[0]));
        if (cboMetodoPago.getItemCount() == 0) {
            cboMetodoPago = new JComboBox<>(new String[]{"Efectivo", "Transferencia"});
        }
        cboMetodoPago.setBounds(10, 30, 230, 22);
        cboMetodoPago.setBorder(BorderFactory.createEmptyBorder());
        panelPago.add(cboMetodoPago);

        contenido.add(panelPago);

        String[] columnas = {"id", "Producto", "Precio", "Cantidad", "Subtotal", "Eliminar"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };

        tablaProductos = new JTable(modeloTabla);
        tablaProductos.setRowHeight(30);
        tablaProductos.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaProductos.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 12));
        tablaProductos.getTableHeader().setBackground(celesteFondo);
        tablaProductos.setBackground(grisTabla);
        tablaProductos.setGridColor(grisBorde);

        TableColumn colCantidad = tablaProductos.getColumnModel().getColumn(3);
        colCantidad.setCellEditor(new DefaultCellEditor(new JTextField()) {
            @Override
            public boolean stopCellEditing() {
                String valor = ((JTextField) getComponent()).getText().trim();
                if (valor.matches("\\d+")) {
                    int cantidad = Integer.parseInt(valor);
                    if (cantidad > 0) {
                        int fila = tablaProductos.getSelectedRow();
                        if (fila >= 0) {
                            double precio = Double.parseDouble(modeloTabla.getValueAt(fila, 2).toString().replace("$", "").replace(",", ""));
                            modeloTabla.setValueAt(df.format(precio * cantidad), fila, 4);
                            actualizarTotales();
                        }
                        return super.stopCellEditing();
                    }
                }
                JOptionPane.showMessageDialog(null, "Solo ingresa números mayores a 0");
                return false;
            }
        });

        tablaProductos.getColumnModel().getColumn(0).setMinWidth(0);
        tablaProductos.getColumnModel().getColumn(0).setMaxWidth(0);
        tablaProductos.getColumnModel().getColumn(1).setPreferredWidth(220);
        tablaProductos.getColumnModel().getColumn(2).setPreferredWidth(90);
        tablaProductos.getColumnModel().getColumn(3).setPreferredWidth(80);
        tablaProductos.getColumnModel().getColumn(4).setPreferredWidth(90);
        tablaProductos.getColumnModel().getColumn(5).setPreferredWidth(60);

        TableColumn colEliminar = tablaProductos.getColumnModel().getColumn(5);
        colEliminar.setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JButton btnBasura = new JButton("🗑");
                btnBasura.setOpaque(false);
                btnBasura.setContentAreaFilled(false);
                btnBasura.setBorderPainted(false);
                btnBasura.setFocusable(false);
                btnBasura.setFont(new Font("Arial", Font.PLAIN, 18));
                return btnBasura;
            }
        });

        tablaProductos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tablaProductos.rowAtPoint(e.getPoint());
                int columna = tablaProductos.columnAtPoint(e.getPoint());
                if (columna == 5 && fila >= 0) {
                    modeloTabla.removeRow(fila);
                    actualizarTotales();
                }
            }
        });

        DefaultTableCellRenderer centrar = new DefaultTableCellRenderer();
        centrar.setHorizontalAlignment(SwingConstants.CENTER);
        tablaProductos.getColumnModel().getColumn(2).setCellRenderer(centrar);
        tablaProductos.getColumnModel().getColumn(3).setCellRenderer(centrar);
        tablaProductos.getColumnModel().getColumn(4).setCellRenderer(centrar);

        JScrollPane spTabla = new JScrollPane(tablaProductos);
        spTabla.setBounds(20, 115, 710, 200);
        spTabla.setBorder(BorderFactory.createLineBorder(grisBorde));
        contenido.add(spTabla);

        JPanel panelObs = new JPanel(null);
        panelObs.setBackground(celesteFondo);
        panelObs.setBounds(20, 325, 450, 100);
        panelObs.setBorder(BorderFactory.createLineBorder(grisBorde));

        JLabel lblObs = new JLabel("Observaciones");
        lblObs.setFont(new Font("Arial", Font.PLAIN, 12));
        lblObs.setBounds(10, 8, 120, 20);
        panelObs.add(lblObs);

        txtObservaciones = new JTextArea("Escribe alguna observación (opcional)");
        txtObservaciones.setBounds(10, 30, 430, 60);
        txtObservaciones.setBorder(BorderFactory.createEmptyBorder());
        txtObservaciones.setBackground(celesteFondo);
        panelObs.add(txtObservaciones);

        contenido.add(panelObs);

        JPanel panelResumen = new JPanel(null);
        panelResumen.setBackground(celesteFondo);
        panelResumen.setBounds(480, 325, 250, 100);
        panelResumen.setBorder(BorderFactory.createLineBorder(grisBorde));

        JLabel lblSub = new JLabel("Subtotal");
        lblSub.setFont(new Font("Arial", Font.PLAIN, 12));
        lblSub.setBounds(10, 10, 80, 20);
        panelResumen.add(lblSub);
        lblSubtotal = new JLabel("$000,000.00");
        lblSubtotal.setBounds(140, 10, 100, 20);
        lblSubtotal.setHorizontalAlignment(SwingConstants.RIGHT);
        panelResumen.add(lblSubtotal);

        JLabel lblIvaTxt = new JLabel("IVA (16%)");
        lblIvaTxt.setFont(new Font("Arial", Font.PLAIN, 12));
        lblIvaTxt.setBounds(10, 35, 80, 20);
        panelResumen.add(lblIvaTxt);
        lblIva = new JLabel("$000,000.00");
        lblIva.setBounds(140, 35, 100, 20);
        lblIva.setHorizontalAlignment(SwingConstants.RIGHT);
        panelResumen.add(lblIva);

        JLabel lblTotalTxt = new JLabel("Total");
        lblTotalTxt.setFont(new Font("Arial", Font.BOLD, 12));
        lblTotalTxt.setBounds(10, 60, 80, 20);
        panelResumen.add(lblTotalTxt);
        lblTotal = new JLabel("$000,000.00");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 12));
        lblTotal.setBounds(140, 60, 100, 20);
        lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
        panelResumen.add(lblTotal);

        contenido.add(panelResumen);

        JButton btnCancelar = new JButton("CANCELAR VENTA");
        btnCancelar.setBackground(rojoBoton);
        btnCancelar.setForeground(blanco);
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 12));
        btnCancelar.setBounds(20, 440, 150, 30);
        btnCancelar.setBorderPainted(false);
        btnCancelar.addActionListener(e -> limpiarTodo());
        contenido.add(btnCancelar);

        JButton btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBackground(grisBoton);
        btnLimpiar.setFont(new Font("Arial", Font.BOLD, 12));
        btnLimpiar.setBounds(550, 440, 80, 30);
        btnLimpiar.setBorderPainted(false);
        btnLimpiar.addActionListener(e -> limpiarTodo());
        contenido.add(btnLimpiar);

        JButton btnGenerar = new JButton("Generar...");
        btnGenerar.setBackground(azulBoton);
        btnGenerar.setForeground(blanco);
        btnGenerar.setFont(new Font("Arial", Font.BOLD, 12));
        btnGenerar.setBounds(640, 440, 90, 30);
        btnGenerar.setBorderPainted(false);
        btnGenerar.addActionListener(e -> guardarVentaYGenerarTicket());
        contenido.add(btnGenerar);

        return contenido;
    }

    private void mostrarListaProductos() {
        String criterio = txtBuscar.getText().trim();
        JDialog dialog = new JDialog(this, "Seleccionar Producto", true);
        dialog.setSize(500, 350);
        dialog.setLocationRelativeTo(this);

        String[] cols = {"ID", "Producto", "Precio"};
        DefaultTableModel modeloLista = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable tablaLista = new JTable(modeloLista);
        tablaLista.setFocusable(false);
        JScrollPane sp = new JScrollPane(tablaLista);
        dialog.add(sp);

        try (Connection con = ConexionBD.getConexion()) {
            String sql = "SELECT id_producto, nombre, precio_venta FROM producto " +
                         "WHERE (nombre LIKE ? OR id_producto LIKE ? OR codigo_barras LIKE ?) AND estado = 1 AND stock > 0";
            PreparedStatement ps = con.prepareStatement(sql);
            String filtro = "%" + criterio + "%";
            ps.setString(1, filtro);
            ps.setString(2, filtro);
            ps.setString(3, filtro);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                modeloLista.addRow(new Object[]{
                    rs.getInt("id_producto"),
                    rs.getString("nombre"),
                    df.format(rs.getDouble("precio_venta"))
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(dialog, "Error: " + e.getMessage());
        }

        tablaLista.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int fila = tablaLista.getSelectedRow();
                    if (fila >= 0) {
                        int id = Integer.parseInt(modeloLista.getValueAt(fila, 0).toString());
                        String nombre = modeloLista.getValueAt(fila, 1).toString();
                        double precio = Double.parseDouble(modeloLista.getValueAt(fila, 2).toString().replace("$", "").replace(",", ""));

                        modeloTabla.addRow(new Object[]{id, nombre, df.format(precio), 1, df.format(precio), ""});
                        actualizarTotales();
                        dialog.dispose();
                    }
                }
            }
        });

        dialog.setVisible(true);
    }

    private void actualizarTotales() {
        subtotalGeneral = 0.0;
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            try {
                double subtotal = Double.parseDouble(modeloTabla.getValueAt(i, 4).toString().replace("$", "").replace(",", ""));
                subtotalGeneral += subtotal;
            } catch (Exception e) { }
        }
        double iva = subtotalGeneral * IVA;
        double total = subtotalGeneral + iva;

        lblSubtotal.setText(df.format(subtotalGeneral));
        lblIva.setText(df.format(iva));
        lblTotal.setText(df.format(total));
    }

    private void guardarVentaYGenerarTicket() {
        if (modeloTabla.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Agrega productos primero");
            return;
        }

        Connection con = null;
        try {
            con = ConexionBD.getConexion();
            con.setAutoCommit(false);

            String metodoSeleccionado = cboMetodoPago.getSelectedItem().toString();
            Integer idMetodo = metodosPago.getOrDefault(metodoSeleccionado, 1);

            double ivaValor = subtotalGeneral * IVA;
            double totalValor = subtotalGeneral + ivaValor;

            String sqlVenta = "INSERT INTO venta (fecha, hora, subtotal, iva, total, observaciones, estado, id_empleado, id_metodo) " +
                              "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement psVenta = con.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS);
            
            psVenta.setDate(1, new java.sql.Date(new Date().getTime()));
            psVenta.setTime(2, java.sql.Time.valueOf(LocalTime.now()));
            psVenta.setDouble(3, subtotalGeneral);
            psVenta.setDouble(4, ivaValor);
            psVenta.setDouble(5, totalValor);
            psVenta.setString(6, txtObservaciones.getText());
            psVenta.setBoolean(7, true);
            psVenta.setInt(8, empleado.getIdEmpleado());
            psVenta.setInt(9, idMetodo);
            
            psVenta.executeUpdate();

            ResultSet rs = psVenta.getGeneratedKeys();
            int idVenta = 0;
            if (rs.next()) idVenta = rs.getInt(1);

            String sqlDetalle = "INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio_unitario, importe) VALUES (?, ?, ?, ?, ?)";
            String sqlStock = "UPDATE producto SET stock = stock - ? WHERE id_producto = ?";

            for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                int idProd = Integer.parseInt(modeloTabla.getValueAt(i, 0).toString());
                int cant = Integer.parseInt(modeloTabla.getValueAt(i, 3).toString());
                double prec = Double.parseDouble(modeloTabla.getValueAt(i, 2).toString().replace("$", "").replace(",", ""));
                double imp = Double.parseDouble(modeloTabla.getValueAt(i, 4).toString().replace("$", "").replace(",", ""));

                PreparedStatement psDet = con.prepareStatement(sqlDetalle);
                psDet.setInt(1, idVenta);
                psDet.setInt(2, idProd);
                psDet.setInt(3, cant);
                psDet.setDouble(4, prec);
                psDet.setDouble(5, imp);
                psDet.executeUpdate();

                PreparedStatement psStk = con.prepareStatement(sqlStock);
                psStk.setInt(1, cant);
                psStk.setInt(2, idProd);
                psStk.executeUpdate();
            }

            String sqlTicket = "INSERT INTO ticket (numero_ticket, fecha_hora, id_venta, id_empleado, total) " +
                               "VALUES (?, NOW(), ?, ?, ?)";
            PreparedStatement psTicket = con.prepareStatement(sqlTicket);
            String numeroTicket = "TKT-" + idVenta + "-" + System.currentTimeMillis();
            psTicket.setString(1, numeroTicket);
            psTicket.setInt(2, idVenta);
            psTicket.setInt(3, empleado.getIdEmpleado());
            psTicket.setDouble(4, totalValor);
            psTicket.executeUpdate();

            con.commit();
            JOptionPane.showMessageDialog(this, " Venta y Ticket guardados correctamente\nN° Ticket: " + numeroTicket);
            limpiarTodo();

        } catch (Exception e) {
            try { if (con != null) con.rollback(); } catch (Exception ex) {}
            JOptionPane.showMessageDialog(this, "❌ Error: " + e.getMessage());
        } finally {
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
    }

    private void limpiarTodo() {
        txtBuscar.setText("");
        txtObservaciones.setText("Escribe alguna observación (opcional)");
        modeloTabla.setRowCount(0);
        subtotalGeneral = 0.0;
        lblSubtotal.setText("$000,000.00");
        lblIva.setText("$000,000.00");
        lblTotal.setText("$000,000.00");
    }
}