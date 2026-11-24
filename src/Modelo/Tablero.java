package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Tablero {

             private List<Casilla> recorrido = new ArrayList<>();
             private CasillaFinal[] finales = new CasillaFinal[4];
             private Ficha[] fichas = new Ficha[16];

int[][] coordenadas = {
    // Superior
    {0, -415},
    {-120, -415}, {-120, -380}, {-120, -340}, {-120, -300},
    {-120, -260}, {-120, -220}, {-120, -180}, {-120, -140},
    {-160, -100}, {-205, -100}, {-250, -100}, {-295, -100},
    {-340, -100}, {-380, -100}, {-430, -100}, {-475, -100},
    {-475, 0},

    // Izquierdo
    {-475, 100}, {-430, 100}, {-380, 100}, {-340, 100},
    {-295, 100}, {-250, 100}, {-205, 100}, {-160, 100},
    {-120, 140}, {-120, 180}, {-120, 220}, {-120, 260},
    {-120, 300}, {-120, 340}, {-120, 380}, {-120, 415},
    {0, 415},

    // Inferior
    {120, 415},
    {120, 380}, {120, 340}, {120, 300}, {120, 260},
    {120, 220}, {120, 180}, {120, 140},
    {160, 100}, {205, 100}, {250, 100}, {295, 100},
    {340, 100}, {380, 100}, {430, 100}, {475, 100},
    {475, 0},

    // Derecho
    {475, -100}, {430, -100}, {380, -100}, {340, -100},
    {295, -100}, {250, -100}, {205, -100}, {160, -100},
    {120, -140}, {120, -180}, {120, -220}, {120, -260},
    {120, -300}, {120, -340}, {120, -380}, {120, -415}
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
                                                                 case 0:
                                                                              y += 30 + i * 30;
                                                                              break;
                                                                 case 1:
                                                                              x += 30 + i * 30;
                                                                              break;
                                                                 case 2:
                                                                              y -= 30 + i * 30;
                                                                              break;
                                                                 case 3:
                                                                              x -= 30 + i * 30;
                                                                              break;
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
