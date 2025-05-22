package juego.personaje;

import juego.Nivel;
import juego.excepciones.MovimientoInvalidoException;
import juego.excepciones.SalirDelJuegoException;
import multimedia.Lienzo;

public class FantasmaListo extends Fantasma {
    private static final String IMAGEN = "fantasmas/fantasma-listo.png";
    private final Posicion posPacman;
    private final Posicion actual;

    public FantasmaListo(Lienzo lienzo, Nivel nivel, Pacman pacman) {
        super(lienzo, nivel, IMAGEN);

        actual = getPosicion();
        posPacman = pacman.getPosicion();
    }

    public void tick() throws SalirDelJuegoException {
        Direccion dir;

        if (!posPacman.difiereMasEnHorizontal(actual)) dir = actual.izquieraODerecha(posPacman);
        else                                           dir = actual.arribaOAbajo    (posPacman);

        try {
            mover(dir);
        } catch (MovimientoInvalidoException ignored) {
        }
    }
}
