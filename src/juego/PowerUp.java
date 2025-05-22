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

public class PowerUp implements Dibujable {
    private Lienzo lienzo;
    private int duracionMax;

    private Image imagen;
    private Posicion posicion;

    public PowerUp(Lienzo lienzo, Nivel nivel) {
        setLienzo(lienzo);

        if (nivel.getNivelActual() == 2) setImagen("champi.png");
        else                             setImagen("pwr-up.png");

        setDuracionMax(nivel.getNivelActual());
        generarPosicion(nivel);
    }

    private void setDuracionMax(int nivelActual) {
        if      (nivelActual == 1) duracionMax = 4;
        else if (nivelActual == 2) duracionMax = 5;
        else                       duracionMax = 6;
    }

    public Posicion getPosicion() {
        return posicion;
    }
    public int getDuracionMax() {
        return duracionMax;
    }

    private void setImagen(String nombreImagen) {
        try {
            imagen = ImageIO.read(new File("src/assets/"  + nombreImagen));

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
