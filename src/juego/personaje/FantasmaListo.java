package juego.personaje;

import juego.Nivel;
import juego.excepciones.MovimientoInvalidoException;
import juego.excepciones.SalirDelJuegoException;
import multimedia.Lienzo;

public class FantasmaListo extends Fantasma {
    private static final String IMAGEN = "fantasmas/fantasma-listo.png";
    private final Posicion posPacman;
    private final Posicion actual;
    private final int fantasmaId;

    public FantasmaListo(Lienzo lienzo, Nivel nivel, Posicion posPacman, int fantasmaId) {
        super(lienzo, nivel, IMAGEN);
        this.actual = getPosicion();
        this.posPacman = posPacman;
        this.fantasmaId = fantasmaId;
    }


    public void tick() throws SalirDelJuegoException {
        if (!debil) {
            Direccion dir = null;
            double distanciaMasCorta = Double.MAX_VALUE;

            for (Direccion direccion : Direccion.values()) {
                if (direccion != Direccion.Q) {
                    Posicion nuevaPosicion = actual.desplazarse(direccion);

                    if (nivel.esTransitable(nuevaPosicion)) {
                        double distancia = nuevaPosicion.distanciaHastaPacman(posPacman);

                        if (fantasmaId % 2 == 0) {
                            distancia += 15; // Desfasamos la distancia artificialmente para ciertos fantasmas
                        }

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

        } else {
            try {
                Direccion dir = Direccion.values()[random.nextInt(4)];
                mover(dir);
            } catch (MovimientoInvalidoException ignored) {
            }
        }
    }
}
