package juego.personaje;

import juego.Nivel;
import juego.excepciones.MovimientoInvalidoException;
import juego.excepciones.SalirDelJuegoException;
import multimedia.Lienzo;

/** Esta clase es un tipo de fantasma el cual sabe la posición de pacman y se mueve basándose en su posición para acercarse a él.
 * Aunque parezca listo, este se puede quedar atascado para darle tiempo al jugador para planificar su siguiente movimiento.
 */
public class FantasmaListo extends Fantasma {
    private static final String IMAGEN = "fantasmas/fantasma-listo.png";
    private final Posicion posPacman;
    private final Posicion actual;
    private final int fantasmaId;

    private int ticksEnPartida = 0;

    /** Constructor
     * En este constructor, se le indica un ID al fantasma para que el segundo vaya con más retraso para evitar sobre posicionamiento entre fantasmas.
     *
     * @param lienzo        Lienzo en el que dibujarse, este se pasa a la clase superior.
     * @param nivel         Nivel en el que se encuentra, este se pasa a la clase superior.
     * @param posPacman     Posición del jugador para perseguir.
     * @param fantasmaId    ID del fantasma en cuestión.
     */
    public FantasmaListo(Lienzo lienzo, Nivel nivel, Posicion posPacman, int fantasmaId) {
        super(lienzo, nivel, IMAGEN, 10);

        this.actual = getPosicion();
        this.posPacman = posPacman;
        this.fantasmaId = fantasmaId;
    }

    /** Tick
     * Para hacer al fantasma más lento, se le hace el módulo de 5 para que se mueva 3 veces de 5.
     * Primero se observa si está débil o no, en caso de estarlo, se mueve de manera aleatoria.
     * En caso contrario, compara la distancia que hay hacia pacman en todas las direcciones para saber cuál es la mejor.
     *
     * @throws SalirDelJuegoException Al ser la clase Actor tickable, este implementa la excepción, ya que el jugador puede decidir salir del juego.
     */
    public void tick() throws SalirDelJuegoException {
        if (ticksEnPartida % 5 != 0) { // Hacemos que el movimiento inteligente se haga menos veces para que vaya más lento
            if (!debil) irPorPacman();
        }

        if (debil) {
            try { // Hacemos movimiento aleatorio
                Direccion dir = Direccion.values()[random.nextInt(4)];
                mover(dir);
            } catch (MovimientoInvalidoException ignored) {
            }
        }

        ticksEnPartida++;
    }

    private void irPorPacman() throws SalirDelJuegoException {
        Direccion dir = null;
        double distanciaMasCorta = Double.MAX_VALUE;

        for (Direccion direccion : Direccion.values()) {
            if (direccion != Direccion.Q) {
                Posicion nuevaPosicion = actual.desplazarse(direccion);

                if (nivel.esTransitable(nuevaPosicion)) {
                    double distancia = nuevaPosicion.distanciaHastaPacman(posPacman); // Calculamos a cuanta distancia queda pacman si nos movemos en esa dirección

                    if (fantasmaId % 2 == 0) {
                        distancia += 15; // Desfasamos la distancia para generar separación entre los fantasmas si somos el segundo fantasma listo
                    }

                    if (distancia < distanciaMasCorta) {
                        distanciaMasCorta = distancia;
                        dir = direccion; // Guardamos la mejor dirección
                    }
                }
            }
        }

        if (dir != null) {
            try {
                mover(dir);
            } catch (MovimientoInvalidoException ignored) {
            }
        }
    }
}
