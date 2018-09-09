package casoUno;

public class Buffer {

	//--------------------------
	// Atributos 
	//--------------------------
	
	/**
	 * Clientes totales
	 */
	private int clientesT;
	
	/**
	 * Capacidad maxima del Buffer
	 */
	private int capacidadMaxima;
	
	/**
	 * Mensajes almacenados
	 */
	private Mensaje[] mensajesAlmacenados;
		
	/**
	 * Booleano para indicar si se acabo la operacion
	 */
	private boolean finOp;
	

	//--------------------------
	// Constructor 
	//--------------------------
	
	public Buffer (int pCapacidadMax, int pNumeroClientes) {
		capacidadMaxima = pCapacidadMax;
		mensajesAlmacenados = new Mensaje [capacidadMaxima];
		finOp = false;
		clientesT = pNumeroClientes;
	}

	
	//--------------------------
	// Metodos 
	//--------------------------
	
	public synchronized boolean almacenarMensajeCliente (Mensaje pMensaje){
		boolean seAlmaceno = false;
		for (int i = 0; i < mensajesAlmacenados.length && !seAlmaceno; i++){
			if (mensajesAlmacenados[i] == null){
				mensajesAlmacenados[i] = pMensaje;
				seAlmaceno = true;
			}
		}
		return seAlmaceno;
	}
	
	/**
	 * Metodo para almacenar los mensajes respuesta del servidor.
	 * Tambien se encarga de despertar al objeto mensaje.
	 * @param mensaje Mensaje que se desea almacenar.
	 * @return Boolean. Si el mensaje se almaceno dentro del Buffer o no.
	 */
	public synchronized boolean almacenarMensajeServidor (Mensaje mensaje) {
		synchronized (mensaje) {mensaje.notifyAll();} return true;
	}
	
	/**
	 * Metodo para enviarle cualquier mensaje al servidor
	 * @return Mensaje. Mensaje que el servidor va a recibir del Buffer.
	 */
	public synchronized Mensaje cederMensajeServidor(){
		for (int i = 0; i < mensajesAlmacenados.length; i++){
			Mensaje temporal = mensajesAlmacenados[i];
			if (temporal != null){
				mensajesAlmacenados[i] = null;
				return temporal;
			}
		}
		return null;
	}
	
	/**
	 * Retirar a un cliente de los servicios del Buffer
	 */
	public void eliminarCliente(){
		clientesT--;
		if(clientesT <= 0){finalizarOP();}
	}
	
	//--------------------------
	// Getter - Setter
	//--------------------------
	
	public int getCapacidadMaxima() {
		return capacidadMaxima;
	}

	public void setCapacidadMaxima(int capacidadMaxima) {
		this.capacidadMaxima = capacidadMaxima;
	}
	
	public boolean getOp(){
		return finOp;
	}
	
	public void finalizarOP(){
		finOp = true;
	}
}