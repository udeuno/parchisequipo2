package Controlador;

import Modelo.*;
import java.util.List;

public class FuenteMovimiento {

    private FuenteCapturas fuenteCapturas;

    public FuenteMovimiento(FuenteCapturas fuenteCapturas) {
        this.fuenteCapturas = fuenteCapturas;
    }

    public boolean mover(PizarraPartida pizarra, Ficha ficha) {

        if (ficha == null || !pizarra.isDadoLanzado()
                || pizarra.isPartidaFinalizada()) {
            return false;
        }

        ModeloJugador jugador = pizarra.getJugadorActual();

        if (jugador == null || jugador.getColor() != ficha.getColor()) {
            return false;
        }

        Tablero tablero = pizarra.getTablero();

        if (ficha.isEnCasa()) {

            if (!pizarra.puedeSacarDeCasa()) {
                return false;
            }

            int[] salidas = {5, 22, 39, 56};

            Casilla salida = tablero.getRecorrido().get(
                    salidas[ficha.getColor()]
            );

            ficha.setPosicion(salida);
            ficha.setEnCasa(false);
            ficha.setEnCaminoFinal(false);
            ficha.setIndiceCamino(-1);

            salida.setOcupada(salida.getOcupada() + 1);

            return true;
        }

        int pasos = pizarra.getSumaDados();

        Casilla actual = ficha.getPosicion();
        Casilla destino = actual;

        boolean entraFinal = ficha.isEnCaminoFinal();
        int indiceFinal = ficha.getIndiceCamino();

        List<Casilla> recorrido = tablero.getRecorrido();

        CasillaPaso[] caminoFinal = tablero.getFinales()[ficha.getColor()].getCamino();

        for (int i = 0; i < pasos; i++) {

            if (entraFinal) {

                int siguiente = indiceFinal + 1;

                if (siguiente >= caminoFinal.length) {
                    return false;
                }

                indiceFinal = siguiente;
                destino = caminoFinal[indiceFinal];

            } else {

                int indice = recorrido.indexOf(destino);

                if (indice == -1) {
                    return false;
                }

                Casilla siguiente = recorrido.get(
                        (indice + 1) % recorrido.size()
                );

                destino = siguiente;

                if (siguiente instanceof CasillaFinal
                        && ((CasillaFinal) siguiente).getColor()
                        == ficha.getColor()) {

                    entraFinal = true;
                    indiceFinal = -1;
                }
            }
        }

        actual.setOcupada(actual.getOcupada() - 1);
        destino.setOcupada(destino.getOcupada() + 1);

        ficha.setPosicion(destino);
        ficha.setEnCaminoFinal(entraFinal);
        ficha.setIndiceCamino(indiceFinal);

        if (fuenteCapturas != null) {
            fuenteCapturas.capturar(pizarra, ficha);
        }

        return true;
    }
}
