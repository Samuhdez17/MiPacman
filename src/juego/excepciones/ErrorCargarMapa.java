package juego.excepciones;

public class ErrorCargarMapa extends RuntimeException {
    public ErrorCargarMapa(String message) {
        super(message);
    }

    public ErrorCargarMapa(Exception e) {
        super("No se puede cargar el patrón del mapa: " + e);
    }
}
