/**
 *
 * @author ivanp
 */
package herenciapersona;

import javax.swing.JOptionPane;

public class DAOEstudiante {

    Estudiante obj[] = new Estudiante[10];
    int x = 0;

    void menu() {
        String var = "si";

        while (var.equalsIgnoreCase("si")) {

            int op = Integer.parseInt(JOptionPane.showInputDialog(
                    "Ingresa la opcion deseada:\n"
                    + "1.- Dar de alta a nuevo estudiante\n"
                    + "2.- Mostrar los datos de todos los estudiantes\n"));

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

            var = JOptionPane.showInputDialog("¿Desea repetir el programa?");
        }
    }

    public void pedirEstudiante() {

        if (x < obj.length) {

            int numboleta = Integer.parseInt(
                    JOptionPane.showInputDialog("Ingresa la boleta del estudiante: "));

            String nom = JOptionPane.showInputDialog(
                    "Ingrese el nombre del estudiante: ");

            int edad = Integer.parseInt(
                    JOptionPane.showInputDialog("Ingrese la edad del estudiante:"));

            char gen = JOptionPane.showInputDialog(
                    "Ingrese el genero del estudiante (M/F):").charAt(0);

            obj[x] = new Estudiante(numboleta, nom, edad, gen);
            x++; // 🔥 IMPORTANTE

        } else {
            JOptionPane.showMessageDialog(null, "Arreglo lleno");
        }
    }

    public void mostrarEstudiante() {

        String datos = "";

        for (int i = 0; i < x; i++) {

            datos += "Boleta: " + obj[i].getNumBoleta() + "\n"
                    + "Nombre: " + obj[i].getNombre() + "\n"
                    + "Edad: " + obj[i].getEdad() + "\n"
                    + "Genero: " + obj[i].getGenero() + "\n\n";
        }

        JOptionPane.showMessageDialog(null, datos);
    }
}