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

public class Inicio extends JFrame {

    Color turquesa       = new Color(0, 190, 185);
    Color turquesaClaro  = new Color(188, 237, 234);
    Color turquesaOscuro = new Color(0, 160, 155);
    Color blanco        = Color.WHITE;
    Color grisFondo     = new Color(245, 245, 245);
    Color textoGris     = new Color(80, 80, 80);
    Color rojoBoton     = new Color(200, 30, 30);

    private Empleado empleado;
    private JLabel lblVentasDia, lblPedidosPend, lblTotalProd, lblTotalProv;
    private JTable tablaVentas;

    public Inicio(Empleado empleado) {
        this.empleado = empleado;
        setTitle("Inicio - Proyecto Perrón");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        JPanel fondo = new JPanel(new BorderLayout());
        fondo.setBackground(grisFondo);
        fondo.add(crearSidebar(), BorderLayout.WEST);

        JPanel contenido = new JPanel(null);
        contenido.setBackground(grisFondo);

        JPanel cabecera = new JPanel(new BorderLayout());
        cabecera.setBounds(20, 15, 700, 40);
        cabecera.setBackground(turquesaClaro);
        cabecera.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));

        JLabel lblUsuario = new JLabel("Usuario: " + empleado.getNombre() + " | Rol: " + empleado.getNombreRol());
        lblUsuario.setFont(new Font("Arial", Font.PLAIN, 14));
        cabecera.add(lblUsuario, BorderLayout.WEST);

        JButton btnCerrar = new JButton("Cerrar sesión");
        btnCerrar.setBackground(rojoBoton);
        btnCerrar.setForeground(blanco);
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 12));
        btnCerrar.setBorderPainted(false);
        btnCerrar.setFocusPainted(false);
        btnCerrar.addActionListener(e -> { new Inicio_sesion(); dispose(); });
        cabecera.add(btnCerrar, BorderLayout.EAST);

        contenido.add(cabecera);

        JLabel lblTitulo = new JLabel("INICIO / INVENTARIO");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(textoGris);
        lblTitulo.setBounds(20, 60, 300, 30);
        contenido.add(lblTitulo);

        JPanel panelEstadisticas = new JPanel(new GridLayout(1, 4, 15, 0));
        panelEstadisticas.setBounds(20, 95, 700, 70);
        panelEstadisticas.setBackground(grisFondo);

        JPanel tarjeta1 = new JPanel(new BorderLayout());
        tarjeta1.setBackground(blanco);
        tarjeta1.setBorder(BorderFactory.createLineBorder(new Color(220,220,220)));
        JLabel lblTit1 = new JLabel("Ventas del día");
        lblTit1.setFont(new Font("Arial", Font.PLAIN, 12));
        lblTit1.setBorder(BorderFactory.createEmptyBorder(10,10,0,0));
        lblVentasDia = new JLabel("0");
        lblVentasDia.setFont(new Font("Arial", Font.BOLD, 24));
        lblVentasDia.setBorder(BorderFactory.createEmptyBorder(0,10,10,0));
        tarjeta1.add(lblTit1, BorderLayout.NORTH);
        tarjeta1.add(lblVentasDia, BorderLayout.CENTER);
        panelEstadisticas.add(tarjeta1);

        JPanel tarjeta2 = new JPanel(new BorderLayout());
        tarjeta2.setBackground(blanco);
        tarjeta2.setBorder(BorderFactory.createLineBorder(new Color(220,220,220)));
        JLabel lblTit2 = new JLabel("Pedidos pendientes");
        lblTit2.setFont(new Font("Arial", Font.PLAIN, 12));
        lblTit2.setBorder(BorderFactory.createEmptyBorder(10,10,0,0));
        lblPedidosPend = new JLabel("0");
        lblPedidosPend.setFont(new Font("Arial", Font.BOLD, 24));
        lblPedidosPend.setBorder(BorderFactory.createEmptyBorder(0,10,10,0));
        tarjeta2.add(lblTit2, BorderLayout.NORTH);
        tarjeta2.add(lblPedidosPend, BorderLayout.CENTER);
        panelEstadisticas.add(tarjeta2);

        JPanel tarjeta3 = new JPanel(new BorderLayout());
        tarjeta3.setBackground(blanco);
        tarjeta3.setBorder(BorderFactory.createLineBorder(new Color(220,220,220)));
        JLabel lblTit3 = new JLabel("Total productos");
        lblTit3.setFont(new Font("Arial", Font.PLAIN, 12));
        lblTit3.setBorder(BorderFactory.createEmptyBorder(10,10,0,0));
        lblTotalProd = new JLabel("0");
        lblTotalProd.setFont(new Font("Arial", Font.BOLD, 24));
        lblTotalProd.setBorder(BorderFactory.createEmptyBorder(0,10,10,0));
        tarjeta3.add(lblTit3, BorderLayout.NORTH);
        tarjeta3.add(lblTotalProd, BorderLayout.CENTER);
        panelEstadisticas.add(tarjeta3);

        JPanel tarjeta4 = new JPanel(new BorderLayout());
        tarjeta4.setBackground(blanco);
        tarjeta4.setBorder(BorderFactory.createLineBorder(new Color(220,220,220)));
        JLabel lblTit4 = new JLabel("Total proveedores");
        lblTit4.setFont(new Font("Arial", Font.PLAIN, 12));
        lblTit4.setBorder(BorderFactory.createEmptyBorder(10,10,0,0));
        lblTotalProv = new JLabel("0");
        lblTotalProv.setFont(new Font("Arial", Font.BOLD, 24));
        lblTotalProv.setBorder(BorderFactory.createEmptyBorder(0,10,10,0));
        tarjeta4.add(lblTit4, BorderLayout.NORTH);
        tarjeta4.add(lblTotalProv, BorderLayout.CENTER);
        panelEstadisticas.add(tarjeta4);

        contenido.add(panelEstadisticas);

        JLabel lblTabla = new JLabel("Ventas Recientes");
        lblTabla.setFont(new Font("Arial", Font.BOLD, 14));
        lblTabla.setBounds(20, 180, 200, 25);
        contenido.add(lblTabla);

        String[] columnas = {"Fecha", "Producto", "Cantidad", "Total"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        tablaVentas = new JTable(modelo);
        tablaVentas.setRowHeight(28);
        tablaVentas.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaVentas.getTableHeader().setBackground(turquesaClaro);
        tablaVentas.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        JScrollPane sp = new JScrollPane(tablaVentas);
        sp.setBounds(20, 210, 700, 350);
        sp.setBorder(BorderFactory.createLineBorder(new Color(220,220,220)));
        contenido.add(sp);

        cargarDatos();

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
            String nombreIcono = item[0];
            String texto = item[1];
            boolean esActivo = texto.equals("INICIO");

            JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 12));
            fila.setMaximumSize(new Dimension(200, 55));
            fila.setCursor(new Cursor(Cursor.HAND_CURSOR));
            fila.setBackground(esActivo ? turquesaOscuro : turquesa);
            fila.setOpaque(true);

            // ✅ ICONOS AGREGADOS AQUÍ
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
                        case "INICIO":
                            new Inicio(empleado);
                            dispose();
                            break;
                        case "VENTAS":
                            new Ventas(empleado);
                            dispose();
                            break;
                        case "PRODUCTOS":
                            new Productos(empleado);
                            dispose();
                            break;
                        case "PROVEEDORES":
                            new Proveedores(empleado);
                            dispose();
                            break;
                        case "PEDIDOS":
                            new Pedidos(empleado);
                            dispose();
                            break;
                        case "REPORTES":
                            new Reportes(empleado);
                            dispose();
                            break;
                        case "USUARIOS":
                            new Usuarios(empleado);
                            dispose();
                            break;
                        case "TICKETS":
                            new Tickets(empleado);
                            dispose();
                            break;
                        case "REGRESAR":
                            new Inicio_sesion();
                            dispose();
                            break;
                    }
                }
            });
            sidebar.add(fila);
        }
        return sidebar;
    }

    private void cargarDatos() {
        try (Connection con = ConexionBD.getConexion()) {
            ResultSet rs1 = con.createStatement().executeQuery(
                "SELECT COUNT(*) AS total FROM venta WHERE DATE(fecha) = CURDATE() AND estado = 1"
            );
            if (rs1.next()) lblVentasDia.setText(rs1.getString("total"));

            ResultSet rs2 = con.createStatement().executeQuery(
                "SELECT COUNT(*) AS total FROM pedido WHERE id_estado = 1"
            );
            if (rs2.next()) lblPedidosPend.setText(rs2.getString("total"));

            ResultSet rs3 = con.createStatement().executeQuery("SELECT COUNT(*) AS total FROM producto");
            if (rs3.next()) lblTotalProd.setText(rs3.getString("total"));

            ResultSet rs4 = con.createStatement().executeQuery("SELECT COUNT(*) AS total FROM proveedor");
            if (rs4.next()) lblTotalProv.setText(rs4.getString("total"));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar totales: " + e.getMessage());
        }

        DefaultTableModel modelo = (DefaultTableModel) tablaVentas.getModel();
        modelo.setRowCount(0);

        try (Connection con = ConexionBD.getConexion()) {
            String sql = """
                SELECT v.fecha, p.nombre AS producto, dv.cantidad, dv.importe
                FROM venta v
                INNER JOIN detalle_venta dv ON v.id_venta = dv.id_venta
                INNER JOIN producto p ON dv.id_producto = p.id_producto
                WHERE v.estado = 1
                ORDER BY v.fecha DESC
                LIMIT 10
            """;

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            while (rs.next()) {
                modelo.addRow(new Object[]{
                    sdf.format(rs.getTimestamp("fecha")),
                    rs.getString("producto"),
                    rs.getInt("cantidad"),
                    String.format("$ %.2f", rs.getDouble("importe"))
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos: " + e.getMessage());
        }
    }
}