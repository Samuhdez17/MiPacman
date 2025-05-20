package juego;

import java.util.Random;

import juego.excepciones.ErrorCargarImagenException;
import juego.personaje.Posicion;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class PowerUp {
    private Image imagen;
    private Posicion posicion;

    public PowerUp() {
        setImagen();

        generarPosicion();
    }

    private void setImagen() {
        try {
            imagen = ImageIO.read(new File("src/assets/pwr-up.png"));

        } catch (IOException e) {
            throw new ErrorCargarImagenException(e);
        }
    }

    private void generarPosicion() {
        Random random = new Random();


    }
}
