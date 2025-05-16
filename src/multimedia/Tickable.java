package multimedia;

import juego.excepciones.SalirDelJuegoException;

public interface Tickable {
	public void tick() throws SalirDelJuegoException;
}