package Modelo;


public class ModeloJugador {
    private String nombre;
    private int color;

    public ModeloJugador(String nombre, int color) {
        this.nombre = nombre;
        this.color = color;
    }

    public String getNombre() {
        return nombre;
    }

    public int getColor() {
        return color;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setColor(int color) {
        this.color = color;
    }
}

