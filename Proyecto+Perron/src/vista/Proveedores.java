
/**
 *
 * @author ivanp
 */
package vista;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import modelo.*;
import java.util.List;

public class Proveedores extends JFrame {

    Color turquesa       = new Color(0, 190, 185);
    Color turquesaClaro  = new Color(188, 237, 234);
    Color turquesaOscuro = new Color(0, 160, 155);

    private Empleado empleado;
    private DefaultTableModel modeloTabla;
    private JTable tabla;
    private ProveedorDAO dao = new ProveedorDAO();

    public Proveedores(Empleado empleado) {
        this.empleado = empleado;
        setTitle("Proveedores");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel fondo = new JPanel(new BorderLayout());
        fondo.setBackground(turquesaClaro);
        fondo.add(crearSidebar(), BorderLayout.WEST);

        JPanel principal = new JPanel(new BorderLayout());
        principal.setBackground(turquesaClaro);
        principal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("PROVEEDORES", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setOpaque(true);
        titulo.setBackground(new Color(200, 230, 230));
        titulo.setBorder(BorderFactory.createEmptyBorder(8, 24, 8, 24));

        JPanel panelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTitulo.setBackground(turquesaClaro);
        panelTitulo.add(titulo);

        String[] columnas = {"ID", "NOMBRE", "TELEFONO", "DIRECCION", "TIPO PRODUCTO", "Editar", "Eliminar"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        tabla = new JTable(modeloTabla);
        tabla.setFont(new Font("Arial", Font.PLAIN, 12));
        tabla.setRowHeight(30);
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 11));
        tabla.getTableHeader().setBackground(new Color(240, 240, 240));
        tabla.setShowHorizontalLines(true);
        tabla.setGridColor(new Color(220, 220, 220));
        tabla.setSelectionBackground(turquesaClaro);

        tabla.getColumnModel().getColumn(5).setMaxWidth(40);
        tabla.getColumnModel().getColumn(6).setMaxWidth(40);

        tabla.getColumnModel().getColumn(5).setCellRenderer((t, v, s, f, r, c) -> {
            JLabel l = new JLabel(new ImageIcon(new ImageIcon("Iconos/edit.png").getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH)));
            l.setHorizontalAlignment(SwingConstants.CENTER);
            l.setOpaque(true); l.setBackground(s ? turquesaClaro : Color.WHITE);
            return l;
        });
        tabla.getColumnModel().getColumn(6).setCellRenderer((t, v, s, f, r, c) -> {
            JLabel l = new JLabel(new ImageIcon(new ImageIcon("Iconos/trash.png").getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH)));
            l.setHorizontalAlignment(SwingConstants.CENTER);
            l.setOpaque(true); l.setBackground(s ? turquesaClaro : Color.WHITE);
            return l;
        });

        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int fila = tabla.rowAtPoint(e.getPoint());
                int col  = tabla.columnAtPoint(e.getPoint());
                if (fila < 0) return;
                int id = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
                if (col == 5) abrirFormulario(dao.listarProveedores().get(fila));
                if (col == 6) eliminar(id);
            }
        });

        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBackground(Color.WHITE);
        panelTabla.setBorder(BorderFactory.createLineBorder(new Color(210, 210, 210)));
        panelTabla.add(new JScrollPane(tabla), BorderLayout.CENTER);

        JButton btnAgregar = new JButton("AGREGAR");
        btnAgregar.setBackground(turquesa);
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.setFont(new Font("Arial", Font.BOLD, 13));
        btnAgregar.setBorderPainted(false);
        btnAgregar.setFocusPainted(false);
        btnAgregar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAgregar.setPreferredSize(new Dimension(130, 36));
        btnAgregar.addActionListener(e -> abrirFormulario(null));

        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBtn.setBackground(turquesaClaro);
        panelBtn.add(btnAgregar);

        JPanel centro = new JPanel(new BorderLayout(0, 8));
        centro.setBackground(turquesaClaro);
        centro.add(panelTabla, BorderLayout.CENTER);
        centro.add(panelBtn,   BorderLayout.SOUTH);

        principal.add(panelTitulo, BorderLayout.NORTH);
        principal.add(centro,      BorderLayout.CENTER);

        fondo.add(principal, BorderLayout.CENTER);
        add(fondo);
        setVisible(true);
        cargarTabla();
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        for (Proveedor p : dao.listarProveedores()) {
            modeloTabla.addRow(new Object[]{
                p.getIdProveedor(),
                p.getNombre(),
                p.getTelefono(),
                p.getDireccion(),
                p.getNombreTipo(),
                "", ""
            });
        }
    }

    private void abrirFormulario(Proveedor prov) {
        boolean esEdicion = prov != null;
        JDialog d = new JDialog(this, esEdicion ? "Editar Proveedor" : "AGREGAR PROVEEDORES", true);
        d.setSize(350, 320);
        d.setLocationRelativeTo(this);
        d.setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(5, 1, 8, 8));
        form.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        form.setBackground(Color.WHITE);

        JTextField txtNombre  = new JTextField();
        JTextField txtTelefono= new JTextField();
        JTextField txtDireccion= new JTextField();
        JComboBox<String> cbTipo = new JComboBox<>(new String[]{"Alimentos", "Medicamentos", "Juguetes", "Accesorios", "Higiene", "Varios"});

        if (esEdicion) {
            txtNombre.setText(prov.getNombre());
            txtTelefono.setText(prov.getTelefono());
            txtDireccion.setText(prov.getDireccion());
            cbTipo.setSelectedItem(prov.getNombreTipo());
        }

        form.add(campo("Nombre del proveedor:", txtNombre));
        form.add(campo("Telefono", txtTelefono));
        form.add(campo("Direccion", txtDireccion));
        form.add(campo("Producto", cbTipo));

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        botones.setBackground(Color.WHITE);

        JButton btnGuardar = new JButton("AGREGAR");
        btnGuardar.setBackground(turquesa);
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setFocusPainted(false);
        btnGuardar.addActionListener(e -> {
            try {
                Proveedor p = esEdicion ? prov : new Proveedor();
                p.setNombre(txtNombre.getText());
                p.setTelefono(txtTelefono.getText());
                p.setDireccion(txtDireccion.getText());
                p.setEstado(true);
                p.setIdTipo(cbTipo.getSelectedIndex() + 1);

                boolean ok = esEdicion ? dao.actualizarProveedor(p) : dao.insertarProveedor(p);
                if (ok) { d.dispose(); cargarTabla(); }
                else JOptionPane.showMessageDialog(d, "Error al guardar.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(d, "Error: " + ex.getMessage());
            }
        });

        botones.add(btnGuardar);
        d.add(form,    BorderLayout.CENTER);
        d.add(botones, BorderLayout.SOUTH);
        d.setVisible(true);
    }

    private void eliminar(int id) {
        if (JOptionPane.showConfirmDialog(this, "¿Eliminar proveedor?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
            if (dao.eliminarProveedor(id)) cargarTabla();
    }

    private JPanel campo(String etiqueta, JComponent comp) {
        JPanel p = new JPanel(new BorderLayout(0, 4));
        p.setBackground(Color.WHITE);
        JLabel l = new JLabel(etiqueta);
        l.setFont(new Font("Arial", Font.PLAIN, 11));
        p.add(l,    BorderLayout.NORTH);
        p.add(comp, BorderLayout.CENTER);
        return p;
    }

    private JPanel crearSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(turquesa);
        sidebar.setPreferredSize(new Dimension(200, 600));

        String[][] items = {
            {"vista/Iconos/home.png",             "INICIO"},
            {"vista/Iconos/shopping-cart.png",    "VENTAS"},
            {"vista/Iconos/cube.png",             "PRODUCTOS"},
            {"vista/Iconos/users-alt.png",        "PROVEEDORES"},
            {"vista/Iconos/truck-side.png",       "PEDIDOS"},
            {"vista/Iconos/book-alt.png",         "REPORTES"},
            {"vista/Iconos/users.png",            "USUARIOS"},
            {"vista/Iconos/search-alt.png",       "TICKETS"},
            {"vista/Iconos/sign-out-alt (1).png", "REGRESAR"}
        };

        for (String[] item : items) {
            String ruta      = item[0];
            String texto     = item[1];
            boolean esActivo = texto.equals("PROVEEDORES");

            JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 12));
            fila.setMaximumSize(new Dimension(200, 55));
            fila.setCursor(new Cursor(Cursor.HAND_CURSOR));
            fila.setBackground(esActivo ? turquesaOscuro : turquesa);

            JLabel icono = new JLabel(new ImageIcon(new ImageIcon(ruta).getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
            JLabel lbl = new JLabel(texto);
            lbl.setForeground(Color.WHITE);
            lbl.setFont(new Font("Arial", Font.BOLD, 13));

            fila.add(icono);
            fila.add(lbl);

            Color bgNormal = esActivo ? turquesaOscuro : turquesa;
            fila.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent e) { fila.setBackground(turquesaOscuro); }
                public void mouseExited(java.awt.event.MouseEvent e)  { fila.setBackground(bgNormal); }
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    switch (texto) {
                        case "INICIO"      -> { new Inicio(empleado);      dispose(); }
                        case "VENTAS"      -> { new Ventas(empleado);      dispose(); }
                        case "PRODUCTOS"   -> { new Productos(empleado);   dispose(); }
                        case "PEDIDOS"     -> { new Pedidos(empleado);     dispose(); }
                        case "REPORTES"    -> { new Reportes(empleado);    dispose(); }
                        case "USUARIOS"    -> { new Usuarios(empleado);    dispose(); }
                        case "TICKETS"     -> { new Tickets(empleado);     dispose(); }
                        case "REGRESAR"    -> { new Inicio_sesion();       dispose(); }
                    }
                }
            });
            sidebar.add(fila);
        }
        return sidebar;
    }
}