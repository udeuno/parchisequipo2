package Modelo;


import java.util.HashMap;
import java.util.Map;

public class ModeloConfiguracion {

    private int cantJugadores;
    private Map<String, Integer> reglasParchis;
    

    public ModeloConfiguracion() {

        reglasParchis = new HashMap<>();

        reglasParchis.put("Premio por llegar a meta (Casillas)", 10);
        reglasParchis.put("Recompensa por captura (Casillas)", 10);
        reglasParchis.put("Tiro extra por sacar pares (1=Si, 0=No)", 1);
        reglasParchis.put("Barreras activas (1=Si, 0=No)", 1);
    }

    public void actualizarCapacidad(int cant) {
        cantJugadores = cant;
    }

    public void asignarFichasYColores() {
    }

    public void setRegla(String regla, int valor) {
        reglasParchis.put(regla, valor);
    }

    public int getCantJugadores() {
        return cantJugadores;
    }

    public String getDatosGuardados() {
        return "Jugadores en tablero: " + cantJugadores
                + "\nReglas de partida: " + reglasParchis;
    }
}