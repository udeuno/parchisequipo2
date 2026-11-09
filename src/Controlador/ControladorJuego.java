package Controlador;

import Modelo.*;
import Vista.*;
import java.util.ArrayList;

public class ControladorJuego {

    private PizarraPartida pizarra;
    private FuenteDados fuenteDados;
    private FuenteMovimiento fuenteMovimiento;
    private FuenteCapturas fuenteCapturas;
    private FuenteTurnos fuenteTurnos;
    private Ficha fichaSeleccionada;
    private VistaJuego vista;
    private int colorJugador;
    private ControladorConexionCliente conexionCliente;
    private ControladorConexionServer conexionServidor;

    public ControladorJuego(ArrayList<ModeloJugador> jugadores,
            boolean barrerasActivas, boolean tiroExtraPorPar) {

        pizarra = new PizarraPartida(jugadores, tiroExtraPorPar);
        fuenteCapturas = new FuenteCapturas(barrerasActivas);
        fuenteDados = new FuenteDados();
        fuenteMovimiento = new FuenteMovimiento(fuenteCapturas);
        fuenteTurnos = new FuenteTurnos();
    }

    public void conectarVista(VistaJuego vista, int colorJugador) {
        this.vista = vista;
        this.colorJugador = colorJugador;

        vista.setControlador(this);
        actualizarVista();
    }

    public int[] lanzarDados(int color) {
        if (conexionCliente != null) {
            conexionCliente.enviarSolicitud("LANZAR_DADOS");
            return null;
        }

        if (!fuenteTurnos.puedeLanzar(pizarra, color)) {
            return null;
        }

        int[] dados = fuenteDados.lanzar(pizarra);

        if (dados != null) {
            actualizarVista();

            if (conexionServidor != null) {
                conexionServidor.notificarDados(dados[0], dados[1]);
            }

            comprobarTurnoSinMovimientos(color);
        }

        return dados;
    }

    private void comprobarTurnoSinMovimientos(int color) {
        boolean tieneFichaFuera = false;

        for (Ficha ficha : pizarra.getTablero().getFichas()) {
            if (ficha.getColor() == color && !ficha.isEnCasa()) {
                tieneFichaFuera = true;
                break;
            }
        }

        if (!pizarra.puedeSacarDeCasa() && !tieneFichaFuera) {
            new Thread(() -> {
                try {
                    Thread.sleep(3000);

                    javax.swing.SwingUtilities.invokeLater(() -> {
                        if (pizarra.isDadoLanzado()
                                && obtenerJugadorActual() != null
                                && obtenerJugadorActual().getColor() == color) {
                            finalizarTurno();
                        }
                    });

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }
    }

    public boolean moverFicha(int color, int numeroFicha) {
        if (conexionCliente != null) {
            conexionCliente.enviarSolicitud("MOVER_FICHA:" + numeroFicha);
            return true;
        }

        if (!fuenteTurnos.puedeMover(pizarra, color)) {
            return false;
        }

        Ficha ficha = pizarra.getFicha(color, numeroFicha);

        if (ficha == null) {
            return false;
        }

        boolean movimiento = fuenteMovimiento.mover(pizarra, ficha);

        if (movimiento) {

            if (conexionServidor != null) {
                conexionServidor.notificarMovimiento(color, numeroFicha);
            }

            finalizarTurno();
        }

        return movimiento;
    }

    public void recibirCambioTurno(int color) {
        pizarra.sincronizarTurno(color);
        actualizarVista();
    }

    public void recibirDados(int d1, int d2) {
        pizarra.registrarLanzamiento(d1, d2);
        actualizarVista();
    }

    public void recibirMovimiento(int color, int numeroFicha) {
        if (!fuenteTurnos.puedeMover(pizarra, color)) {
            return;
        }

        Ficha ficha = pizarra.getFicha(color, numeroFicha);

        if (ficha != null && fuenteMovimiento.mover(pizarra, ficha)) {
            finalizarTurno();
        }
    }

    public void seleccionarFicha(Ficha ficha) {
        ModeloJugador jugador = obtenerJugadorActual();

        if (ficha == null || jugador == null) {
            return;
        }

        if (ficha.getColor() != jugador.getColor()) {
            return;
        }

        if (!pizarra.isDadoLanzado()) {
            return;
        }

        fichaSeleccionada = ficha;

        moverFicha(
                fichaSeleccionada.getColor(),
                fichaSeleccionada.getNumero()
        );

        fichaSeleccionada = null;
    }

    public void actualizarVista() {
        if (vista == null) {
            return;
        }

        ModeloJugador jugador = obtenerJugadorActual();

        if (jugador != null) {
            vista.actualizarTurno(jugador.getNombre());
            vista.configurarBotonDados(
                    jugador.getColor() == colorJugador
                    && !pizarra.isDadoLanzado()
            );
        }

        vista.actualizarDados(
                getDado1(),
                getDado2()
        );

        vista.actualizarJugadores(pizarra.getJugadores());
        vista.actualizarTablero();
    }

    public void finalizarTurno() {
        fuenteTurnos.finalizarTurno(pizarra);
        actualizarVista();

        if (conexionServidor != null) {
            ModeloJugador jugador = obtenerJugadorActual();

            if (jugador != null) {
                conexionServidor.notificarCambioTurno(jugador.getColor());
            }
        }
    }

    public ModeloJugador obtenerJugadorActual() {
        return fuenteTurnos.obtenerJugadorActual(pizarra);
    }

    public PizarraPartida getPizarra() {
        return pizarra;
    }

    public Tablero getTablero() {
        return pizarra.getTablero();
    }

    public int getDado1() {
        return pizarra.getDado1();
    }

    public int getDado2() {
        return pizarra.getDado2();
    }

    public int getSumaDados() {
        return pizarra.getSumaDados();
    }

    public boolean isPartidaFinalizada() {
        return pizarra.isPartidaFinalizada();
    }

    public void setConexionCliente(ControladorConexionCliente conexionCliente) {
        this.conexionCliente = conexionCliente;
    }

    public void setConexionServidor(ControladorConexionServer conexionServidor) {
        this.conexionServidor = conexionServidor;
    }
}
