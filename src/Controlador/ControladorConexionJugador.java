package Controlador;

import Vista.VistaRegistrarJugador;
import Modelo.ModeloJugador;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ControladorConexionJugador {

    private VistaRegistrarJugador vista;
    private Socket socket;
    private PrintWriter salida;
    private ControladorLobby controladorLobby;
    private ControladorChat controladorChat;

    public ControladorConexionJugador(
            VistaRegistrarJugador vista,
            Socket socket) {

        this.vista = vista;
        this.socket = socket;

        try {

            salida = new PrintWriter(
                    socket.getOutputStream(),
                    true
            );

        } catch (Exception e) {

            System.out.println(
                    "No se pudo crear la conexion"
            );

            return;
        }

        iniciarConexion();
    }

    public void setControladorLobby(
            ControladorLobby controladorLobby) {

        this.controladorLobby =
                controladorLobby;
    }

    public void setControladorChat(
            ControladorChat controladorChat) {

        this.controladorChat =
                controladorChat;
    }

    private void iniciarConexion() {

        Thread hilo = new Thread(() -> {

            try {

                BufferedReader entrada =
                        new BufferedReader(
                                new InputStreamReader(
                                        socket.getInputStream()
                                )
                        );

                String mensaje;

                while ((mensaje =
                        entrada.readLine()) != null) {

                    System.out.println(
                            "Mensaje recibido del servidor: "
                            + mensaje
                    );

                    if (mensaje.startsWith("COLORES:")) {

                        actualizarColores(mensaje);

                    } else if (
                            mensaje.startsWith("JUGADORES:")
                    ) {

                        actualizarJugadores(mensaje);

                    } else if (
                            mensaje.startsWith("MENSAJE:")
                    ) {

                        recibirMensaje(
                                mensaje.substring(8)
                        );
                    }
                }

            } catch (Exception e) {

                System.out.println(
                        "Conexion perdida"
                );
            }
        });

        hilo.start();
    }

    public void enviarJugador(
            String nombre,
            int color) {

        if (salida != null) {

            salida.println(
                    "JUGADOR:"
                    + nombre
                    + ":"
                    + color
            );
        }
    }

    public void enviarColor(int color) {

        if (salida != null) {

            salida.println(
                    "COLOR:" + color
            );
        }
    }

    public void enviarMensaje(
            String mensaje) {

        if (salida != null) {

            System.out.println(
                    "Enviando al servidor: "
                    + mensaje
            );

            salida.println(
                    "MENSAJE:" + mensaje
            );

            salida.flush();
        }
    }

    private void recibirMensaje(
            String mensaje) {

        if (controladorChat != null) {

            controladorChat.recibirMensaje(
                    mensaje
            );
        }
    }

    private void actualizarColores(
            String mensaje) {

        String datos =
                mensaje.substring(8);

        String[] valores =
                datos.split(",");

        boolean[] colores =
                new boolean[4];

        for (int i = 0;
                i < valores.length && i < 4;
                i++) {

            colores[i] =
                    valores[i].equals("1");
        }

        vista.actualizarColores(colores);
    }

    private void actualizarJugadores(
            String mensaje) {

        String datos =
                mensaje.substring(10);

        if (datos.isEmpty()) {
            return;
        }

        String[] jugadores =
                datos.split("\\|");

        ArrayList<ModeloJugador> lista =
                new ArrayList<>();

        for (String jugador : jugadores) {

            String[] datosJugador =
                    jugador.split(":");

            if (datosJugador.length >= 2) {

                String nombre =
                        datosJugador[0];

                int color =
                        Integer.parseInt(
                                datosJugador[1]
                        );

                lista.add(
                        new ModeloJugador(
                                nombre,
                                color
                        )
                );
            }
        }

        if (controladorLobby != null) {

            controladorLobby.actualizarJugadores(
                    lista
            );
        }
    }
}