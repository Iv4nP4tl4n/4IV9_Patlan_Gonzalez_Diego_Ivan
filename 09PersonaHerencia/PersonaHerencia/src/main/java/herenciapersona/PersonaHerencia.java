/**
 *
 * @author ivanp
 */
package herenciapersona;

import javax.swing.JOptionPane;

public class PersonaHerencia {

    public static void main(String[] args) {

        DAOEstudiante est = new DAOEstudiante();
        DAOProfesor prof = new DAOProfesor();

        String var = "si";

        while (var.equalsIgnoreCase("si")) {

            int op = Integer.parseInt(JOptionPane.showInputDialog(
                    "MENU PRINCIPAL\n"
                    + "1.- CRUD Estudiantes\n"
                    + "2.- CRUD Profesores"));

            switch (op) {

                case 1:
                    est.menu();
                    break;

                case 2:
                    prof.menu();
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Opcion no valida");
            }

            var = JOptionPane.showInputDialog("Desea regresar al menu principal?");
        }
    }
}