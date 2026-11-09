package Modelo;

public class Ficha {
    private int color;
    private int numero;
    private Casilla posicion;
    private int indiceCamino = -1;
    private boolean enCaminoFinal;
    private boolean enCasa = true;
    private int xInicial;
    private int yInicial;

    public Ficha(int color, int numero) {
        this.color = color;
        this.numero = numero;

        switch (color) {
            case 0:
                xInicial = -222 + (numero % 2) * 35;
                yInicial = -222 + (numero / 2) * 35;
                break;
            case 1:
                xInicial = -222 + (numero % 2) * 35;
                yInicial = 187 + (numero / 2) * 35;
                break;
            case 2:
                xInicial = 187 + (numero % 2) * 35;
                yInicial = 187 + (numero / 2) * 35;
                break;
            case 3:
                xInicial = 187 + (numero % 2) * 35;
                yInicial = -222 + (numero / 2) * 35;
                break;
        }
    }

    public int getColor() { return color; }
    public int getNumero() { return numero; }
    public Casilla getPosicion() { return posicion; }
    public void setPosicion(Casilla posicion) { this.posicion = posicion; }
    public int getXInicial() { return xInicial; }
    public int getYInicial() { return yInicial; }
    public int getIndiceCamino() { return indiceCamino; }
    public void setIndiceCamino(int indiceCamino) { this.indiceCamino = indiceCamino; }
    public boolean isEnCaminoFinal() { return enCaminoFinal; }
    public void setEnCaminoFinal(boolean enCaminoFinal) { this.enCaminoFinal = enCaminoFinal; }
    public boolean isEnCasa() { return enCasa; }
    public void setEnCasa(boolean enCasa) { this.enCasa = enCasa; }

    @Override
    public String toString() {
        String[] nombres = {"Rojo", "Verde", "Amarillo", "Azul"};
        return nombres[color] + " - Ficha " + (numero + 1);
    }
}