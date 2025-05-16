package juego.personaje;

import juego.Coordinador;
import juego.excepciones.MovimientoInvalidoException;
import juego.excepciones.PacmanComidoException;
import multimedia.Lienzo;


public class FantasmaComun extends Fantasma {
    private static final String[] IMAGENES = {"fantasma-comun1.png", "fantasma-comun2.png"};

    public FantasmaComun(Lienzo lienzo, Coordinador coordinador, int numFantasma) {
        super(lienzo, coordinador, numFantasma == 1 ? IMAGENES[0] : IMAGENES[1]);
    }

    public void tick() {
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
