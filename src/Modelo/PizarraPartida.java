package Modelo;

import java.util.ArrayList;
import java.util.Comparator;

public class PizarraPartida {

    private Tablero tablero;
    private ArrayList<ModeloJugador> jugadores;
    private int turnoActual;
    private int dado1;
    private int dado2;
    private boolean dadoLanzado;
    private boolean turnoExtraPendiente;
    private boolean tiroExtraPorPar;
    private boolean partidaFinalizada;

    public PizarraPartida(ArrayList<ModeloJugador> jugadores) {
        this(jugadores, true);
    }

    public PizarraPartida(ArrayList<ModeloJugador> jugadores, boolean tiroExtraPorPar) {
        tablero = new Tablero();
        this.jugadores = new ArrayList<>(jugadores);
        this.jugadores.sort(Comparator.comparingInt(ModeloJugador::getColor));

        turnoActual = 0;
        dado1 = 0;
        dado2 = 0;
        dadoLanzado = false;
        turnoExtraPendiente = false;
        this.tiroExtraPorPar = tiroExtraPorPar;
        partidaFinalizada = false;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public ArrayList<ModeloJugador> getJugadores() {
        return new ArrayList<>(jugadores);
    }

    public ModeloJugador getJugadorActual() {
        if (jugadores.isEmpty()) {
            return null;
        }

        return jugadores.get(turnoActual);
    }

    public Ficha getFicha(int color, int numero) {
        for (Ficha ficha : tablero.getFichas()) {
            if (ficha.getColor() == color && ficha.getNumero() == numero) {
                return ficha;
            }
        }

        return null;
    }

    public int getTurnoActual() {
        return turnoActual;
    }

    public int getDado1() {
        return dado1;
    }

    public int getDado2() {
        return dado2;
    }

    public int getSumaDados() {
        return dado1 + dado2;
    }

    public int getResultadoDado() {
        return getSumaDados();
    }

    public boolean isPar() {
        return dado1 != 0 && dado1 == dado2;
    }

    public boolean puedeSacarDeCasa() {
        return dado1 == 5 || dado2 == 5;
    }

    public boolean isDadoLanzado() {
        return dadoLanzado;
    }

    public boolean isTurnoExtraPendiente() {
        return turnoExtraPendiente;
    }

    public boolean isPartidaFinalizada() {
        return partidaFinalizada;
    }

    public void registrarLanzamiento(int dado1, int dado2) {
        if (dadoLanzado || partidaFinalizada) {
            return;
        }

        if (dado1 < 1 || dado1 > 6 || dado2 < 1 || dado2 > 6) {
            return;
        }

        this.dado1 = dado1;
        this.dado2 = dado2;
        dadoLanzado = true;

        if (tiroExtraPorPar && isPar()) {
            turnoExtraPendiente = true;
        }
    }

    public void regresarCasa(Ficha ficha) {
        if (ficha == null || ficha.isEnCasa()) {
            return;
        }

        if (ficha.getPosicion() != null) {
            ficha.getPosicion().setOcupada(
                    ficha.getPosicion().getOcupada() - 1
            );
        }

        ficha.setPosicion(null);
        ficha.setEnCasa(true);
        ficha.setEnCaminoFinal(false);
        ficha.setIndiceCamino(-1);
    }

    public void finalizarTurno() {
        if (jugadores.isEmpty()) {
            return;
        }

        if (!turnoExtraPendiente) {
            turnoActual = (turnoActual + 1) % jugadores.size();
        }

        turnoExtraPendiente = false;
        dado1 = 0;
        dado2 = 0;
        dadoLanzado = false;
    }

    public void finalizarPartida() {
        partidaFinalizada = true;
    }

    public void sincronizarTurno(int color) {
        for (int i = 0; i < jugadores.size(); i++) {
            if (jugadores.get(i).getColor() == color) {
                turnoActual = i;
                dado1 = 0;
                dado2 = 0;
                dadoLanzado = false;
                turnoExtraPendiente = false;
                return;
            }
        }
    }
}
