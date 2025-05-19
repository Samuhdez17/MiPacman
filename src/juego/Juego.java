package juego;

import juego.excepciones.ErrorCargarImagenException;
import juego.excepciones.PacmanComidoException;
import juego.excepciones.SalirDelJuegoException;
import juego.personaje.*;
import multimedia.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class Juego implements Dibujable {
    private final EstadoJuego estado;
    private Lienzo lienzo;

    private final Mapa mapa;

    private final Pacman pacman;
    private final ArrayList<Fantasma> fantasmas;

    public Juego(Coordinador coordinador) {
        setLienzo(coordinador.getLienzo());

        pacman = coordinador.getPacman();
        fantasmas = coordinador.getFantasmas();
        mapa = coordinador.getMapa();

        estado = new EstadoJuego(coordinador.getLienzo(), mapa.getMaxPuntos());

        asignarSprites(coordinador.getNivelActual());
        mapa.generarPuntos();
    }

    private void asignarSprites(int numMapa) {
        switch (numMapa) {
            case 1 -> {
                try {
                    mapa.setSuelo(Color.BLACK);
                    mapa.setImagenMuro(ImageIO.read(new File("src/assets/mapas/mapa1/muro.png")));
                    mapa.setImagenMoneda(ImageIO.read(new File("src/assets/mapas/mapa1/moneda.png")));

                } catch (IOException e){
                    throw new ErrorCargarImagenException(e);
                }
            }

            case 2 -> {
                try {
                    mapa.setSuelo(Color.BLACK);
                    mapa.setImagenMuro(ImageIO.read(new File("src/assets/mapas/mapa2/muro.png")));
                    mapa.setImagenMoneda(ImageIO.read(new File("src/assets/mapas/mapa2/moneda.png")));

                } catch (IOException e){
                    throw new ErrorCargarImagenException(e);
                }
            }

            case 3 -> {
                try {
                    mapa.setSuelo(Color.BLACK);
                    mapa.setImagenMuro(ImageIO.read(new File("src/assets/mapas/mapa3/muro.png")));
                    mapa.setImagenMoneda(ImageIO.read(new File("src/assets/mapas/mapa3/moneda.png")));

                } catch (IOException e){
                    throw new ErrorCargarImagenException(e);
                }
            }
        }
    }

    public void tick() throws PacmanComidoException, SalirDelJuegoException {
        pacman.tick();

        if (mapa.hayPunto(pacman.getPosicion())) {
            estado.incrementarPuntuacion();
            mapa.retirarPunto(pacman.getPosicion());
        }

        for (Fantasma fantasma : fantasmas) {
            if (verificarIntercambio(fantasma)) throw new PacmanComidoException("¡Pacman ha sido comido!");

            fantasma.tick();

            if (verificarIntercambio(fantasma)) throw new PacmanComidoException("¡Pacman ha sido comido!");
        }
    }

    public boolean verificarIntercambio(Fantasma fantasma) {
        return fantasma.getPosicion().equals(pacman.getPosicion());
    }

    @Override
    public void setLienzo(Lienzo lienzo) {
        this.lienzo = lienzo;
    }

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

    public boolean comioTodosLosPuntos() {
        return estado.pacmanComioTodo();
    }
}