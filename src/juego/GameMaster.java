package juego;

import multimedia.Lienzo;
import multimedia.Teclado;

public class GameMaster {
    private final Nivel nivel;

    public GameMaster(int nivelActual, Lienzo lienzo, Teclado teclado) {
        nivel = new Nivel(lienzo, teclado, nivelActual);
    }

    public Nivel generarNivel() {
        return nivel;
    }
}
