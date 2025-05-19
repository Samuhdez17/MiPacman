import juego.Nivel;
import juego.excepciones.PacmanComidoException;
import juego.excepciones.SalirDelJuegoException;
import multimedia.*;
import java.awt.*;

public class Principal {
    private static final int MILLIS = 110;

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

        for (int nivelActual = 1 ; nivelActual <= 3 ; nivelActual++) {
            int inicioPartida = (int) (System.currentTimeMillis() / 1000);

            nivel = new Nivel(ventana, ventana.getTeclado(), nivelActual);

            while (!nivel.pasarNivel()) {
                int tiempoEnPartida = ((int) (System.currentTimeMillis() / 1000) - inicioPartida);

                try {
                    nivel.dibujar();

                    ventana.getTeclado().tick();
                    nivel.tick();

//                     if (nivel.powerUp(nivelActual, tiempoEnPartida)) nivel.spawnearPwrUp();
                } catch (PacmanComidoException e) {
                    System.out.println("¡Game Over! Te han comido.");
                    break;

                } catch (SalirDelJuegoException e) {
                    System.out.println("Has elegido salir del juego.");
                    System.exit(0);
                }

                espera(MILLIS);
//                    System.out.println(tiempoEnPartida + " segundos transcurridos");
            }

            nivel.dibujar();
        }
    }
}