package juego.personaje;

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

    public boolean difiereMasEnHorizontal(Posicion otra) {
        int diferenciaX = Math.abs(this.x - otra.x);
        int diferenciaY = Math.abs(this.y - otra.y);

        return diferenciaX > diferenciaY;
    }


    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Posicion)) return false;
        Posicion otra = (Posicion) obj;

        return this.x == otra.x && this.y == otra.y;
    }

}