//javac Infografia_eventos_awt.java
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Infografia_eventos_awt extends JFrame {

    public Infografia_eventos_awt() {
        setTitle("Infografía - Eventos AWT y Swing");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel principal = new JPanel();
        principal.setLayout(new BoxLayout(principal, BoxLayout.Y_AXIS));
        principal.setBackground(new Color(240, 248, 255));

        JScrollPane scroll = new JScrollPane(principal);
        add(scroll);

        JLabel titulo = new JLabel("PROGRAMACIÓN DE EVENTOS EN JAVA");
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(new Color(25, 25, 112));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setBorder(new EmptyBorder(20, 10, 20, 10));

        principal.add(titulo);

        principal.add(crearSeccion(
                "¿Qué son los eventos?",
                "Los eventos permiten que un programa responda a acciones del usuario, como clics, teclas o movimientos del mouse."
        ));

        principal.add(crearSeccion(
                "Modelo de Eventos",
                "Fuente del evento → Evento → Listener → Método manejador"
        ));

        principal.add(crearSeccion(
                "AWT (Abstract Window Toolkit)",
                "AWT fue la primera librería gráfica de Java y utiliza componentes básicos como Frame, Button y Label."
        ));

        JTextArea codigoAWT = crearCodigo(
                "import java.awt.*;\n" +
                "import java.awt.event.*;\n\n" +
                "public class VentanaAWT extends Frame implements ActionListener {\n" +
                "    Button boton;\n\n" +
                "    public VentanaAWT() {\n" +
                "        boton = new Button(\"Presionar\");\n" +
                "        add(boton);\n\n" +
                "        boton.addActionListener(this);\n\n" +
                "        setSize(300,200);\n" +
                "        setVisible(true);\n" +
                "    }\n\n" +
                "    public void actionPerformed(ActionEvent e) {\n" +
                "        System.out.println(\"Botón presionado\");\n" +
                "    }\n" +
                "}"
        );

        principal.add(codigoAWT);

        principal.add(crearSeccion(
                "Swing",
                "Swing mejora AWT con componentes más modernos y flexibles como JFrame, JButton y JTable."
        ));

        JTextArea codigoSwing = crearCodigo(
                "import javax.swing.*;\n" +
                "import java.awt.event.*;\n\n" +
                "public class VentanaSwing extends JFrame implements ActionListener {\n" +
                "    JButton boton;\n\n" +
                "    public VentanaSwing() {\n" +
                "        boton = new JButton(\"Aceptar\");\n" +
                "        add(boton);\n\n" +
                "        boton.addActionListener(this);\n\n" +
                "        setSize(300,200);\n" +
                "        setVisible(true);\n" +
                "    }\n\n" +
                "    public void actionPerformed(ActionEvent e) {\n" +
                "        JOptionPane.showMessageDialog(null,\n" +
                "            \"Evento ejecutado\");\n" +
                "    }\n" +
                "}"
        );

        principal.add(codigoSwing);

        principal.add(crearSeccion(
                "¿Qué hace addActionListener()?",
                "Conecta un componente con el evento que debe escuchar."
        ));

        principal.add(crearSeccion(
                "Tipos de Listener",
                "ActionListener → Botones\n" +
                "MouseListener → Mouse\n" +
                "KeyListener → Teclado\n" +
                "WindowListener → Ventanas"
        ));

        principal.add(crearSeccion(
                "Flujo del Evento",
                "Usuario hace clic → Se genera el evento → El listener lo detecta → Se ejecuta actionPerformed()"
        ));

        principal.add(crearSeccion(
                "Conclusión",
                "La programación orientada a eventos permite crear aplicaciones dinámicas e interactivas en Java."
        ));
    }

    public JPanel crearSeccion(String titulo, String contenido) {

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new CompoundBorder(
                new EmptyBorder(10, 15, 10, 15),
                new LineBorder(new Color(70, 130, 180), 2)
        ));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(0, 51, 102));

        JTextArea texto = new JTextArea(contenido);
        texto.setFont(new Font("Arial", Font.PLAIN, 16));
        texto.setEditable(false);
        texto.setLineWrap(true);
        texto.setWrapStyleWord(true);
        texto.setBackground(Color.WHITE);

        panel.add(lblTitulo, BorderLayout.NORTH);
        panel.add(texto, BorderLayout.CENTER);

        return panel;
    }

    public JTextArea crearCodigo(String codigo) {

        JTextArea area = new JTextArea(codigo);
        area.setFont(new Font("Consolas", Font.PLAIN, 14));
        area.setEditable(false);
        area.setBackground(new Color(245, 245, 245));
        area.setBorder(new CompoundBorder(
                new EmptyBorder(10, 20, 10, 20),
                new LineBorder(Color.GRAY, 1)
        ));

        return area;
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new Infografia_eventos_awt().setVisible(true);
        });
    }
}

