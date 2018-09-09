package casoUno;
import java.util.ArrayList;

public class Servidor extends Thread {

	//--------------------------------
	// Atributos
	//--------------------------------
	
	/**
	 * Buffer de la aplicacion
	 */
	private Buffer buffer;
	
	/**
	 * Lista de Threads
	 */
	private ArrayList<SubThread> threads;
	
	/**
	 * Numero de Threads en servidor
	 */
	private int numThreads;
	
	
	//--------------------------------
	// Constructor
	//-------------------------------- 
	
	public Servidor(Buffer pBuff, int pNumTh){
		buffer = pBuff;
		numThreads = pNumTh;
		threads = new ArrayList<>();
	}
	
	//--------------------------------
	// Metodos
	//-------------------------------- 
	
	public void run (){
		while (numThreads > 0){
			SubThread thread = new SubThread();
			threads.add(thread);
			numThreads--;
		}
	}
	
	public boolean apagarServidor(){
		return buffer.getOp();
	}
	
	public int getNumThreads() {
		return numThreads;
	}

	public void setNumThreads(int numThreads) {
		this.numThreads = numThreads;
	}

	//--------------------------------
	// Threads asociados al servidor
	//--------------------------------
	public class SubThread extends Thread{
		
		public SubThread() {
			start();
		}
		
		@SuppressWarnings("static-access")
		public void run (){
			while(!apagarServidor()){
				Mensaje aResponder = obtenerMensaje();
				responderMensaje(aResponder);
			}
			this.yield();
		}
		
		public synchronized Mensaje obtenerMensaje(){
			Mensaje obtenido = buffer.cederMensajeServidor();
			if ( obtenido != null){
				return obtenido;
			}
			return null;
		}
		
		public synchronized void responderMensaje(Mensaje pMensaje){
			if (pMensaje != null){
				pMensaje.setMensaje(pMensaje.getMensaje() + 1500);
				buffer.almacenarMensajeServidor(pMensaje);
			}
		}
	}
	//--------------------------------
	// Fin Nested Class
	//--------------------------------
}