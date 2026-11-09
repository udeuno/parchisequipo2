
package Controlador;

import Modelo.PizarraPartida;
import java.util.Random;

public class FuenteDados {

    private Random random = new Random();

    public int[] lanzar(PizarraPartida pizarra) {
        if (pizarra.isDadoLanzado() || pizarra.isPartidaFinalizada()) {
            return null;
        }

        int dado1 = random.nextInt(6) + 1;
        int dado2 = random.nextInt(6) + 1;

        pizarra.registrarLanzamiento(dado1, dado2);

        return new int[]{dado1, dado2};
    }
}
