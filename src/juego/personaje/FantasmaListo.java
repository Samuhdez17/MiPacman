package juego.personaje;

import juego.Nivel;
import juego.excepciones.MovimientoInvalidoException;
import juego.excepciones.SalirDelJuegoException;
import multimedia.Lienzo;

public class FantasmaListo extends Fantasma {
    private static final String IMAGEN = "fantasmas/fantasma-listo.png";
    private final Posicion posPacman;

    public FantasmaListo(Lienzo lienzo, Nivel nivel, Pacman pacman) {
        super(lienzo, nivel, IMAGEN);

        this.posPacman = pacman.getPosicion();
    }

    public void tick() throws SalirDelJuegoException {
        try {

            Direccion dir = Direccion.values()[random.nextInt(4)];
            mover(dir);
        } catch (MovimientoInvalidoException ignored) {
        }
    }
}
