package juego.personaje;

import juego.Nivel;
import multimedia.*;

import java.util.Random;

/** Clase padre de FantasmaListo y FantasmaComún.
 * Esta gestiona los spawns; la imagen inicial para el cambio de débil a normal y la generación del mismo.
 */
public abstract class Fantasma extends Actor {
    protected final Random random = new Random();

    private static final Posicion[] SPAWNS = {new Posicion(1,1), new Posicion(1,13), new Posicion(13,1)};
    private static final boolean[] SPAWNS_OCUPADOS = new boolean[SPAWNS.length];

    private String imagenInicial;

    protected boolean debil = false;
    private final int duracionComido;

    /** Constructor
     * Aquí se recogen todos los datos del fantasma creado.
     *
     * @param lienzo            Lienzo en el que dibujarse, este se pasa a la clase superior.
     * @param nivel             Nivel en el que se encuentra, este se pasa a la clase superior.
     * @param imagen            Nombre de la ruta de la imagen a usar, este se pasa a la clase superior
     * @param duracionComido    Duración del fantasma cuando está comido, cada uno tiene el suyo propio.
     */
    public Fantasma(Lienzo lienzo, Nivel nivel, String imagen, int duracionComido) {
        super(imagen, lienzo, nivel, new Posicion(0,0));

        this.duracionComido = duracionComido;
        imagenInicial = imagen;
        generarPosicion();
    }

    public int getDuracionComido() {
        return duracionComido;
    }

    public void setImagenInicial(String imagen) {
        imagenInicial = imagen;
    }

    // Indica al fantasma cuál va a ser su spawn, dependiendo de si está libre o no.
    protected void generarPosicion() {
        int posicionRandom = random.nextInt(SPAWNS.length);

        while (SPAWNS_OCUPADOS[posicionRandom]) posicionRandom = random.nextInt(SPAWNS.length);

        posicion.setX(SPAWNS[posicionRandom].getX());
        posicion.setY(SPAWNS[posicionRandom].getY());

        SPAWNS_OCUPADOS[posicionRandom] = true;
    }

    /** Este método lo llama la clase Nivel, para cada nivel, cuando se pasa de nivel se liberan las posiciones para los siguientes fantasmas.
     * Y se llama en el método revivir para liberar la posición usada para re aparecer
     *
     * @param posicion Posición a liberar.
     * @see Nivel#fantasmaLiberarPosiciones()
     * @see Fantasma#revivir()
     */
    public void liberarPosicion(Posicion posicion) {
        for (int i = 0 ; i < SPAWNS.length ; i++) {
            if (posicion.equals(SPAWNS[i])) {
                SPAWNS_OCUPADOS[i] = false;

                break;
            }
        }
    }

    /** Se establece que el fantasma está débil, para el cambio de imagen y el movimiento del FantasmaListo.
     */
    public void debilitar() {
        debil = true;

        setImagen("fantasmas/fantasma-debil.png");
    }

    /** Una vez deja de estar débil, este recupera su estado original.
     */
    public void fortalecer() {
        debil = false;

        setImagen(imagenInicial);
    }

    /** Si el fantasma fue comido, tras un tiempo se le "revive", cambiando su imagen y haciéndole aparecer de nuevo en un spawn y liberándola.
     */
    public void revivir() {
        fortalecer();
        generarPosicion();
        liberarPosicion(getPosicionInicial());
    }
}