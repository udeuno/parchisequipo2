package Controlador;

import Modelo.*;
import Vista.*;

public class ControladorChat {

    private ModeloChat modelo;
    private VistaChat vista;
    private ModeloJugador jugadorActual;
    private ModeloConexion conexionServidor;
    private ControladorConexionJugador conexionJugador;

    public ControladorChat(ModeloChat modelo, VistaChat vista) {
        this.modelo = modelo;
        this.vista = vista;
        vista.setControlador(this);
    }

    public void setConexionServidor(
            ModeloConexion conexionServidor) {

        this.conexionServidor = conexionServidor;
    }

    public void setJugadorActual(ModeloJugador jugador) {
        jugadorActual = jugador;
    }

    public void setConexionJugador(
            ControladorConexionJugador conexionJugador) {

        this.conexionJugador = conexionJugador;
    }

    public void recibirMensaje(String texto) {

        vista.mostrarMensaje(texto);
    }

    public void enviarMensaje() {

        String texto
                = vista.obtenerTexto().trim();

        if (texto.isEmpty()
                || jugadorActual == null) {

            return;
        }

        String mensaje
                = jugadorActual.getNombre()
                + ": "
                + texto;

        if (conexionJugador != null) {

            conexionJugador.enviarMensaje(
                    mensaje
            );

        } else if (conexionServidor != null) {

            conexionServidor.enviarMensajeAdmin(
                    mensaje
            );
        }

        System.out.println(
                "Enviando mensaje: " + mensaje
        );
        vista.limpiarMensaje();
    }

    public void actualizarChat() {
        vista.limpiarConversacion();

        for (ModeloMensaje mensaje : modelo.obtenerMensajes()) {
            String texto = mensaje.getEmisor().getNombre()
                    + ": " + mensaje.getMensaje();

            vista.mostrarMensaje(texto);
        }
    }
}
