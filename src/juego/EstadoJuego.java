package juego;

import multimedia.Dibujable;
import multimedia.Lienzo;

import javax.swing.*;
import java.awt.*;

/** Clase para saber el estado del juego.
 * Tiene un lienzo para mostrar mensajes.
 * Sabe:
 * - En qué nivel se encuentra.
 * - Los puntos restantes en el mapa.
 * - La puntuación del jugador.
 * - Si Pac-Man es invencible.
 * - Cuando aparece el power up.
 * - Cuanto dura el power up.
 * - Cuando acaba la invencibilidad.
 */
public class EstadoJuego {
    private Lienzo lienzo;
    private final Nivel nivelActual;

    private int puntosEnMapa;
    private int puntuacion;

    private boolean invencibilidad = false;
    private int momentoPwrUp = 0;
    private int duracionPwrUp = 0;
    private int invencibilidadAcaba;

    /** Constructor
     * Se le indica su lienzo y el nivel en el que se encuentra.
     *
     * @param lienzo        Lienzo en el que dibujarse.
     * @param nivelActual   Nivel en el que se encuentra.
     */
    public EstadoJuego(Lienzo lienzo, Nivel nivelActual) {
        this.lienzo = lienzo;
        this.nivelActual = nivelActual;
    }

    public int getPuntuacion() {
        return puntuacion;
    }
    public int getInvencibilidadAcaba() {
        return invencibilidadAcaba;
    }

    public void setPuntosEnMapa(int puntos) {
        this.puntosEnMapa = puntos;
    }

    public boolean pacmanInvencible() {
        return invencibilidad;
    }

    public void decrementarPuntosEnMapa() {
        puntosEnMapa--;
    }

    public void incrementarPuntuacion() {
        puntuacion++;
        decrementarPuntosEnMapa();
    }

    /** Tick
     * Por cada tick, mira si el jugador se ha comido el power up, en caso de habérselo comido,
     * registra cuando se ha comido, su duración, activa la invencibilidad y elimina el power up del nivel.
     * En caso de ya haber sido comido, calcula cuanto tiempo lleva activa la invencibilidad para desactivarla y registrarlo para dejar que los fantasmas comidos hagan spawn de nuevo
     *
     *  @param tiempoEnPartida Tiempo en el que se encuentra el juego.
     */
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
            invencibilidadAcaba = tiempoEnPartida;
        }
    }

    // En caso de que el jugador haya ganado el juego, se le felicita
    protected void gG() {
        JOptionPane.showMessageDialog((Component) lienzo, "¡Felicidades! Has ganado el juego.", "Felicidades", JOptionPane.INFORMATION_MESSAGE);
    }

    public void variarInvencibilidad() {
        invencibilidad = !invencibilidad;
    }

    protected boolean todosPuntosComidos() {
        return puntosEnMapa == 0;
    }
}
