import juego.Nivel;
import juego.excepciones.JugadorGanoJuegoException;
import juego.excepciones.PacmanComidoException;
import juego.excepciones.SalirDelJuegoException;
import multimedia.*;

import javax.swing.*;
import java.awt.*;

public class Principal {
    private static final int MILLIS = 500;

    public static void espera(int milisegundos) {
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
        }
    }

    public static void main(String[] args) {
        int anchoVentana = 15;
        int altoVentana = 15;
        int tamPixel = 32;
        Color colorFondo = Color.BLACK;

        VentanaMultimedia ventana = new VentanaMultimedia("PacMan", anchoVentana, altoVentana, tamPixel, colorFondo);
        Nivel nivel;
        boolean pacmanComido = false;

        for (int nivelActual = 1; nivelActual <= 3; nivelActual++) {
            int inicioPartida = (int) (System.currentTimeMillis() / 1000);

            nivel = new Nivel(ventana, ventana.getTeclado(), nivelActual);

            try {

                while (!nivel.pasarNivel() && !pacmanComido) {
                    int tiempoEnPartida = ((int) (System.currentTimeMillis() / 1000) - inicioPartida);

                    nivel.dibujar();

                    ventana.getTeclado().tick();
                    nivel.tick();

//                if (nivel.powerUp(nivelActual, tiempoEnPartida)) nivel.spawnearPwrUp();

                    nivel.dibujar();

                    espera(MILLIS);
                }

            } catch (PacmanComidoException e) {
                nivel.dibujar();

                System.out.println("¡Game Over! Te han comido.");
                pacmanComido = true;

            } catch (SalirDelJuegoException e) {
                System.out.println("Has elegido salir del juego.");
                System.exit(0);

            } catch (JugadorGanoJuegoException e) {
                nivel.dibujar();

                nivel.gG();
                JOptionPane.showMessageDialog(ventana, "¡Felicidades! Has ganado el juego.", "Felicidades", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
        }
    }
}