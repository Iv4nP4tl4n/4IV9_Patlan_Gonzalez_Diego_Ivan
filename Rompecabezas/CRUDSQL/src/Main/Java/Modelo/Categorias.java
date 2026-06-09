package Modelo;

/**
 * CAPA: MODELO — Clase Categorias
 * Representa una categoría de rompecabezas.
 */
public class Categorias {

    private int idCategoria;
    private String nombreCategoria;

    public Categorias() {
        this.idCategoria = 0;
        this.nombreCategoria = "";
    }

    public Categorias(int idCategoria, String nombreCategoria) {
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
    }

    public int getIdCategoria() { return idCategoria; }
    public void setIdCategoria(int idCategoria) { this.idCategoria = idCategoria; }

    public String getNombreCategoria() { return nombreCategoria; }
    public void setNombreCategoria(String nombreCategoria) { this.nombreCategoria = nombreCategoria; }

    @Override
    public String toString() {
        return nombreCategoria;
    }
}