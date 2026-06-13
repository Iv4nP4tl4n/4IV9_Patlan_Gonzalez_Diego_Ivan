package Controlador;

/**
 *
 * @author ivanp
 */

import Modelo.Producto;
import Modelo.ProductoDAO;
import Vista.ProductoVista;
import java.sql.*;
import java.util.*;
import javax.swing.*;

import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class ProductoControlador {

    /*
    El controlador es el intermediario, entre la vista y 
    el modelo, recibe los eventos del usuario, los clic, seleccion
    eventos de teclado, y este ejecuta la logica, la cual 
    corresponde al modelo
    */

    private ProductoDAO dao;
    private ProductoVista vista;

    //necesita su propio constructor
    public ProductoControlador(ProductoDAO dao, ProductoVista vista){
        this.dao = dao;
        this.vista = vista;

        //como se van a registrar cada uno de los eventos
        //un metodo para iniciarlizar los eventos de la vista
        //inicializarEventos();
    }

    //un metodo para cada comportamiento
    //agregar
    private void agregar(){

        try{

            //instancia del producto
            Producto producto = construirProductoFormulario();

            if(producto == null) return;

            dao.agregar(producto);

            //validamos mostrando los productos
            cargarTabla();

        }catch(NumberFormatException ex){

            System.out.println("Verifique que los campos "
                    + "numericos sean validos");

        }catch(SQLException e){

            System.out.println("Error al agregar "
                    + e.getMessage());
        }
    }

    private Producto construirProductoFormulario() {

        try{

            Producto producto = new Producto();

            producto.setId(
                    Integer.parseInt(
                            vista.getTxtId().getText()));

            producto.setNombre(
                    vista.getTxtNombre().getText());

            producto.setPrecio(
                    Double.parseDouble(
                            vista.getTxtPrecio().getText()));

            producto.setCantidad(
                    Integer.parseInt(
                            vista.getTxtCantidad().getText()));

            producto.setCategoria(
                    vista.getTxtCategoria().getText());

            producto.setTipoProducto(
                    (String)
                    vista.getCambioTipoProducto()
                            .getSelectedItem());

            return producto;

        }catch(Exception e){

            return null;
        }
    }

    private void cargarTabla() {

        try{

            DefaultTableModel modelo =
                    vista.getModeloTabla();

            modelo.setRowCount(0);

            for(Producto p : dao.listar()){

                modelo.addRow(new Object[]{

                    p.getId(),
                    p.getNombre(),
                    p.getPrecio(),
                    p.getCantidad(),
                    p.getCategoria(),
                    p.getTipoProducto()

                });
            }

        }catch(SQLException e){

            System.out.println(
                    "Error al cargar tabla "
                    + e.getMessage());
        }
    }
}