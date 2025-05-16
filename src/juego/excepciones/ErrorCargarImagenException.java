package juego.excepciones;

public class ErrorCargarImagenException extends RuntimeException {
    public ErrorCargarImagenException(String message) {
        super(message);
    }

    public ErrorCargarImagenException(Exception e) {
        super("No se puede cargar la imagen: " + e);
    }
}
