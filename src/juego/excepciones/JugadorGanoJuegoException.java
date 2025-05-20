package juego.excepciones;

public class JugadorGanoJuegoException extends RuntimeException {
    public JugadorGanoJuegoException(String message) {
        super(message);
    }

    public JugadorGanoJuegoException() {
        super("¡Jugador ha ganado el juego!");
    }
}
