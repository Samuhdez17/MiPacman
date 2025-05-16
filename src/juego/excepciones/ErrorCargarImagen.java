package juego.excepciones;

public class ErrorCargarImagen extends RuntimeException {
    public ErrorCargarImagen(String message) {
        super(message);
    }

    public ErrorCargarImagen(Exception e) {
        super("No se puede cargar la imagen: " + e);
    }
}
