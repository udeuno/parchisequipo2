
package Vista;

import Modelo.*;
import Controlador.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class VistaJuego extends JPanel {

    private VentanaTablero ventanaTablero;
    private VistaChat vistaChat;
    private JButton btnDados;
    private JLabel lblDados;
    private JLabel lblTurno;
    private JLabel lblImagenDados;
    private ControladorJuego controlador;

    private final Color fondo = new Color(24, 32, 52);
    private final Color panelColor = new Color(36, 47, 68);
    private final Color azul = new Color(52, 152, 219);
    private final Color texto = new Color(236, 240, 241);

    public VistaJuego(Tablero tablero, VistaChat vistaChat, int colorJugador) {

        this.vistaChat = vistaChat;

        setLayout(new BorderLayout(10, 10));
        setBackground(fondo);
        setBorder(new EmptyBorder(12, 12, 12, 12));

        // Tablero
        ventanaTablero = new VentanaTablero(tablero, colorJugador);

        // Panel derecho
        JPanel panelDerecho = new JPanel(new BorderLayout(8, 8));
        panelDerecho.setBackground(fondo);
        panelDerecho.setPreferredSize(new Dimension(320, 700));

        // Panel de dados
        JPanel panelDados = new JPanel(new GridLayout(4, 1, 5, 8));
        panelDados.setBackground(panelColor);
        panelDados.setBorder(new EmptyBorder(15, 15, 15, 15));

        lblTurno = new JLabel("Turno: esperando");
        lblTurno.setForeground(texto);
        lblTurno.setFont(new Font("Arial", Font.BOLD, 16));

        lblImagenDados = new JLabel("🎲       🎲", SwingConstants.CENTER);
        lblImagenDados.setForeground(texto);
        lblImagenDados.setFont(new Font("Arial", Font.BOLD, 30));

        lblDados = new JLabel("Dados: - / -", SwingConstants.CENTER);
        lblDados.setForeground(texto);
        lblDados.setFont(new Font("Arial", Font.PLAIN, 16));

        btnDados = new JButton("Lanzar dados");
        btnDados.setBackground(azul);
        btnDados.setForeground(Color.WHITE);
        btnDados.setFont(new Font("Arial", Font.BOLD, 14));
        btnDados.setFocusPainted(false);
        btnDados.setBorderPainted(false);
        btnDados.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnDados.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btnDados.setBackground(new Color(41, 128, 185));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btnDados.setBackground(azul);
            }
        });

        panelDados.add(lblTurno);
        panelDados.add(lblImagenDados);
        panelDados.add(lblDados);
        panelDados.add(btnDados);

        // Chat
        panelDerecho.add(panelDados, BorderLayout.NORTH);
        panelDerecho.add(vistaChat, BorderLayout.CENTER);

        // Distribución
        add(ventanaTablero, BorderLayout.CENTER);
        add(panelDerecho, BorderLayout.EAST);

        // Evento de dados
        btnDados.addActionListener(e -> {
            if (controlador != null) {
                controlador.lanzarDados(colorJugador);
            }
        });

        // Selección de fichas
        ventanaTablero.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {

                Ficha ficha = ventanaTablero.obtenerFicha(
                    e.getX(),
                    e.getY()
                );

                if (ficha != null && controlador != null) {
                    controlador.seleccionarFicha(ficha);
                }
            }
        });
    }

    public void setControlador(ControladorJuego controlador) {
        this.controlador = controlador;
    }

    public void actualizarDados(int dado1, int dado2) {
        lblDados.setText("Dados: " + dado1 + " / " + dado2);
        lblImagenDados.setText("🎲 " + dado1 + "     🎲 " + dado2);
    }

    public void actualizarTurno(String nombre) {
        lblTurno.setText("Turno: " + nombre);
    }

    public void actualizarTablero() {
        ventanaTablero.repaint();
    }

    public void actualizarJugadores(java.util.ArrayList<ModeloJugador> jugadores) {
        ventanaTablero.setJugadores(jugadores);
    }

    public void configurarBotonDados(boolean habilitado) {
        btnDados.setEnabled(habilitado);
    }

    public VentanaTablero getVentanaTablero() {
        return ventanaTablero;
    }
}
