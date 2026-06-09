/**
 *
 * @author ivanp
 */

package Vista;
 
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import Modelo.Categorias;
 
/*
 * ============================================================================
 * CAPA: VISTA — RompecabezasVista
 * ============================================================================
 * Solo crea y organiza componentes visuales.
 * NO tiene lógica de negocio ni conexión a BD.
 * El Controlador le agrega los ActionListeners a los botones.
 * ============================================================================
 */
public class RompecabezasVista extends JFrame {
 
    // --- Tabla ---
    private JTable tablaRompecabezas;
    private DefaultTableModel modeloTabla;
 
    // --- Campos del formulario ---
    private JTextField txtId, txtNombre, txtPiezas;
    private JComboBox<String> cmbDificultad;
    private JComboBox<String> cmbEstado;
    private JComboBox<Categorias> cmbCategoria;
 
    // --- Botones CRUD ---
    private JButton btnAgregar, btnActualizar, btnEliminar, btnBuscar, btnLimpiar, btnVerAvances;
 
    public RompecabezasVista() {
        setTitle("CRUD Rompecabezas — Patrón MVC");
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        inicializarComponentes();
    }
 
    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));
 
        // --- NORTH: Título ---
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(52, 100, 180));
        JLabel lblTitulo = new JLabel("Sistema de Gestión de Rompecabezas — MVC");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);
        add(panelTitulo, BorderLayout.NORTH);
 
        // --- CENTER: Tabla ---
        modeloTabla = new DefaultTableModel(
            new String[]{"ID", "Nombre", "Piezas", "Dificultad", "Estado", "Categoría"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
 
        tablaRompecabezas = new JTable(modeloTabla);
        tablaRompecabezas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaRompecabezas.setRowHeight(25);
 
        JScrollPane scrollTabla = new JScrollPane(tablaRompecabezas);
        scrollTabla.setPreferredSize(new Dimension(900, 280));
        add(scrollTabla, BorderLayout.CENTER);
 
        // --- SOUTH: Formulario + Botones ---
        JPanel panelInferior = new JPanel(new BorderLayout(5, 5));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        panelInferior.add(crearPanelFormulario(), BorderLayout.CENTER);
        panelInferior.add(crearPanelBotones(), BorderLayout.SOUTH);
        add(panelInferior, BorderLayout.SOUTH);
    }
 
    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new GridLayout(2, 6, 8, 8));
        panel.setBorder(BorderFactory.createTitledBorder("Datos del Rompecabezas"));
 
        panel.add(new JLabel("ID:"));
        txtId = new JTextField();
        panel.add(txtId);
 
        panel.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panel.add(txtNombre);
 
        panel.add(new JLabel("Piezas:"));
        txtPiezas = new JTextField();
        panel.add(txtPiezas);
 
        panel.add(new JLabel("Dificultad:"));
        cmbDificultad = new JComboBox<>(new String[]{"Fácil", "Media", "Difícil"});
        panel.add(cmbDificultad);
 
        panel.add(new JLabel("Estado:"));
        cmbEstado = new JComboBox<>(new String[]{"Pendiente", "En proceso", "Terminado"});
        panel.add(cmbEstado);
 
        panel.add(new JLabel("Categoría:"));
        cmbCategoria = new JComboBox<>();
        panel.add(cmbCategoria);
 
        return panel;
    }
 
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
 
        btnAgregar    = new JButton("Agregar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar   = new JButton("Eliminar");
        btnBuscar     = new JButton("Buscar por ID");
        btnLimpiar    = new JButton("Limpiar");
        btnVerAvances = new JButton("Ver Avances");
 
        Color colorBtn = new Color(30, 80, 160);
        Color colorEliminar = new Color(160, 30, 30);
 
        for (JButton btn : new JButton[]{btnAgregar, btnActualizar, btnBuscar, btnLimpiar}) {
            btn.setBackground(colorBtn);
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setOpaque(true);
            btn.setBorderPainted(false);
        }
        btnEliminar.setBackground(colorEliminar);
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFocusPainted(false);
        btnEliminar.setOpaque(true);
        btnEliminar.setBorderPainted(false);
 
        btnVerAvances.setBackground(new Color(100, 40, 160));
        btnVerAvances.setForeground(Color.WHITE);
        btnVerAvances.setFocusPainted(false);
        btnVerAvances.setOpaque(true);
        btnVerAvances.setBorderPainted(false);
 
        panel.add(btnAgregar);
        panel.add(btnActualizar);
        panel.add(btnEliminar);
        panel.add(btnBuscar);
        panel.add(btnLimpiar);
        panel.add(btnVerAvances);
 
        return panel;
    }
 
    // ========================================================================
    // GETTERS — El Controlador los usa para leer/escribir datos y agregar listeners
    // ========================================================================
 
    public JTable getTablaRompecabezas() { return tablaRompecabezas; }
    public DefaultTableModel getModeloTabla() { return modeloTabla; }
 
    public JTextField getTxtId()     { return txtId; }
    public JTextField getTxtNombre() { return txtNombre; }
    public JTextField getTxtPiezas() { return txtPiezas; }
 
    public JComboBox<String>     getCmbDificultad() { return cmbDificultad; }
    public JComboBox<String>     getCmbEstado()     { return cmbEstado; }
    public JComboBox<Categorias> getCmbCategoria()  { return cmbCategoria; }
 
    public JButton getBtnAgregar()    { return btnAgregar; }
    public JButton getBtnActualizar() { return btnActualizar; }
    public JButton getBtnEliminar()   { return btnEliminar; }
    public JButton getBtnBuscar()     { return btnBuscar; }
    public JButton getBtnLimpiar()    { return btnLimpiar; }
    public JButton getBtnVerAvances() { return btnVerAvances; }
 
    // ========================================================================
    // MÉTODOS DE LA VISTA
    // ========================================================================
 
    // Carga las categorías en el ComboBox (llamado por el Controlador al inicio)
    public void cargarCategorias(List<Categorias> categorias) {
        cmbCategoria.removeAllItems();
        for (Categorias c : categorias) {
            cmbCategoria.addItem(c);
        }
    }
 
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
 
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
 
    public boolean confirmar(String mensaje) {
        return JOptionPane.showConfirmDialog(this, mensaje,
            "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }
 
    public void limpiarFormulario() {
        txtId.setText("");
        txtNombre.setText("");
        txtPiezas.setText("");
        cmbDificultad.setSelectedIndex(0);
        cmbEstado.setSelectedIndex(0);
        if (cmbCategoria.getItemCount() > 0) cmbCategoria.setSelectedIndex(0);
        tablaRompecabezas.clearSelection();
    }
}