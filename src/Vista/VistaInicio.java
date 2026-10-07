package Vista;

import javax.swing.*;
import java.awt.*;

public class VistaInicio extends JFrame {
    public JButton btnCrear = new JButton("Crear partida");
    public JButton btnUnir = new JButton("Unirse a partida");
    
    public VistaInicio() {
        setTitle("Parchis");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Parchis", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 32));
        titulo.setBorder(BorderFactory.createEmptyBorder(80, 0, 40, 0));

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(2, 1, 0, 20));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(30, 250, 100, 250));

        panelBotones.add(btnCrear);
        panelBotones.add(btnUnir);

        add(titulo, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);
    }
}
