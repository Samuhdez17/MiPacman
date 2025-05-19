import juego.GameMaster;
import juego.Nivel;
import juego.excepciones.PacmanComidoException;
import juego.excepciones.SalirDelJuegoException;
import multimedia.*;
import java.awt.*;

public class Principal {
    private static final int MILLIS = 100;

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

        for (int nivelActual = 1 ; nivelActual <= 3 ; nivelActual++) {
            GameMaster juego = new GameMaster(nivelActual, ventana, ventana.getTeclado());
            Nivel nivel = juego.generarNivel();

            try {
                while (true) {
                    nivel.dibujar();

                    ventana.getTeclado().tick();
                    nivel.tick();

                    espera(MILLIS);
                }

            } catch (PacmanComidoException e) {
                System.out.println("¡Game Over! Te han comido.");
                break;

            } catch (SalirDelJuegoException e) {
                System.out.println("Has elegido salir del juego.");
                System.exit(0);
            }
        }
    }
}