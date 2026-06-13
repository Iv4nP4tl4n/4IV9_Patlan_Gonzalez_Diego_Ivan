/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package nointernet;

import Controlador.ProductoControlador;
import Controlador.ProductoDAO;
import Vista.ProductoVista;
import Modelo.*;
import javax.swing.JOptionPane;

/**
 *
 * @author demon
 */
public class NoInternet {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        if(!ConexionBD.probarConexion()){
            JOptionPane.showMessageDialog(
                    null, "No se pudo conectar a la bd");
            return;
        }
        
        ProductoDAO modelo = new ProductoDAO();
        
        ProductoVista vista = new ProductoVista();
        
        new ProductoControlador(modelo, vista);
        
        vista.setVisible(true);
        
        
    }
    
}
