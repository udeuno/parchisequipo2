package Modelo;

import Controlador.ControladorChat;
import Controlador.ControladorLobby;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ModeloConexion {

    private ServerSocket servidor;
    private ArrayList<Socket> jugadores = new ArrayList<>();
    private ArrayList<String> datosJugadores = new ArrayList<>();
    private ControladorLobby lobbyAdmin;
    private ControladorChat chatAdmin;

    public void CrearServidor() {

        try {

            servidor = new ServerSocket(5000);

            System.out.println("Servidor iniciado");
            System.out.println("Esperando jugadores...");

            while (jugadores.size() < 3) {

                Socket socket = servidor.accept();

                jugadores.add(socket);

                System.out.println("Jugador conectado");

                Thread hilo = new Thread(() -> {
                    escucharJugador(socket);
                });

                hilo.start();
            }

        } catch (IOException e) {

            System.out.println(
                    "Error al crear servidor: "
                    + e.getMessage()
            );
        }
    }

    public void enviarMensajeAdmin(String mensaje) {

        enviarMensajeChat(mensaje);

        if (chatAdmin != null) {
            chatAdmin.recibirMensaje(mensaje);
        }
    }

    private void procesarMensaje(String mensaje) {
        String texto
                = mensaje.substring(8);

        System.out.println(
                "Reenviando mensaje: " + texto
        );

        enviarMensajeChat(texto);

        if (chatAdmin != null) {

            chatAdmin.recibirMensaje(texto);
        }
    }

    private void enviarMensajeChat(String mensaje) {
        for (Socket socket : jugadores) {

            try {

                PrintWriter salida
                        = new PrintWriter(
                                socket.getOutputStream(),
                                true
                        );

                salida.println(
                        "MENSAJE:" + mensaje
                );

            } catch (IOException e) {

                System.out.println(
                        "No se pudo enviar el mensaje"
                );
            }
        }
    }

    public void setChatAdmin(ControladorChat chatAdmin) {
        this.chatAdmin = chatAdmin;
    }

    public Socket UnirServidor() {

        try {

            System.out.println("Intentando conectar...");

            Socket socket
                    = new Socket("127.0.0.1", 5000);

            System.out.println("Conectado al servidor");

            return socket;

        } catch (IOException e) {

            System.out.println(
                    "No se pudo conectar: "
                    + e.getMessage()
            );

            return null;
        }
    }

    public void setLobbyAdmin(
            ControladorLobby lobbyAdmin) {

        this.lobbyAdmin = lobbyAdmin;
    }

    public void agregarAdmin(String nombre, int color) {

        datosJugadores.add(
                nombre + ":" + color
        );

        System.out.println(
                "Admin agregado: " + nombre + ":" + color
        );

        System.out.println(
                "Jugadores actuales: " + datosJugadores
        );

        enviarJugadores();
        actualizarLobbyAdmin();
    }

    private void escucharJugador(Socket socket) {

        try {

            BufferedReader entrada
                    = new BufferedReader(
                            new InputStreamReader(
                                    socket.getInputStream()
                            )
                    );

            String mensaje;

            while ((mensaje
                    = entrada.readLine()) != null) {

                System.out.println(
                        "Mensaje recibido: " + mensaje
                );

                if (mensaje.startsWith("JUGADOR:")) {

                    datosJugadores.add(
                            mensaje.substring(8)
                    );

                    enviarJugadores();
                    actualizarLobbyAdmin();
                } else if (mensaje.startsWith("MENSAJE:")) {

                    procesarMensaje(mensaje);
                }
            }

        } catch (IOException e) {

            System.out.println(
                    "Jugador desconectado"
            );
        }
    }

    private void enviarJugadores() {

        String mensaje = "JUGADORES:";

        for (int i = 0;
                i < datosJugadores.size();
                i++) {

            mensaje += datosJugadores.get(i);

            if (i < datosJugadores.size() - 1) {
                mensaje += "|";
            }
        }

        for (Socket socket : jugadores) {

            try {

                PrintWriter salida
                        = new PrintWriter(
                                socket.getOutputStream(),
                                true
                        );

                salida.println(mensaje);

            } catch (IOException e) {

                System.out.println(
                        "No se pudo enviar la lista"
                );
            }
        }
    }

    private void actualizarLobbyAdmin() {

        if (lobbyAdmin == null) {
            return;
        }

        ArrayList<ModeloJugador> lista
                = new ArrayList<>();

        for (String dato : datosJugadores) {

            String[] datos
                    = dato.split(":");

            if (datos.length >= 2) {

                String nombre = datos[0];

                int color
                        = Integer.parseInt(datos[1]);

                lista.add(
                        new ModeloJugador(
                                nombre,
                                color
                        )
                );
            }
        }

        lobbyAdmin.actualizarJugadores(lista);
    }
}
