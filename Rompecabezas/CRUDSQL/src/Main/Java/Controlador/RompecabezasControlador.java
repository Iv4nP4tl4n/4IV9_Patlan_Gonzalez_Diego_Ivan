/**
 *
 * @author ivanp
 */

package Controlador;
import Modelo.RompecabezasDAO;
import Modelo.RompecabezasModelo;
import Modelo.Categorias;
import Vista.RompecabezasVista;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;

/*
 * ============================================================================
 * CAPA: CONTROLADOR — RompecabezasControlador
 * ============================================================================
 * Intermediario entre la Vista y el DAO.
 * Recibe eventos del usuario (clic en botones) y ejecuta la lógica del modelo.
 * La Vista no sabe qué hace el Controlador.
 * El DAO no sabe que existe una GUI.
 * ============================================================================
 */
public class RompecabezasControlador {

    private RompecabezasDAO dao;
    private RompecabezasVista vista;

    public RompecabezasControlador(RompecabezasDAO dao, RompecabezasVista vista) {
        this.dao = dao;
        this.vista = vista;
        inicializarEventos();
        cargarCategorias();
        cargarTabla();
    }

    // Registra los ActionListeners en cada botón de la Vista
    private void inicializarEventos() {
        vista.getBtnAgregar().addActionListener(e -> agregar());
        vista.getBtnActualizar().addActionListener(e -> actualizar());
        vista.getBtnEliminar().addActionListener(e -> eliminar());
        vista.getBtnBuscar().addActionListener(e -> buscarPorId());
        vista.getBtnLimpiar().addActionListener(e -> vista.limpiarFormulario());

        // Al seleccionar una fila en la tabla, llena el formulario automáticamente
        vista.getTablaRompecabezas().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                llenarFormularioDesdeTabla();
            }
        });
    }

    // Carga las categorías en el ComboBox de la Vista
    private void cargarCategorias() {
        try {
            vista.cargarCategorias(dao.listarCategorias());
        } catch (Exception e) {
            vista.mostrarError("Error al cargar categorías: " + e.getMessage());
        }
    }

    // AGREGAR
    private void agregar() {
        try {
            RompecabezasModelo r = construirDesdeFormulario();
            if (r == null) return;
            dao.agregar(r);
            vista.mostrarMensaje("Rompecabezas agregado correctamente.");
            cargarTabla();
            vista.limpiarFormulario();
        } catch (NumberFormatException ex) {
            vista.mostrarError("Verifica que el campo Piezas sea un número válido.");
        } catch (SQLException e) {
            vista.mostrarError("Error al agregar: " + e.getMessage());
        } catch (Exception e) {
            vista.mostrarError("Error inesperado: " + e.getMessage());
        }
    }

    // ACTUALIZAR
    private void actualizar() {
        try {
            RompecabezasModelo r = construirDesdeFormulario();
            if (r == null) return;
            if (r.getIdRompecabezas() == 0) {
                vista.mostrarError("Selecciona un rompecabezas de la tabla para actualizar.");
                return;
            }
            dao.actualizar(r);
            vista.mostrarMensaje("Rompecabezas actualizado correctamente.");
            cargarTabla();
            vista.limpiarFormulario();
        } catch (NumberFormatException ex) {
            vista.mostrarError("Verifica que el campo Piezas sea un número válido.");
        } catch (SQLException e) {
            vista.mostrarError("Error al actualizar: " + e.getMessage());
        } catch (Exception e) {
            vista.mostrarError("Error inesperado: " + e.getMessage());
        }
    }

    // ELIMINAR
    private void eliminar() {
        try {
            String idTexto = vista.getTxtId().getText().trim();
            if (idTexto.isEmpty()) {
                vista.mostrarError("Selecciona un rompecabezas de la tabla para eliminar.");
                return;
            }
            int id = Integer.parseInt(idTexto);
            if (!vista.confirmar("¿Eliminar el rompecabezas con ID " + id + "?")) return;
            dao.eliminar(id);
            vista.mostrarMensaje("Rompecabezas eliminado correctamente.");
            cargarTabla();
            vista.limpiarFormulario();
        } catch (NumberFormatException ex) {
            vista.mostrarError("ID inválido.");
        } catch (Exception e) {
            vista.mostrarError("Error al eliminar: " + e.getMessage());
        }
    }

    // BUSCAR POR ID
    private void buscarPorId() {
        try {
            String idTexto = vista.getTxtId().getText().trim();
            if (idTexto.isEmpty()) {
                vista.mostrarError("Escribe un ID para buscar.");
                return;
            }
            int id = Integer.parseInt(idTexto);
            RompecabezasModelo r = dao.buscarPorId(id);
            if (r == null) {
                vista.mostrarError("No se encontró rompecabezas con ID " + id);
                return;
            }
            llenarFormularioDesdeObjeto(r);
        } catch (NumberFormatException ex) {
            vista.mostrarError("ID inválido.");
        } catch (Exception e) {
            vista.mostrarError("Error al buscar: " + e.getMessage());
        }
    }

    // CARGAR TABLA — Muestra todos los rompecabezas
    private void cargarTabla() {
        try {
            DefaultTableModel modelo = vista.getModeloTabla();
            modelo.setRowCount(0);
            for (RompecabezasModelo r : dao.listarTodos()) {
                modelo.addRow(new Object[]{
                    r.getIdRompecabezas(),
                    r.getNombre(),
                    r.getPiezas(),
                    r.getDificultad(),
                    r.getEstado(),
                    r.getNombreCategoria()
                });
            }
        } catch (Exception e) {
            vista.mostrarError("Error al cargar tabla: " + e.getMessage());
        }
    }

    // Construye un RompecabezasModelo desde los campos del formulario
    private RompecabezasModelo construirDesdeFormulario() {
        try {
            RompecabezasModelo r = new RompecabezasModelo();

            String idTexto = vista.getTxtId().getText().trim();
            r.setIdRompecabezas(idTexto.isEmpty() ? 0 : Integer.parseInt(idTexto));
            r.setNombre(vista.getTxtNombre().getText().trim());
            r.setPiezas(Integer.parseInt(vista.getTxtPiezas().getText().trim()));
            r.setDificultad((String) vista.getCmbDificultad().getSelectedItem());
            r.setEstado((String) vista.getCmbEstado().getSelectedItem());

            Categorias cat = (Categorias) vista.getCmbCategoria().getSelectedItem();
            if (cat == null) {
                vista.mostrarError("Selecciona una categoría.");
                return null;
            }
            r.setIdCategoria(cat.getIdCategoria());
            return r;
        } catch (Exception e) {
            vista.mostrarError("Verifica los campos del formulario.");
            return null;
        }
    }

    // Llena el formulario al seleccionar una fila en la tabla
    private void llenarFormularioDesdeTabla() {
        int fila = vista.getTablaRompecabezas().getSelectedRow();
        if (fila < 0) return;

        DefaultTableModel modelo = vista.getModeloTabla();
        vista.getTxtId().setText(modelo.getValueAt(fila, 0).toString());
        vista.getTxtNombre().setText(modelo.getValueAt(fila, 1).toString());
        vista.getTxtPiezas().setText(modelo.getValueAt(fila, 2).toString());
        vista.getCmbDificultad().setSelectedItem(modelo.getValueAt(fila, 3).toString());
        vista.getCmbEstado().setSelectedItem(modelo.getValueAt(fila, 4).toString());
    }

    // Llena el formulario desde un objeto RompecabezasModelo
    private void llenarFormularioDesdeObjeto(RompecabezasModelo r) {
        vista.getTxtId().setText(String.valueOf(r.getIdRompecabezas()));
        vista.getTxtNombre().setText(r.getNombre());
        vista.getTxtPiezas().setText(String.valueOf(r.getPiezas()));
        vista.getCmbDificultad().setSelectedItem(r.getDificultad());
        vista.getCmbEstado().setSelectedItem(r.getEstado());
    }
}