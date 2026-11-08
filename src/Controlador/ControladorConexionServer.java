package Controlador;

import Modelo.ModeloJugador;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ControladorConexionServer {

    private ServerSocket servidor;
    private ArrayList<Socket> jugadores = new ArrayList<>();
    private ArrayList<String> datosJugadores = new ArrayList<>();
    private Map<Socket, String> conexionesJugadores = new HashMap<>();
    private boolean[] coloresOcupados = new boolean[4];
    private ControladorLobby lobbyAdmin;
    private ControladorChat chatAdmin;
    private int capacidad;

    public ControladorConexionServer(int capacidad) {
        this.capacidad = capacidad;
    }

    public void iniciarServidor() {
        new Thread(() -> {
            try {
                servidor = new ServerSocket(5000);
                System.out.println("Servidor iniciado");
                System.out.println("Esperando jugadores...");

                while (true) {
                    Socket socket = servidor.accept();

                    if (jugadores.size() >= capacidad - 1) {
                        socket.close();
                        continue;
                    }

                    jugadores.add(socket);
                    enviarColores();
                    System.out.println("Jugador conectado");

                    new Thread(() -> escucharJugador(socket)).start();
                }

            } catch (IOException e) {
                System.out.println("Error al crear servidor: " + e.getMessage());
            }
        }).start();
    }

    public synchronized void agregarAdmin(String nombre, int color) {
        if (color < 0 || color >= 4 || coloresOcupados[color]) {
            return;
        }

        coloresOcupados[color] = true;
        datosJugadores.add(nombre + ":" + color);

        enviarJugadores();
        enviarColores();
        actualizarLobbyAdmin();
    }

    public void enviarMensajeAdmin(String mensaje) {
        enviarMensajeChat(mensaje);

        if (chatAdmin != null) {
            chatAdmin.recibirMensaje(mensaje);
        }
    }

    private void enviarColores() {
        String mensaje = "COLORES:";

        for (int i = 0; i < coloresOcupados.length; i++) {
            mensaje += coloresOcupados[i] ? "1" : "0";

            if (i < coloresOcupados.length - 1) {
                mensaje += ",";
            }
        }

        for (Socket socket : jugadores) {
            try {
                PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
                salida.println(mensaje);
            } catch (IOException e) {
                System.out.println("No se pudieron actualizar los colores");
            }
        }
    }

    public void setChatAdmin(ControladorChat chatAdmin) {
        this.chatAdmin = chatAdmin;
    }

    public void setLobbyAdmin(ControladorLobby lobbyAdmin) {
        this.lobbyAdmin = lobbyAdmin;
    }

    private void escucharJugador(Socket socket) {
        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String mensaje;

            while ((mensaje = entrada.readLine()) != null) {
                System.out.println("Mensaje recibido: " + mensaje);

                if (mensaje.startsWith("JUGADOR:")) {
                    String datos = mensaje.substring(8);
                    String[] partes = datos.split(":");

                    if (partes.length < 2) {
                        continue;
                    }

                    int color = Integer.parseInt(partes[1]);

                    synchronized (this) {
                        if (color < 0 || color >= 4 || coloresOcupados[color]) {
                            try {
                                PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
                                salida.println("COLOR_OCUPADO");
                                socket.close();
                            } catch (IOException e) {
                                System.out.println("Error al rechazar color");
                            }
                            continue;
                        }

                        coloresOcupados[color] = true;
                        datosJugadores.add(datos);
                        conexionesJugadores.put(socket, datos);

                        enviarJugadores();
                        enviarColores();
                        actualizarLobbyAdmin();
                    }
                } else if (mensaje.startsWith("MENSAJE:")) {
                    String texto = mensaje.substring(8);
                    enviarMensajeChat(texto);

                    if (chatAdmin != null) {
                        chatAdmin.recibirMensaje(texto);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Jugador desconectado");
        }
    }

    private void enviarMensajeChat(String mensaje) {
        for (Socket socket : jugadores) {
            try {
                PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
                salida.println("MENSAJE:" + mensaje);
            } catch (IOException e) {
                System.out.println("No se pudo enviar el mensaje");
            }
        }
    }

    private void enviarJugadores() {
        String mensaje = "JUGADORES:";

        for (int i = 0; i < datosJugadores.size(); i++) {
            mensaje += datosJugadores.get(i);

            if (i < datosJugadores.size() - 1) {
                mensaje += "|";
            }
        }

        for (Socket socket : jugadores) {
            try {
                PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
                salida.println(mensaje);
            } catch (IOException e) {
                System.out.println("No se pudo enviar la lista");
            }
        }
    }

    private void actualizarLobbyAdmin() {
        if (lobbyAdmin == null) {
            return;
        }

        ArrayList<ModeloJugador> lista = new ArrayList<>();

        for (String dato : datosJugadores) {
            String[] datos = dato.split(":");

            if (datos.length >= 2) {
                lista.add(new ModeloJugador(datos[0], Integer.parseInt(datos[1])));
            }
        }

        lobbyAdmin.actualizarJugadores(lista);
    }

    public void expulsarJugador(ModeloJugador jugador) {
        String datos = jugador.getNombre() + ":" + jugador.getColor();
        int color = jugador.getColor();
        coloresOcupados[color] = false;

        for (Map.Entry<Socket, String> entrada : conexionesJugadores.entrySet()) {
            if (entrada.getValue().equals(datos)) {
                Socket socket = entrada.getKey();

                try {
                    PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
                    salida.println("EXPULSADO");

                    socket.close();

                    conexionesJugadores.remove(socket);
                    jugadores.remove(socket);
                    datosJugadores.remove(datos);

                    enviarJugadores();
                    enviarColores();
                    actualizarLobbyAdmin();

                    return;
                } catch (IOException e) {
                    System.out.println("Error al expulsar jugador");
                    return;
                }
            }
        }
    }

    public int getCapacidad() {
        return capacidad;
    }
}
