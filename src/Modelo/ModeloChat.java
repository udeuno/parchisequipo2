package Modelo;

import java.util.ArrayList;

public class ModeloChat {
    private ArrayList<ModeloMensaje> mensajes;

    public ModeloChat() {
        mensajes = new ArrayList<>();
    }

    public void agregarMensaje(ModeloMensaje mensaje) {
        mensajes.add(mensaje);
    }

    public ArrayList<ModeloMensaje> obtenerMensajes() {
        return new ArrayList<>(mensajes);
    }
}