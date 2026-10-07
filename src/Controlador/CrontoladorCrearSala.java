package Controlador;

import Modelo.*;
import Vista.*;

public class CrontoladorCrearSala {

    public ControladorInicio inicio;
    private ControladorConexion Conexion;

    public CrontoladorCrearSala(
            ControladorInicio inicio) {

        this.inicio = inicio;
        Configuracion();
    }

    public void Configuracion() {

        ModeloConfiguracion MConfiguracion
                = new ModeloConfiguracion();

        VistaConfiguracion VConfiguracion
                = new VistaConfiguracion();

        ControladorConfiguracion CConfiguracion
                = new ControladorConfiguracion(
                        MConfiguracion,
                        VConfiguracion,
                        this
                );

        VConfiguracion.setControlador(
                CConfiguracion
        );

        inicio.cambiarPanel(
                VConfiguracion
        );
    }

    public void Jugador() {
        Conexion
                = new ControladorConexion(
                        true,
                        inicio
                );

        VistaRegistrarJugador Vjugador
                = new VistaRegistrarJugador();

        ControladorRegistroJugador CJugador
                = new ControladorRegistroJugador(
                        Vjugador,
                        inicio,
                        true,
                        null,
                        Conexion
                );

        inicio.cambiarPanel(Vjugador);
    }

    public ControladorConexion getConexion() {
        return Conexion;
    }
}
