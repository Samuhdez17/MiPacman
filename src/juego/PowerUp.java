package juego;

import java.util.Random;

import juego.excepciones.ErrorCargarImagenException;
import juego.personaje.Posicion;
import multimedia.Dibujable;
import multimedia.Lienzo;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/** Clase PowerUp.
 * Esta clase es un power up consumible para pacman, le otorga invencibilidad frente a los fantasmas, pudiendo hacer que se los coma.
 */
public class PowerUp implements Dibujable {
    private Lienzo lienzo;
    private int duracionMax;

    private Image imagen;
    private Posicion posicion;

    /** Constructor
     * Se le establece:
     *  - Lienzo donde representarse.
     *  - Imagen representativa dependiendo del nivel.
     *  - Duración máxima del power up dependiendo del nivel.
     *  - Posición aleatoria.
     *
     * @param lienzo Lienzo en el que dibujarse.
     * @param nivel  Nivel en el que se encuentra.
     * @see #setDuracionMax(int) Para ver como se establece la duración máxima del power up.
     * @see #generarPosicion(Nivel) Para ver como se genera la posición del power up.
     */
    public PowerUp(Lienzo lienzo, Nivel nivel) {
        setLienzo(lienzo);

        if (nivel.getNivelActual() == 2) setImagen("estrella.png");
        else                             setImagen("pwr-up.png");

        setDuracionMax(nivel.getNivelActual());
        generarPosicion(nivel);
    }

    /** Método para establecer la duración máxima del power up.
     * Dependiendo del nivel en el que nos encontremos, la duración es mayor o menor.
     *
     * @param nivelActual Nivel en el que estamos.
     */
    private void setDuracionMax(int nivelActual) {
        if      (nivelActual == 1) duracionMax = 7;
        else if (nivelActual == 2) duracionMax = 10;
        else                       duracionMax = 15;
    }

    private void setImagen(String nombreImagen) {
        try {
            imagen = ImageIO.read(new File("src/assets/"  + nombreImagen));

        } catch (IOException e) {
            throw new ErrorCargarImagenException(e);
        }
    }
    public Posicion getPosicion() {
        return posicion;
    }

    public int getDuracionMax() {
        return duracionMax;
    }

    /** Método para generar la posición del power up.
     * Se genera una posición random teniendo en cuenta los bordes del mapa y que sea transitable.
     *
     * @param nivel Nivel en el que se encuentra.
     */
    private void generarPosicion(Nivel nivel) {
        Random random = new Random();

        posicion = new Posicion(0,0);

        while (!nivel.estaLibre(posicion)) {
            posicion.setX(random.nextInt(nivel.getLimiteX()));
            posicion.setY(random.nextInt(nivel.getLimiteY()));
        }
    }

    @Override
    public void setLienzo(Lienzo lienzo) {
        this.lienzo = lienzo;
    }

    @Override
    public void dibujar() {
        lienzo.dibujarImagen(posicion.getX(), posicion.getY(), imagen);
    }
}
