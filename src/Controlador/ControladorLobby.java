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
    private ControladorInicio Sala;
    private ControladorJuego controladorJuego;
    private ControladorConexionCliente conexionCliente;

    public ControladorLobby(ModeloLobby modelo, VistaLobby vista, ControladorChat controladorChat,
            boolean administrador, ControladorInicio sala) {

        this.modelo = modelo;
        this.vista = vista;
        this.controladorChat = controladorChat;
        this.administrador = administrador;
        this.Sala = sala;

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

        if (!administrador) {
            return;
        }

        modelo.iniciarPartida();

        if (!modelo.isPartidaIniciada()) {
            JOptionPane.showMessageDialog(
                    vista,
                    "Se necesitan al menos 2 jugadores."
            );
            return;
        }

        if (jugadorActual == null || conexionServidor == null) {
            JOptionPane.showMessageDialog(
                    vista,
                    "No se pudo iniciar la partida."
            );
            return;
        }

        ModeloConfiguracion configuracion
                = conexionServidor.getConfiguracion();

        if (configuracion == null) {
            JOptionPane.showMessageDialog(
                    vista,
                    "No se encontro la configuracion."
            );
            return;
        }

        controladorJuego = new ControladorJuego(
                modelo.getJugadores(),
                configuracion.isBarrerasActivas(),
                configuracion.isTiroExtraPorPar()
        );
        controladorJuego.setConexionServidor(conexionServidor);
        conexionServidor.setControladorJuego(controladorJuego);

        VistaJuego vistaJuego = new VistaJuego(
                controladorJuego.getTablero(),
                controladorChat.getVista(),
                jugadorActual.getColor()
        );

        controladorJuego.conectarVista(
                vistaJuego,
                jugadorActual.getColor()
        );

        Sala.cambiarPanel(vistaJuego);
        conexionServidor.notificarInicioPartida();
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

        if (controladorJuego != null) {
            controladorJuego.setConexionServidor(conexionServidor);
        }
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

    private void mostrarJuego(ModeloConfiguracion configuracion) {

        controladorJuego = new ControladorJuego(
                modelo.getJugadores(),
                configuracion.isBarrerasActivas(),
                configuracion.isTiroExtraPorPar()
        );

        controladorJuego.setConexionCliente(conexionCliente);

        VistaJuego vistaJuego = new VistaJuego(
                controladorJuego.getTablero(),
                controladorChat.getVista(),
                jugadorActual.getColor()
        );

        controladorJuego.conectarVista(
                vistaJuego,
                jugadorActual.getColor()
        );

        Sala.cambiarPanel(vistaJuego);
    }

    public void setConexionCliente(ControladorConexionCliente conexionCliente) {
        this.conexionCliente = conexionCliente;
    }

    public void recibirDados(int d1, int d2) {
        if (controladorJuego != null) {
            controladorJuego.recibirDados(d1, d2);
        }
    }

    public void recibirMovimiento(int color, int numeroFicha) {
        if (controladorJuego != null) {
            controladorJuego.recibirMovimiento(color, numeroFicha);
        }
    }

    public void iniciarPartidaRemota(String mensaje) {

        try {
            String[] partes = mensaje.split(":", 6);

            ModeloConfiguracion configuracion = new ModeloConfiguracion();

            configuracion.actualizarCapacidad(modelo.getCapacidad());

            configuracion.setRegla(
                    "Premio por llegar a meta (Casillas)",
                    Integer.parseInt(partes[1])
            );

            configuracion.setRegla(
                    "Recompensa por captura (Casillas)",
                    Integer.parseInt(partes[2])
            );

            configuracion.setRegla(
                    "Barreras activas (1=Si, 0=No)",
                    Integer.parseInt(partes[3])
            );

            configuracion.setRegla(
                    "Tiro extra por sacar pares (1=Si, 0=No)",
                    Integer.parseInt(partes[4])
            );

            java.util.ArrayList<ModeloJugador> jugadores
                    = new java.util.ArrayList<>();

            for (String dato : partes[5].split("\\|")) {

                String[] datosJugador = dato.split(":", 2);

                if (datosJugador.length == 2) {
                    jugadores.add(new ModeloJugador(
                            datosJugador[0],
                            Integer.parseInt(datosJugador[1])
                    ));
                }
            }

            modelo.reemplazarJugadores(jugadores);

            if (jugadorActual == null) {
                return;
            }

            mostrarJuego(configuracion);

        } catch (Exception e) {
            System.out.println("Error al iniciar partida: " + e.getMessage());
        }
    }

    public void recibirCambioTurno(int color) {
        if (controladorJuego != null) {
            controladorJuego.recibirCambioTurno(color);
        }
    }
}
