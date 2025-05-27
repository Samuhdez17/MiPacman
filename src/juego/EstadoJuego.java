package juego;

import multimedia.Dibujable;
import multimedia.Lienzo;

import javax.swing.*;
import java.awt.*;

public class EstadoJuego implements Dibujable {
    private Lienzo lienzo;
    private final Nivel nivelActual;

    private int puntosEnMapa;
    public int puntuacion;

    private boolean invencibilidad = false;
    private int momentoPwrUp = 0;

    public EstadoJuego(Lienzo lienzo, Nivel nivelActual) {
        setLienzo(lienzo);
        this.nivelActual = nivelActual;
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

    public void tick(int tiempoEnPartida) {
        if (nivelActual.getPowerUp() != null) {
            if (nivelActual.pacmanComioPwrUp()) {
                nivelActual.eliminarPwrUp();
                momentoPwrUp = tiempoEnPartida;
                cambiarInvencibilidad();
            }
        }
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
