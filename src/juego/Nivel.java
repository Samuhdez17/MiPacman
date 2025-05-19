package juego;

import juego.excepciones.ErrorCargarMapaException;
import juego.personaje.*;
import multimedia.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class Nivel extends Juego {
    private BufferedReader lecturaPatronMapa;
    private Scanner entrada;

    public Nivel(Lienzo lienzo, Teclado teclado, int nivel) {
        super(lienzo, teclado);
        crearLaberinto(nivel);
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
                    fantasmas.add(new FantasmaComun(lienzo, this, i));

                    if (i == 3) fantasmas.add(new FantasmaListo(lienzo, this, pacman));
                }
            }

            case 3 -> {
                pacman = new Pacman(lienzo, teclado, this, new Posicion(6, 7));

                for (int i = 1 ; i <= 3 ; i++) {
                    if (i == 3) fantasmas.add(new FantasmaComun(lienzo, this, i));

                    fantasmas.add(new FantasmaListo(lienzo, this, pacman));
                }
            }
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

    public boolean estaLibre(Posicion posicion) {
        if (!mapa.esTransitable(posicion)) return false;

        if (posicion.equals(pacman.getPosicion())) return false;

        for (Fantasma fantasma : fantasmas) {
            if (posicion.equals(fantasma.getPosicion())) return false;
        }

        return true;
    } // se usaba para la posicion random del fantasma

    public boolean esPared(int x, int y) {
        return mapa.esPared(y, x);
    }

    public boolean esFantasma(Posicion posicion) {
        for (Fantasma fantasma : fantasmas) {
            if (posicion.equals(fantasma.getPosicion())) return true;
        }

        return false;
    }
}