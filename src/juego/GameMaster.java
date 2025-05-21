package juego;

import multimedia.Lienzo;
import multimedia.Teclado;

public class GameMaster {
    private final Nivel nivel;

    public GameMaster(Lienzo lienzo, Teclado teclado, int nivelActual) {
        nivel = new Nivel(lienzo, teclado, nivelActual);
    }

    public Nivel getNivel() {
        return nivel;
    }
}
