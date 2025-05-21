package juego;

import java.util.Random;

import juego.excepciones.ErrorCargarImagenException;
import juego.personaje.Posicion;
import multimedia.Dibujable;
import multimedia.Lienzo;
import multimedia.Tickable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class PowerUp implements Dibujable {
    private Lienzo lienzo;
    private int duracion;

    private Image imagen;
    private Posicion posicion;

    public PowerUp(Lienzo lienzo, Nivel nivel) {
        setLienzo(lienzo);
        setImagen();

        setDuracion(nivel.getNivelActual());
        generarPosicion(nivel);
    }

    private void setDuracion(int nivelActual) {
        if (nivelActual == 1) duracion = 4;
        else if (nivelActual == 2) duracion = 5;
        else duracion = 6;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    private void setImagen() {
        try {
            imagen = ImageIO.read(new File("src/assets/pwr-up.png"));

        } catch (IOException e) {
            throw new ErrorCargarImagenException(e);
        }
    }

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
