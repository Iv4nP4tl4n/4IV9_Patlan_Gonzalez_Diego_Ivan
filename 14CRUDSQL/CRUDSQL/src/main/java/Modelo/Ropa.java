/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Gustavo
 */
public class Ropa extends Producto {

    private String talla;
    private String color;
    private String material;

    public Ropa() {
        super();
        this.talla = "";
        this.color = "";
        this.material = "";
    }

    public Ropa(int id, String nombre, double precio, int cantidad, String categoria,
                String talla, String color, String material) {
        super(id, nombre, precio, cantidad, categoria);
        this.talla = talla;
        this.color = color;
        this.material = material;
    }

    public String getTalla() { return talla; }
    public void setTalla(String talla) { this.talla = talla; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }

    @Override
    public String mostrarDetalle() {
        return super.mostrarDetalle() + String.format(
            " | Talla: %s | Color: %s | Material: %s",
            talla, color, material);
    }

    @Override
    public String toString() { return "[ROPA] " + mostrarDetalle(); }
}