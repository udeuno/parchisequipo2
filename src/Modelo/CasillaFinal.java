package Modelo;

public class CasillaFinal extends Casilla {
    private int color;
    private CasillaPaso[] camino = new CasillaPaso[8];

    public CasillaFinal(int x, int y, int color) {
        super(x, y, x + 10, y + 10, x + 20, y + 20);
        this.color = color;

        for (int i = 0; i < camino.length; i++) {
            camino[i] = new CasillaPaso(0, 0, color);
        }
    }

    public int getColor() {
        return color;
    }

    public CasillaPaso[] getCamino() {
        return camino;
    }
}