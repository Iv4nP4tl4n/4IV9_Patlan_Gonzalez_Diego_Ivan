package Modelo;


import Modelo.Producto;



/*
 * CAPA: MODELO — Subclase Alimento
 * Sin cambios respecto a Práctica 2. Herencia y polimorfismo intactos.
 */
public class Alimento extends Producto {

    private String fechaCaducidad;
    private boolean esPerecedero;
    private double peso;

    public Alimento() {
        super();
        this.fechaCaducidad = "";
        this.esPerecedero = false;
        this.peso = 0.0;
    }

    public Alimento(int id, String nombre, double precio, int cantidad, String categoria,
                    String fechaCaducidad, boolean esPerecedero, double peso) {
        super(id, nombre, precio, cantidad, categoria);
        this.fechaCaducidad = fechaCaducidad;
        this.esPerecedero = esPerecedero;
        this.peso = peso;
    }

    public String getFechaCaducidad() { return fechaCaducidad; }
    public void setFechaCaducidad(String fechaCaducidad) { this.fechaCaducidad = fechaCaducidad; }

    public boolean isEsPerecedero() { return esPerecedero; }
    public void setEsPerecedero(boolean esPerecedero) { this.esPerecedero = esPerecedero; }

    public double getPeso() { return peso; }
    public void setPeso(double peso) { if (peso >= 0) this.peso = peso; }

    @Override
    public String mostrarDetalle() {
        return super.mostrarDetalle() + String.format(
            " | Fecha Cad: %s | Perecedero: %s | Peso: %.2f kg",
            fechaCaducidad, esPerecedero ? "Sí" : "No", peso);
    }

    @Override
    public String toString() { return "[ALIMENTO] " + mostrarDetalle(); }
}
