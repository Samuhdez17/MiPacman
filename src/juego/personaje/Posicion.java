package juego.personaje;

import juego.excepciones.SalirDelJuegoException;

public class Posicion {
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

    public Posicion desplazarse(Direccion dir) throws SalirDelJuegoException {
        int nuevaX = getX();
        int nuevaY = getY();

        switch (dir) {
            case ARR -> nuevaY = getY() - 1;
            case ABA -> nuevaY = getY() + 1;
            case IZD -> nuevaX = getX() - 1;
            case DCH -> nuevaX = getX() + 1;
            case Q -> throw new SalirDelJuegoException();
        }

        return new Posicion(nuevaX, nuevaY);
    }

    public double distanciaHaciaPacman(Posicion posPacman) {
        int dx = posPacman.getX() - this.getX();
        int dy = posPacman.getY() - this.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Posicion)) return false;
        Posicion otra = (Posicion) obj;

        return this.x == otra.x && this.y == otra.y;
    }

}