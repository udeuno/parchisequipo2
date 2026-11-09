package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Tablero {
    private List<Casilla> recorrido = new ArrayList<>();
    private CasillaFinal[] finales = new CasillaFinal[4];
    private Ficha[] fichas = new Ficha[16];

    private int[][] coordenadas = {
        {0, -310},
        {-80, -310}, {-80, -280}, {-80, -251}, {-80, -222},
        {-80, -193}, {-80, -164}, {-80, -134}, {-80, -105},
        {-105, -80}, {-134, -80}, {-164, -80}, {-193, -80},
        {-222, -80}, {-251, -80}, {-280, -80}, {-310, -80},
        {-310, 0},
        {-310, 80}, {-280, 80}, {-251, 80}, {-222, 80},
        {-193, 80}, {-164, 80}, {-134, 80}, {-105, 80},
        {-80, 105}, {-80, 134}, {-80, 164}, {-80, 193},
        {-80, 222}, {-80, 251}, {-80, 280}, {-80, 310},
        {0, 310},
        {80, 310}, {80, 280}, {80, 251}, {80, 222},
        {80, 193}, {80, 164}, {80, 134}, {80, 105},
        {105, 80}, {134, 80}, {164, 80}, {193, 80},
        {222, 80}, {251, 80}, {280, 80}, {310, 80},
        {310, 0},
        {310, -80}, {280, -80}, {251, -80}, {222, -80},
        {193, -80}, {164, -80}, {134, -80}, {105, -80},
        {80, -105}, {80, -134}, {80, -164}, {80, -193},
        {80, -222}, {80, -251}, {80, -280}, {80, -310}
    };

    private int[] indicesFinales = {0, 17, 34, 51};

    public Tablero() {
        crearFinales();
        crearRecorrido();
        conectarCasillas();
        crearFichas();
        organizarCaminosFinales();
    }

    private void crearFinales() {
        for (int i = 0; i < 4; i++) {
            int[] p = coordenadas[indicesFinales[i]];
            finales[i] = new CasillaFinal(p[0], p[1], i);
        }
    }

    private void crearRecorrido() {
        for (int i = 0; i < coordenadas.length; i++) {
            Casilla casilla = null;

            for (int j = 0; j < 4; j++) {
                if (i == indicesFinales[j]) {
                    casilla = finales[j];
                    break;
                }
            }

            if (casilla == null) {
                casilla = new CasillaPaso(coordenadas[i][0], coordenadas[i][1]);
            }

            recorrido.add(casilla);
        }
    }

    private void conectarCasillas() {
        for (int i = 0; i < recorrido.size(); i++) {
            Casilla actual = recorrido.get(i);
            Casilla siguiente = recorrido.get((i + 1) % recorrido.size());

            if (actual instanceof CasillaPaso) {
                ((CasillaPaso) actual).setSiguiente(siguiente);
            }
        }
    }

    private void crearFichas() {
        int indice = 0;

        for (int color = 0; color < 4; color++) {
            for (int numero = 0; numero < 4; numero++) {
                fichas[indice++] = new Ficha(color, numero);
            }
        }
    }

    private void organizarCaminosFinales() {
        for (CasillaFinal f : finales) {
            CasillaPaso[] camino = f.getCamino();

            for (int i = 0; i < camino.length; i++) {
                int x = f.getX1();
                int y = f.getY1();

                switch (f.getColor()) {
                    case 0: y += 30 + i * 30; break;
                    case 1: x += 30 + i * 30; break;
                    case 2: y -= 30 + i * 30; break;
                    case 3: x -= 30 + i * 30; break;
                }

                camino[i].setCoordenadas(x, y);
            }
        }
    }

    public List<Casilla> getRecorrido() {
        return recorrido;
    }

    public CasillaFinal[] getFinales() {
        return finales;
    }

    public Ficha[] getFichas() {
        return fichas;
    }
}