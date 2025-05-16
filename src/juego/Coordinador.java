package juego;

import juego.excepciones.ErrorCargarMapaException;
import juego.personaje.*;
import multimedia.Lienzo;
import multimedia.Teclado;

import java.io.IOException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Coordinador {
    private BufferedReader lectura;
    private Scanner entrada;
    
    private Lienzo lienzo;
    private final Teclado teclado;

    private final int nivelActual;
    private final Mapa mapa;
    private Pacman pacman;
    private final ArrayList<Fantasma> fantasmas = new ArrayList<>();

    public Coordinador(int nivel, Lienzo lienzo, Teclado teclado) {
        setLienzo(lienzo);
        this.teclado = teclado;

        nivelActual = nivel;
        mapa = new Mapa(lienzo);

        crearLaberinto(nivelActual);
        cerrarLectores();
    }

    public Lienzo getLienzo() {
        return lienzo;
    }

    public Mapa getMapa() {
        return mapa;
    }

    public Pacman getPacman() {
        return pacman;
    }

    public ArrayList<Fantasma> getFantasmas() {
        return fantasmas;
    }

    public int getNivelActual() {
        return nivelActual;
    }

    public int mapaGetLimiteX(){
        return mapa.getLimiteX();
    }

    public int mapaGetLimiteY(){
        return mapa.getLimiteY();
    }

    public void setLienzo(Lienzo lienzo) {
        this.lienzo = lienzo;
    }

    private void crearLaberinto(int nivel) {
        leerLaberinto(nivel);

        cargarLaberinto();
        situarPersonajes(nivel);
    }

    private void leerLaberinto(int numMapa) {
        switch (numMapa) {
            case 1 -> {
                try {
                    lectura = new BufferedReader(new FileReader("src/assets/mapas/mapa1/patron.txt"));
                    entrada = new Scanner(lectura);
                } catch (IOException e) {
                    throw new ErrorCargarMapaException(e);
                }
            }

            case 2 -> {
                try {
                    lectura = new BufferedReader(new FileReader("src/assets/mapas/mapa2/patron.txt"));
                    entrada = new Scanner(lectura);
                } catch (IOException e) {
                    throw new ErrorCargarMapaException(e);
                }
            }

            case 3 -> {
                try {
                    lectura = new BufferedReader(new FileReader("src/assets/mapas/mapa3/patron.txt"));
                    entrada = new Scanner(lectura);
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
        switch (nivel) {
            case 1 -> {
                pacman = new Pacman(lienzo, teclado, this, new Posicion(6, 7));

                for (int i = 1 ; i <= 3 ; i++) {
                    fantasmas.add(new FantasmaComun(lienzo, this, i));
                }
            }

            case 2 -> {
                pacman = new Pacman(lienzo, teclado, this, new Posicion(6, 7));

                for (int i = 1 ; i <= 3 ; i++) {
                    if (i == 3) fantasmas.add(new FantasmaListo(lienzo, this, pacman));

                    fantasmas.add(new FantasmaComun(lienzo, this, i));
                }
            }

            case 3 -> {
                pacman = new Pacman(lienzo, teclado,this, new Posicion(6, 7));

                for (int i = 1 ; i <= 3 ; i++) {
                    if (i == 3) fantasmas.add(new FantasmaComun(lienzo, this, i));

                    fantasmas.add(new FantasmaListo(lienzo, this, pacman));
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

    public boolean esPared(int x, int y) {
        return mapa.esPared(y, x);
    }

    public boolean esFantasma(Posicion posicion) {
        for (Fantasma fantasma : fantasmas) {
            if (posicion.equals(fantasma.getPosicion())) return true;
        }

        return false;
    }

    private void cerrarLectores() {
        try {
            if (entrada != null) entrada.close();
            if (lectura != null) lectura.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
