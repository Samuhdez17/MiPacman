package juego;

import juego.excepciones.PacmanComidoException;
import juego.excepciones.SalirDelJuegoException;
import juego.personaje.Fantasma;
import juego.personaje.Pacman;
import multimedia.Dibujable;
import multimedia.Lienzo;
import multimedia.Teclado;

import java.util.ArrayList;

public abstract class Juego implements Dibujable {
    protected Lienzo lienzo;
    protected Teclado teclado;

    protected final EstadoJuego estado;
    protected Mapa mapa;

    protected Pacman pacman;
    protected ArrayList<Fantasma> fantasmas = new ArrayList<>();

    public Juego(Lienzo lienzo, Teclado teclado) {
        setLienzo(lienzo);
        this.teclado = teclado;

        estado = new EstadoJuego(lienzo);
    }

    public boolean verificarIntercambio(Fantasma fantasma) {
        return fantasma.getPosicion().equals(pacman.getPosicion());
    }

    public void tick() throws PacmanComidoException, SalirDelJuegoException {
        pacman.tick();

        if (mapa.hayPunto(pacman.getPosicion())) {
            estado.incrementarPuntuacion();
            mapa.retirarPunto(pacman.getPosicion());
        }

        for (Fantasma fantasma : fantasmas) {
            if (verificarIntercambio(fantasma)) throw new PacmanComidoException();

            fantasma.tick();

            if (verificarIntercambio(fantasma)) throw new PacmanComidoException();
        }
    }

    public boolean pasarNivel() {
        return estado.todosPuntosComidos();
    }

    public int puntuacion() {
        return estado.puntuacion;
    }

    @Override
    public void setLienzo(Lienzo lienzo) {
        this.lienzo = lienzo;
    }

    @Override
    public void dibujar() {
        lienzo.limpiar();

        mapa.dibujar();
        pacman.dibujar();

        for (Fantasma fantasma : fantasmas) {
            fantasma.dibujar();
        }

        estado.dibujar();

        lienzo.volcar();
    }
}
