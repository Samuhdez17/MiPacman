package juego;

import juego.personaje.Posicion;
import multimedia.*;

import java.awt.*;

public class Mapa implements Dibujable {
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

    public void setImagenMoneda(Image imagenMoneda) {
        this.imagenMoneda = imagenMoneda;
    }
    public void setImagenMuro(Image imagenMuro) {
        this.imagenMuro = imagenMuro;
    }
    public void setSuelo(Color suelo) {
        this.suelo = suelo;
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
                if (getContenidoMapa(x, y) == ' ') setContenidoMapa(x, y, '·');
            }
        }
    }

    public boolean hayPunto(Posicion posicion) {
        return getContenidoMapa(posicion) == '·';
    }

    public void retirarPunto(Posicion posicion) {
        setContenidoMapa(posicion, ' ');
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