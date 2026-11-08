
package Modelo;

import java.util.ArrayList;

public class ModeloLobby {

    private ArrayList<ModeloJugador> jugadores;
    private boolean partidaIniciada;
    private int capacidad;

    public ModeloLobby(int capacidad) {
        jugadores = new ArrayList<>();
        partidaIniciada = false;
        this.capacidad = capacidad;
    }

    public boolean unirJugador(ModeloJugador jugador) {
        if (jugadores.size() >= capacidad || partidaIniciada) {
            return false;
        }

        jugadores.add(jugador);
        return true;
    }

    public void expulsarJugador(ModeloJugador jugador) {
        jugadores.remove(jugador);
    }

    public void iniciarPartida() {
        if (jugadores.size() >= 2) {
            partidaIniciada = true;
        }
    }

    public void reemplazarJugadores(ArrayList<ModeloJugador> jugadores) {
        if (jugadores.size() <= capacidad) {
            this.jugadores = new ArrayList<>(jugadores);
        }
    }

    public void salirDelLobby(ModeloJugador jugador) {
        jugadores.remove(jugador);
    }

    public ArrayList<ModeloJugador> getJugadores() {
        return new ArrayList<>(jugadores);
    }

    public boolean isPartidaIniciada() {
        return partidaIniciada;
    }

    public int getCapacidad() {
        return capacidad;
    }
}
