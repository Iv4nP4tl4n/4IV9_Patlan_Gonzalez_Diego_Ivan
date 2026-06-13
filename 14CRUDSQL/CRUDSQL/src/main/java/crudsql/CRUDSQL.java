/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package crudsql;

import Controlador.ProductoControlador;
import Modelo.ConexionBD;
import Modelo.ProductoDAO;
import Vista.ProductoVista;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author demon
 */
public class CRUDSQL {

    public static void main(String[] args) throws Exception {
        //verificar la conexion con la bd
        if(!ConexionBD.probarConexion()){
            JOptionPane.showMessageDialog(null, 
                    "No se pudo conectar a la BD");
            return;
        }
        
        //invocamos nuestro Swing
        SwingUtilities.invokeLater(() -> {
            try{
                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName());
            }catch(Exception e){
                System.out.println("Error "+ e.getMessage());
            }
            
            //Primero debemos de crear el modelo
            ProductoDAO modelo = new ProductoDAO();
            
            //Segundo instanciamos la vista
            ProductoVista vista = new ProductoVista();
            
            //enviamos el controlador con el modelo y la vista
            new ProductoControlador(modelo, vista);
            
            //lo mostramos
            vista.setVisible(true);
        });
        
    }
}
