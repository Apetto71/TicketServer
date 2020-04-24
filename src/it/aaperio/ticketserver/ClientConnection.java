package it.aaperio.ticketserver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import org.apache.log4j.Logger;
import it.aaperio.ticketserver.model.*;

public class ClientConnection extends Thread {
	
	private Thread ct = Thread.currentThread(); 	// Riferimento al Thread creato per la singola connessione client
	private Model model;								// Riferimento al Model
	private Socket sock;							//Socket per la connessione al client
	private ObjectInputStream in;					//Buffer per leggere sullo stream
	private ObjectOutputStream out;					//Buffer per scrivere sullo stream
	private boolean connected = false;
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
			
		// creazione stream di output su clientSocket
        try {
			logger.debug("Creo i buffer di scrittura su socket");
			out = new ObjectOutputStream(sock.getOutputStream());
			out.flush();
		} catch (IOException e) {
			logger.error("Errore inizializzazione Stream Writer", e);
			this.connected = false;
		}
  
		// creazione stream di input da clientSocket
	    try {
			logger.debug("creo il buffer di ricezione sul socket");
			in = new ObjectInputStream(sock.getInputStream());
			this.connected = true;
		} catch (IOException e) {
			logger.error("Errore inizializzazione Stream Reader", e);
			this.connected = false;
		}
	        
	        if (this.connected) {
	        	logger.info("La connessione con il client � attiva");
	        } else { logger.debug("La connessione non � andata a buon fine");}
	        // Devo ottenere dal sistema il numero di sessione, da inviare al client per poi attendere da lui ulteriori richieste
	        
	        
	        // Mi metto in ascolto per la ricezione dei messaggi
	        while (connected) {
	        	String msgrcv = new String();
	        	try {
					msgrcv = (String) this.in.readObject();
					logger.info("messaggio ricevuto: " + msgrcv);
				} catch (ClassNotFoundException | IOException e) {
					logger.error("Errore in lettura sullo stream", e);
					closeSock();;
					} 
	        	// Ho ricevuto un messaggio quindi lo metto in coda ai messaggi da lavorare
	        	if (msgrcv == "CLOSE") {
	        		logger.info("Ricevuta richiesta di chiusura dal client");
	        		closeSock();
	        		model.addMsgToQueue(msgrcv);
	        	}
	        	
	        }
	        // Mi predispongo a ricevere un messaggio per ora sotto forma di stringa dal client connesso

	}     
	
	public void closeSock() {
		try {
			logger.info("La connessione � stata terminata, chiudo la socket");
			this.sock.close();
			this.connected = false;
		} catch (IOException e) {
			logger.error("Non riesco a chiudere la socket");
		}
	}
}