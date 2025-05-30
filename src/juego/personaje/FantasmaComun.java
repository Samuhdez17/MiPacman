package juego.personaje;

import juego.Nivel;
import juego.excepciones.MovimientoInvalidoException;
import juego.excepciones.SalirDelJuegoException;
import multimedia.Lienzo;


public class FantasmaComun extends Fantasma {
    private static final String[] IMAGENES = {"fantasmas/fantasma-comun1.png", "fantasmas/fantasma-comun2.png"};

    public FantasmaComun(Lienzo lienzo, Nivel nivel, int numFantasma) {
        super(lienzo, nivel, numFantasma == 1 ? IMAGENES[0] : IMAGENES[1], 3);
    }

    public void tick() throws SalirDelJuegoException {
        try {
            Direccion dir = Direccion.values()[random.nextInt(4)];
            mover(dir);
        } catch (MovimientoInvalidoException ignored) {
        }
    }
}
