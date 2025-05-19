package juego;

import juego.excepciones.ErrorCargarImagenException;
import juego.personaje.Posicion;
import multimedia.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Mapa implements Dibujable {
    private int puntosEnMapa = 0;

    private char[][] laberinto = new char[15][15];
    private Color suelo;
    private Image imagenMuro;
    private Image imagenMoneda;

    private Lienzo lienzo;

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

    public int getMaxPuntos() {
        return puntosEnMapa;
    }

    public int getLimiteX(){
        return getAncho() - 1;
    }
    public int getLimiteY(){
        return getAlto() - 1;
    }

    // Estos get y set son para "traducir" el sistema de coordenadas x, y que se utiliza
    // a lo largo de todo el programa, al sistema de coordenadas de filas y columnas
    // que se utiliza en la matriz de chars.

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
    private void setSuelo(Color suelo) {
        this.suelo = suelo;
    }

    public void asignarSprites(int numMapa) {
        switch (numMapa) {
            case 1 -> {
                try {
                    setSuelo(Color.BLACK);
                    setImagenMuro(ImageIO.read(new File("src/assets/mapas/mapa1/muro.png")));
                    setImagenMoneda(ImageIO.read(new File("src/assets/mapas/mapa1/moneda.png")));

                } catch (IOException e){
                    throw new ErrorCargarImagenException(e);
                }
            }

            case 2 -> {
                try {
                    setSuelo(Color.GREEN);
                    setImagenMuro(ImageIO.read(new File("src/assets/mapas/mapa2/muro.png")));
                    setImagenMoneda(ImageIO.read(new File("src/assets/mapas/mapa2/moneda.png")));

                } catch (IOException e){
                    throw new ErrorCargarImagenException(e);
                }
            }

            case 3 -> {
                try {
                    setSuelo(Color.RED);
                    setImagenMuro(ImageIO.read(new File("src/assets/mapas/mapa3/muro.png")));
                    setImagenMoneda(ImageIO.read(new File("src/assets/mapas/mapa3/moneda.png")));

                } catch (IOException e){
                    throw new ErrorCargarImagenException(e);
                }
            }
        }
    }

    public boolean esTransitable(Posicion posicion) {
        int x = posicion.getX();
        int y = posicion.getY();

        return x >= 0 && x < laberinto.length && y >= 0 && y < laberinto[0].length && getContenidoMapa(x, y) != '#';
    }

    public boolean esPared(int x, int y) {
        return getContenidoMapa(y, x) == '#';
    }

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

    public boolean hayPunto(Posicion posicion) {
        return getContenidoMapa(posicion) == '·';
    }

    public void retirarPunto(Posicion posicion) {
        setContenidoMapa(posicion, ' ');
        puntosEnMapa--;
    }

    public void dibujar() {
        for (int x = 0; x < getAncho(); x++) {
            for (int y = 0; y < getAlto(); y++) {
                lienzo.marcarPixel(x, y, suelo);

                if (getContenidoMapa(x, y) == '#') lienzo.dibujarImagen(x, y, imagenMuro);
                else if (getContenidoMapa(x, y) == '·') lienzo.dibujarImagen(x, y, imagenMoneda);
            }
        }
    }
}