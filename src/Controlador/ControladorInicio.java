package Controlador;

import Vista.VistaRegistrarJugador;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import Vista.VistaRegistrarJugador;
import java.net.Socket;

public class ControladorInicio {

    private JFrame ventana;

    public ControladorInicio(JFrame ventana, JButton botonCrear, JButton botonUnirse) {
        this.ventana = ventana;

        botonCrear.addActionListener(e -> {
            CrontoladorCrearSala Crear = new CrontoladorCrearSala(this);
        });

        botonUnirse.addActionListener(e -> {
            ControladorConexion Conexion
                    = new ControladorConexion(false, this);

            Conexion.conectarJugador();
        });
    }

    public void cambiarPanel(JPanel nuevoPanel) {
        ventana.setContentPane(nuevoPanel);
        ventana.revalidate();
        ventana.repaint();
    }

    public void mostrarRegistroJugador(Socket socket) {
        VistaRegistrarJugador vista
                = new VistaRegistrarJugador();

        ControladorConexionJugador conexionJugador
                = new ControladorConexionJugador(
                        vista,
                        socket
                );

        new ControladorRegistroJugador(
                vista,
                this,
                false,
                conexionJugador
        );

        cambiarPanel(vista);
    }
}
