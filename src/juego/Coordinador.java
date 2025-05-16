package juego;

import juego.excepciones.ErrorCargarImagen;
import juego.excepciones.PacmanComidoException;
import juego.excepciones.SalirDelJuegoException;
import juego.personaje.*;
import multimedia.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class Coordinador implements Dibujable {
    private Lienzo lienzo;
    private Teclado teclado;

    private EstadoJuego estado;
    private Mapa mapa;

    private Pacman pacman;
    private ArrayList<Fantasma> fantasmas;

    public Coordinador(Nivel nivel, Lienzo lienzo, Teclado teclado) {
        setLienzo(lienzo);
        estado = new EstadoJuego(lienzo);
        this.teclado = teclado;

        this.mapa = new Mapa(lienzo);
        mapa.setLaberinto(nivel.getMapa());

        situarActores(nivel.getNivelActual());
        asignarSprites(nivel.getNivelActual());
        mapa.generarPuntos();
    }

    private void situarActores(int nivel) {
        switch (nivel) {
            case 1 -> {
                pacman = new Pacman(lienzo, teclado,this, new Posicion(6, 7));

                for (int i = 1 ; i <= 3 ; i++) {
                    fantasmas.add(new FantasmaComun(lienzo, this, i));
                }
            }

            case 2 -> {
                pacman = new Pacman(lienzo, teclado,this, new Posicion(6, 7));

                for (int i = 1 ; i <= 3 ; i++) {
                    if (i == 3) fantasmas.add(new FantasmaListo());

                    fantasmas.add(new FantasmaComun(lienzo, this, i));
                }
            }

            case 3 -> {
                pacman = new Pacman(lienzo, teclado,this, new Posicion(6, 7));

                for (int i = 1 ; i <= 3 ; i++) {
                    if (i > 1) new FantasmaListo();

                    fantasmas.add(new FantasmaComun(lienzo, this, i));
                }
            }
        }
    }

    private void asignarSprites(int numMapa) {
        switch (numMapa) {
            case 1 -> {
                try {
                    mapa.setSuelo(Color.BLACK);
                    mapa.setImagenMuro(ImageIO.read(new File("src/assets/mapas/mapa1/muro.png")));
                    mapa.setImagenMoneda(ImageIO.read(new File("src/assets/mapas/mapa1/moneda.png")));

                } catch (IOException e){
                    throw new ErrorCargarImagen("No se puede cargar la imagen: " + e);
                }
            }

            case 2 -> {
                try {
                    mapa.setSuelo(Color.BLACK);
                    mapa.setImagenMuro(ImageIO.read(new File("src/assets/mapas/mapa2/muro.png")));
                    mapa.setImagenMoneda(ImageIO.read(new File("src/assets/mapas/mapa2/moneda.png")));

                } catch (IOException e){
                    throw new ErrorCargarImagen("No se puede cargar la imagen: " + e);
                }
            }

            case 3 -> {
                try {
                    mapa.setSuelo(Color.BLACK);
                    mapa.setImagenMuro(ImageIO.read(new File("src/assets/mapas/mapa3/muro.png")));
                    mapa.setImagenMoneda(ImageIO.read(new File("src/assets/mapas/mapa3/moneda.png")));

                } catch (IOException e){
                    throw new ErrorCargarImagen("No se puede cargar la imagen: " + e);
                }
            }
        }
    }

    public boolean estaLibre(Posicion posicion) {
        if (!mapa.esTransitable(posicion)) return false;

        if (posicion.equals(pacman.getPosicion())) return false;

        for (Fantasma fantasma : fantasmas) {
            if (posicion.equals(fantasma.getPosicion())) return false;
        }

        return true;
    }

    public boolean esPared(Posicion posicion) {
        return mapa.esPared(posicion);
    }

    public void tick() {
        pacman.tick();

        if (mapa.hayPunto(pacman.getPosicion())) {
            estado.incrementarPuntuacion();
            mapa.retirarPunto(pacman.getPosicion());
        }

        for (Fantasma fantasma : fantasmas) {
            fantasma.tick();
        }
    }

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
}