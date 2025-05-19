package juego.personaje;

import juego.Nivel;
import multimedia.*;

import java.util.Random;

public abstract class Fantasma extends Actor {
    protected final Random random = new Random();
    private static final Posicion[] SPAWNS = {new Posicion(1,1), new Posicion(1,13), new Posicion(13,1)};
    private static final boolean[] SPAWNS_OCUPADOS = new boolean[SPAWNS.length];

    public Fantasma(Lienzo lienzo, Nivel nivel, String imagen) {
        super(imagen, lienzo, nivel, new Posicion(0,0));
        posicion = SPAWNS[generarPosicion()];
    }

    protected int generarPosicion() {
        int posicionRandom = random.nextInt(SPAWNS.length);

        if (SPAWNS_OCUPADOS[posicionRandom]) {
            while (SPAWNS_OCUPADOS[posicionRandom]) {
                posicionRandom = random.nextInt(SPAWNS.length);
            }
        }

        SPAWNS_OCUPADOS[posicionRandom] = true;

        return posicionRandom;
    }
}