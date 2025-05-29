package juego;

import juego.excepciones.ErrorCargarMapaException;
import juego.excepciones.JugadorGanoJuegoException;
import juego.excepciones.PacmanComidoException;
import juego.excepciones.SalirDelJuegoException;
import juego.personaje.*;
import multimedia.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Nivel implements Dibujable {
    private BufferedReader lecturaPatronMapa;
    private Scanner entrada;

    protected Lienzo lienzo;
    protected Teclado teclado;

    protected EstadoJuego estado;
    protected Mapa mapa;
    private final int nivelActual;
    protected PowerUp powerUp;

    protected Pacman pacman;
    private static final int FANTASMAS_POR_NIVEL = 3;
    protected ArrayList<Fantasma> fantasmasVivos = new ArrayList<>();
    protected ArrayList<Fantasma> fantasmasComidos = new ArrayList<>();

    public Nivel(Lienzo lienzo, Teclado teclado, int nivelActual) {
        setLienzo(lienzo);
        this.teclado = teclado;
        this.nivelActual = nivelActual;

        estado = new EstadoJuego(lienzo, this);

        crearLaberinto(this.nivelActual);
    }

    public int getNivelActual() {
        return nivelActual;
    }
    public PowerUp getPowerUp() {
        return powerUp;
    }
    public int getDuracionPwrUp() {
        return powerUp.getDuracionMax();
    }
    public int getPuntuacion() {
        return estado.getPuntuacion();
    }
    public int getLimiteX() {
        return mapa.getLimiteX();
    }
    public int getLimiteY() {
        return mapa.getLimiteY();
    }

    public void eliminarPwrUp() {
        powerUp = null;
    }

    private void crearLaberinto(int nivel) {
        mapa = new Mapa(lienzo);
        leerLaberinto(nivel);

        cargarLaberinto();
        mapa.generarPuntos();
        estado.setPuntosEnMapa(mapa.getPuntosMapa());
        mapa.asignarSprites(nivel);

        situarPersonajes(nivel);

        cerrarLectores();
    }

    private void leerLaberinto(int numMapa) {
        switch (numMapa) {
            case 1 -> {
                try {
                    lecturaPatronMapa = new BufferedReader(new FileReader("src/assets/mapas/mapa1/patron.txt"));
                    entrada = new Scanner(lecturaPatronMapa);
                } catch (IOException e) {
                    throw new ErrorCargarMapaException(e);
                }
            }

            case 2 -> {
                try {
                    lecturaPatronMapa = new BufferedReader(new FileReader("src/assets/mapas/mapa2/patron.txt"));
                    entrada = new Scanner(lecturaPatronMapa);
                } catch (IOException e) {
                    throw new ErrorCargarMapaException(e);
                }
            }

            case 3 -> {
                try {
                    lecturaPatronMapa = new BufferedReader(new FileReader("src/assets/mapas/mapa3/patron.txt"));
                    entrada = new Scanner(lecturaPatronMapa);
                } catch (IOException e) {
                    throw new ErrorCargarMapaException(e);
                }
            }

        }
    }

    private void cargarLaberinto() {
        String linea;
        int fila = 0;

        while (entrada.hasNextLine()) {
            linea = entrada.nextLine();
            char[] filaMapa = linea.toCharArray();

            for (int col = 0; col < filaMapa.length; col++) {
                mapa.setContenidoMapa(col, fila, filaMapa[col]);
            }

            fila++;
        }
    }

    private void situarPersonajes(int nivel) {
        fantasmasVivos.clear();

        switch (nivel) {
            case 1 -> {
                pacman = new Pacman(lienzo, teclado, this, new Posicion(6, 7));

                for (int i = 1 ; i <= FANTASMAS_POR_NIVEL ; i++) {
                    fantasmasVivos.add(new FantasmaComun(lienzo, this, i));
                }
            }

            case 2 -> {
                pacman = new Pacman(lienzo, teclado, this, new Posicion(6, 7));

                for (int i = 1 ; i <= FANTASMAS_POR_NIVEL ; i++) {
                    if (i == 1) fantasmasVivos.add(new FantasmaListo(lienzo, this, pacman.getPosicion(), i));
                    else fantasmasVivos.add(new FantasmaComun(lienzo, this, i));
                }
            }

            case 3 -> {
                pacman = new Pacman(lienzo, teclado, this, new Posicion(6, 7));

                for (int i = 1 ; i <= FANTASMAS_POR_NIVEL ; i++) {
                    if (i == 1) fantasmasVivos.add(new FantasmaComun(lienzo, this, i));
                    else fantasmasVivos.add(new FantasmaListo(lienzo, this, pacman.getPosicion(), i));
                }
            }
        }

        fantasmaLiberarPosiciones();
    }

    /* Como se acaba de crear el fantasma, su posición inicial es la de alguna de las esquinas */
    private void fantasmaLiberarPosiciones() {
        for (Fantasma fantasma : fantasmasVivos) {
            fantasma.liberarPosicion(fantasma.getPosicionInicial());
        }
    }

    private void cerrarLectores() {
        try {
            if (entrada != null) entrada.close();
            if (lecturaPatronMapa != null) lecturaPatronMapa.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tick(int tiempoTranscurrido) throws PacmanComidoException, SalirDelJuegoException, JugadorGanoJuegoException {
        pacman.tick();

        if (mapa.hayPunto(pacman.getPosicion())) {
            estado.incrementarPuntuacion();
            mapa.retirarPunto(pacman.getPosicion());
        }

        fantasmasTick(tiempoTranscurrido);

        generarPwrUp(nivelActual, tiempoTranscurrido);

        estado.tick(tiempoTranscurrido);

        if (nivelActual == 3 && estado.todosPuntosComidos()) throw new JugadorGanoJuegoException();
    }

    private void fantasmasTick(int tiempoTranscurrido) throws SalirDelJuegoException {
        for (Fantasma fantasma : fantasmasVivos) {
            if (estado.pacmanInvencible()) {
                fantasma.debilitar();

                if (verificarIntercambio(fantasma)) fantasma.haSidoComido();

                fantasma.tick();

                if (verificarIntercambio(fantasma)) fantasma.haSidoComido();

            } else {
                fantasma.fortalecer();

                if (verificarIntercambio(fantasma)) throw new PacmanComidoException();

                fantasma.tick();

                if (verificarIntercambio(fantasma)) throw new PacmanComidoException();
            }
        }

        for (int fantasmaActual = 0 ; fantasmaActual < fantasmasVivos.size() ; fantasmaActual++) {
            Fantasma fantasma = fantasmasVivos.get(fantasmaActual);

            if (fantasma.estaComido()) {
                fantasma.setMomentoComido(tiempoTranscurrido);
                fantasma.liberarPosicion(fantasma.getPosicionInicial());

                fantasmasComidos.add(fantasma);
                fantasmasVivos.remove(fantasma);
            }
        }

        if (!fantasmasComidos.isEmpty()) {
            for (int fantasma = 0 ; fantasma < fantasmasComidos.size() ; fantasma++) {
                Fantasma fantasmaComido = fantasmasComidos.get(fantasma);

                if (tiempoTranscurrido - fantasmaComido.getMomentoComido() >= 5) {
                    fantasmasVivos.add(fantasmaComido);
                    fantasmasComidos.remove(fantasmaComido);

                    fantasmaComido.revivir();
                    fantasmaComido.reUbicar();
                }
            }
        }
    }

    public boolean verificarIntercambio(Fantasma fantasma) {
        return fantasma.getPosicion().equals(pacman.getPosicion());
    }

    private void generarPwrUp(int nivelActual, int tiempoTranscurrido) {
        int momentoAparicion;

        if      (nivelActual == 1) momentoAparicion = 1;
        else if (nivelActual == 2) momentoAparicion = 5;
        else                       momentoAparicion = 6;

        if (tiempoTranscurrido == momentoAparicion) { // "Animación de aparición"
            powerUp = new PowerUp(lienzo, this);

        } else if (tiempoTranscurrido > momentoAparicion && powerUp != null) {
            // Una vez asentado, borramos el punto de donde está y modificamos los puntos en mapa
            if (mapa.hayPunto(powerUp.getPosicion())) {
                mapa.retirarPunto(powerUp.getPosicion());
                estado.setPuntosEnMapa(mapa.getPuntosMapa());
            }
        }
    }

    public boolean esPwrUp(Posicion posicion) {
        return powerUp != null && powerUp.getPosicion().equals(posicion);
    }

    public boolean estaLibre(Posicion posicion) {
        if (!mapa.esTransitable(posicion)) return false;

        if (posicion.equals(pacman.getPosicion())) return false;

        for (Fantasma fantasma : fantasmasVivos) {
            if (posicion.equals(fantasma.getPosicion())) return false;
        }

        return true;
    }

    public boolean esTransitable(Posicion posicion) {
        return mapa.esTransitable(posicion);
    }

    public boolean esPared(int x, int y) {
        return mapa.esPared(y, x);
    }

    public boolean esFantasma(Posicion posicion) {
        for (Fantasma fantasma : fantasmasVivos) {
            if (posicion.equals(fantasma.getPosicion())) return true;
        }

        return false;
    }

    public boolean pacmanComioPwrUp() {
        return esPwrUp(pacman.getPosicion());
    }

    public boolean pasarNivel() {
        return estado.todosPuntosComidos();
    }

    public void gG() {
        estado.gG();
    }

    @Override
    public void setLienzo(Lienzo lienzo) {
        this.lienzo = lienzo;
    }

    @Override
    public void dibujar() {
        lienzo.limpiar();

        mapa.dibujar();
        if (powerUp != null) powerUp.dibujar();

        pacman.dibujar();

        for (Fantasma fantasma : fantasmasVivos) fantasma.dibujar();

        lienzo.volcar();
    }
}