package juego.personaje;

import juego.Nivel;
import juego.excepciones.MovimientoInvalidoException;
import juego.excepciones.SalirDelJuegoException;
import multimedia.Lienzo;

public class FantasmaListo extends Fantasma {
    private static final String IMAGEN = "fantasmas/fantasma-listo.png";
    private final Posicion posPacman;
    private final Posicion actual;

    public FantasmaListo(Lienzo lienzo, Nivel nivel, Posicion posPacman) {
        super(lienzo, nivel, IMAGEN);

        actual = getPosicion();
        this.posPacman = posPacman;
    }

    public void tick() throws SalirDelJuegoException {
        Direccion dir = null;
        double distanciaMasCorta = Double.MAX_VALUE;

        for (Direccion direccion : Direccion.values()) {
            if (direccion != Direccion.Q) {
                Posicion nuevaPosicion = actual.desplazarse(direccion);

                if (nivel.esTransitable(nuevaPosicion)) {
                    double distancia = nuevaPosicion.distanciaHaciaPacman(posPacman);

                    if (distancia < distanciaMasCorta) {
                        distanciaMasCorta = distancia;
                        dir = direccion;
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
