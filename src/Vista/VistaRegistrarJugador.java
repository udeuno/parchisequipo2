package Vista;

import java.awt.Color;
import javax.swing.*;

public class VistaRegistrarJugador extends JPanel {

    private JTextField txtNombre;
    private JComboBox<String> comboColor;
    private JButton btnRegistrar;
    private JLabel lblMensaje;

    public VistaRegistrarJugador() {

        setLayout(null);
        setBackground(new Color(30, 20, 50));

        JLabel lblInstruccion = new JLabel("Ingrese su nombre:");
        lblInstruccion.setForeground(Color.WHITE);
        lblInstruccion.setBounds(100, 30, 200, 30);

        txtNombre = new JTextField();
        txtNombre.setBounds(100, 65, 200, 30);

        JLabel lblColor = new JLabel("Seleccione su color:");
        lblColor.setForeground(Color.WHITE);
        lblColor.setBounds(100, 105, 200, 30);

        comboColor = new JComboBox<>();
        comboColor.addItem("Rojo");
        comboColor.addItem("Verde");
        comboColor.addItem("Amarillo");
        comboColor.addItem("Azul");
        comboColor.setBounds(100, 140, 200, 30);

        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(135, 185, 120, 40);

        lblMensaje = new JLabel("");
        lblMensaje.setBounds(100, 240, 250, 30);

        add(lblInstruccion);
        add(txtNombre);
        add(lblColor);
        add(comboColor);
        add(btnRegistrar);
        add(lblMensaje);
    }

    public void actualizarColores(boolean[] colores) {
        String seleccionado = (String) comboColor.getSelectedItem();

        comboColor.removeAllItems();

        String[] nombres = {"Rojo", "Verde", "Amarillo", "Azul"};

        for (int i = 0; i < nombres.length; i++) {
            if (!colores[i]) {
                comboColor.addItem(nombres[i]);
            }
        }

        if (seleccionado != null) {
            comboColor.setSelectedItem(seleccionado);
        }
    }

    public int obtenerColorSeleccionado() {
        String color = (String) comboColor.getSelectedItem();

        if (color == null) {
            return -1;
        }

        switch (color) {
            case "Rojo":
                return 0;
            case "Verde":
                return 1;
            case "Amarillo":
                return 2;
            case "Azul":
                return 3;
            default:
                return -1;
        }
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JComboBox<String> getComboColor() {
        return comboColor;
    }

    public JButton getBtnRegistrar() {
        return btnRegistrar;
    }

    public void mostrarMensajeDeExito(String msj) {
        lblMensaje.setForeground(Color.GREEN);
        lblMensaje.setText(msj);
    }

    public void mostrarMensajeDeError(String msj) {
        lblMensaje.setForeground(Color.RED);
        lblMensaje.setText(msj);
    }
}
