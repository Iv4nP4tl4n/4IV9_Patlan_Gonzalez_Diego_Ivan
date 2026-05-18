import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Ventana {
    public static void main(String[] args) {
        JFrame ventana = new JFrame();
        ventana.setSize(400, 160);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();

        ventana.add(panel);

        agregarComponente(panel);

        ventana.setVisible(true);
    }

    private static void agregarComponente(JPanel panel){
        panel.setLayout(null);

        JLabel userlabel = new JLabel ("Nombre del usuario: ");

        userlabel.setBounds(10,10,120, 50);

        panel.add (userlabel);

        JTextField usertext = new JTextField(20);

        usertext.setBounds(160, 10, 160, 50);

        panel.add(userlabel);
    }
}
