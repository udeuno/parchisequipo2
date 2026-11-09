package Controlador;

import Modelo.*;
import Vista.*;

public class CrontoladorCrearSala {

    public ControladorInicio inicio;
    private ControladorConexionServer conexion;

    public CrontoladorCrearSala(ControladorInicio inicio) {
        this.inicio = inicio;
        Configuracion();
    }

    public void Configuracion() {
        ModeloConfiguracion modeloConfiguracion = new ModeloConfiguracion();
        VistaConfiguracion vistaConfiguracion = new VistaConfiguracion();

        ControladorConfiguracion controladorConfiguracion
                = new ControladorConfiguracion(
                        modeloConfiguracion,
                        vistaConfiguracion,
                        this
                );

        vistaConfiguracion.setControlador(controladorConfiguracion);
        inicio.cambiarPanel(vistaConfiguracion);
    }

    public void Jugador(ModeloConfiguracion configuracion) {
        int capacidad = configuracion.getCantJugadores();

        conexion = new ControladorConexionServer(capacidad);
        conexion.setConfiguracion(configuracion);
        conexion.iniciarServidor();

        VistaRegistrarJugador vistaJugador = new VistaRegistrarJugador();

        new ControladorRegistroJugador(
                vistaJugador,
                inicio,
                true,
                null,
                conexion
        );

        inicio.cambiarPanel(vistaJugador);
    }

    public ControladorConexionServer getConexion() {
        return conexion;
    }
}