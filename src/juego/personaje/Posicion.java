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

    protected boolean difiereMasEnHorizontal(Posicion otra) {
        int diferenciaX = Math.abs(this.getX() - otra.getX());
        int diferenciaY = Math.abs(this.getY() - otra.getY());

        return diferenciaX < diferenciaY;
    }

    protected Direccion arribaOAbajo(Posicion otra) {
        if (this.getY() > otra.getY()) return Direccion.ARR;
        else                           return Direccion.ABA;
    }

    protected Direccion izquieraODerecha(Posicion otra) {
        if (this.getX() < otra.getX()) return Direccion.DCH;
        else                           return Direccion.IZD;
    }


    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Posicion)) return false;
        Posicion otra = (Posicion) obj;

        return this.x == otra.x && this.y == otra.y;
    }

}