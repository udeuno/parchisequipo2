package parchisequipo2;

import Controlador.ControladorInicio;
import Vista.VistaInicio;


public class ParchisEquipo2 {


    public static void main(String[] args) {
        VistaInicio ventana = new VistaInicio();
        ControladorInicio controlador = new ControladorInicio(ventana,ventana.btnCrear,ventana.btnUnir);
        ventana.setVisible(true);
    }
    
}
