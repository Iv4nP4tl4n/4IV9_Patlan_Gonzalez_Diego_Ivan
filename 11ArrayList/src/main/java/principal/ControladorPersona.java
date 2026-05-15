/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal;

/**
 *
 * @author ivanp
 */

import java.util.ArrayList;
public class ControladorPersona {
    
    /*
    vamos a ocupar un objeto especial para poder almacenar
    diferentes tipos de variable, y diferentes objetos, llamado
    arraylist, su particularidad es redimencional 
    */
    
    //creamos el objeto para consultar a las personas
    public static ArrayList<Persona> listadePersonas 
            = new ArrayList<Persona>();
    
    //el metodo
    public ArrayList<Persona> mostrarPersonas(){
        return listadePersonas;
    }
    
    //vamos a registrar
    public void registrarPersona(Persona p){
        listadePersonas.add(p);
    }
    
    //buscar una persona por id
    public Persona buscarPersona(int id){
        //necesito una instancia de persona para encontrarla
        Persona encontrada = new Persona();
        //recorro el array list
        for(Persona p : listadePersonas){
            if(id == p.getId()){
                encontrada = p;
            }else{
                System.out.println("Solo juguito contigo");
            }
        }
        return encontrada;
             
    }
    
    //quiero actualizar
    public void actualizarPersona(Persona personaActualizada){
        Persona personaActualizar 
                = buscarPersona(personaActualizada.getId());
        listadePersonas.remove(personaActualizar);
        listadePersonas.add(personaActualizar);
    }
    
    //vamos a eliminar
    public void eliminarPersona(Persona personaEliminar){
        listadePersonas.remove(personaEliminar);
    }
}