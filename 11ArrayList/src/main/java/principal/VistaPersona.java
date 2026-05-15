package principal;

/**
 *
 * @author ivanp
 */
import java.util.ArrayList;
import java.util.Scanner;

public class VistaPersona {
    
    Scanner entrada = new Scanner(System.in);
    int opcion;
    ControladorPersona crud = new ControladorPersona();
    
    public void Principal() {
        do {
            System.out.println("Bienvenido a gestionar personas");
            System.out.println("Elija una opcion:");
            System.out.println("1.- Mostrar lista de personas");
            System.out.println("2.- Registrar nueva persona");
            System.out.println("3.- Editar datos de persona");
            System.out.println("4.- Borrar persona");
            System.out.println("5.- Salir");
            
            opcion = Integer.parseInt(entrada.nextLine());
            
            switch (opcion) {
                case 1:
                    ArrayList<Persona> listadePersonas = crud.mostrarPersonas();
                    
                    for (Persona objeto : listadePersonas) {
                        System.out.println("ID: " + objeto.getId()
                                + " Nombre: " + objeto.getNombre()
                                + " Edad: " + objeto.getEdad());
                    }
                    break;
                    
                case 2:
                    System.out.println("Ingresa el id");
                    int idpersona = Integer.parseInt(entrada.nextLine());
                    
                    System.out.println("Ingresa el nombre");
                    String nompersona = entrada.nextLine();
                    
                    System.out.println("Ingresa la edad");
                    int edadpersona = Integer.parseInt(entrada.nextLine());
                    
                    Persona personaNueva = new Persona(idpersona, nompersona, edadpersona);
                    crud.registrarPersona(personaNueva);
                    break;
                    
                case 3:
                    System.out.println("Ingresa el id para buscar una persona");
                    idpersona = Integer.parseInt(entrada.nextLine());
                    
                    Persona personaSeleccionada = crud.buscarPersona(idpersona);
                    
                    System.out.println("La informacion es:");
                    System.out.println("ID: " + personaSeleccionada.getId());
                    System.out.println("Nombre: " + personaSeleccionada.getNombre());
                    System.out.println("Edad: " + personaSeleccionada.getEdad());
                    
                    System.out.println("Ingresa el nuevo nombre");
                    String nuevonombre = entrada.nextLine();
                    
                    System.out.println("Ingresa la nueva edad");
                    int nuevaedad = Integer.parseInt(entrada.nextLine());
                    
                    personaSeleccionada.setNombre(nuevonombre);
                    personaSeleccionada.setEdad(nuevaedad);
                    
                    crud.actualizarPersona(personaSeleccionada);
                    break;
                    
                case 4:
                    System.out.println("Ingresa el id para borrar una persona");
                    int ideliminar = Integer.parseInt(entrada.nextLine());
                    
                    Persona personaEliminar = crud.buscarPersona(ideliminar);
                    crud.eliminarPersona(personaEliminar);
                    
                    System.out.println("Persona eliminada");
                    break;
                    
                case 5:
                    System.out.println("Saliendo...");
                    break;
                    
                default:
                    System.out.println("Escoge la opcion correcta");
            }
            
        } while (opcion != 5);
    }
}