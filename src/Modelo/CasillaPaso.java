package Modelo;

public class CasillaPaso extends Casilla {
    private Casilla siguiente;
    private int colorCamino = -1;

    public CasillaPaso(int x, int y) {
        super(x, y, x + 10, y + 10, x + 20, y + 20);
    }

    public CasillaPaso(int x, int y, int color) {
        this(x, y);
        colorCamino = color;
    }

    public Casilla getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Casilla siguiente) {
        this.siguiente = siguiente;
    }

    public int getColorCamino() {
        return colorCamino;
    }
}