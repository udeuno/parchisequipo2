package Controlador;

import Modelo.ModeloConexion;
import java.net.Socket;
import javax.swing.SwingUtilities;

public class ControladorConexion {

    private ModeloConexion Conexion;
    private ControladorInicio Sala;

    public ControladorConexion(
            boolean admin,
            ControladorInicio Sala) {

        this.Sala = Sala;
        this.Conexion = new ModeloConexion();

        if (admin) {

            Thread hilo = new Thread(() -> {
                Conexion.CrearServidor();
            });

            hilo.start();
        }
    }

    public void conectarJugador() {

        Thread hilo = new Thread(() -> {

            Socket socket =
                    Conexion.UnirServidor();

            if (socket != null) {

                System.out.println(
                        "Conexion establecida correctamente"
                );

                SwingUtilities.invokeLater(() -> {
                    Sala.mostrarRegistroJugador(socket);
                });
            }
        });

        hilo.start();
    }

    public ModeloConexion getConexion() {
        return Conexion;
    }
}