
package Vista;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class VistaRegistrarJugador extends JPanel {

    private JTextField txtNombre;
    private JComboBox<String> comboColor;
    private JButton btnRegistrar;
    private JLabel lblMensaje;

    private Color fondo = new Color(24, 32, 52);
    private Color panelColor = new Color(36, 47, 68);
    private Color azul = new Color(52, 152, 219);

    public VistaRegistrarJugador() {

        setLayout(new GridBagLayout());
        setBackground(fondo);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(panelColor);
        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(60, 75, 100), 1
                        ),
                        BorderFactory.createEmptyBorder(35, 45, 35, 45)
                )
        );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 0, 8, 0);

        // Titulo
        JLabel titulo = new JLabel(
                "REGISTRO DE JUGADOR",
                SwingConstants.CENTER
        );

        titulo.setFont(new Font("Arial", Font.BOLD, 25));
        titulo.setForeground(Color.WHITE);

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 30, 0);
        panel.add(titulo, gbc);

        // Nombre
        gbc.gridwidth = 1;
        gbc.insets = new Insets(8, 0, 8, 10);

        JLabel lblInstruccion = crearEtiqueta("Nombre del jugador:");

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lblInstruccion, gbc);

        txtNombre = new JTextField(18);
        configurarCampo(txtNombre);

        gbc.gridx = 1;
        gbc.insets = new Insets(8, 10, 8, 0);
        panel.add(txtNombre, gbc);

        // Color
        JLabel lblColor = crearEtiqueta("Selecciona tu color:");

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(8, 0, 8, 10);
        panel.add(lblColor, gbc);

        comboColor = new JComboBox<>();
        comboColor.addItem("Rojo");
        comboColor.addItem("Verde");
        comboColor.addItem("Amarillo");
        comboColor.addItem("Azul");

        comboColor.setFont(new Font("Arial", Font.PLAIN, 15));
        comboColor.setPreferredSize(new Dimension(180, 35));

        gbc.gridx = 1;
        gbc.insets = new Insets(8, 10, 8, 0);
        panel.add(comboColor, gbc);

        // Boton
        btnRegistrar = new JButton("REGISTRAR");
        configurarBoton(btnRegistrar);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(25, 0, 10, 0);
        panel.add(btnRegistrar, gbc);

        // Mensaje
        lblMensaje = new JLabel(" ", SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Arial", Font.BOLD, 13));
        lblMensaje.setForeground(Color.WHITE);

        gbc.gridy = 4;
        gbc.insets = new Insets(10, 0, 0, 0);
        panel.add(lblMensaje, gbc);

        add(panel);
    }

    private JLabel crearEtiqueta(String texto) {

        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(new Font("Arial", Font.BOLD, 14));
        etiqueta.setForeground(Color.WHITE);

        return etiqueta;
    }

    private void configurarCampo(JTextField campo) {

        campo.setFont(new Font("Arial", Font.PLAIN, 15));
        campo.setPreferredSize(new Dimension(180, 35));
    }

    private void configurarBoton(JButton boton) {

        boton.setFont(new Font("Arial", Font.BOLD, 15));
        boton.setForeground(Color.WHITE);
        boton.setBackground(azul);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setOpaque(true);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(200, 45));

        boton.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(azul.brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(azul);
            }
        });
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
            case "Rojo": return 0;
            case "Verde": return 1;
            case "Amarillo": return 2;
            case "Azul": return 3;
            default: return -1;
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
        lblMensaje.setForeground(new Color(46, 204, 113));
        lblMensaje.setText(msj);
    }

    public void mostrarMensajeDeError(String msj) {
        lblMensaje.setForeground(new Color(231, 76, 60));
        lblMensaje.setText(msj);
    }
}
