package juego.personaje;

import juego.Coordinador;
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

    protected Posicion posicion;
    protected Coordinador coordinador;

    public Actor(String nombreFicheroImagen, Lienzo lienzo, Coordinador coordinador, Posicion posicionInicial) {
        this.posicion = posicionInicial;
        this.coordinador = coordinador;

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

    public void mover(Direccion dir) throws MovimientoInvalidoException, SalirDelJuegoException {
        int nuevaX = posicion.getX();;
        int nuevaY = posicion.getY();

        switch (dir) {
            case ARR -> nuevaY = posicion.getY() - 1;
            case ABA -> nuevaY = posicion.getY() + 1;
            case IZD -> nuevaX = posicion.getX() - 1;
            case DCH -> nuevaX = posicion.getX() + 1;
            case Q -> throw new SalirDelJuegoException();
        }

        if (coordinador.esPared(nuevaX, nuevaY)) {
            if (this instanceof Pacman) System.out.println("Chocaste con una pared!");
            else this.tick();

        } else if (this instanceof Fantasma && coordinador.esFantasma(new Posicion(nuevaX, nuevaY))) {
            this.tick();

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