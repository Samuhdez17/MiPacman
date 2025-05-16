package juego;

import juego.excepciones.ErrorCargarMapa;

import java.io.IOException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class Nivel {
    // private static final int NUM_NIVELES = 3;

    private BufferedReader lectura;
    private Scanner entrada;

    private final int nivelActual;
    private char[][] mapa = new char[15][15];

    public Nivel(int nivel) {
        nivelActual = nivel;

        crearMapa(nivelActual);
        cerrarLectores();
    }

    public char[][] getMapa() {
        return mapa;
    }

    public int getNivelActual() {
        return nivelActual;
    }

    private void crearMapa(int nivel) {
        leerMapa(nivel);

        cargarMapa();
    }

    private void leerMapa(int numMapa) {
        switch (numMapa) {
            case 1 -> {
                try {
                    lectura = new BufferedReader(new FileReader("src/assets/mapas/mapa1/patron.txt"));
                    entrada = new Scanner(lectura);
                } catch (IOException e) {
                    throw new ErrorCargarMapa(e);
                }
            }

            case 2 -> {
                try {
                    lectura = new BufferedReader(new FileReader("src/assets/mapas/mapa2/patron.txt"));
                    entrada = new Scanner(lectura);
                } catch (IOException e) {
                    throw new ErrorCargarMapa(e);
                }
            }

            case 3 -> {
                try {
                    lectura = new BufferedReader(new FileReader("src/assets/mapas/mapa3/patron.txt"));
                    entrada = new Scanner(lectura);
                } catch (IOException e) {
                    throw new ErrorCargarMapa(e);
                }
            }

        }
    }

    private void cargarMapa() {
        String linea;
        int fila = 0;

        while (entrada.hasNextLine()) {
            linea = entrada.nextLine();

            char[] filaMapa = linea.toCharArray();

            for (int col = 0; col < filaMapa.length; col++) {
                mapa[fila][col] = filaMapa[col];
            }

            fila++;
        }
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
