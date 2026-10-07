package Controlador;

import Modelo.*;
import Vista.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorRegistroJugador {

    private VistaRegistrarJugador vista;
    private ControladorInicio Sala;
    private boolean admin;
    private ControladorConexionJugador conexionJugador;
    private ControladorConexion conexionServidor;

    public ControladorRegistroJugador(
            VistaRegistrarJugador vista,
            ControladorInicio Sala,
            boolean admin,
            ControladorConexionJugador conexionJugador) {

        this.vista = vista;
        this.Sala = Sala;
        this.admin = admin;
        this.conexionJugador = conexionJugador;

        inicializarEventos();
    }

    public ControladorRegistroJugador(
            VistaRegistrarJugador vista,
            ControladorInicio Sala,
            boolean admin,
            ControladorConexionJugador conexionJugador,
            ControladorConexion conexionServidor) {

        this.vista = vista;
        this.Sala = Sala;
        this.admin = admin;
        this.conexionJugador = conexionJugador;
        this.conexionServidor = conexionServidor;

        inicializarEventos();
    }

    private void inicializarEventos() {

        vista.getBtnRegistrar().addActionListener(
                new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                registrarJugador();
            }
        });
    }

    public boolean validarDatos(String nombre) {

        return nombre != null
                && !nombre.trim().isEmpty();
    }

    public void registrarJugador() {

        String nombre
                = vista.getTxtNombre()
                        .getText()
                        .trim();

        int color
                = vista.getComboColor()
                        .getSelectedIndex();

        if (!validarDatos(nombre)) {

            vista.mostrarMensajeDeError(
                    "El nombre no puede estar vacio"
            );

            return;
        }

        guardarJugador(nombre, color);

        if (admin && conexionServidor != null) {

            conexionServidor.getConexion().agregarAdmin(
                    nombre,
                    color
            );
        }

        if (conexionJugador != null) {

            conexionJugador.enviarJugador(
                    nombre,
                    color
            );
        }

        vista.mostrarMensajeDeExito(
                "Jugador registrado con exito"
        );
    }

    public ModeloJugador guardarJugador(
            String nombre,
            int color) {

        ModeloJugador nuevoJugador
                = new ModeloJugador(
                        nombre,
                        color
                );

        ModeloChat modeloChat
                = new ModeloChat();

        VistaChat vistaChat
                = new VistaChat();

        ControladorChat controladorChat
                = new ControladorChat(
                        modeloChat,
                        vistaChat
                );

        if (conexionJugador != null) {

            conexionJugador.setControladorChat(
                    controladorChat
            );

            controladorChat.setConexionJugador(
                    conexionJugador
            );
        }

        if (admin && conexionServidor != null) {

            conexionServidor.getConexion()
                    .setChatAdmin(
                            controladorChat
                    );

            controladorChat.setConexionServidor(
                    conexionServidor.getConexion()
            );
        }
        
        ModeloLobby modeloLobby
                = new ModeloLobby();

        VistaLobby vistaLobby
                = new VistaLobby(vistaChat);

        ControladorLobby controladorLobby
                = new ControladorLobby(
                        modeloLobby,
                        vistaLobby,
                        controladorChat
                );

        if (conexionJugador != null) {

            conexionJugador.setControladorLobby(
                    controladorLobby
            );
        }

        if (admin && conexionServidor != null) {

            conexionServidor.getConexion()
                    .setLobbyAdmin(
                            controladorLobby
                    );
        }

        Sala.cambiarPanel(vistaLobby);

        controladorLobby.unirJugador(
                nuevoJugador
        );

        return nuevoJugador;
    }
}
