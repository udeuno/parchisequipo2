package Vista;


import javax.swing.*;
import java.awt.*;
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

    public VistaLobby(VistaChat vistaChat) {
        this.vistaChat = vistaChat;

        setLayout(new BorderLayout(10, 10));

        modeloLista = new DefaultListModel<>();
        listaJugadores = new JList<>(modeloLista);

        btnIniciar = new JButton("Iniciar partida");
        btnExpulsar = new JButton("expulsar jugador");
        btnSalir = new JButton("Salir del lobby");
        
        JPanel panelJugadores = new JPanel(new BorderLayout());
        panelJugadores.add(new JLabel("Jugadores"), BorderLayout.NORTH);
        panelJugadores.add(new JScrollPane(listaJugadores), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new GridLayout(2, 1, 5, 5));
        panelBotones.add(btnIniciar);
        panelBotones.add(btnExpulsar);
        panelBotones.add(btnSalir);

        JPanel panelIzquierdo = new JPanel(new BorderLayout(5, 5));
        panelIzquierdo.add(panelJugadores, BorderLayout.CENTER);
        panelIzquierdo.add(panelBotones, BorderLayout.SOUTH);

        add(panelIzquierdo, BorderLayout.WEST);
        add(vistaChat, BorderLayout.CENTER);

        btnIniciar.addActionListener(e -> {
            if (controlador != null) {
                controlador.iniciarPartida();
            }
        });
        
        btnExpulsar.addActionListener(e -> {
            if (controlador != null) {
                controlador. expulsarJugador();
            }
        });

        btnSalir.addActionListener(e -> {
            if (controlador != null) {
                controlador.salirDelLobby();
            }
        });
    }

    public void setControlador(ControladorLobby controlador) {
        this.controlador = controlador;
    }

    public ModeloJugador obtenerJugadorSeleccionado() {
        int indice = listaJugadores.getSelectedIndex();

        if (indice == -1) {
            return null;
        }

        return controlador.obtenerJugadores().get(indice);
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
}
