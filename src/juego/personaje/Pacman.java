package juego.personaje;

import juego.*;
import juego.excepciones.MovimientoInvalidoException;
import juego.excepciones.SalirDelJuegoException;
import multimedia.*;
import java.awt.event.KeyEvent;

public class Pacman extends Actor {
    private Teclado teclado;

    public Pacman(Lienzo lienzo, Teclado teclado, Coordinador coordinador, Posicion posicionInicial) {
        super("pacman-abierto.png", lienzo, coordinador, posicionInicial);

        this.teclado = teclado;
    }

    public void tick() {
        try {
            if (teclado.pulsada(KeyEvent.VK_UP)    || teclado.pulsada(KeyEvent.VK_W)) mover(Direccion.ARR);
            if (teclado.pulsada(KeyEvent.VK_LEFT)  || teclado.pulsada(KeyEvent.VK_A)) mover(Direccion.IZD);
            if (teclado.pulsada(KeyEvent.VK_DOWN)  || teclado.pulsada(KeyEvent.VK_S)) mover(Direccion.ABA);
            if (teclado.pulsada(KeyEvent.VK_RIGHT) || teclado.pulsada(KeyEvent.VK_D)) mover(Direccion.DCH);
            if (teclado.pulsada(KeyEvent.VK_Q)) throw new SalirDelJuegoException("Saliendo del juego...");
        } catch (MovimientoInvalidoException e) {
            // No hacemos nada. Pierde el turno.
        }
    }
}