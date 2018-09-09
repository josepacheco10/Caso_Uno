package casoUno;
import java.util.ArrayList;

public class Main {

	//------------------------------
	// Ejecutable
	//------------------------------
	
	public static void main(String[] args) {
		
		ArrayList<Cliente> users = new ArrayList<>();
		int totalClientes = 1;
		int totalMensajesCliente = 2;
		int bufferSize = 15;
		int numeroThreads = 40;
		
		// Buffer
		Buffer buffer = new Buffer(bufferSize, totalClientes);
		
		// Clientes
		for (int i = 0; i < totalClientes; i++){
			Cliente user = new Cliente(totalMensajesCliente, i, buffer);
			users.add(user);
		}
		
		// Servidor 
		Servidor server = new Servidor(buffer, numeroThreads);
		
		for(Cliente cliente: users) {
			cliente.start();
		}
		
		// Sr. Star(t)k
		server.start();
		
	}
}