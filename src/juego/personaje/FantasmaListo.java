package juego.personaje;

import juego.Nivel;
import juego.excepciones.MovimientoInvalidoException;
import juego.excepciones.SalirDelJuegoException;
import multimedia.Lienzo;

public class FantasmaListo extends Fantasma {
    private static final String IMAGEN = "fantasma-listo.png";
    private final Pacman pacman;

    public FantasmaListo(Lienzo lienzo, Nivel nivel, Pacman pacman) {
        super(lienzo, nivel, IMAGEN);

        this.pacman = pacman;
    }

    public void tick() throws SalirDelJuegoException {
        try {
            Posicion posPacman = pacman.getPosicion();

            Direccion dir = Direccion.values()[random.nextInt(4)];
            mover(dir);
        } catch (MovimientoInvalidoException ignored) {
        }
    }
}
