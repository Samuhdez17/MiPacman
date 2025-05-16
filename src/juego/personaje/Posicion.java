package juego.personaje;

import juego.Mapa;

import java.util.Random;

public class Posicion {
    private Random random = new Random();

    private int x;
    private int y;

    public Posicion(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Posicion)) return false;
        Posicion otra = (Posicion) obj;

        return this.x == otra.x && this.y == otra.y;
    }
}