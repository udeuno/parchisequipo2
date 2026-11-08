
package Controlador;

import Vista.VistaRegistrarJugador;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.net.Socket;

public class ControladorInicio {

    private JFrame ventana;

    public ControladorInicio(JFrame ventana, JButton botonCrear, JButton botonUnirse) {
        this.ventana = ventana;

        botonCrear.addActionListener(e -> {
            new CrontoladorCrearSala(this);
        });

        botonUnirse.addActionListener(e -> {
            conectarJugador();
        });
    }

    private void conectarJugador() {
        ControladorConexionCliente conexion = new ControladorConexionCliente(this);
        conexion.conectarJugador();
    }

    public void cambiarPanel(JPanel nuevoPanel) {
        ventana.setContentPane(nuevoPanel);
        ventana.revalidate();
        ventana.repaint();
    }

    public void mostrarRegistroJugador(Socket socket) {
        VistaRegistrarJugador vista = new VistaRegistrarJugador();

        ControladorConexionCliente conexionJugador =
                new ControladorConexionCliente(vista, socket);

        new ControladorRegistroJugador(
                vista, this, false, conexionJugador, null
        );

        cambiarPanel(vista);
    }
}
