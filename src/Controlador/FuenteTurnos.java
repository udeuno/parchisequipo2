
package Controlador;

import Modelo.*;

public class FuenteTurnos {

    public ModeloJugador obtenerJugadorActual(PizarraPartida pizarra) {
        return pizarra.getJugadorActual();
    }

    public boolean esTurnoDe(PizarraPartida pizarra, int color) {
        ModeloJugador jugador = pizarra.getJugadorActual();

        return jugador != null && jugador.getColor() == color;
    }

    public boolean puedeLanzar(PizarraPartida pizarra, int color) {
        return !pizarra.isPartidaFinalizada()
                && !pizarra.isDadoLanzado()
                && esTurnoDe(pizarra, color);
    }

    public boolean puedeMover(PizarraPartida pizarra, int color) {
        return !pizarra.isPartidaFinalizada()
                && pizarra.isDadoLanzado()
                && esTurnoDe(pizarra, color);
    }

    public void finalizarTurno(PizarraPartida pizarra) {
        if (pizarra.isPartidaFinalizada() || !pizarra.isDadoLanzado()) {
            return;
        }

        pizarra.finalizarTurno();
    }
}
