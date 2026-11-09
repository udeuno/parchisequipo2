package Controlador;

import Modelo.*;
import Vista.*;

public class ControladorRegistroJugador {

    private VistaRegistrarJugador vista;
    private ControladorInicio Sala;
    private boolean admin;
    private ControladorConexionCliente conexionJugador;
    private ControladorConexionServer conexionServidor;

    public ControladorRegistroJugador(VistaRegistrarJugador vista, ControladorInicio Sala, boolean admin, ControladorConexionCliente conexionJugador, ControladorConexionServer conexionServidor) {
        this.vista = vista;
        this.Sala = Sala;
        this.admin = admin;
        this.conexionJugador = conexionJugador;
        this.conexionServidor = conexionServidor;

        vista.getBtnRegistrar().addActionListener(e -> registrarJugador());
    }

    public boolean validarDatos(String nombre) {
        return nombre != null && !nombre.trim().isEmpty();
    }

    public void registrarJugador() {
        String nombre = vista.getTxtNombre().getText().trim();
        int color = vista.obtenerColorSeleccionado();

        if (color == -1) {
            vista.mostrarMensajeDeError("No hay colores disponibles");
            return;
        }
        if (!validarDatos(nombre)) {
            vista.mostrarMensajeDeError("El nombre no puede estar vacio");
            return;
        }

        ModeloJugador jugador = guardarJugador(nombre, color);
        registrarConexion(nombre, color);
        vista.mostrarMensajeDeExito("Jugador registrado con exito");
    }

    public ModeloJugador guardarJugador(String nombre, int color) {
        ModeloJugador jugador = new ModeloJugador(nombre, color);

        ControladorChat controladorChat = crearChat();
        configurarChat(controladorChat);

        ControladorLobby controladorLobby = crearLobby(controladorChat);
        configurarLobby(controladorLobby);

        Sala.cambiarPanel(controladorLobby.getVista());
        controladorLobby.unirJugador(jugador);

        return jugador;
    }

    private ControladorChat crearChat() {
        ModeloChat modeloChat = new ModeloChat();
        VistaChat vistaChat = new VistaChat();

        return new ControladorChat(modeloChat, vistaChat);
    }

    private void configurarChat(ControladorChat controladorChat) {
        if (conexionJugador != null) {
            conexionJugador.setControladorChat(controladorChat);
            controladorChat.setConexionJugador(conexionJugador);
        }

        if (admin && conexionServidor != null) {
            conexionServidor.setChatAdmin(controladorChat);
            controladorChat.setConexionServidor(conexionServidor);
        }
    }

    private ControladorLobby crearLobby(ControladorChat controladorChat) {
        ModeloLobby modeloLobby = new ModeloLobby(conexionServidor != null
                ? conexionServidor.getCapacidad() : 4);
        VistaLobby vistaLobby = new VistaLobby(controladorChat.getVista());

        return new ControladorLobby(modeloLobby,vistaLobby,controladorChat,admin,Sala);
    }

    private void configurarLobby(ControladorLobby controladorLobby) {
        if (conexionJugador != null) {
            conexionJugador.setControladorLobby(controladorLobby);
        }

        if (admin && conexionServidor != null) {
            conexionServidor.setLobbyAdmin(controladorLobby);
            controladorLobby.setConexionServidor(conexionServidor);
        }
    }

    private void registrarConexion(String nombre, int color) {
        if (admin && conexionServidor != null) {
            conexionServidor.agregarAdmin(nombre, color);
        }

        if (conexionJugador != null) {
            conexionJugador.enviarJugador(nombre, color);
        }
    }
}
