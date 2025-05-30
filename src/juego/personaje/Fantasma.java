package juego.personaje;

import juego.Nivel;
import multimedia.*;

import java.util.Random;

public abstract class Fantasma extends Actor {
    protected final Random random = new Random();

    private static final Posicion[] SPAWNS = {new Posicion(1,1), new Posicion(1,13), new Posicion(13,1)};
    private static final boolean[] SPAWNS_OCUPADOS = new boolean[SPAWNS.length];

    private final String imagenInicial;

    protected boolean debil = false;
    protected boolean comido = false;
    private final int duracionComido;

    public Fantasma(Lienzo lienzo, Nivel nivel, String imagen, int duracionComido) {
        super(imagen, lienzo, nivel, new Posicion(0,0));

        this.duracionComido = duracionComido;
        imagenInicial = imagen;
        generarPosicion();
    }

    public int getDuracionComido() {
        return duracionComido;
    }

    protected void generarPosicion() {
        int posicionRandom = random.nextInt(SPAWNS.length);

        while (SPAWNS_OCUPADOS[posicionRandom]) posicionRandom = random.nextInt(SPAWNS.length);

        posicion.setX(SPAWNS[posicionRandom].getX());
        posicion.setY(SPAWNS[posicionRandom].getY());

        SPAWNS_OCUPADOS[posicionRandom] = true;
    }

    public void liberarPosicion(Posicion posicion) {
        for (int i = 0 ; i < SPAWNS.length ; i++) {
            if (posicion.equals(SPAWNS[i])) {
                SPAWNS_OCUPADOS[i] = false;

                break;
            }
        }
    }

    public void debilitar() {
        debil = true;

        setImagen("fantasmas/fantasma-debil.png");
    }

    public void fortalecer() {
        debil = false;

        setImagen(imagenInicial);
    }

    public void haSidoComido() {
        comido = true;
    }

    public void revivir() {
        fortalecer();
        comido = false;
        generarPosicion();
    }

    public boolean estaComido() {
        return comido;
    }
}