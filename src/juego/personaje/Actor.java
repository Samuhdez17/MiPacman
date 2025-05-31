package juego.personaje;

import juego.Nivel;
import juego.excepciones.ErrorCargarImagenException;
import juego.excepciones.MovimientoInvalidoException;
import juego.excepciones.SalirDelJuegoException;
import multimedia.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/** Esta clase hace el movimiento del personaje, se dibuja y permite el cambio de imagen, ya sea para poder representar a los fantasmas débiles o la animación de pacman.
 * La clase Actor contiene:
 * Lienzo en el que dibujarse.
 * Imagen con la que representarse.
 * Posición inicial, esto es para los fantasmas que han sido comidos se generen en una de las esquinas de spawn.
 * Posición con la cual se van a mover.
 * El nivel en el que se encuentra para pedirle partes del mapa.
 */
public abstract class Actor implements Dibujable, Tickable {
    private Lienzo lienzo;
    protected Image imagen;

    private final Posicion posicionInicial;
    protected Posicion posicion;
    protected Nivel nivel;

    /** Aquí se recogen todos los datos del personaje creado.
     *
     * @param nombreFicheroImagen Nombre de la ruta de la imagen a usar.
     * @param lienzo              Lienzo en el que dibujarse.
     * @param nivel               Nivel en el que se encuentra.
     * @param posicionInicial     Posición inicial del personaje.
     */
    public Actor(String nombreFicheroImagen, Lienzo lienzo, Nivel nivel, Posicion posicionInicial) {
        this.posicionInicial = posicionInicial;
        this.posicion = this.posicionInicial;
        this.nivel = nivel;

        setLienzo(lienzo);
        setImagen(nombreFicheroImagen);
    }

    protected void setImagen(String nombreFicheroImagen) {
        try {
            imagen = ImageIO.read(new File("src/assets/" + nombreFicheroImagen));
        } catch (IOException e) {
            throw new ErrorCargarImagenException(e);
        }
    }

    public Posicion getPosicion() {
        return posicion;
    }
    public Posicion getPosicionInicial() {
        return posicionInicial;
    }

    /** Movimiento del personaje.
     * Tiene en cuenta de si es una pared para que el jugador no se mueva y se le advierta de que se ha chocado, y en caso de ser un fantasma, este genera otro movimiento.
     *
     * @param dir                               Dirección en la que se quiere mover.
     * @throws MovimientoInvalidoException      En caso de ser un movimiento inválido, el personaje no se mueve.
     * @throws SalirDelJuegoException           En caso de que el jugador quiera salir del juego.
     */
    public void mover(Direccion dir) throws MovimientoInvalidoException, SalirDelJuegoException {
        int nuevaX = posicion.desplazarse(dir).getX();
        int nuevaY = posicion.desplazarse(dir).getY();

        if (nivel.esPared(nuevaX, nuevaY)) {
            if (this instanceof Pacman) System.out.println("Chocaste con una pared!");
            else this.tick();

        } else if (this instanceof Fantasma && nivel.esFantasma(new Posicion(nuevaX, nuevaY))) {
            try {
                mover(dir);
            } catch (StackOverflowError e) {
                // Dejamos que otros fantasmas se muevan para que dejen hueco libre y asi poder movernos
            }

        } else {
            posicion.setX(nuevaX);
            posicion.setY(nuevaY);
        }
    }

    @Override
    public void setLienzo(Lienzo lienzo) {
        this.lienzo = lienzo;
    }

    @Override
    // Se representa en la ventana que ve el jugador con su imagen y posición correspondiente.
    public void dibujar() {
        lienzo.dibujarImagen(posicion.getX(), posicion.getY(), imagen);
    }
}