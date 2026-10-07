package Controlador;

import Modelo.ModeloConfiguracion;
import Vista.VistaConfiguracion;
import javax.swing.JOptionPane;

public class ControladorConfiguracion {

    private ModeloConfiguracion modelo;
    private VistaConfiguracion vistaConfig;
    private CrontoladorCrearSala sala;

    public ControladorConfiguracion(
            ModeloConfiguracion modelo,
            VistaConfiguracion vistaConfig,
            CrontoladorCrearSala sala) {

        this.modelo = modelo;
        this.vistaConfig = vistaConfig;
        this.sala=sala;

    }

    public void CrearPartida() {

        System.out.println("XD");
        int jugadores = (int) vistaConfig.comboJugadores.getSelectedItem();
        int premio = (int) vistaConfig.spPremioMeta.getValue();
        int captura = (int) vistaConfig.spRecompensaCaptura.getValue();
        int barreras = vistaConfig.chkBarreras.isSelected() ? 1 : 0;

        if (setJugadores(jugadores)) {

            actualizarReglas(
                    "Premio por llegar a meta (Casillas)",
                    premio
            );

            actualizarReglas(
                    "Recompensa por captura (Casillas)",
                    captura
            );

            actualizarReglas(
                    "Barreras activas (1=Si, 0=No)",
                    barreras
            );

            iniciarPartida();
        }
    }

    public boolean setJugadores(int cant) {

        if (cant >= 2 && cant <= 4) {

            modelo.actualizarCapacidad(cant);
            modelo.asignarFichasYColores();

            return true;
        }

        return false;
    }

    public void pedirResumen() {
        vistaConfig.mostrarResumen(modelo.getDatosGuardados());
    }

    public void actualizarReglas(String nombreRegla, int valor) {
        modelo.setRegla(nombreRegla, valor);
    }

    public void iniciarPartida() {

        if (modelo.getCantJugadores() >= 2 && modelo.getCantJugadores() <= 4) {
            
            JOptionPane.showMessageDialog(vistaConfig, "Configuracion validada correctamente.\nAbriendo sala de espera...");
            sala.Jugador();
            
        } else {

            JOptionPane.showMessageDialog(vistaConfig, "Configura entre 2 y 4 jugadores.");
        }
    }
}
