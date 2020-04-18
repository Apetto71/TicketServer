package it.aaperio.ticketserver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.apache.log4j.Logger;
import it.aaperio.ticketserver.model.*;

public class ClientConnection extends Thread {
	
	private Thread ct = Thread.currentThread(); 	// Riferimento al Thread creato per la singola connessione client
	private Model model;								// Riferimento al Model
	private Socket sock;							//Socket per la connessione al client
	private ObjectInputStream in;					//Buffer per leggere sullo stream
	private ObjectOutputStream out;					//Buffer per scrivere sullo stream
	private Logger logger = Logger.getLogger(ClientConnection.class);	// logger

	// Costruttore della classe con input il riferimento alla socket
	public ClientConnection (Socket clientSock) {
		
		//Salvo il valore ricevuto come argomento nella variabile di istanza della classe
		logger.info("Creo la socket id: " + clientSock);
		try {
			this.sock = clientSock;
			logger.debug("Creata la socket e salvata nella variabile di istanza sock: " + sock);
		} catch (Exception e) {
			logger.error(e);
		}
	}

	

	/** Pernette di settare il model a cui la classe fa riferimento 
	 * per chiamare metodi per l'accesso ai dati
	 * @param model the model to set
	 */
	public void setModel(Model m) {
		this.model = m;
	}
	
	//Scrivo il metodo run
	public void run() {
			// creazione stream di input da clientSocket
	        
			try {
				in = new ObjectInputStream(new BufferedInputStream(sock.getInputStream()));
			} catch (IOException e) {
				logger.error("Errore inizializzazione Stream Reader", e);
			}
	        
	        // creazione stream di output su clientSocket
	        try {
				logger.debug("Creo i buffer di ricezione e scrittura su socket");
				out = new ObjectOutputStream(new BufferedOutputStream(sock.getOutputStream()));
			} catch (IOException e) {
				logger.error("Errore inizializzazione Stream Writer");
				logger.error(e);
			}
	        
	        // Devo ottenere dal sistema il numero di sessione, da inviare al client per poi attendere da lui ulteriori richieste
	        
	        
	        // Invio al client collegato un messaggio di benvenuto
	        try	{
	        	out.writeBytes("Welcom to the Hotel California, your IP is: " + sock.getInetAddress());
	        } catch (IOException e) {
	        	
	        }
	        
	        // Mi predispongo a ricevere un messaggio per ora sotto forma di stringa dal client connesso
	        
	}     
}
