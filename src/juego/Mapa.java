package juego;

import juego.excepciones.ErrorCargarImagenException;
import juego.personaje.Posicion;
import multimedia.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/** Esta clase genera un mapa dependiendo del nivel en el que se encuentre el jugador.
 * Tiene:
 *  - Puntos totales.
 *  - Patrón de laberinto.
 *  - Imagen para el suelo.
 *  - Imagen para los muros.
 *  - Imagen para las monedas.
 *  - Lienzo en el que representarse.
 */
public class Mapa implements Dibujable {
    private int puntosEnMapa = 0;

    private final char[][] laberinto = new char[15][15];
    private Image imagenSuelo;
    private Image imagenMuro;
    private Image imagenMoneda;

    private Lienzo lienzo;

    /** Constructor.
     * Se le indica el lienzo en el que dibujarse.
     *
     * @param lienzo Lienzo en el que dibujarse.
     */
    public Mapa(Lienzo lienzo) {
        setLienzo(lienzo);
    }

    public void setLienzo(Lienzo lienzo) {
        this.lienzo = lienzo;
    }

    public int getAncho() {
        return laberinto[1].length;
    }

    public int getAlto() {
        return laberinto.length;
    }

    public int getPuntosMapa() {
        return puntosEnMapa;
    }

    public int getLimiteX(){
        return getAncho() - 1;
    }
    public int getLimiteY(){
        return getAlto() - 1;
    }

    private char getContenidoMapa(int x, int y) {
        return laberinto[y][x];
    }
    private char getContenidoMapa(Posicion posicion) {
        return laberinto[posicion.getY()][posicion.getX()];
    }

    protected void setContenidoMapa(int x, int y, char c) {
        laberinto[y][x] = c;
    }
    private void setContenidoMapa(Posicion posicion, char c) {
        laberinto[posicion.getY()][posicion.getX()] = c;
    }

    private void setImagenMoneda(Image imagenMoneda) {
        this.imagenMoneda = imagenMoneda;
    }
    private void setImagenMuro(Image imagenMuro) {
        this.imagenMuro = imagenMuro;
    }
    private void setImagenSuelo(Image imagenSuelo) {
        this.imagenSuelo = imagenSuelo;
    }

    /** Método para asignar los spraits del mapa.
     * Este es usado por la clase Nivel, al establecerse el nivel en el que se va a jugar, se le indica al mapa para que sepa que imágenes usar.
     *
     * @param numMapa Número del mapa en el que se encuentra.
     * @see Nivel#crearLaberinto(int)
     */
    public void asignarSprites(int numMapa) {
        switch (numMapa) {
            case 1 -> {
                try {
                    setImagenSuelo(ImageIO.read(new File("src/assets/mapas/mapa1/suelo.png")));
                    setImagenMuro(ImageIO.read(new File("src/assets/mapas/mapa1/muro.png")));
                    setImagenMoneda(ImageIO.read(new File("src/assets/mapas/mapa1/moneda.png")));

                } catch (IOException e){
                    throw new ErrorCargarImagenException(e);
                }
            }

            case 2 -> {
                try {
                    setImagenSuelo(ImageIO.read(new File("src/assets/mapas/mapa2/suelo.png")));
                    setImagenMuro(ImageIO.read(new File("src/assets/mapas/mapa2/muro.png")));
                    setImagenMoneda(ImageIO.read(new File("src/assets/mapas/mapa2/moneda.png")));

                } catch (IOException e){
                    throw new ErrorCargarImagenException(e);
                }
            }

            case 3 -> {
                try {
                    setImagenSuelo(ImageIO.read(new File("src/assets/mapas/mapa3/suelo.png")));
                    setImagenMuro(ImageIO.read(new File("src/assets/mapas/mapa3/muro.png")));
                    setImagenMoneda(ImageIO.read(new File("src/assets/mapas/mapa3/moneda.png")));

                } catch (IOException e){
                    throw new ErrorCargarImagenException(e);
                }
            }
        }
    }

    /** Método para generar puntos en el mapa.
     * Tras haber generado el mapa con el laberinto, se rellenan los huecos en blanco por puntos.
     */
    public void generarPuntos() {
        for (int x = 0; x < laberinto.length; x++) {
            for (int y = 0; y < laberinto[0].length; y++) {
                if (getContenidoMapa(x, y) == ' ') {
                    setContenidoMapa(x, y, '·');
                    puntosEnMapa++;
                }
            }
        }
    }

    public void retirarPunto(Posicion posicion) {
        setContenidoMapa(posicion, ' ');
        puntosEnMapa--;
    }

    /* Indica si no hay muro */
    public boolean esTransitable(Posicion posicion) {
        int x = posicion.getX();
        int y = posicion.getY();

        return x >= 0 && x < laberinto.length && y >= 0 && y < laberinto[0].length && getContenidoMapa(x, y) != '#';
    }

    public boolean esPared(int x, int y) {
        return getContenidoMapa(y, x) == '#';
    }

    public boolean hayPunto(Posicion posicion) {
        return getContenidoMapa(posicion) == '·';
    }

    /* Carga el suelo, y en caso de requerirlo, el muro o moneda correspondiente */
    public void dibujar() {
        for (int x = 0; x < getAncho(); x++) {
            for (int y = 0; y < getAlto(); y++) {
                lienzo.dibujarImagen(x, y, imagenSuelo);

                if (getContenidoMapa(x, y) == '#') lienzo.dibujarImagen(x, y, imagenMuro);
                else if (getContenidoMapa(x, y) == '·') lienzo.dibujarImagen(x, y, imagenMoneda);
            }
        }
    }
}