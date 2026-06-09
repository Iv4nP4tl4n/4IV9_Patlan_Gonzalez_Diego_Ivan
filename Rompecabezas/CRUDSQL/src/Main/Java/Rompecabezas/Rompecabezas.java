/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package rompecabezas;

import Controlador.RompecabezasControlador;
import Modelo.RompecabezasDAO;
import Modelo.ConexionBD;
import Vista.RompecabezasVista;
import javax.swing.JOptionPane;

/**
 *
 * @author ivanp
 */
public class Rompecabezas {
public static void main(String[] args) throws Exception {
        // TODO code application logic here
        if(!ConexionBD.probarConexion()){
            JOptionPane.showMessageDialog(
                    null, "No se pudo conectar a la bd");
            return;
        }
        
        RompecabezasDAO modelo = new RompecabezasDAO();
        
        RompecabezasVista vista = new RompecabezasVista();
        
        new RompecabezasControlador(modelo, vista);
        
        vista.setVisible(true);
        
        
    }
    
    }
    

