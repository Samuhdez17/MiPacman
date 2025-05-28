package juego;

import multimedia.Dibujable;
import multimedia.Lienzo;

import javax.swing.*;
import java.awt.*;

public class EstadoJuego implements Dibujable {
    private Lienzo lienzo;
    private final Nivel nivelActual;

    private int puntosEnMapa;
    private int puntuacion;

    private boolean invencibilidad = false;
    private int momentoPwrUp = 0;
    private int duracionPwrUp = 0;

    public EstadoJuego(Lienzo lienzo, Nivel nivelActual) {
        setLienzo(lienzo);
        this.nivelActual = nivelActual;
    }

    public void setPuntosEnMapa(int puntos) {
        this.puntosEnMapa = puntos;
    }

    public boolean pacmanInvencible() {
        return invencibilidad;
    }

    public int getPuntuacion() {
        return puntuacion;
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
                momentoPwrUp = tiempoEnPartida; // momento en el que se ha comido el power up
                duracionPwrUp = nivelActual.getDuracionPwrUp();
                variarInvencibilidad();

                nivelActual.eliminarPwrUp();
            }
        }

        if (invencibilidad) {
            if (tiempoEnPartida - momentoPwrUp == duracionPwrUp) variarInvencibilidad();
        }
    }

    protected void gG() {
        JOptionPane.showMessageDialog((Component) lienzo, "¡Felicidades! Has ganado el juego.", "Felicidades", JOptionPane.INFORMATION_MESSAGE);
    }

    public void variarInvencibilidad() {
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
    }
}
