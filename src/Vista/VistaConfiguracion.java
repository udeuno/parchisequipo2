
package Vista;

import Controlador.ControladorConfiguracion;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VistaConfiguracion extends JPanel {

    private ControladorConfiguracion controlador;

    public JComboBox<Integer> comboJugadores;
    public JButton btnCrear;

    public JSpinner spPremioMeta;
    public JSpinner spRecompensaCaptura;
    public JCheckBox chkBarreras;
    public JCheckBox chkTiroExtra;

    private Color fondo = new Color(24, 32, 52);
    private Color panelColor = new Color(36, 47, 68);
    private Color azul = new Color(52, 152, 219);

    public VistaConfiguracion() {

        setLayout(new BorderLayout());
        setBackground(fondo);

        // Titulo
        JLabel titulo = new JLabel(
                "CONFIGURACIÓN DE PARTIDA",
                SwingConstants.CENTER
        );

        titulo.setFont(new Font("Arial", Font.BOLD, 30));
        titulo.setForeground(Color.WHITE);
        titulo.setBorder(
                BorderFactory.createEmptyBorder(45, 0, 30, 0)
        );

        // Panel de configuracion
        JPanel panel = new JPanel(new GridLayout(5, 2, 20, 25));
        panel.setBackground(panelColor);
        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(60, 75, 100), 1
                        ),
                        BorderFactory.createEmptyBorder(35, 45, 35, 45)
                )
        );

        // Jugadores
        JLabel lblJugadores = crearEtiqueta("Cantidad de jugadores:");

        comboJugadores = new JComboBox<>();
        comboJugadores.addItem(2);
        comboJugadores.addItem(3);
        comboJugadores.addItem(4);
        configurarCombo(comboJugadores);

        // Premio
        JLabel lblPremio = crearEtiqueta("Premio por llegar a meta:");

        spPremioMeta = new JSpinner(
                new SpinnerNumberModel(10, 1, 100, 1)
        );
        configurarSpinner(spPremioMeta);

        // Captura
        JLabel lblCaptura = crearEtiqueta("Recompensa por captura:");

        spRecompensaCaptura = new JSpinner(
                new SpinnerNumberModel(10, 1, 100, 1)
        );
        configurarSpinner(spRecompensaCaptura);

        // Barreras
        JLabel lblBarreras = crearEtiqueta("Barreras:");

        chkBarreras = crearCheckBox("Activadas");
        chkBarreras.setSelected(true);

        // Tiro extra
        JLabel lblTiro = crearEtiqueta("Tiro extra:");

        chkTiroExtra = crearCheckBox("Al sacar pares");
        chkTiroExtra.setSelected(true);

        // Boton
        btnCrear = new JButton("CREAR SALA");
        configurarBoton(btnCrear);

        // Agregar componentes
        panel.add(lblJugadores);
        panel.add(comboJugadores);

        panel.add(lblPremio);
        panel.add(spPremioMeta);

        panel.add(lblCaptura);
        panel.add(spRecompensaCaptura);

        panel.add(lblBarreras);
        panel.add(chkBarreras);

        panel.add(lblTiro);
        panel.add(chkTiroExtra);

        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setBackground(fondo);
        contenedor.setBorder(
                BorderFactory.createEmptyBorder(10, 150, 15, 150)
        );

        contenedor.add(panel, BorderLayout.CENTER);

        JPanel panelBoton = new JPanel();
        panelBoton.setBackground(fondo);
        panelBoton.setBorder(
                BorderFactory.createEmptyBorder(10, 0, 35, 0)
        );

        panelBoton.add(btnCrear);

        add(titulo, BorderLayout.NORTH);
        add(contenedor, BorderLayout.CENTER);
        add(panelBoton, BorderLayout.SOUTH);

        btnCrear.addActionListener(e -> {
            if (controlador != null) {
                controlador.CrearPartida();
            }
        });
    }

    private JLabel crearEtiqueta(String texto) {

        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(new Font("Arial", Font.BOLD, 15));
        etiqueta.setForeground(Color.WHITE);

        return etiqueta;
    }

    private JCheckBox crearCheckBox(String texto) {

        JCheckBox check = new JCheckBox(texto);
        check.setFont(new Font("Arial", Font.PLAIN, 14));
        check.setForeground(Color.WHITE);
        check.setBackground(panelColor);
        check.setFocusPainted(false);

        return check;
    }

    private void configurarCombo(JComboBox<Integer> combo) {

        combo.setFont(new Font("Arial", Font.PLAIN, 15));
        combo.setBackground(Color.WHITE);
        combo.setFocusable(false);
    }

    private void configurarSpinner(JSpinner spinner) {

        spinner.setFont(new Font("Arial", Font.PLAIN, 15));
        spinner.setPreferredSize(new Dimension(100, 32));

        JComponent editor = spinner.getEditor();

        if (editor instanceof JSpinner.DefaultEditor) {
            ((JSpinner.DefaultEditor) editor)
                    .getTextField()
                    .setHorizontalAlignment(JTextField.CENTER);
        }
    }

    private void configurarBoton(JButton boton) {

        boton.setFont(new Font("Arial", Font.BOLD, 16));
        boton.setForeground(Color.WHITE);
        boton.setBackground(azul);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setOpaque(true);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        boton.setPreferredSize(new Dimension(220, 50));

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

    public void setControlador(ControladorConfiguracion controlador) {
        this.controlador = controlador;
    }

    public void mostrarResumen(String datos) {

        JOptionPane.showMessageDialog(
                this,
                datos,
                "Resumen de partida",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
