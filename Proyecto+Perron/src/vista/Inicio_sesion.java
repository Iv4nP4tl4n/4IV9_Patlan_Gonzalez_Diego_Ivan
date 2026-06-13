

/**
 *
 * @author ivanp
 */
package vista;

import modelo.Empleado;
import modelo.EmpleadoDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Inicio_sesion extends JFrame {

    // COLORES EXACTOS DE TU PANTALLA
    Color turquesa = new Color(0, 180, 170);
    Color celesteFondo = new Color(180, 235, 230);
    Color blanco = Color.WHITE;
    Color grisTexto = new Color(70, 70, 70);

    public Inicio_sesion() {
        // CONFIGURACIÓN ORIGINAL
        setTitle("Inicio de sesión");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        // PANEL DE FONDO CON LA FORMA CURVA IGUAL QUE TU IMAGEN
        JPanel fondo = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(celesteFondo);
                // DIBUJA LA CURVA TAL CUAL LA TIENES
                g2.fillArc(-200, -50, 450, 400, 0, 270);
            }
        };
        fondo.setBackground(blanco);
        add(fondo);

        // --- TEXTOS ---
        JLabel lblBienvenido = new JLabel("Bienvenido");
        lblBienvenido.setFont(new Font("Arial", Font.BOLD, 22));
        lblBienvenido.setForeground(grisTexto);
        lblBienvenido.setBounds(240, 50, 150, 30);
        fondo.add(lblBienvenido);

        JLabel lblSub = new JLabel("Inicia sesión para continuar");
        lblSub.setFont(new Font("Arial", Font.PLAIN, 12));
        lblSub.setForeground(grisTexto);
        lblSub.setBounds(240, 80, 180, 20);
        fondo.add(lblSub);

        // --- USUARIO ---
        JLabel lblUsuario = new JLabel("Usuario");
        lblUsuario.setFont(new Font("Arial", Font.PLAIN, 12));
        lblUsuario.setForeground(grisTexto);
        lblUsuario.setBounds(240, 120, 80, 20);
        fondo.add(lblUsuario);

        JTextField txtUsuario = new JTextField();
        txtUsuario.setBounds(240, 140, 200, 25);
        txtUsuario.setBorder(BorderFactory.createLineBorder(celesteFondo, 2));
        fondo.add(txtUsuario);

        // --- CONTRASEÑA ---
        JLabel lblPass = new JLabel("Contraseña");
        lblPass.setFont(new Font("Arial", Font.PLAIN, 12));
        lblPass.setForeground(grisTexto);
        lblPass.setBounds(240, 180, 80, 20);
        fondo.add(lblPass);

        JPasswordField txtPass = new JPasswordField();
        txtPass.setBounds(240, 200, 200, 25);
        txtPass.setBorder(BorderFactory.createLineBorder(celesteFondo, 2));
        fondo.add(txtPass);

        // --- BOTÓN INICIAR SESIÓN ---
        JButton btnEntrar = new JButton("Iniciar sesión");
        btnEntrar.setBackground(turquesa);
        btnEntrar.setForeground(blanco);
        btnEntrar.setFont(new Font("Arial", Font.BOLD, 12));
        btnEntrar.setBounds(240, 240, 200, 30);
        btnEntrar.setBorderPainted(false);
        btnEntrar.setFocusPainted(false);

        // CÓDIGO DE ACCESO CORREGIDO
        btnEntrar.addActionListener(e -> {
            String usuario = txtUsuario.getText().trim();
            String contrasena = new String(txtPass.getPassword());

            EmpleadoDAO dao = new EmpleadoDAO();
            Empleado emp = dao.login(usuario, contrasena);

            if (emp != null) {
                new Inicio(emp);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null,
                        "Usuario o contraseña incorrectos.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        fondo.add(btnEntrar);

        // --- BOTÓN SALIR ---
        JButton btnSalir = new JButton("Salir");
        btnSalir.setFont(new Font("Arial", Font.PLAIN, 12));
        btnSalir.setBounds(310, 280, 80, 25);
        btnSalir.addActionListener(e -> System.exit(0));
        fondo.add(btnSalir);

        setVisible(true);
    }

    public static void main(String[] args) {
        new Inicio_sesion();
    }
}