/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Gustavo
 */
public class Electronico extends Producto {

    private String marca;
    private int garantiaMeses;
    private double voltaje;

    public Electronico() {
        super();
        this.marca = "";
        this.garantiaMeses = 0;
        this.voltaje = 0.0;
    }

    public Electronico(int id, String nombre, double precio, int cantidad, String categoria,
                       String marca, int garantiaMeses, double voltaje) {
        super(id, nombre, precio, cantidad, categoria);
        this.marca = marca;
        this.garantiaMeses = garantiaMeses;
        this.voltaje = voltaje;
    }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public int getGarantiaMeses() { return garantiaMeses; }
    public void setGarantiaMeses(int garantiaMeses) { this.garantiaMeses = garantiaMeses; }

    public double getVoltaje() { return voltaje; }
    public void setVoltaje(double voltaje) { this.voltaje = voltaje; }

    @Override
    public String mostrarDetalle() {
        return super.mostrarDetalle() + String.format(
            " | Marca: %s | Garantía: %d meses | Voltaje: %.1fV",
            marca, garantiaMeses, voltaje);
    }

    @Override
    public String toString() { return "[ELECTRÓNICO] " + mostrarDetalle(); }
}