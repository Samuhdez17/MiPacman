package juego.personaje;

import juego.Coordinador;
import juego.excepciones.ErrorCargarImagen;
import juego.excepciones.MovimientoInvalidoException;
import multimedia.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public abstract class Actor implements Dibujable {
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
            throw new ErrorCargarImagen(e);
        }
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public Posicion getPosicionActual() {
        return posicion;
    }

    public void mover(Direccion dir) throws MovimientoInvalidoException {
        Posicion destino = desplazar(dir);

        if (destino.equals(this.posicion)) throw new MovimientoInvalidoException("Movimiento no válido.");

        if (coordinador.esPared(destino)) {
            if (this instanceof Fantasma) this.tick();
        }
        this.posicion = destino;
    }

    public Posicion desplazar(Direccion dir) {
        int nuevaX = posicion.getX();
        int nuevaY = posicion.getY();

        switch (dir) {
            case ARR -> posicion.setY(nuevaY - 1);
            case ABA -> posicion.setY(nuevaY + 1);
            case IZD -> posicion.setX(nuevaX - 1);
            case DCH -> posicion.setX(nuevaX + 1);
            default -> {
                return posicion;
            }
        }

        return new Posicion(nuevaX, nuevaY);
    }

    public void setLienzo(Lienzo lienzo) {
        this.lienzo = lienzo;
    }

    public void dibujar() {
        lienzo.dibujarImagen(posicion.getX(), posicion.getY(), imagen);
    }

    public abstract void tick();
}