package Vista;



import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/*
 * ============================================================================
 * CAPA: VISTA — ProductoVista
 * ============================================================================
 *
 * PATRÓN MVC — ¿QUÉ ES LA VISTA?
 * La Vista es responsable ÚNICAMENTE de la interfaz gráfica:
 *   - Crear y organizar los componentes visuales (campos, botones, tabla)
 *   - Mostrar datos al usuario
 *   - Recoger datos del usuario
 *
 * La Vista NO DEBE:
 *   - Conectarse a la base de datos
 *   - Ejecutar lógica de negocio
 *   - Decidir qué hacer cuando el usuario hace clic en un botón
 *
 * CAMBIO RESPECTO A PRÁCTICA 2:
 * En Práctica 2, VentanaPrincipal HACÍA TODO:
 *   - Creaba los componentes (VISTA) ✓
 *   - Manejaba eventos de botones (CONTROLADOR) ✗ → ahora en ProductoControlador
 *   - Llamaba al servicio de datos (MODELO) ✗ → ahora el controlador lo hace
 *
 * Ahora VentanaPrincipal se divide en:
 *   - ProductoVista: solo componentes gráficos (esta clase)
 *   - ProductoControlador: maneja eventos y lógica
 *
 * CLAVE: Los botones son PÚBLICOS (o tienen getters) para que el Controlador
 * pueda agregarles ActionListeners. La Vista no sabe qué pasará al hacer clic.
 * ============================================================================
 */
public class ProductoVista extends JFrame {

    // --- Tabla ---
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;

    // --- Campos comunes ---
    private JTextField txtId, txtNombre, txtPrecio, txtCantidad, txtCategoria;
    private JComboBox<String> cmbTipoProducto;

    // --- Campos Alimento ---
    private JTextField txtFechaCaducidad, txtPeso;
    private JCheckBox chkPerecedero;

    // --- Campos Electrónico ---
    private JTextField txtMarca, txtGarantia, txtVoltaje;

    // --- Campos Ropa ---
    private JTextField txtTalla, txtColor, txtMaterial;

    // --- Botones CRUD ---
    private JButton btnAgregar, btnActualizar, btnEliminar, btnBuscar, btnLimpiar;

    // --- Panel de campos específicos con CardLayout ---
    private JPanel panelCamposEspecificos;
    private CardLayout cardLayout;

    /*
     * CONSTRUCTOR:
     * SOLO crea y organiza componentes. NO agrega lógica a los botones.
     * Eso lo hará el Controlador después.
     */
    public ProductoVista() {
        setTitle("Práctica 3: CRUD de Productos — Patrón MVC");
        setSize(950, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));

