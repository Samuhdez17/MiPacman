package juego.excepciones;

public class ErrorCargarMapaException extends RuntimeException {
    public ErrorCargarMapaException(String message) {
        super(message);
    }

    public ErrorCargarMapaException(Exception e) {
        super("No se puede cargar el patrón del mapa: " + e);
    }
}
