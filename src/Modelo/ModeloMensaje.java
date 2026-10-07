package Modelo;

public class ModeloMensaje {
    private String mensaje;
    private ModeloJugador emisor;

    public ModeloMensaje(String mensaje, ModeloJugador emisor) {
        this.mensaje = mensaje;
        this.emisor = emisor;
    }

    public String getMensaje() {
        return mensaje;
    }

    public ModeloJugador getEmisor() {
        return emisor;
    }

    public void enviar(ModeloChat chat) {
        chat.agregarMensaje(this);
    }
}
