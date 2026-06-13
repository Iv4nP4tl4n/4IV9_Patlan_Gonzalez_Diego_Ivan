package Controlador;



import Vista.ProductoVista;
import Modelo.*;


import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * ============================================================================
 * CAPA: CONTROLADOR — ProductoControlador
 * ============================================================================
 *
 * PATRÓN MVC — ¿QUÉ ES EL CONTROLADOR?
 * El Controlador es el INTERMEDIARIO entre la Vista y el Modelo.
 * Recibe los EVENTOS del usuario (clics, selecciones) desde la Vista,
 * ejecuta la LÓGICA correspondiente usando el Modelo, y actualiza la Vista
 * con los resultados.
 *
 * RESPONSABILIDADES DEL CONTROLADOR:
 *   1. Escuchar eventos de la Vista (botones, tabla, combo box)
 *   2. Leer datos del formulario de la Vista
 *   3. Validar datos antes de enviarlos al Modelo
 *   4. Llamar al Modelo (DAO) para operaciones CRUD
 *   5. Actualizar la Vista con el resultado (tabla, mensajes)
 *   6. Manejar errores y mostrar mensajes apropiados
 *
 * CAMBIO RESPECTO A PRÁCTICA 2:
 * En Práctica 2, toda esta lógica estaba DENTRO de VentanaPrincipal.
 * Los métodos accionAgregar(), accionActualizar(), etc. vivían en la Vista.
 * Ahora están aquí, en su propio lugar.
 *
 * FLUJO COMPLETO:
 *
 *   1. Usuario hace clic en "Agregar" (evento en la VISTA)
 *   2. El CONTROLADOR captura el evento (ActionListener)
 *   3. El CONTROLADOR lee los datos del formulario (desde la VISTA)
 *   4. El CONTROLADOR construye un objeto Producto (MODELO)
 *   5. El CONTROLADOR llama a dao.agregar() (MODELO)
 *   6. El CONTROLADOR actualiza la tabla (VISTA)
 *   7. El CONTROLADOR muestra mensaje de éxito (VISTA)
 *
 * ¿POR QUÉ SEPARAR?
 *   - Si cambiamos la GUI (ej: de Swing a JavaFX), el Controlador se adapta
 *     pero el Modelo NO cambia.
 *   - Si cambiamos la BD (ej: de MySQL a PostgreSQL), el Modelo se adapta
 *     pero la Vista y el Controlador NO cambian.
 *   - Más fácil de mantener, probar y extender.
 * ============================================================================
 */
public class ProductoControlador {

    // Referencias a las otras dos capas
    private ProductoDAO dao;     // MODELO — acceso a datos
    private ProductoVista vista; // VISTA — interfaz gráfica

    /*
     * CONSTRUCTOR:
     * Recibe el Modelo (DAO) y la Vista como parámetros.
     * El Controlador no CREA ni el Modelo ni la Vista, solo los CONECTA.
     * Esto se llama INYECCIÓN DE DEPENDENCIAS — las dependencias se
     * pasan desde afuera (Main) en lugar de crearlas internamente.
     */
    public ProductoControlador(ProductoDAO dao, ProductoVista vista) throws Exception {
        this.dao = dao;
        this.vista = vista;

        // Registrar los eventos (conectar botones con acciones)
        inicializarEventos();

        // Cargar datos iniciales en la tabla
        cargarTabla();
    }

