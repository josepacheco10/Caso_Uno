package casoUno;

public class Cliente extends Thread{

	//--------------------------
	// Atributos 
	//--------------------------
	
	/**
	 * Numero de mensajes enviados por el cliente.
	 */
	private int mensajesEnviados;
	
	/**
	 * Identificador del clientes.
	 */
	private int id;
	
	/**
	 * Buffer de la aplicacion
	 */
	private Buffer buffer;
	
	/**
	 * Mensajes respondidos por el servidor
	 */
	private Mensaje[] mensajesConRespuesta;
	
	
	//--------------------------
	// Constructor 
	//--------------------------
	
	public Cliente (int pMensajes, int pID, Buffer pBuff){
		mensajesEnviados = pMensajes;
		id = pID;
		buffer = pBuff;
		mensajesConRespuesta = new Mensaje[mensajesEnviados];
	}
	
	//--------------------------
	// Metodos
	//--------------------------
	
	@SuppressWarnings("static-access")
	public synchronized boolean consultar(){
		int numeroMensaje = (int) Math.floor(Math.random() * 100) + 1  ;
		Mensaje newMensaje = new Mensaje(numeroMensaje, id);
		System.out.println("Mensaje entrada: " + newMensaje.getMensaje());
		
		boolean esPosibleAlmacenar = buffer.almacenarMensajeCliente(newMensaje);
		
		if (esPosibleAlmacenar){
			synchronized(newMensaje){
				buffer.almacenarMensajeCliente(newMensaje);
				try {newMensaje.wait();} 
				catch (InterruptedException e) {e.printStackTrace();}
			}
			
			almacenarRespueta(newMensaje);
			System.out.println("Mensaje salida: " + newMensaje.getMensaje());
			System.out.println("-------------------");
			return true;
		}
		
		else {this.yield(); return false;}
	}
	
	public synchronized void almacenarRespueta (Mensaje mensaje) {
		for(int i=0;i<mensajesConRespuesta.length;i++) {
			if(mensajesConRespuesta[i]==null) {
				mensajesConRespuesta[i]=mensaje;
				break;
			}
		}
	}
	
	public void retirarCliente(){
		buffer.eliminarCliente();
	}
	
	public void run(){
		while (mensajesEnviados > 0){
			if (consultar()){
				mensajesEnviados--;
			}
		}
		retirarCliente();
	}
	
	//--------------------------
	// Getter - Setter 
	//--------------------------
	
	public int getMensajesEnviados() {
		return mensajesEnviados;
	}

	public void setMensajesEnviados(int mensajesEnviados) {
		this.mensajesEnviados = mensajesEnviados;
	}

	public int getID() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Buffer getBuffer() {
		return buffer;
	}

	public void setBuffer(Buffer buffer) {
		this.buffer = buffer;
	}
}