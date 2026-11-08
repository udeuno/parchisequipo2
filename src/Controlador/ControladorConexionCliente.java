package Controlador;

import Vista.VistaRegistrarJugador;
import Modelo.ModeloJugador;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.SwingUtilities;

public class ControladorConexionCliente {

    private VistaRegistrarJugador vista;
    private ControladorInicio Sala;
    private Socket socket;
    private PrintWriter salida;
    private ControladorLobby controladorLobby;
    private ControladorChat controladorChat;

    public ControladorConexionCliente(ControladorInicio Sala) {
        this.Sala = Sala;
    }

    public void conectarJugador() {
        new Thread(() -> {
            try {
                socket = new Socket("127.0.0.1", 5000);
                System.out.println("Conectado al servidor");

                SwingUtilities.invokeLater(() -> {
                    Sala.mostrarRegistroJugador(socket);
                });

            } catch (Exception e) {
                System.out.println("No se pudo conectar: " + e.getMessage());
            }
        }).start();
    }

    public ControladorConexionCliente(VistaRegistrarJugador vista, Socket socket) {
        this.vista = vista;
        this.socket = socket;

        try {
            salida = new PrintWriter(socket.getOutputStream(), true);
            iniciarConexion();
        } catch (Exception e) {
            System.out.println("No se pudo crear la conexion");
        }
    }

    public void setControladorLobby(ControladorLobby controladorLobby) {
        this.controladorLobby = controladorLobby;
    }

    public void setControladorChat(ControladorChat controladorChat) {
        this.controladorChat = controladorChat;
    }

    public void enviarJugador(String nombre, int color) {
        if (salida != null) {
            salida.println("JUGADOR:" + nombre + ":" + color);
        }
    }

    public void enviarColor(int color) {
        if (salida != null) {
            salida.println("COLOR:" + color);
        }
    }

    public void enviarMensaje(String mensaje) {
        if (salida != null) {
            salida.println("MENSAJE:" + mensaje);
        }
    }

    private void iniciarConexion() {
        new Thread(() -> {
            try {
                BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String mensaje;

                while ((mensaje = entrada.readLine()) != null) {
                    if (mensaje.startsWith("COLORES:")) {
                        actualizarColores(mensaje);

                    } else if (mensaje.startsWith("JUGADORES:")) {
                        actualizarJugadores(mensaje);

                    } else if (mensaje.startsWith("MENSAJE:")) {
                        recibirMensaje(mensaje.substring(8));
                    } else if (mensaje.equals("EXPULSADO")) {
                        SwingUtilities.invokeLater(() -> {
                            if (controladorLobby != null) {
                                controladorLobby.expulsadoDelLobby();
                            }
                            try {
                                socket.close();
                            } catch (Exception e) {
                                System.out.println("Error al cerrar conexion");
                            }
                        });
                        break;
                    } else if (mensaje.equals("COLOR_OCUPADO")) {
                        SwingUtilities.invokeLater(() -> {
                            vista.mostrarMensajeDeError("Ese color ya fue seleccionado por otro jugador");
                        });
                    }
                }

            } catch (Exception e) {
                System.out.println("Conexion perdida");
            }
        }).start();
    }

    private void recibirMensaje(String mensaje) {
        if (controladorChat != null) {
            controladorChat.recibirMensaje(mensaje);
        }
    }

    private void actualizarColores(String mensaje) {
        String[] valores = mensaje.substring(8).split(",");
        boolean[] colores = new boolean[4];

        for (int i = 0; i < valores.length && i < 4; i++) {
            colores[i] = valores[i].equals("1");
        }

        SwingUtilities.invokeLater(() -> vista.actualizarColores(colores));
    }

    private void actualizarJugadores(String mensaje) {
        String datos = mensaje.substring(10);

        if (datos.isEmpty()) {
            return;
        }

        ArrayList<ModeloJugador> lista = new ArrayList<>();

        for (String jugador : datos.split("\\|")) {
            String[] datosJugador = jugador.split(":");

            if (datosJugador.length >= 2) {
                lista.add(new ModeloJugador(datosJugador[0], Integer.parseInt(datosJugador[1])));
            }
        }

        if (controladorLobby != null) {
            SwingUtilities.invokeLater(() -> controladorLobby.actualizarJugadores(lista));
        }
    }
}
