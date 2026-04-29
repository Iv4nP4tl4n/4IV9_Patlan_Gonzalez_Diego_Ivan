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
                    + "2.- Mostrar los datos de todos los estudiantes"));

            switch (op) {

                case 1:
                    pedirEstudiante();
                    break;

                case 2:
                    mostrarEstudiante();
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
}