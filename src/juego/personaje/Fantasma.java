package juego.personaje;

import juego.Coordinador;
import multimedia.*;

import java.util.Random;

public abstract class Fantasma extends Actor {
    protected final Random random = new Random();

    public Fantasma(Lienzo lienzo, Coordinador coordinador, String imagen) {
        super(imagen, lienzo, coordinador, new Posicion(0, 0));
        posicion = generarPosicion();
    }

    protected Posicion generarPosicion() {
        int x, y;

        do {
            x = random.nextInt(coordinador.mapaGetLimiteX());
            y = random.nextInt(coordinador.mapaGetLimiteY());
        } while (!coordinador.estaLibre(new Posicion(x, y)));

        return new Posicion(x, y);
    }
}