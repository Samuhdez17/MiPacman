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

public abstract class Actor implements Dibujable, Tickable {
    private Lienzo lienzo;
    protected Image imagen;

    private final Posicion posicionInicial;
    protected Posicion posicion;
    protected Nivel nivel;

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

    public void setLienzo(Lienzo lienzo) {
        this.lienzo = lienzo;
    }

    public void dibujar() {
        lienzo.dibujarImagen(posicion.getX(), posicion.getY(), imagen);
    }
}