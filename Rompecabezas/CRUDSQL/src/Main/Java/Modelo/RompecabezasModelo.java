package Modelo;

/**
 * CAPA: MODELO — Clase RompecabezasModelo
 * Representa un rompecabezas con sus atributos principales.
 */
public class RompecabezasModelo {
 
    private int idRompecabezas;
    private String nombre;
    private int piezas;
    private String dificultad;
    private String estado;
    private int idCategoria;
    private String nombreCategoria; // para mostrar en tabla (JOIN)
 
    public RompecabezasModelo() {
        this.idRompecabezas = 0;
        this.nombre = "";
        this.piezas = 0;
        this.dificultad = "";
        this.estado = "";
        this.idCategoria = 0;
        this.nombreCategoria = "";
    }
 
    public RompecabezasModelo(int idRompecabezas, String nombre, int piezas,
                              String dificultad, String estado, int idCategoria) {
        this.idRompecabezas = idRompecabezas;
        this.nombre = nombre;
        this.piezas = piezas;
        this.dificultad = dificultad;
        this.estado = estado;
        this.idCategoria = idCategoria;
        this.nombreCategoria = "";
    }
 
    public int getIdRompecabezas() { return idRompecabezas; }
    public void setIdRompecabezas(int idRompecabezas) { this.idRompecabezas = idRompecabezas; }
 
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
 
    public int getPiezas() { return piezas; }
    public void setPiezas(int piezas) { if (piezas > 0) this.piezas = piezas; }
 
    public String getDificultad() { return dificultad; }
    public void setDificultad(String dificultad) { this.dificultad = dificultad; }
 
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
 
    public int getIdCategoria() { return idCategoria; }
    public void setIdCategoria(int idCategoria) { this.idCategoria = idCategoria; }
 
    public String getNombreCategoria() { return nombreCategoria; }
    public void setNombreCategoria(String nombreCategoria) { this.nombreCategoria = nombreCategoria; }
 
    public String mostrarDetalle() {
        return String.format("ID: %d | Nombre: %s | Piezas: %d | Dificultad: %s | Estado: %s | Categoría: %s",
                idRompecabezas, nombre, piezas, dificultad, estado, nombreCategoria);
    }
 
    @Override
    public String toString() {
        return mostrarDetalle();
    }
}
 