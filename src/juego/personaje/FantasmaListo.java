package juego.personaje;

import juego.Coordinador;
import juego.excepciones.MovimientoInvalidoException;
import juego.excepciones.PacmanComidoException;
import multimedia.Lienzo;

public class FantasmaListo extends Fantasma {
    private static final String IMAGEN = "fantasma-listo.png";
    private final Pacman pacman;

    public FantasmaListo(Lienzo lienzo, Coordinador coordinador, Pacman pacman) {
        super(lienzo, coordinador, IMAGEN);

        this.pacman = pacman;
    }

    public void tick() throws PacmanComidoException {
        if (this.posicion.equals(pacman.getPosicion())) throw new PacmanComidoException("¡Pacman ha sido comido!");

        boolean movido = false;

        while (!movido) {
            try {
                Direccion dir = Direccion.values()[random.nextInt(4)];
                mover(dir);
                movido = true;
            } catch (MovimientoInvalidoException ignored) {
            }
        }

        if (this.posicion.equals(pacman.getPosicion())) throw new PacmanComidoException("¡Pacman ha sido comido!"); //TODO para coordinador
    }
}
