package juego.personaje;

import juego.*;
import juego.excepciones.MovimientoInvalidoException;
import juego.excepciones.SalirDelJuegoException;
import multimedia.*;
import java.awt.event.KeyEvent;

public class Pacman extends Actor {
    private Teclado teclado;
    private boolean invencible = false;

    private static final String[] ANIMACION = {"pacman-abierto.png", "pacman-cerrado.png"};

    public Pacman(Lienzo lienzo, Teclado teclado, Nivel nivel, Posicion posicionInicial) {
        super("pacman-abierto.png", lienzo, nivel, posicionInicial);

        this.teclado = teclado;
    }

    public void tick() throws SalirDelJuegoException {
        try {
            if (teclado.pulsada(KeyEvent.VK_UP)    || teclado.pulsada(KeyEvent.VK_W)) mover(Direccion.ARR);
            if (teclado.pulsada(KeyEvent.VK_LEFT)  || teclado.pulsada(KeyEvent.VK_A)) mover(Direccion.IZD);
            if (teclado.pulsada(KeyEvent.VK_DOWN)  || teclado.pulsada(KeyEvent.VK_S)) mover(Direccion.ABA);
            if (teclado.pulsada(KeyEvent.VK_RIGHT) || teclado.pulsada(KeyEvent.VK_D)) mover(Direccion.DCH);
            if (teclado.pulsada(KeyEvent.VK_Q))                                       mover(Direccion.Q);
        } catch (MovimientoInvalidoException e) {
            // No hacemos nada. Pierde el turno.
        }

        setImagen(ANIMACION[(int) (System.currentTimeMillis() / 100) % ANIMACION.length]);
    }
}