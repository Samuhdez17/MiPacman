package juego;

import multimedia.Dibujable;
import multimedia.Lienzo;

import javax.swing.*;
import java.awt.*;

public class EstadoJuego implements Dibujable {
    private Lienzo lienzo;

    private int puntosEnMapa;
    public int puntuacion;

    private boolean invencibilidad = false;

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

    public void tick() {

    }

    protected void gG() {
        JOptionPane.showMessageDialog((Component) lienzo, "¡Felicidades! Has ganado el juego.", "Felicidades", JOptionPane.INFORMATION_MESSAGE);
    }

    public void cambiarInvencibilidad() {
        invencibilidad = !invencibilidad;
    }

    protected boolean todosPuntosComidos() {
        return puntosEnMapa == 0;
//        return puntuacion == 10;
    }

    public void setLienzo(Lienzo lienzo) {
        this.lienzo = lienzo;
    }

    public void dibujar() {
        lienzo.escribirTexto(0, 0, "Puntuación: " + puntuacion, Color.GREEN);
    }
}
