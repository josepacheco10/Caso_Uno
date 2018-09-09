package casoUno;

public class Mensaje {

	//--------------------------
	// Atributos 
	//--------------------------
	
	/**
	 * Numero que representa el contenido del mensaje
	 */
	private int mensaje;
	
	/**
	 * Identificador del mensaje asociado al cliente.
	 */
	private int identificadorMensaje;
	
	//--------------------------
	// Constructor 
	//--------------------------
	
	public Mensaje (int pMensaje,  int pID){
		mensaje = pMensaje;
		identificadorMensaje = pID;
	}
	
	//--------------------------
	// Metodos 
	//--------------------------

	public int getMensaje() {
		return mensaje;
	}

	public void setMensaje(int mensaje) {
		this.mensaje = mensaje;
	}

	public int getIdentificadorMensaje() {
		return identificadorMensaje;
	}

	public void setIdentificadorMensaje(int identificadorMensaje) {
		this.identificadorMensaje = identificadorMensaje;
	}	
}