    /*
     * ========================================================================
     * REGISTRO DE EVENTOS
     * ========================================================================
     *
     * Aquí conectamos cada botón de la VISTA con su acción en el CONTROLADOR.
     * La Vista no sabe qué hará cada botón. El Controlador lo decide.
     *
     * COMPARACIÓN CON PRÁCTICA 2:
     * Antes:  btnAgregar.addActionListener(e -> accionAgregar());  // DENTRO de la Vista
     * Ahora:  vista.getBtnAgregar().addActionListener(e -> agregar()); // FUERA de la Vista
     *
     * La Vista solo expone los botones. El Controlador les da comportamiento.
     * ========================================================================
     */
    private void inicializarEventos() {
        vista.getBtnAgregar().setEnabled(false);
        vista.getBtnActualizar().setEnabled(false);
        vista.getBtnEliminar().setEnabled(false);
        vista.getBtnBuscar().setEnabled(false);
        vista.getBtnLimpiar().addActionListener(e -> vista.limpiarFormulario());

        // Solo se usa un panel de detalles adaptado a rompecabezas
        vista.getCmbTipoProducto().addActionListener(e -> cambiarCamposEspecificos());

        // Clic en fila de la tabla → cargar datos en formulario
        vista.getTablaProductos().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    cargarProductoSeleccionado();
                } catch (Exception ex) {
                    Logger.getLogger(ProductoControlador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // ========================================================================
    // ACCIONES CRUD — la lógica que antes estaba en VentanaPrincipal
    // ========================================================================

    /*
     * AGREGAR:
     * 1. Lee datos del formulario (Vista)
     * 2. Construye objeto Producto (Modelo)
     * 3. Llama al DAO para guardar (Modelo)
     * 4. Actualiza tabla y muestra mensaje (Vista)
     */
    private void agregar() throws Exception {
        try {
            Producto producto = construirProductoDesdeFormulario();
            if (producto == null) return;

            dao.agregar(producto);
            cargarTabla();
            vista.limpiarFormulario();
            vista.mostrarMensaje("Producto agregado exitosamente.");
        } catch (NumberFormatException ex) {
            vista.mostrarError("Verifique que los campos numéricos sean válidos.");
        } catch (SQLException ex) {
            vista.mostrarError("Error al agregar: " + ex.getMessage());
        }
    }

    private void actualizar() throws Exception {
        try {
            Producto producto = construirProductoDesdeFormulario();
            if (producto == null) return;

            dao.actualizar(producto);
            cargarTabla();
            vista.limpiarFormulario();
            vista.mostrarMensaje("Producto actualizado exitosamente.");
        } catch (NumberFormatException ex) {
            vista.mostrarError("Verifique que los campos numéricos sean válidos.");
        } catch (SQLException ex) {
            vista.mostrarError("Error al actualizar: " + ex.getMessage());
        }
    }

    private void eliminar() throws Exception {
        String idStr = vista.getTxtId().getText().trim();
        if (idStr.isEmpty()) {
            vista.mostrarError("Ingrese el ID del producto a eliminar.");
            return;
        }

        int id = Integer.parseInt(idStr);

        if (vista.confirmar("¿Está seguro de eliminar el producto con ID " + id + "?")) {
            try {
                dao.eliminar(id);
                cargarTabla();
                vista.limpiarFormulario();
                vista.mostrarMensaje("Producto eliminado.");
            } catch (SQLException ex) {
                vista.mostrarError("Error al eliminar: " + ex.getMessage());
            }
        }
    }

    private void buscar() throws Exception {
        String idStr = vista.getTxtId().getText().trim();
        if (idStr.isEmpty()) {
            vista.mostrarError("Ingrese el ID del producto a buscar.");
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            Producto producto = dao.buscarPorId(id);

            if (producto != null) {
                cargarProductoEnFormulario(producto);
                vista.mostrarMensaje("Producto encontrado:\n" + producto.mostrarDetalle());
            } else {
                vista.mostrarError("No se encontró un producto con ID " + id);
            }
        } catch (SQLException ex) {
            vista.mostrarError("Error al buscar: " + ex.getMessage());
        }
    }

    // ========================================================================
    // MÉTODOS AUXILIARES
    // ========================================================================

    // Cambia los campos específicos según el tipo seleccionado
    private void cambiarCamposEspecificos() {
        String tipo = (String) vista.getCmbTipoProducto().getSelectedItem();
        vista.getCardLayout().show(vista.getPanelCamposEspecificos(), tipo);
    }

    /*
     * Lee datos de la VISTA y construye un objeto del MODELO.
     * Este método es el PUENTE entre Vista y Modelo:
     * toma datos visuales y los convierte en objetos de negocio.
     */
    private Producto construirProductoDesdeFormulario() {
        if (vista.getTxtId().getText().trim().isEmpty() ||
            vista.getTxtNombre().getText().trim().isEmpty()) {
            vista.mostrarError("ID y Nombre son obligatorios.");
            return null;
        }

        int id = Integer.parseInt(vista.getTxtId().getText().trim());
        String nombre = vista.getTxtNombre().getText().trim();
        double precio = Double.parseDouble(vista.getTxtPrecio().getText().trim());
        int cantidad = Integer.parseInt(vista.getTxtCantidad().getText().trim());
        String categoria = vista.getTxtCategoria().getText().trim();
        String tipo = (String) vista.getCmbTipoProducto().getSelectedItem();

        switch (tipo) {
            case "ALIMENTO":
                return new Alimento(id, nombre, precio, cantidad, categoria,
                    vista.getTxtFechaCaducidad().getText().trim(),
                    vista.getChkPerecedero().isSelected(),
                    parseDoubleSeguro(vista.getTxtPeso().getText()));

            case "ELECTRONICO":
                return new Electronico(id, nombre, precio, cantidad, categoria,
                    vista.getTxtMarca().getText().trim(),
                    parseIntSeguro(vista.getTxtGarantia().getText()),
                    parseDoubleSeguro(vista.getTxtVoltaje().getText()));

            case "ROPA":
                return new Ropa(id, nombre, precio, cantidad, categoria,
                    vista.getTxtTalla().getText().trim(),
                    vista.getTxtColor().getText().trim(),
                    vista.getTxtMaterial().getText().trim());

            default:
                return new Producto(id, nombre, precio, cantidad, categoria);
        }
    }

    /*
     * Carga todos los productos del MODELO y los muestra en la VISTA.
     * CONTROLADOR como puente: pide datos al DAO, los pone en la tabla.
     */
    private void cargarTabla() throws Exception {
        DefaultTableModel modelo = vista.getModeloTabla();
        modelo.setRowCount(0);

        try {
            List<Producto> productos = dao.listarTodos();
            for (Producto p : productos) {
                modelo.addRow(new Object[]{
                    p.getId(),
                    p.getNombre(),
                    p.getPiezas(),
                    p.getDificultad(),
                    p.getEstado(),
                    p.getCategoria(),
                    p.getAvance()
                });
            }
        } catch (SQLException ex) {
            vista.mostrarError("Error al cargar rompecabezas: " + ex.getMessage());
        }
    }

    // Al hacer clic en una fila, carga sus datos en el formulario
    private void cargarProductoSeleccionado() throws Exception {
        int fila = vista.getTablaProductos().getSelectedRow();
        if (fila < 0) return;

        int id = (int) vista.getModeloTabla().getValueAt(fila, 0);
        try {
            Producto producto = dao.buscarPorId(id);
            if (producto != null) {
                cargarProductoEnFormulario(producto);
            }
        } catch (SQLException ex) {
            vista.mostrarError("Error al cargar producto: " + ex.getMessage());
        }
    }

    // Llena los campos de la VISTA con datos de un objeto del MODELO
    private void cargarProductoEnFormulario(Producto producto) {
        vista.getTxtId().setText(String.valueOf(producto.getId()));
        vista.getTxtNombre().setText(producto.getNombre());
        vista.getTxtPrecio().setText(String.valueOf(producto.getPiezas()));
        vista.getTxtCantidad().setText(String.valueOf(producto.getAvance()));
        vista.getTxtCategoria().setText(producto.getCategoria());
        vista.getTxtFechaCaducidad().setText(producto.getDificultad());
        vista.getTxtPeso().setText(producto.getEstado());
        vista.getCmbTipoProducto().setSelectedItem("ROMPECABEZAS");
        cambiarCamposEspecificos();
    }

    // Parseo seguro para campos opcionales vacíos
    private double parseDoubleSeguro(String texto) {
        texto = texto.trim();
        return texto.isEmpty() ? 0.0 : Double.parseDouble(texto);
    }

    private int parseIntSeguro(String texto) {
        texto = texto.trim();
        return texto.isEmpty() ? 0 : Integer.parseInt(texto);
    }
}
