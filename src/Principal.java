import juego.GameMaster;
import juego.Nivel;
import juego.excepciones.JugadorGanoJuegoException;
import juego.excepciones.PacmanComidoException;
import juego.excepciones.SalirDelJuegoException;
import multimedia.*;

import java.awt.*;

public class Principal {
    private static final int TICK = 100;
    private static final int CAMBIO_NIVEL = 100;

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
        boolean pacmanComido = false;

        while (nivelActual <= 3 && !pacmanComido) {
            int inicioPartida = (int) (System.currentTimeMillis() / 1000);

            GameMaster gameMaster = new GameMaster(ventana, ventana.getTeclado(), nivelActual);
            Nivel nivel = gameMaster.getNivel();

            try {
                while (!nivel.pasarNivel()) {
                    int tiempoEnPartida = ((int) (System.currentTimeMillis() / 1000) - inicioPartida);

                    nivel.dibujar();

                    ventana.getTeclado().tick();
                    nivel.tick(tiempoEnPartida);

                    espera(TICK);
                }

                nivel.dibujar();
                espera(CAMBIO_NIVEL);

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
                System.exit(0);

            }

            nivelActual++;
        }
    }
}