package juego.excepciones;

public class PacmanComidoException extends RuntimeException {
  public PacmanComidoException(String message) {
    super(message);
  }

  public PacmanComidoException() {
    super("¡Pacman ha sido comido!");
  }
}
