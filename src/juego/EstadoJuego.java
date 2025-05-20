package juego;

import multimedia.Dibujable;
import multimedia.Lienzo;

import java.awt.*;

public class EstadoJuego implements Dibujable {
    private int puntosEnMapa;
    public int puntuacion;
    private Lienzo lienzo;

    public EstadoJuego(Lienzo lienzo) {
        setLienzo(lienzo);
    }

    public void setPuntosEnMapa(int puntos) {
        this.puntosEnMapa = puntos;
    }

    public void decrementarPuntosEnMapa() {
        puntosEnMapa--;
    }

    public void incrementarPuntuacion() {
        puntuacion++;
        decrementarPuntosEnMapa();
    }

    protected void gG() {
        lienzo.escribirTexto(6, 7, "¡Ganaste!", Color.GREEN);
    }

    protected boolean todosPuntosComidos() {
        //return puntosEnMapa == 0;
        return puntuacion == 5;
    }

    public void setLienzo(Lienzo lienzo) {
        this.lienzo = lienzo;
    }

    public void dibujar() {
        lienzo.escribirTexto(0, 0, "Puntuación: " + puntuacion, Color.GREEN);
    }
}
