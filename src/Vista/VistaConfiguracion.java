package Vista;

import Controlador.ControladorConfiguracion;
import javax.swing.*;
import java.awt.*;

public class VistaConfiguracion extends JPanel {

    private ControladorConfiguracion controlador;

    public JComboBox<Integer> comboJugadores;

    public JButton btnCrear;

    public JSpinner spPremioMeta;
    public JSpinner spRecompensaCaptura;
    public JCheckBox chkBarreras;

    public VistaConfiguracion() {
        
        
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel(
                "CONFIGURACION DE PARTIDA DE PARCHIS",
                SwingConstants.CENTER
        );
        titulo.setFont(new Font("Arial", Font.BOLD, 24));

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 150, 40, 150));

        JLabel lblJugadores = new JLabel("Cantidad de jugadores:");
        comboJugadores = new JComboBox<>();
        comboJugadores.addItem(2);
        comboJugadores.addItem(3);
        comboJugadores.addItem(4);

        JLabel lblPremio = new JLabel("Premio por llegar a meta:");
        spPremioMeta = new JSpinner(new SpinnerNumberModel(10, 1, 100, 1));

        JLabel lblCaptura = new JLabel("Recompensa por captura:");
        spRecompensaCaptura = new JSpinner(new SpinnerNumberModel(10, 1, 100, 1));

        JLabel lblBarreras = new JLabel("Barreras:");
        chkBarreras = new JCheckBox("Activadas");
        chkBarreras.setSelected(true);

        btnCrear = new JButton("Crear sala");

        panel.add(lblJugadores);
        panel.add(comboJugadores);

        panel.add(lblPremio);
        panel.add(spPremioMeta);

        panel.add(lblCaptura);
        panel.add(spRecompensaCaptura);

        panel.add(lblBarreras);
        panel.add(chkBarreras);

        panel.add(new JLabel());
        panel.add(btnCrear);

        add(titulo, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        btnCrear.addActionListener(e -> {
            controlador.CrearPartida();
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
