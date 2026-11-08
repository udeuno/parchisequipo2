package Controlador;

import javax.swing.*;
import Modelo.*;
import Vista.*;

public class ControladorLobby {

    private ModeloLobby modelo;
    private VistaLobby vista;
    private ControladorChat controladorChat;
    private ModeloJugador jugadorActual;
    private boolean administrador;
    private ControladorConexionServer conexionServidor;

    public ControladorLobby(ModeloLobby modelo, VistaLobby vista,
            ControladorChat controladorChat, boolean administrador) {
        this.modelo = modelo;
        this.vista = vista;
        this.controladorChat = controladorChat;
        this.administrador = administrador;

        vista.setControlador(this);
        vista.configurarAdministrador(administrador);
        mostrarJugadores();
    }

    public void actualizarJugadores(
            java.util.ArrayList<ModeloJugador> jugadores) {

        modelo.reemplazarJugadores(jugadores);
        mostrarJugadores();
    }

    public void unirJugador() {
        String nombre = JOptionPane.showInputDialog(
                vista, "Nombre del jugador:");

        if (nombre == null || nombre.trim().isEmpty()) {
            return;
        }

        ModeloJugador jugador = new ModeloJugador(nombre.trim(), 0);
        unirJugador(jugador);
    }

    public void unirJugador(ModeloJugador jugador) {
        if (modelo.unirJugador(jugador)) {
            if (jugadorActual == null) {
                jugadorActual = jugador;
                controladorChat.setJugadorActual(jugador);
            }

            mostrarJugadores();
        } else {
            JOptionPane.showMessageDialog(vista,
                    "El lobby esta lleno o la partida ya inicio.");
        }
    }

    public void expulsarJugador() {
        if (!administrador || conexionServidor == null) {
            return;
        }

        ModeloJugador jugador = vista.obtenerJugadorSeleccionado();

        if (jugador == null || jugador == jugadorActual) {
            return;
        }

        conexionServidor.expulsarJugador(jugador);
    }

    public void iniciarPartida() {
        modelo.iniciarPartida();

        if (modelo.isPartidaIniciada()) {
            JOptionPane.showMessageDialog(vista,
                    "La partida ha comenzado.");
        } else {
            JOptionPane.showMessageDialog(vista,
                    "Se necesitan al menos 2 jugadores.");
        }
    }

    public void salirDelLobby() {
        if (jugadorActual == null) {
            return;
        }

        modelo.salirDelLobby(jugadorActual);
        jugadorActual = null;
        controladorChat.setJugadorActual(null);
        mostrarJugadores();
    }

    public void mostrarJugadores() {
        vista.mostrarJugadores(modelo.getJugadores());
        vista.mostrarLobby();
    }

    public java.util.ArrayList<ModeloJugador> obtenerJugadores() {
        return modelo.getJugadores();
    }

    public VistaLobby getVista() {
        return vista;
    }

    public void setConexionServidor(ControladorConexionServer conexionServidor) {
        this.conexionServidor = conexionServidor;
    }

    public void expulsadoDelLobby() {
        if (jugadorActual != null) {
            modelo.salirDelLobby(jugadorActual);
            jugadorActual = null;
            controladorChat.setJugadorActual(null);
        }

        mostrarJugadores();

        JOptionPane.showMessageDialog(vista, "Has sido expulsado de la partida.");
    }
}
