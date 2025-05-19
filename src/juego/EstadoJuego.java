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

    public void setPuntosEnMapa(int puntuacion) {
        this.puntosEnMapa = puntuacion;
    }

    public void decrementarPuntosEnMapa() {
        puntosEnMapa--;
    }

    public void incrementarPuntuacion() {
        puntuacion++;
        decrementarPuntosEnMapa();
    }

    public boolean todosPuntosComidos() {
        return puntosEnMapa == 0;
//        return puntuacion == 1;
    }

    public void setLienzo(Lienzo lienzo) {
        this.lienzo = lienzo;
    }

    public void dibujar() {
        lienzo.escribirTexto(0, 0, "Puntuación: " + puntuacion, Color.GREEN);
    }
}
