package juego.personaje;

import juego.Coordinador;
import juego.excepciones.PacmanComidoException;
import multimedia.*;

import java.util.Random;

public abstract class Fantasma extends Actor {
    private final Random random = new Random();

    public Fantasma(Lienzo lienzo, Coordinador coordinador, String imagen) {
        super(imagen, lienzo, coordinador, new Posicion(0, 0));
        posicion = coordinador.obtenerPosicionVaciaAleatoria();
    }
}