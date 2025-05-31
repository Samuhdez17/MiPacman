package juego;

import multimedia.Lienzo;
import multimedia.Teclado;

/** Clase generadora de niveles.
 * Esta clase se usa para generar niveles según en el que estemos en el juego.
 */
public class GameMaster {
    private final Nivel nivel;

    /** Constructor
     * Se le indica el lienzo, el teclado y el nivel en el que se encuentra para poder crear el nivel.
     *
     * @param lienzo        Lienzo en el que dibujarse.
     * @param teclado       Teclado con el que se comunica con el jugador.
     * @param nivelActual   Nivel en el que se encuentra.
     */
    public GameMaster(Lienzo lienzo, Teclado teclado, int nivelActual) {
        nivel = new Nivel(lienzo, teclado, nivelActual);
    }

    public Nivel getNivel() {
        return nivel;
    }
}