        // --- NORTH: Título ---
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(39, 174, 96));
        JLabel lblTitulo = new JLabel("Sistema de Gestión de Productos — Práctica 3 (MVC)");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);
        add(panelTitulo, BorderLayout.NORTH);

        // --- CENTER: Tabla ---
        modeloTabla = new DefaultTableModel(
            new String[]{"ID", "Nombre", "Precio", "Cantidad", "Categoría", "Tipo"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaProductos = new JTable(modeloTabla);
        tablaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaProductos.setRowHeight(25);

        JScrollPane scrollTabla = new JScrollPane(tablaProductos);
        scrollTabla.setPreferredSize(new Dimension(900, 250));
        add(scrollTabla, BorderLayout.CENTER);

        // --- SOUTH: Formulario + Botones ---
        JPanel panelInferior = new JPanel(new BorderLayout(5, 5));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        panelInferior.add(crearPanelFormulario(), BorderLayout.CENTER);
        panelInferior.add(crearPanelBotones(), BorderLayout.SOUTH);
        add(panelInferior, BorderLayout.SOUTH);
    }

    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Datos del Producto"));

        // Campos comunes
        JPanel panelComun = new JPanel(new GridLayout(2, 6, 5, 5));

        panelComun.add(new JLabel("Tipo:"));
        cmbTipoProducto = new JComboBox<>(new String[]{"ALIMENTO", "ELECTRONICO", "ROPA"});
        panelComun.add(cmbTipoProducto);

        panelComun.add(new JLabel("ID:"));
        txtId = new JTextField();
        panelComun.add(txtId);

        panelComun.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelComun.add(txtNombre);

        panelComun.add(new JLabel("Precio:"));
        txtPrecio = new JTextField();
        panelComun.add(txtPrecio);

        panelComun.add(new JLabel("Cantidad:"));
        txtCantidad = new JTextField();
        panelComun.add(txtCantidad);

        panelComun.add(new JLabel("Categoría:"));
        txtCategoria = new JTextField();
        panelComun.add(txtCategoria);

        panel.add(panelComun, BorderLayout.NORTH);

        // Campos específicos con CardLayout
        cardLayout = new CardLayout();
        panelCamposEspecificos = new JPanel(cardLayout);
        panelCamposEspecificos.setBorder(BorderFactory.createTitledBorder("Datos Específicos"));

        // Alimento
        JPanel panelAlimento = new JPanel(new GridLayout(1, 6, 5, 5));
        panelAlimento.add(new JLabel("Fecha Cad.:"));
        txtFechaCaducidad = new JTextField();
        panelAlimento.add(txtFechaCaducidad);
        panelAlimento.add(new JLabel("Peso (kg):"));
        txtPeso = new JTextField();
        panelAlimento.add(txtPeso);
        panelAlimento.add(new JLabel("Perecedero:"));
        chkPerecedero = new JCheckBox();
        panelAlimento.add(chkPerecedero);
        panelCamposEspecificos.add(panelAlimento, "ALIMENTO");

        // Electrónico
        JPanel panelElectronico = new JPanel(new GridLayout(1, 6, 5, 5));
        panelElectronico.add(new JLabel("Marca:"));
        txtMarca = new JTextField();
        panelElectronico.add(txtMarca);
        panelElectronico.add(new JLabel("Garantía (meses):"));
        txtGarantia = new JTextField();
        panelElectronico.add(txtGarantia);
        panelElectronico.add(new JLabel("Voltaje:"));
        txtVoltaje = new JTextField();
        panelElectronico.add(txtVoltaje);
        panelCamposEspecificos.add(panelElectronico, "ELECTRONICO");

        // Ropa
        JPanel panelRopa = new JPanel(new GridLayout(1, 6, 5, 5));
        panelRopa.add(new JLabel("Talla:"));
        txtTalla = new JTextField();
        panelRopa.add(txtTalla);
        panelRopa.add(new JLabel("Color:"));
        txtColor = new JTextField();
        panelRopa.add(txtColor);
        panelRopa.add(new JLabel("Material:"));
        txtMaterial = new JTextField();
        panelRopa.add(txtMaterial);
        panelCamposEspecificos.add(panelRopa, "ROPA");

        panel.add(panelCamposEspecificos, BorderLayout.CENTER);
        return panel;
    }

    /*
     * PUNTO CLAVE MVC:
     * Los botones se CREAN aquí (Vista), pero NO tienen ActionListener.
     * El Controlador obtendrá referencias a estos botones mediante getters
     * y les asignará la acción correspondiente.
     *
     * La Vista dice: "Aquí hay un botón que dice Agregar."
     * El Controlador dice: "Cuando lo presionen, yo haré esto."
     */
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        btnAgregar = new JButton("Agregar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnBuscar = new JButton("Buscar por ID");
        btnLimpiar = new JButton("Limpiar");

        // Estilo — colores oscuros para visibilidad en Windows LAF
        Color colorBtn = new Color(0, 100, 60);
        Color colorBtnEliminar = new Color(140, 20, 20);
        for (JButton btn : new JButton[]{btnAgregar, btnActualizar, btnBuscar, btnLimpiar}) {
            btn.setBackground(colorBtn);
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setOpaque(true);
            btn.setBorderPainted(false);
        }
        btnEliminar.setBackground(colorBtnEliminar);
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFocusPainted(false);
        btnEliminar.setOpaque(true);
        btnEliminar.setBorderPainted(false);

        panel.add(btnAgregar);
        panel.add(btnActualizar);
        panel.add(btnEliminar);
        panel.add(btnBuscar);
        panel.add(btnLimpiar);

        return panel;
    }

    // ========================================================================
    // GETTERS — El Controlador los usa para acceder a los componentes
    // ========================================================================
    /*
     * PATRÓN MVC — COMUNICACIÓN VISTA ↔ CONTROLADOR:
     * La Vista expone sus componentes mediante getters.
     * El Controlador usa estos getters para:
     *   1. Leer datos del formulario (getText())
     *   2. Escribir datos en el formulario (setText())
     *   3. Agregar listeners a botones (addActionListener())
     *   4. Actualizar la tabla (modeloTabla)
     */

    public JTable getTablaProductos() { return tablaProductos; }
    public DefaultTableModel getModeloTabla() { return modeloTabla; }

    // Campos comunes
    public JTextField getTxtId() { return txtId; }
    public JTextField getTxtNombre() { return txtNombre; }
    public JTextField getTxtPrecio() { return txtPrecio; }
    public JTextField getTxtCantidad() { return txtCantidad; }
    public JTextField getTxtCategoria() { return txtCategoria; }
    public JComboBox<String> getCmbTipoProducto() { return cmbTipoProducto; }

    // Campos Alimento
    public JTextField getTxtFechaCaducidad() { return txtFechaCaducidad; }
    public JTextField getTxtPeso() { return txtPeso; }
    public JCheckBox getChkPerecedero() { return chkPerecedero; }

    // Campos Electrónico
    public JTextField getTxtMarca() { return txtMarca; }
    public JTextField getTxtGarantia() { return txtGarantia; }
    public JTextField getTxtVoltaje() { return txtVoltaje; }

    // Campos Ropa
    public JTextField getTxtTalla() { return txtTalla; }
    public JTextField getTxtColor() { return txtColor; }
    public JTextField getTxtMaterial() { return txtMaterial; }

    // Botones
    public JButton getBtnAgregar() { return btnAgregar; }
    public JButton getBtnActualizar() { return btnActualizar; }
    public JButton getBtnEliminar() { return btnEliminar; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JButton getBtnLimpiar() { return btnLimpiar; }

    // CardLayout
    public CardLayout getCardLayout() { return cardLayout; }
    public JPanel getPanelCamposEspecificos() { return panelCamposEspecificos; }

    // ========================================================================
    // MÉTODOS DE LA VISTA — Acciones visuales que el Controlador puede invocar
    // ========================================================================

    // Muestra un mensaje informativo
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    // Muestra un mensaje de error
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Muestra diálogo de confirmación, retorna true si el usuario acepta
    public boolean confirmar(String mensaje) {
        return JOptionPane.showConfirmDialog(this, mensaje,
            "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    // Limpia todos los campos del formulario
    public void limpiarFormulario() {
        txtId.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
        txtCantidad.setText("");
        txtCategoria.setText("");
        txtFechaCaducidad.setText("");
        txtPeso.setText("");
        chkPerecedero.setSelected(false);
        txtMarca.setText("");
        txtGarantia.setText("");
        txtVoltaje.setText("");
        txtTalla.setText("");
        txtColor.setText("");
        txtMaterial.setText("");
        tablaProductos.clearSelection();
    }
}
