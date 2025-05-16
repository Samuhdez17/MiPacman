import juego.Juego;
import juego.Coordinador;
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
        Coordinador coordinador = new Coordinador(1, ventana, ventana.getTeclado());
        Juego juego = new Juego(coordinador);

        try {
            while (true) {
                juego.dibujar();

                ventana.getTeclado().tick();
                juego.tick();

                espera(MILLIS);
            }

        } catch (PacmanComidoException e) {
            System.out.println("¡Game Over! Te han comido.");

        } catch (SalirDelJuegoException e) {
            System.out.println("Has elegido salir del juego.");
            System.exit(0);

        } finally {
            juego.dibujar();
        }
    }
}