
package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import Modelo.*;
import Controlador.*;

public class VistaLobby extends JPanel {

    private JList<String> listaJugadores;
    private DefaultListModel<String> modeloLista;

    private JButton btnIniciar;
    private JButton btnExpulsar;
    private JButton btnSalir;

    private VistaChat vistaChat;
    private ControladorLobby controlador;

    private Color fondo = new Color(24, 32, 52);
    private Color panelColor = new Color(36, 47, 68);
    private Color azul = new Color(52, 152, 219);
    private Color verde = new Color(46, 204, 113);
    private Color rojo = new Color(231, 76, 60);

    public VistaLobby(VistaChat vistaChat) {

        this.vistaChat = vistaChat;

        setLayout(new BorderLayout(15, 15));
        setBackground(fondo);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        modeloLista = new DefaultListModel<>();
        listaJugadores = new JList<>(modeloLista);

        listaJugadores.setFont(new Font("Arial", Font.PLAIN, 16));
        listaJugadores.setForeground(Color.WHITE);
        listaJugadores.setBackground(panelColor);
        listaJugadores.setSelectionBackground(azul);
        listaJugadores.setSelectionForeground(Color.WHITE);
        listaJugadores.setFixedCellHeight(40);
        listaJugadores.setBorder(
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        );

        // Panel de jugadores
        JPanel panelJugadores = new JPanel(new BorderLayout(5, 10));
        panelJugadores.setBackground(panelColor);
        panelJugadores.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(60, 75, 100), 1
                        ),
                        BorderFactory.createEmptyBorder(15, 15, 15, 15)
                )
        );

        JLabel tituloJugadores = new JLabel("JUGADORES");
        tituloJugadores.setFont(new Font("Arial", Font.BOLD, 18));
        tituloJugadores.setForeground(Color.WHITE);

        JScrollPane scrollJugadores = new JScrollPane(listaJugadores);
        scrollJugadores.setBorder(BorderFactory.createEmptyBorder());
        scrollJugadores.getViewport().setBackground(panelColor);

        panelJugadores.add(tituloJugadores, BorderLayout.NORTH);
        panelJugadores.add(scrollJugadores, BorderLayout.CENTER);

        // Botones
        btnIniciar = new JButton("INICIAR PARTIDA");
        btnExpulsar = new JButton("EXPULSAR JUGADOR");
        btnSalir = new JButton("SALIR DEL LOBBY");

        configurarBoton(btnIniciar, verde);
        configurarBoton(btnExpulsar, rojo);
        configurarBoton(btnSalir, azul);

        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 0, 10));
        panelBotones.setBackground(panelColor);

        panelBotones.add(btnIniciar);
        panelBotones.add(btnExpulsar);
        panelBotones.add(btnSalir);

        panelJugadores.add(panelBotones, BorderLayout.SOUTH);
        panelJugadores.setPreferredSize(new Dimension(260, 0));

        // Panel del chat
        JPanel panelChat = new JPanel(new BorderLayout());
        panelChat.setBackground(panelColor);
        panelChat.setBorder(
                BorderFactory.createLineBorder(
                        new Color(60, 75, 100), 1
                )
        );

        panelChat.add(vistaChat, BorderLayout.CENTER);

        // Agregar paneles
        add(panelJugadores, BorderLayout.WEST);
        add(panelChat, BorderLayout.CENTER);

        // Eventos
        btnIniciar.addActionListener(e -> {
            if (controlador != null) {
                controlador.iniciarPartida();
            }
        });

        btnExpulsar.addActionListener(e -> {
            if (controlador != null) {
                controlador.expulsarJugador();
            }
        });

        btnSalir.addActionListener(e -> {
            if (controlador != null) {
                controlador.salirDelLobby();
            }
        });
    }

    private void configurarBoton(JButton boton, Color color) {

        boton.setFont(new Font("Arial", Font.BOLD, 12));
        boton.setForeground(Color.WHITE);
        boton.setBackground(color);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setOpaque(true);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        boton.setPreferredSize(new Dimension(220, 40));

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

    public void setControlador(ControladorLobby controlador) {
        this.controlador = controlador;
    }

    public ModeloJugador obtenerJugadorSeleccionado() {

        int indice = listaJugadores.getSelectedIndex();

        if (indice == -1 || controlador == null) {
            return null;
        }

        ArrayList<ModeloJugador> jugadores = controlador.obtenerJugadores();

        if (indice >= jugadores.size()) {
            return null;
        }

        return jugadores.get(indice);
    }

    public void mostrarJugadores(ArrayList<ModeloJugador> jugadores) {

        modeloLista.clear();

        for (ModeloJugador jugador : jugadores) {
            modeloLista.addElement(jugador.getNombre());
        }
    }

    public void mostrarLobby() {
        revalidate();
        repaint();
    }

    public void configurarAdministrador(boolean administrador) {
        btnExpulsar.setVisible(administrador);
        btnIniciar.setVisible(administrador);
    }
}
