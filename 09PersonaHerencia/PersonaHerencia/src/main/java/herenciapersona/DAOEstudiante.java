/**
 *
 * @author ivanp
 */
package herenciapersona;

import javax.swing.JOptionPane;

public class DAOEstudiante {

    Estudiante obj[] = new Estudiante[5];
    int x = 0;

    void menu() {
        String var = "si";

        while (var.equalsIgnoreCase("si")) {

            int op = Integer.parseInt(JOptionPane.showInputDialog(
                    "Ingresa la opcion deseada:\n"
                    + "1.- Dar de alta a nuevo estudiante\n"
                    + "2.- Mostrar los datos de todos los estudiantes\n"
                    + "3.- Buscar estudiante\n"
                    + "4.- Editar estudiante\n"
                    + "5.- Eliminar estudiante"));

            switch (op) {

                case 1:
                    pedirEstudiante();
                    break;

                case 2:
                    mostrarEstudiante();
                    break;

                case 3:
                    buscarEstudiante();
                    break;

                case 4:
                    editarEstudiante();
                    break;

                case 5:
                    eliminarEstudiante();
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Opcion no valida");
            }

            var = JOptionPane.showInputDialog("Desea repetir el programa?");
        }
    }

    public void pedirEstudiante() {

        if (x < 5) {

            Estudiante est = new Estudiante();

            est.setNumBoleta(Integer.parseInt(
                    JOptionPane.showInputDialog("Ingresa la boleta del estudiante:")));

            est.setNombre(
                    JOptionPane.showInputDialog("Ingrese el nombre del estudiante:"));

            est.setEdad(Integer.parseInt(
                    JOptionPane.showInputDialog("Ingrese la edad del estudiante:")));

            est.setGenero(
                    JOptionPane.showInputDialog("Ingrese el genero del estudiante:")
                            .charAt(0));

            obj[x] = est;
            x++;

        } else {
            JOptionPane.showMessageDialog(null, "Solo se pueden guardar 5 alumnos");
        }
    }

    public void mostrarEstudiante() {

        String datos = "";

        for (int i = 0; i < x; i++) {

            datos += "Boleta: " + obj[i].getNumBoleta() + "\n";
            datos += "Nombre: " + obj[i].getNombre() + "\n";
            datos += "Edad: " + obj[i].getEdad() + "\n";
            datos += "Genero: " + obj[i].getGenero() + "\n\n";
        }

        JOptionPane.showMessageDialog(null, datos);
    }

    public void buscarEstudiante() {

        int boleta = Integer.parseInt(
                JOptionPane.showInputDialog("Ingrese la boleta a buscar:"));

        for (int i = 0; i < x; i++) {

            if (obj[i].getNumBoleta() == boleta) {

                JOptionPane.showMessageDialog(null,
                        "Boleta: " + obj[i].getNumBoleta()
                        + "\nNombre: " + obj[i].getNombre()
                        + "\nEdad: " + obj[i].getEdad()
                        + "\nGenero: " + obj[i].getGenero());

                return;
            }
        }

        JOptionPane.showMessageDialog(null, "Estudiante no encontrado");
    }

    public void editarEstudiante() {

        int boleta = Integer.parseInt(
                JOptionPane.showInputDialog("Ingrese la boleta del estudiante a editar:"));

        for (int i = 0; i < x; i++) {

            if (obj[i].getNumBoleta() == boleta) {

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

        JOptionPane.showMessageDialog(null, "Estudiante no encontrado");
    }

    public void eliminarEstudiante() {

        int boleta = Integer.parseInt(
                JOptionPane.showInputDialog("Ingrese la boleta a eliminar:"));

        for (int i = 0; i < x; i++) {

            if (obj[i].getNumBoleta() == boleta) {

                for (int j = i; j < x - 1; j++) {
                    obj[j] = obj[j + 1];
                }

                x--;

                JOptionPane.showMessageDialog(null, "Estudiante eliminado");
                return;
            }
        }

        JOptionPane.showMessageDialog(null, "Estudiante no encontrado");
    }
}