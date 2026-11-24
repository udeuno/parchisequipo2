
package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VistaInicio extends JFrame {

    public JButton btnCrear = new JButton("CREAR PARTIDA");
    public JButton btnUnir = new JButton("UNIRSE A PARTIDA");

    private Color fondo = new Color(24, 32, 52);
    private Color azul = new Color(52, 152, 219);
    private Color verde = new Color(46, 204, 113);

    public VistaInicio() {

        setTitle("Parchís");
        setSize(1200, 800);
        setMinimumSize(new Dimension(1000, 700));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel principal = new JPanel(new BorderLayout());
        principal.setBackground(fondo);

        // Titulo
        JPanel panelTitulo = new JPanel(new GridLayout(2, 1));
        panelTitulo.setBackground(fondo);
        panelTitulo.setBorder(
                BorderFactory.createEmptyBorder(100, 0, 20, 0)
        );

        JLabel titulo = new JLabel("PARCHÍS", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 55));
        titulo.setForeground(Color.WHITE);

        JLabel subtitulo = new JLabel(
                "¡Que comience la partida!",
                SwingConstants.CENTER
        );
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 18));
        subtitulo.setForeground(new Color(189, 195, 199));

        panelTitulo.add(titulo);
        panelTitulo.add(subtitulo);

        // Botones
        JPanel panelBotones = new JPanel(new GridLayout(2, 1, 0, 25));
        panelBotones.setBackground(fondo);
        panelBotones.setBorder(
                BorderFactory.createEmptyBorder(30, 230, 100, 230)
        );

        configurarBoton(btnCrear, azul);
        configurarBoton(btnUnir, verde);

        panelBotones.add(btnCrear);
        panelBotones.add(btnUnir);

        principal.add(panelTitulo, BorderLayout.NORTH);
        principal.add(panelBotones, BorderLayout.CENTER);

        add(principal);
    }

    private void configurarBoton(JButton boton, Color color) {

        boton.setFont(new Font("Arial", Font.BOLD, 17));
        boton.setForeground(Color.WHITE);
        boton.setBackground(color);

        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(false);
        boton.setOpaque(true);

        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        boton.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(color, 2),
                        BorderFactory.createEmptyBorder(15, 20, 15, 20)
                )
        );

        boton.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(color.brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(color);
            }
        });
    }
}
