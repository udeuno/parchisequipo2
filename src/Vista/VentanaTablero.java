package Vista;

import Modelo.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class VentanaTablero extends JPanel {

    private Tablero tablero;
    private Ficha fichaSeleccionada;
    private int colorJugador;
    private ArrayList<ModeloJugador> jugadores;
    private Image imagenFondo;

    private Color[] colores = {
        Color.RED,
        Color.GREEN,
        Color.YELLOW,
        Color.BLUE
    };

    public VentanaTablero(Tablero tablero, int colorJugador) {
        this.tablero = tablero;
        this.colorJugador = colorJugador;
        this.jugadores = new ArrayList<>();

        try {
            imagenFondo = new ImageIcon(
                    getClass().getResource("/parchisequipo2/Tablero.jpg")
            ).getImage();
        } catch (Exception e) {
            System.out.println("Error al cargar el tablero: " + e.getMessage());
        }

        setPreferredSize(new Dimension(700, 700));
        setBackground(Color.WHITE);
    }

    public void setJugadores(ArrayList<ModeloJugador> jugadores) {
        this.jugadores = jugadores;
        repaint();
    }

    public void setFichaSeleccionada(Ficha ficha) {
        fichaSeleccionada = ficha;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (imagenFondo != null) {
            g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
        }

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        int centroX = getWidth() / 2;
        int centroY = getHeight() / 2;

        g2.translate(centroX, centroY);
        g2.scale(0.85, 0.85);

        dibujarFichas(g2);

        g2.dispose();
    }

    private void dibujarCasas(Graphics2D g2) {

        g2.setColor(colores[0]);
        g2.fillRoundRect(-300, -300, 180, 180, 25, 25);

        g2.setColor(colores[1]);
        g2.fillRoundRect(-300, 120, 180, 180, 25, 25);

        g2.setColor(colores[2]);
        g2.fillRoundRect(120, 120, 180, 180, 25, 25);

        g2.setColor(colores[3]);
        g2.fillRoundRect(120, -300, 180, 180, 25, 25);

        g2.setColor(Color.WHITE);
        g2.fillRoundRect(-270, -270, 120, 120, 20, 20);
        g2.fillRoundRect(-270, 150, 120, 120, 20, 20);
        g2.fillRoundRect(150, 150, 120, 120, 20, 20);
        g2.fillRoundRect(150, -270, 120, 120, 20, 20);
    }

    private void dibujarRecorrido(Graphics2D g2) {

        for (Casilla casilla : tablero.getRecorrido()) {

            if (casilla instanceof CasillaFinal) {
                continue;
            }

            g2.setColor(Color.LIGHT_GRAY);
            g2.fillRect(
                    casilla.getX1() - 13,
                    casilla.getY1() - 13,
                    26,
                    26
            );

            g2.setColor(Color.BLACK);
            g2.drawRect(
                    casilla.getX1() - 13,
                    casilla.getY1() - 13,
                    26,
                    26
            );
        }
    }

    private void dibujarCaminosFinales(Graphics2D g2) {

        for (CasillaFinal finalColor : tablero.getFinales()) {

            Color color = colores[finalColor.getColor()];

            for (CasillaPaso casilla : finalColor.getCamino()) {

                g2.setColor(color);

                g2.fillRect(
                        casilla.getX1() - 13,
                        casilla.getY1() - 13,
                        26,
                        26
                );

                g2.setColor(Color.BLACK);

                g2.drawRect(
                        casilla.getX1() - 13,
                        casilla.getY1() - 13,
                        26,
                        26
                );
            }
        }
    }

    private void dibujarFichas(Graphics2D g2) {

        for (Ficha ficha : tablero.getFichas()) {

            int x;
            int y;

            if (ficha.isEnCasa()) {
                x = ficha.getXInicial();
                y = ficha.getYInicial();
            } else {
                x = ficha.getPosicion().getX1();
                y = ficha.getPosicion().getY1();
            }

            g2.setColor(colores[ficha.getColor()]);

            g2.fillOval(x - 10, y - 10, 20, 20);

            g2.setColor(Color.BLACK);
            g2.drawOval(x - 10, y - 10, 20, 20);

            if (ficha == fichaSeleccionada) {
                g2.setStroke(new BasicStroke(3));
                g2.drawOval(x - 14, y - 14, 28, 28);
                g2.setStroke(new BasicStroke(1));
            }
        }
    }

    public Ficha obtenerFicha(int x, int y) {

        Point punto = SwingUtilities.convertPoint(
                this,
                x,
                y,
                this
        );

        double centroX = getWidth() / 2.0;
        double centroY = getHeight() / 2.0;

        double tableroX = (punto.x - centroX) / 0.85;
        double tableroY = (punto.y - centroY) / 0.85;

        for (Ficha ficha : tablero.getFichas()) {

            if (ficha.getColor() != colorJugador) {
                continue;
            }

            int fx;
            int fy;

            if (ficha.isEnCasa()) {
                fx = ficha.getXInicial();
                fy = ficha.getYInicial();
            } else {
                fx = ficha.getPosicion().getX1();
                fy = ficha.getPosicion().getY1();
            }

            if (Math.abs(tableroX - fx) <= 15
                    && Math.abs(tableroY - fy) <= 15) {
                return ficha;
            }
        }

        return null;
    }
}
