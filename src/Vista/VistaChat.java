package Vista;

import javax.swing.*;
import java.awt.*;
import Controlador.*;


public class VistaChat extends JPanel {
    private JTextArea listaMensajes;
    private JTextField txtMensaje;
    private JButton btnEnviar;
    private ControladorChat controlador;

    public VistaChat() {
        setLayout(new BorderLayout());

        listaMensajes = new JTextArea();
        listaMensajes.setEditable(false);

        txtMensaje = new JTextField();
        btnEnviar = new JButton("Enviar");

        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.add(txtMensaje, BorderLayout.CENTER);
        panelInferior.add(btnEnviar, BorderLayout.EAST);

        add(new JScrollPane(listaMensajes), BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);

        btnEnviar.addActionListener(e -> {
            if (controlador != null) {
                controlador.enviarMensaje();
            }
        });
    }

    public void setControlador(ControladorChat controlador) {
        this.controlador = controlador;
    }

    public String obtenerTexto() {
        return txtMensaje.getText();
    }

    public void limpiarMensaje() {
        txtMensaje.setText("");
    }

    public void limpiarConversacion() {
        listaMensajes.setText("");
    }

    public void mostrarMensaje(String mensaje) {
        listaMensajes.append(mensaje + "\n");
    }
}
