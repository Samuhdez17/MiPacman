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
    private int momentoComido = 0;
    protected boolean comido = false;

    public Fantasma(Lienzo lienzo, Nivel nivel, String imagen) {
        super(imagen, lienzo, nivel, new Posicion(0,0));

        imagenInicial = imagen;
        generarPosicion();
    }

    public int getMomentoComido() {
        return momentoComido;
    }

    public void setMomentoComido(int momentoComido) {
        this.momentoComido = momentoComido;
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

    public void reUbicar() {
        generarPosicion();
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
        comido = false;
        setMomentoComido(0);
    }

    public boolean estaComido() {
        return comido;
    }
}