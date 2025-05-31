package juego.personaje;

import juego.Nivel;
import juego.excepciones.MovimientoInvalidoException;
import juego.excepciones.SalirDelJuegoException;
import multimedia.Lienzo;

/** Esta clase es un tipo de fantasma el cual se mueve de manera aleatoria, puede ser de dos colores, rojo o azul
 */
public class FantasmaComun extends Fantasma {
    private static final String[] IMAGENES = {"fantasmas/fantasma-comun1.png", "fantasmas/fantasma-comun2.png"};

    /** Primer constructor
     * Este primer constructor es para los niveles 1 y 2, ya que hay 2 o más fantasmas comunes y se les indica cual es para establecer su color
     *
     * @param lienzo        Lienzo en el que dibujarse, este se pasa a la clase superior.
     * @param nivel         Nivel en el que se encuentra, este se pasa a la clase superior.
     * @param numFantasma   Número para establecer la imagen del fantasma.
     */
    public FantasmaComun(Lienzo lienzo, Nivel nivel, int numFantasma) {
        super(lienzo, nivel, numFantasma == 1 ? IMAGENES[0] : IMAGENES[1], 5);
    }

    /** Segundo constructor
     * Este segundo constructor es para el nivel 3, ya que al haber un único fantasma común, se quiere que el color sea de forma aleatoria, sin indicarle que número de fantasma es.
     *
     * @param lienzo Lienzo en el que dibujarse, este se pasa a la clase superior.
     * @param nivel  Nivel en el que se encuentra, este se pasa a la clase superior
     */
    public FantasmaComun(Lienzo lienzo, Nivel nivel) {
        super(lienzo, nivel, "fantasmas/fantasma-comun1.png", 3);

        String imagenRandom = IMAGENES[random.nextInt(2)];
        setImagen(imagenRandom);
        setImagenInicial(imagenRandom);
    }

    /** Tick
     * Por cada tick del juego se genera una dirección aleatoria, sin tener en cuenta la Q, a la que el fantasma se moverá.
     *
     * @throws SalirDelJuegoException Al ser la clase Actor tickable, este implementa la excepción, ya que el jugador puede decidir salir del juego.
     */
    public void tick() throws SalirDelJuegoException {
        try {
            Direccion dir = Direccion.values()[random.nextInt(4)];
            mover(dir);
        } catch (MovimientoInvalidoException ignored) {
        }
    }
}
