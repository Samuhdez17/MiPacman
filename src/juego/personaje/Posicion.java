package juego.personaje;

import juego.excepciones.SalirDelJuegoException;

/** Esta clase contiene las coordenadas x e y de los personajes en la partida.
 */
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

    /** Este método permite el desplazamiento del personaje
     * Modifica la posición dependiendo de la dirección a la que se le haya indicado.
     *
     * @param dir Dirección en la que se quiere mover.
     * @return Posición resultante del desplazamiento.
     * @throws SalirDelJuegoException En caso de que el jugador quiera salir del juego.
     */
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

    /** Método usado por FantasmaListo.
     * Se calcula la distancia con respecto a pacman mediante la fórmula de Pitágoras.
     *
     * @param posPacman Posición del jugador.
     * @return          Distancia con respecto a pacman.
     * @see FantasmaListo#tick()
     */
    public double distanciaHastaPacman(Posicion posPacman) {
        int dx = posPacman.getX() - this.getX();
        int dy = posPacman.getY() - this.getY();

        return Math.sqrt(dx * dx + dy * dy);
    }

    // Compara las posiciones para ver si son iguales
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Posicion)) return false;
        Posicion otra = (Posicion) obj;

        return this.x == otra.x && this.y == otra.y;
    }

}