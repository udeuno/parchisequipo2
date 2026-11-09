
package Controlador;

import Modelo.*;

public class FuenteCapturas {

    private boolean barrerasActivas;

    public FuenteCapturas() {
        this(true);
    }

    public FuenteCapturas(boolean barrerasActivas) {
        this.barrerasActivas = barrerasActivas;
    }

    public boolean capturar(PizarraPartida pizarra, Ficha ficha) {

        if (ficha == null || ficha.isEnCasa()
                || ficha.isEnCaminoFinal()
                || ficha.getPosicion() == null) {
            return false;
        }

        Casilla posicion = ficha.getPosicion();

        if (esCasillaSegura(pizarra, posicion)) {
            return false;
        }

        Ficha enemiga = null;
        int cantidadEnemigos = 0;

        for (Ficha otra : pizarra.getTablero().getFichas()) {

            if (otra == ficha || otra.isEnCasa()) {
                continue;
            }

            if (otra.getPosicion() == posicion
                    && otra.getColor() != ficha.getColor()) {

                cantidadEnemigos++;
                enemiga = otra;
            }
        }

        if (cantidadEnemigos != 1) {
            return false;
        }

        pizarra.regresarCasa(enemiga);
        return true;
    }

    private boolean esCasillaSegura(PizarraPartida pizarra, Casilla casilla) {

        if (casilla instanceof CasillaFinal) {
            return true;
        }

        int[] salidas = {5, 22, 39, 56};

        for (int salida : salidas) {
            if (pizarra.getTablero().getRecorrido().get(salida) == casilla) {
                return true;
            }
        }

        return false;
    }

    public boolean hayBarrera(PizarraPartida pizarra, Casilla casilla) {

        if (!barrerasActivas) {
            return false;
        }

        int cantidad = 0;

        for (Ficha ficha : pizarra.getTablero().getFichas()) {

            if (!ficha.isEnCasa() && !ficha.isEnCaminoFinal()
                    && ficha.getPosicion() == casilla) {
                cantidad++;
            }
        }

        return cantidad >= 2;
    }
}
