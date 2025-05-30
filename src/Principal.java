import juego.GameMaster;
import juego.Nivel;
import juego.excepciones.JugadorGanoJuegoException;
import juego.excepciones.PacmanComidoException;
import juego.excepciones.SalirDelJuegoException;
import multimedia.*;

import javax.swing.*;
import java.awt.*;

public class Principal {
    private static final int TICK = 120; // 110 500
    private static final int CAMBIO_NIVEL = 200;

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

        int nivelActual = 1;

        while (nivelActual <= 3) {
            int inicioPartida = (int) (System.currentTimeMillis() / 1000);

            GameMaster gameMaster = new GameMaster(ventana, ventana.getTeclado(), nivelActual);
            Nivel nivel = gameMaster.getNivel();

            try {
                while (!nivel.pasarNivel()) {
                    ventana.setTitle("PacMan     Puntuación: " + nivel.getPuntuacion());

                    int tiempoEnPartida = ((int) (System.currentTimeMillis() / 1000) - inicioPartida);

                    nivel.dibujar();

                    ventana.getTeclado().tick();
                    nivel.tick(tiempoEnPartida);

                    espera(TICK);
                }

                nivel.dibujar();
                espera(CAMBIO_NIVEL);

                mensajePasarNivel();

            } catch (PacmanComidoException e) {
                nivel.dibujar();
                mensajeTeHanComido();
                System.out.println("¡Game Over! Te han comido.");
                System.exit(0);

            } catch (SalirDelJuegoException e) {
                System.out.println("Has elegido salir del juego.");
                System.exit(0);

            } catch (JugadorGanoJuegoException e) {
                nivel.dibujar();

                nivel.gG();
                System.exit(0);
            }

            nivelActual++;
        }
    }

    private static void mensajeTeHanComido() {
        JOptionPane.showMessageDialog(null, "¡OH NO! Te han comido.", "GAME OVER", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void mensajePasarNivel() {
        JOptionPane.showMessageDialog(null, "Has pasado de nivel!", "Enhorabuena!", JOptionPane.INFORMATION_MESSAGE);
    }
}