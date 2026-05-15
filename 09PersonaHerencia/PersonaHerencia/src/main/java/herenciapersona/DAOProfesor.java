package herenciapersona;

/**
 *
 * @author ivanp
 */
import javax.swing.JOptionPane;
import java.io.*;

public class DAOProfesor {

    Profesor obj[] = new Profesor[5];
    int x = 0;

    void menu() {

        String var = "si";

        while (var.equalsIgnoreCase("si")) {

            int op = Integer.parseInt(JOptionPane.showInputDialog(
                    "Ingresa la opcion deseada:\n"
                    + "1.- Dar de alta a nuevo profesor\n"
                    + "2.- Mostrar los datos de todos los profesores\n"
                    + "3.- Buscar profesor\n"
                    + "4.- Editar profesor\n"
                    + "5.- Eliminar profesor\n"
                    + "6.- Guardar profesores\n"
                    + "7.- Cargar profesores"));

            switch (op) {

                case 1:
                    pedirProfesor();
                    break;

                case 2:
                    mostrarProfesor();
                    break;

                case 3:
                    buscarProfesor();
                    break;

                case 4:
                    editarProfesor();
                    break;

                case 5:
                    eliminarProfesor();
                    break;

                case 6:
                    guardarProfesores();
                    break;

                case 7:
                    cargarProfesores();
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Opcion no valida");
            }

            var = JOptionPane.showInputDialog("Desea repetir el programa?");
        }
    }

    public void pedirProfesor() {

        if (x < 5) {

            Profesor prof = new Profesor();

            prof.setIdProfesor(Integer.parseInt(
                    JOptionPane.showInputDialog("Ingresa la Id del profesor:")));

            prof.setNombre(
                    JOptionPane.showInputDialog("Ingrese el nombre del profesor:"));

            prof.setEdad(Integer.parseInt(
                    JOptionPane.showInputDialog("Ingrese la edad del profesor:")));

            prof.setGenero(
                    JOptionPane.showInputDialog("Ingrese el genero del profesor:")
                            .charAt(0));

            obj[x] = prof;
            x++;

        } else {
            JOptionPane.showMessageDialog(null, "Solo se pueden guardar 5 profesores");
        }
    }

    public void mostrarProfesor() {

        String datos = "";

        for (int i = 0; i < x; i++) {

            datos += "Id: " + obj[i].getIdProfesor() + "\n";
            datos += "Nombre: " + obj[i].getNombre() + "\n";
            datos += "Edad: " + obj[i].getEdad() + "\n";
            datos += "Genero: " + obj[i].getGenero() + "\n\n";
        }

        JOptionPane.showMessageDialog(null, datos);
    }

    public void buscarProfesor() {

        int id = Integer.parseInt(
                JOptionPane.showInputDialog("Ingrese la Id a buscar:"));

        for (int i = 0; i < x; i++) {

            if (obj[i].getIdProfesor() == id) {

                JOptionPane.showMessageDialog(null,
                        "Id: " + obj[i].getIdProfesor()
                        + "\nNombre: " + obj[i].getNombre()
                        + "\nEdad: " + obj[i].getEdad()
                        + "\nGenero: " + obj[i].getGenero());

                return;
            }
        }

        JOptionPane.showMessageDialog(null, "Profesor no encontrado");
    }

    public void editarProfesor() {

        int id = Integer.parseInt(
                JOptionPane.showInputDialog("Ingrese la id del profesor a editar:"));

        for (int i = 0; i < x; i++) {

            if (obj[i].getIdProfesor() == id) {

                obj[i].setNombre(
                        JOptionPane.showInputDialog("Nuevo nombre:"));

                obj[i].setEdad(Integer.parseInt(
                        JOptionPane.showInputDialog("Nueva edad:")));

                obj[i].setGenero(
                        JOptionPane.showInputDialog("Nuevo genero:")
                                .charAt(0));

                JOptionPane.showMessageDialog(null, "Datos actualizados");
                return;
            }
        }

        JOptionPane.showMessageDialog(null, "Profesor no encontrado");
    }

    public void eliminarProfesor() {

        int id = Integer.parseInt(
                JOptionPane.showInputDialog("Ingrese la id a eliminar:"));

        for (int i = 0; i < x; i++) {

            if (obj[i].getIdProfesor() == id) {

                for (int j = i; j < x - 1; j++) {
                    obj[j] = obj[j + 1];
                }

                x--;

                JOptionPane.showMessageDialog(null, "Profesor eliminado");
                return;
            }
        }

        JOptionPane.showMessageDialog(null, "Profesor no encontrado");
    }

    public void guardarProfesores() {

        try {

            ObjectOutputStream escribir =
                    new ObjectOutputStream(new FileOutputStream("profesores.dat"));

            escribir.writeObject(obj);
            escribir.close();

            JOptionPane.showMessageDialog(null, "Profesores guardados");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar");
        }
    }

    public void cargarProfesores() {

        try {

            ObjectInputStream leer =
                    new ObjectInputStream(new FileInputStream("profesores.dat"));

            obj = (Profesor[]) leer.readObject();
            leer.close();

            x = 0;

            for (int i = 0; i < obj.length; i++) {
                if (obj[i] != null) {
                    x++;
                }
            }

            JOptionPane.showMessageDialog(null, "Profesores cargados");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No hay archivo guardado");
        }
    }
}