
package Vista;

import Modelo.*;
import Controlador.*;
import javax.swing.*;
import java.awt.*;

public class VistaJuego extends JPanel {

    private VentanaTablero ventanaTablero;
    private VistaChat vistaChat;
    private JButton btnDados;
    private JLabel lblDados;
    private JLabel lblTurno;
    private JLabel lblImagenDados;
    private ControladorJuego controlador;

    public VistaJuego(Tablero tablero, VistaChat vistaChat, int colorJugador) {

        this.vistaChat = vistaChat;

        setLayout(new BorderLayout(10, 10));

        ventanaTablero = new VentanaTablero(tablero, colorJugador);

        JPanel panelDerecho = new JPanel(new BorderLayout(5, 5));

        JPanel panelDados = new JPanel(new GridLayout(4, 1, 5, 5));

        lblTurno = new JLabel("Turno: esperando");
        lblDados = new JLabel("Dados: - / -");
        lblImagenDados = new JLabel("🎲 🎲", SwingConstants.CENTER);

        btnDados = new JButton("Lanzar dados");

        panelDados.add(lblTurno);
        panelDados.add(lblImagenDados);
        panelDados.add(lblDados);
        panelDados.add(btnDados);

        panelDerecho.add(panelDados, BorderLayout.NORTH);
        panelDerecho.add(vistaChat, BorderLayout.CENTER);

        add(ventanaTablero, BorderLayout.CENTER);
        add(panelDerecho, BorderLayout.EAST);

        btnDados.addActionListener(e -> {
            if (controlador != null) {
                controlador.lanzarDados(colorJugador);
            }
        });

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
