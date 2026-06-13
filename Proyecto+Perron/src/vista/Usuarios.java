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

public class Usuarios extends JFrame {

    // COLORES DEL SISTEMA
    Color turquesa       = new Color(0, 190, 185);
    Color turquesaClaro  = new Color(188, 237, 234);
    Color turquesaOscuro = new Color(0, 160, 155);
    Color blanco        = Color.WHITE;
    Color grisFondo     = new Color(245, 245, 245);
    Color textoGris     = new Color(80, 80, 80);

    private Empleado empleado;
    private DefaultTableModel modelo;
    private JTable tabla;
    private int idSeleccionado = -1;

    // Campos del formulario
    private JTextField txtNombre, txtTelefono, txtUsuario, txtContrasena;
    private JComboBox<String> cbRol, cbEstado;

    public Usuarios(Empleado empleado) {
        this.empleado = empleado;
        setTitle("Usuarios - Proyecto Perrón");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        JPanel fondo = new JPanel(new BorderLayout());
        fondo.setBackground(grisFondo);
        fondo.add(crearSidebar(), BorderLayout.WEST);

        JPanel contenido = new JPanel(null);
        contenido.setBackground(grisFondo);

        // Título
        JLabel lblTitulo = new JLabel("Gestión de Usuarios");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(textoGris);
        lblTitulo.setBounds(20, 15, 300, 30);
        contenido.add(lblTitulo);

        // Botón Nuevo Usuario
        JButton btnNuevo = new JButton("+ Nuevo Usuario");
        btnNuevo.setBackground(turquesa);
        btnNuevo.setForeground(blanco);
        btnNuevo.setFont(new Font("Arial", Font.BOLD, 13));
        btnNuevo.setBorderPainted(false);
        btnNuevo.setFocusPainted(false);
        btnNuevo.setBounds(580, 15, 130, 30);
        btnNuevo.addActionListener(e -> abrirFormulario(-1));
        contenido.add(btnNuevo);

        // Tabla
        String[] columnas = {"ID", "Nombre", "Teléfono", "Usuario", "Contraseña", "Estado", "Rol", "Acciones"};
        modelo = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int column) {return false;}
        };

        tabla = new JTable(modelo);
        tabla.setRowHeight(30);
        tabla.setFont(new Font("Arial", Font.PLAIN, 12));
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tabla.getTableHeader().setBackground(turquesaClaro);
        tabla.getTableHeader().setForeground(Color.BLACK);
        tabla.getColumnModel().getColumn(0).setMinWidth(0);
        tabla.getColumnModel().getColumn(0).setMaxWidth(0); // Ocultar ID

        // Colorear estado en la tabla
        DefaultTableCellRenderer rendererEstado = new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (value != null) {
                    if (value.toString().equals("Activo")) {
                        c.setBackground(new Color(200, 255, 200));
                        c.setForeground(new Color(0,100,0));
                    } else {
                        c.setBackground(new Color(255, 200, 200));
                        c.setForeground(new Color(150,0,0));
                    }
                }
                return c;
            }
        };
        tabla.getColumnModel().getColumn(5).setCellRenderer(rendererEstado);

        // Evento clic para editar
        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tabla.getSelectedRow();
                int col = tabla.columnAtPoint(e.getPoint());
                if (fila >= 0 && col == 7) {
                    int id = (int) modelo.getValueAt(fila, 0);
                    abrirFormulario(id);
                }
            }
        });

        
        DefaultTableCellRenderer rendererAcciones = new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel lbl = new JLabel();
                lbl.setHorizontalAlignment(SwingConstants.CENTER);
                lbl.setOpaque(true);
                // Ruta absoluta desde el paquete
                java.net.URL urlEditar = getClass().getClassLoader().getResource("vista/iconos/pencil.png");
                Icon editar = new ImageIcon(new ImageIcon(urlEditar).getImage().getScaledInstance(16,16,Image.SCALE_SMOOTH));
                JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
                p.setBackground(isSelected ? new Color(220,240,240) : blanco);
                p.add(new JLabel(editar));
                return p;
            }
        };
        tabla.getColumnModel().getColumn(7).setCellRenderer(rendererAcciones);

        JScrollPane spTabla = new JScrollPane(tabla);
        spTabla.setBounds(20, 60, 700, 460);
        spTabla.setBorder(BorderFactory.createLineBorder(new Color(220,220,220)));
        contenido.add(spTabla);

        cargarUsuarios();

        fondo.add(contenido, BorderLayout.CENTER);
        add(fondo);
        setVisible(true);
    }

    //  MENÚ LATERAL 
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
            {"sign-out-alt.png", "REGRESAR"}
        };

        for (String[] item : items) {
            String nombreImagen = item[0];
            String texto = item[1];
            boolean esActivo = texto.equals("USUARIOS");

            JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 12));
            fila.setMaximumSize(new Dimension(200, 55));
            fila.setCursor(new Cursor(Cursor.HAND_CURSOR));
            fila.setBackground(esActivo ? turquesaOscuro : turquesa);

            
            java.net.URL urlIcono = getClass().getClassLoader().getResource("vista/iconos/" + nombreImagen);
            JLabel icono = new JLabel(new ImageIcon(new ImageIcon(urlIcono).getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
            
            JLabel lbl = new JLabel(texto);
            lbl.setForeground(blanco);
            lbl.setFont(new Font("Arial", Font.BOLD, 13));

            fila.add(icono);
            fila.add(lbl);

            Color bgNormal = esActivo ? turquesaOscuro : turquesa;
            fila.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { fila.setBackground(turquesaOscuro); }
                public void mouseExited(MouseEvent e)  { fila.setBackground(bgNormal); }
                public void mouseClicked(MouseEvent e) {
                    switch (texto) {
                        case "INICIO"      -> { new Inicio(empleado); dispose(); }
                        case "VENTAS"      -> { new Ventas(empleado); dispose(); }
                        case "PRODUCTOS"   -> { new Productos(empleado); dispose(); }
                        case "PROVEEDORES" -> { new Proveedores(empleado); dispose(); }
                        case "PEDIDOS"     -> { new Pedidos(empleado); dispose(); }
                        case "REPORTES"    -> { new Reportes(empleado); dispose(); }
                        case "TICKETS"     -> { new Tickets(empleado); dispose(); }
                        case "REGRESAR"    -> { new Inicio_sesion(); dispose(); }
                    }
                }
            });
            sidebar.add(fila);
        }
        return sidebar;
    }

    
    private void cargarUsuarios() {
        modelo.setRowCount(0);
        String sql = """
            SELECT e.id_empleado, e.nombre, e.telefono, e.usuario, e.contraseña, 
                   CASE WHEN e.estado = TRUE THEN 'Activo' ELSE 'Inactivo' END AS estado,
                   r.nombre AS rol
            FROM empleado e
            JOIN rol r ON e.id_rol = r.id_rol
            ORDER BY e.id_empleado ASC
        """;

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("id_empleado"),
                    rs.getString("nombre"),
                    rs.getString("telefono"),
                    rs.getString("usuario"),
                    rs.getString("contraseña"),
                    rs.getString("estado"),
                    rs.getString("rol"),
                    ""
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    // FORMULARIO AGREGAR / EDITAR
    private void abrirFormulario(int id) {
        idSeleccionado = id;
        JFrame frm = new JFrame(id == -1 ? "Nuevo Usuario" : "Editar Usuario");
        frm.setSize(400, 320);
        frm.setLocationRelativeTo(this);
        frm.setResizable(false);

        JPanel p = new JPanel(null);
        p.setBackground(grisFondo);

        JLabel lblNombre = new JLabel("Nombre:"); lblNombre.setBounds(20,20,100,25); p.add(lblNombre);
        txtNombre = new JTextField(); txtNombre.setBounds(120,20,220,25); p.add(txtNombre);

        JLabel lblTel = new JLabel("Teléfono:"); lblTel.setBounds(20,55,100,25); p.add(lblTel);
        txtTelefono = new JTextField(); txtTelefono.setBounds(120,55,220,25); p.add(txtTelefono);

        JLabel lblUser = new JLabel("Usuario:"); lblUser.setBounds(20,90,100,25); p.add(lblUser);
        txtUsuario = new JTextField(); txtUsuario.setBounds(120,90,220,25); p.add(txtUsuario);

        JLabel lblPass = new JLabel("Contraseña:"); lblPass.setBounds(20,125,100,25); p.add(lblPass);
        txtContrasena = new JTextField(); txtContrasena.setBounds(120,125,220,25); p.add(txtContrasena);

        JLabel lblRol = new JLabel("Rol:"); lblRol.setBounds(20,160,100,25); p.add(lblRol);
        cbRol = new JComboBox<>(); cbRol.setBounds(120,160,220,25); p.add(cbRol);

        JLabel lblEstado = new JLabel("Estado:"); lblEstado.setBounds(20,195,100,25); p.add(lblEstado);
        cbEstado = new JComboBox<>(new String[]{"Activo", "Inactivo"}); cbEstado.setBounds(120,195,220,25); p.add(cbEstado);

        try (Connection con = ConexionBD.getConexion(); ResultSet rs = con.createStatement().executeQuery("SELECT id_rol, nombre FROM rol")) {
            while(rs.next()) cbRol.addItem(rs.getInt(1)+" - "+rs.getString(2));
        } catch (Exception e) { e.printStackTrace(); }

        if(id != -1) {
            try (Connection con = ConexionBD.getConexion();
                 PreparedStatement ps = con.prepareStatement("SELECT * FROM empleado WHERE id_empleado=?")) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if(rs.next()) {
                    txtNombre.setText(rs.getString("nombre"));
                    txtTelefono.setText(rs.getString("telefono"));
                    txtUsuario.setText(rs.getString("usuario"));
                    txtContrasena.setText(rs.getString("contraseña"));
                    cbEstado.setSelectedIndex(rs.getBoolean("estado") ? 0 : 1);
                    int rol = rs.getInt("id_rol");
                    for(int i=0; i<cbRol.getItemCount(); i++) {
                        if(cbRol.getItemAt(i).startsWith(rol+" -")) {
                            cbRol.setSelectedIndex(i);
                            break;
                        }
                    }
                }
            } catch (Exception e) { e.printStackTrace(); }
        }

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(70, 230, 100, 30);
        btnGuardar.setBackground(turquesa);
        btnGuardar.setForeground(blanco);
        btnGuardar.addActionListener(e -> guardarDatos(frm));
        p.add(btnGuardar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(190, 230, 100, 30);
        btnCancelar.addActionListener(e -> frm.dispose());
        p.add(btnCancelar);

        frm.add(p);
        frm.setVisible(true);
    }

    // ✅ GUARDAR DATOS
    private void guardarDatos(JFrame frm) {
        if(txtNombre.getText().isBlank() || txtUsuario.getText().isBlank() || txtContrasena.getText().isBlank()) {
            JOptionPane.showMessageDialog(frm, "Nombre, Usuario y Contraseña son obligatorios");
            return;
        }

        String sql;
        if(idSeleccionado == -1) {
            sql = "INSERT INTO empleado (nombre, telefono, usuario, contraseña, estado, id_rol) VALUES (?, ?, ?, ?, ?, ?)";
        } else {
            sql = "UPDATE empleado SET nombre=?, telefono=?, usuario=?, contraseña=?, estado=?, id_rol=? WHERE id_empleado=?";
        }

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, txtNombre.getText().trim());
            ps.setString(2, txtTelefono.getText().trim());
            ps.setString(3, txtUsuario.getText().trim());
            ps.setString(4, txtContrasena.getText().trim());
            ps.setBoolean(5, cbEstado.getSelectedIndex() == 0);
            int idRol = Integer.parseInt(cbRol.getSelectedItem().toString().split(" - ")[0]);
            ps.setInt(6, idRol);

            if(idSeleccionado != -1) ps.setInt(7, idSeleccionado);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(frm, "✅ Guardado correctamente");
            frm.dispose();
            cargarUsuarios();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(frm, "❌ Error: " + e.getMessage());
        }
    }
}