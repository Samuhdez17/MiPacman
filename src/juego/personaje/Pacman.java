package juego.personaje;

import juego.*;
import juego.excepciones.MovimientoInvalidoException;
import juego.excepciones.SalirDelJuegoException;
import multimedia.*;
import java.awt.event.KeyEvent;

/** Esta clase representa al jugador en forma de Pac-Man.
 * Tiene su imagen animada y teclado para indicarle su dirección
 */
public class Pacman extends Actor {
    private final Teclado teclado;

    private static final String[] ANIMACION = {"pacman/pacman-abierto.png", "pacman/pacman-cerrado.png"};

    /** Constructor
     * Lo que le diferencia de los fantasmas es que este tiene un teclado el cual sirve de intermediario entre la persona real y el juego.
     *
     * @param lienzo            Lienzo en el que dibujarse, este se pasa a la clase superior.
     * @param nivel             Nivel en el que se encuentra, este se pasa a la clase superior.
     * @param teclado           Teclado con el que se comunica con el jugador.
     * @param posicionInicial   Posición inicial del personaje.
     */
    public Pacman(Lienzo lienzo, Teclado teclado, Nivel nivel, Posicion posicionInicial) {
        super("pacman/pacman-abierto.png", lienzo, nivel, posicionInicial);

        this.teclado = teclado;
    }

    /** Tick
     * Lee la dirección en la que el jugador quiere moverse y este se mueve en consecuencia.
     *
     * @throws SalirDelJuegoException Al ser la clase Actor tickable, este implementa la excepción, ya que el jugador puede decidir salir del juego.
     */
    public void tick() throws SalirDelJuegoException {
        try {
            if      (teclado.pulsada(KeyEvent.VK_UP)    || teclado.pulsada(KeyEvent.VK_W)) mover(Direccion.ARR);
            else if (teclado.pulsada(KeyEvent.VK_LEFT)  || teclado.pulsada(KeyEvent.VK_A)) mover(Direccion.IZD);
            else if (teclado.pulsada(KeyEvent.VK_DOWN)  || teclado.pulsada(KeyEvent.VK_S)) mover(Direccion.ABA);
            else if (teclado.pulsada(KeyEvent.VK_RIGHT) || teclado.pulsada(KeyEvent.VK_D)) mover(Direccion.DCH);
            else if (teclado.pulsada(KeyEvent.VK_Q))                                       mover(Direccion.Q);
        } catch (MovimientoInvalidoException e) {
            // No hacemos nada. Pierde el turno.
        } catch (SalirDelJuegoException e) {
            throw new SalirDelJuegoException();
        }

        setImagen(ANIMACION[(int) (System.currentTimeMillis() / 100) % ANIMACION.length]);
    }
}