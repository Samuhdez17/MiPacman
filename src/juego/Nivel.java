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

/** Clase principal del juego.
 * Esta clase crea al mapa, el power up, el estado del juego y sitúa a los personajes.
 * GameMaster llama a la clase indicando el nivel que queremos generar y esta clase hace el resto, la magia.
 */
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

    /** Constructor
     * Se guarda el lienzo, el teclado y el nivel actual, para poder crear los personajes, el mapa y el estado del juego con esos datos.
     * Primero se crea el estado, luego se hace el mapa y ya por último se sitúan los personajes.
     *
     * @param lienzo        Lienzo en el que se van a representar las cosas.
     * @param teclado       Teclado que usará pacman para comunicarse con el usuario.
     * @param nivelActual   Nivel en el que se encuentra.
     * @see #crearMapa(int) Para empezar la cadena.
     */
    public Nivel(Lienzo lienzo, Teclado teclado, int nivelActual) {
        setLienzo(lienzo);
        this.teclado = teclado;
        this.nivelActual = nivelActual;

        estado = new EstadoJuego(lienzo, this);

        crearMapa(this.nivelActual);
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

    /** Método para crear el mapa.
     *
     * @param nivel Nivel que queremos cargar
     * @see #leerLaberinto(int) <-- Para continuar la cadena.
     */
    private void crearMapa(int nivel) {
        mapa = new Mapa(lienzo); // Hacemos el mapa
        leerLaberinto(nivel);    // Leemos el laberinto

        cargarLaberinto();       // Cargamos el laberinto en nuestro mapa
        mapa.generarPuntos();    // Generamos los puntos en el mapa
        estado.setPuntosEnMapa(mapa.getPuntosMapa()); // Le decimos al estado los puntos que hay en el mapa
        mapa.asignarSprites(nivel); // Le decimos al mapa que asigne los sprites correspondientes

        situarPersonajes(nivel); // Situamos a los personajes en su sitio con respecto al nivel que estamos creando

        cerrarLectores(); // Cerramos los lectores de archivos
    }

    /** Método para leer el patrón del mapa que queremos jugar.
     * Abrimos los lectores y guardamos el patron en un Scanner.
     *
     * @param numMapa Número del mapa que queremos generar.
     * @see #cargarLaberinto() <-- Para continuar la cadena.
     */
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

    /** Método para cargar el laberinto en el mapa.
     * Se lee cada linea del Scanner y lo vamos asignando en la matriz de chars que tenemos en el mapa.
     * @see Mapa#asignarSprites(int) <-- Para continuar la cadena.
     */
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

    /** Método para crear y situar a los personajes dentro del mapa.
     * Aquí se termina la cadena, el siguiente paso es cerrar los lectores del patrón de laberinto.
     *
     * @param nivel Posición y tipos dependiendo del nivel en el que nos encontremos
     */
    private void situarPersonajes(int nivel) {
        fantasmasVivos.clear();    // |
        fantasmasComidos.clear();  // |_> Se limpian los ArrayList en caso de haber otros fantasmas de niveles anteriores

        switch (nivel) { // Situamos a pacman y hacemos los fantasmas correspondientes al nivel en el que estemos
            case 1 -> {
                pacman = new Pacman(lienzo, teclado, this, new Posicion(6, 7));

                for (int i = 1 ; i <= FANTASMAS_POR_NIVEL ; i++) {
                    fantasmasVivos.add(new FantasmaComun(lienzo, this, i));
                }
            }

            case 2 -> {
                pacman = new Pacman(lienzo, teclado, this, new Posicion(12, 12));

                for (int i = 1 ; i <= FANTASMAS_POR_NIVEL ; i++) {
                    if (i == 3) fantasmasVivos.add(new FantasmaListo(lienzo, this, pacman.getPosicion(), i));
                    else fantasmasVivos.add(new FantasmaComun(lienzo, this, i));
                }
            }

            case 3 -> {
                pacman = new Pacman(lienzo, teclado, this, new Posicion(7, 7));

                for (int i = 1 ; i <= FANTASMAS_POR_NIVEL ; i++) {
                    if (i == 1) fantasmasVivos.add(new FantasmaComun(lienzo, this));
                    else fantasmasVivos.add(new FantasmaListo(lienzo, this, pacman.getPosicion(), i));
                }
            }
        }

        if (mapa.hayPunto(pacman.getPosicion())) mapa.retirarPunto(pacman.getPosicion()); // Quitamos el punto que ocupa pacman en el mapa

        fantasmaLiberarPosiciones();
    }

    /** Método para liberar las posiciones de spawn de los fantasmas.
     * Se cogen los fantasmas uno a uno para liberar la posición que han usado para hacer spawn.
     */
    private void fantasmaLiberarPosiciones() {
        if (!fantasmasVivos.isEmpty()) {
            for (Fantasma fantasma : fantasmasVivos) {
                fantasma.liberarPosicion(fantasma.getPosicionInicial());
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

    /** Tick.
     * Pasos:
     *  - Pacman se mueve
     *  - Si donde está pacman hay un punto se quita y le aumentamos la puntuación a pacman
     *  - Los fantasmas hacen tick
     *  - Se mira el tiempo para ver si se tiene que generar el power up para pacman
     *  - Estado hace tick
     *  - Se revisa que el jugador haya ganado el juego completo
     *
     * @param tiempoEnPartida Momento en el que se encuentra el juego.
     * @throws PacmanComidoException Excepción para saber si el jugador ha sido comido.
     * @throws SalirDelJuegoException Excepción para saber si el jugador ha salido del juego.
     * @throws JugadorGanoJuegoException Excepción para saber si el jugador ha ganado el juego.
     * @see #fantasmasTick(int) Para ver como hacemos tick a los fantasmas.
     * @see #generarPwrUp(int, int) Para ver como ver si generamos el power up.
     */
    public void tick(int tiempoEnPartida) throws PacmanComidoException, SalirDelJuegoException, JugadorGanoJuegoException {
        pacman.tick();

        if (mapa.hayPunto(pacman.getPosicion())) {
            estado.incrementarPuntuacion();
            mapa.retirarPunto(pacman.getPosicion());
        }

        fantasmasTick(tiempoEnPartida);

        generarPwrUp(nivelActual, tiempoEnPartida);

        estado.tick(tiempoEnPartida);

        if (nivelActual == 3 && estado.todosPuntosComidos()) throw new JugadorGanoJuegoException();
    }

    /** Método para hacer tick a los fantasmas.
     * Pasos:
     *  - Revisamos si hay que revivir a algún fantasma.
     *  - Miramos en el ArrayList de los fantasmas vivos para hacer tick a cada uno.
     *  - Si pacman es invencible, debilitamos al fantasma y si colisiona con pacman, se dice que está comido.
     *  - Si pacman no es invencible, fortalecemos al fantasma y si colisiona con pacman, se dice que pacman ha sido comido.
     *
     * @param tiempoTranscurrido Parámetro para ver si hay que revivir a algún fantasma.
     * @throws SalirDelJuegoException Excepción para saber si el jugador ha salido del juego.
     * @see #pacmanComeFantasma(Fantasma) <-- Para ver como se hace que pacman se coma al fantasma
     * @see #revivirFantasmasComidos(int) <-- Para ver como se revisa si hay que revivir a algún fantasma.
     */
    private void fantasmasTick(int tiempoTranscurrido) throws SalirDelJuegoException {
        revivirFantasmasComidos(tiempoTranscurrido);

        for (int fantasmaActual = 0 ; fantasmaActual < fantasmasVivos.size() ; fantasmaActual++) {
            Fantasma fantasma = fantasmasVivos.get(fantasmaActual);

            if (estado.pacmanInvencible()) {
                fantasma.debilitar();

                if (verificarIntercambio(fantasma)) {
                    pacmanComeFantasma(fantasma);
                    continue;
                }

                fantasma.tick();

                if (verificarIntercambio(fantasma)) pacmanComeFantasma(fantasma);

            } else {
                fantasma.fortalecer();

                if (verificarIntercambio(fantasma)) throw new PacmanComidoException();

                fantasma.tick();

                if (verificarIntercambio(fantasma)) throw new PacmanComidoException();
            }
        }
    }

    /** Método para revisar si hay que revivir a algún fantasma.
     * Pasos:
     * - Primero se revisa que pacman deja de ser invencible o que la lista de los fantasmas comidos esté vacía.
     * - Si no se da ninguna de las dos, recorremos el ArrayList de los fantasmas comidos.
     * - Seleccionamos al fantasma y se calcula la diferencia entre el tiempo que hay de partida menos el momento en el que pacman deja de ser invencible.
     * - Si el tiempo es mayor o igual a la duración que tiene ese fantasma para volver a hacer spawn, se le traslada al ArrayList de los fantasmas vivos y se le revive.
     *
     * @param tiempoTranscurrido Tiempo que ha pasado desde el inicio del juego.
     * @see Fantasma#revivir() <-- Para ver como revivir al fantasma fantasma.
     */
    private void revivirFantasmasComidos(int tiempoTranscurrido) {
        if (estado.pacmanInvencible() || fantasmasComidos.isEmpty()) return;

        for (int fantasmaActual = 0 ; fantasmaActual < fantasmasComidos.size() ; fantasmaActual++) {
            Fantasma fantasmaComido = fantasmasComidos.get(fantasmaActual);

            if (tiempoTranscurrido - estado.getInvencibilidadAcaba() >= fantasmaComido.getDuracionComido()) {
                fantasmasComidos.remove(fantasmaComido);
                fantasmaComido.revivir();
                fantasmasVivos.add(fantasmaComido);
            }
        }
    }


    /** Método para que pacman se "coma" a fantasma
     * Para no eliminar al objeto fantasma y luego crear otro nuevo, lo que se hace es que cuando pacman se come un fantasma, este es movido
     * a otro ArrayList, para que no se le tenga en cuenta, pero el objeto sigue existiendo. El fantasma que ha sido comido libera su posición
     * inicial y es trasladado al otro ArrayList
     *
     * @param fantasma Fantasma que ha sido comido.
     */
    private void pacmanComeFantasma(Fantasma fantasma) {
        fantasma.liberarPosicion(fantasma.getPosicionInicial());

        fantasmasComidos.add(fantasma);
        fantasmasVivos.remove(fantasma);
    }

    public boolean verificarIntercambio(Fantasma fantasma) {
        return fantasma.getPosicion().equals(pacman.getPosicion());
    }

    /** Método para generar el power up.
     * Pasos:
     *  - Se establece en que momento de la partida se quiere que aparezca el power up.
     *  - Cuando se llega a ese momento, se crea el power up posicionándose en el mapa, pero como el tiempo se repite varias veces por el Thread.sleep() del main, se posiciona varias veces a modo de "animación".
     *  - Una vez se supera el momento de aparición, el power up se queda en una posición fija, eliminando el punto en el que está (en caso de estarlo), esperando a ser comido por pacman.
     *
     * @param nivelActual Nivel en el que estamos para establecer el momento de aparición.
     * @param tiempoTranscurrido Tiempo que ha pasado desde el inicio del juego.
     */
    private void generarPwrUp(int nivelActual, int tiempoTranscurrido) {
        int momentoAparicion;

        if      (nivelActual == 1) momentoAparicion = 20;
        else if (nivelActual == 2) momentoAparicion = 15;
        else                       momentoAparicion = 10;

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