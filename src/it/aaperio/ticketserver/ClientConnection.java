package it.aaperio.ticketserver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.time.LocalDate;
import java.util.UUID;

import org.apache.log4j.Logger;
import it.aaperio.ticketserver.model.*;

public class ClientConnection extends Thread {
	
	private Thread ct = Thread.currentThread(); 	// Riferimento al Thread creato per la singola connessione client
	private Model model;								// Riferimento al Model
	private Socket sock;							// Socket per la connessione al client
	private UUID sessionId; 						// ID univoco di sessione
	private ObjectInputStream in;					// Buffer per leggere sullo stream
	private ObjectOutputStream out;					// Buffer per scrivere sullo stream
	private boolean connected = false;
	private Logger logger = Logger.getLogger(ClientConnection.class);	// logger
	private boolean toClose = false ; 

	// Costruttore della classe con input il riferimento alla socket
	public ClientConnection (Socket clientSock) {
		
		//Salvo il valore ricevuto come argomento nella variabile di istanza della classe
		logger.info("Creo la socket id: " + clientSock);
		try {
			this.sock = clientSock;
			logger.debug("Creata la socket e salvata nella variabile di istanza sock: " + sock);
			sessionId = new UUID(LocalDate.now().toEpochDay(), (long) System.nanoTime());
			logger.info("Creata SessionID: " +sessionId.toString() );
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
		
		// Aggiungo il client all'elenco dei client connessi sul model
		this.model.addClientConnessi(this);
		// creazione stream di output su clientSocket
        try {
			logger.debug("Creo i buffer di scrittura su socket");
			this.out = new ObjectOutputStream(sock.getOutputStream());
			this.out.flush();
		} catch (IOException e) {
			logger.error("Errore inizializzazione Stream Writer", e);
			this.connected = false;
		}
  
		// creazione stream di input da clientSocket
	    try {
			logger.debug("creo il buffer di ricezione sul socket");
			this.in = new ObjectInputStream(sock.getInputStream());
			this.connected = true;
		} catch (IOException e) {
			logger.error("Errore inizializzazione Stream Reader", e);
			this.connected = false;
		}
	        
	        if (this.connected) { 
	        	logger.info("La connessione con il client � attiva");
	        } else { logger.debug("La connessione non � andata a buon fine");}
	        
	        // Invio il numero di sessione al client
	        logger.info("Preparo il messaggio per inviare sessionId e lo invio");
	        Messaggio msgSessId = new Messaggio(this.sessionId);
	        try {
				out.writeObject(msgSessId);
				logger.debug("Inviato il sessionId: " + msgSessId.toString()) ;
			} catch (IOException e1) {
				logger.error("Errore nell'invio del sessionId", e1);
			}
	        
	        // Mi metto in ascolto per la ricezione dei messaggi
	        while (!toClose) {
	        	Messaggio msgrcv = new Messaggio();
	        	try {
						msgrcv = (Messaggio) this.in.readObject();
					} catch (IOException e) {
						logger.error("Lettura dallo Streamin errore", e);
						this.setToClose(true) ;
						closeSock();
					} catch (ClassNotFoundException e) {
						closeSock() ; 
						this.setToClose(true) ;
						logger.error("Errore in ricezione dallo Stream", e) ;
					} 
					logger.info("messaggio ricevuto: " + msgrcv) ;
				 
	        	// Verifica che il sessionId sia coerente con la connessione e solo in questo caso aggiungo
				//  alla coda delle attivit� da svolgere
				if (msgrcv.getSessionId().equals(this.sessionId)) {
					model.addMsgToQueue(msgrcv) ;
				} else {
					logger.error("Numero di sessione non corrispondente. Chiudo la connessione");
					this.closeSock();
					this.setToClose(true);
				}  
	        	}
	        	
	        logger.info ("SessionId " + sessionId.toString() + " terminata");
	        }
     
	
	public void closeSock() {
		try {
			logger.info("La connessione � stata terminata, chiudo la socket");
			this.sock.close();
			this.connected = false;
		} catch (IOException e) {
			logger.error("Non riesco a chiudere la socket");
		}
		
		// Se sono arrivato a chiudere la socket devo anche cancellare la connessione
		// dall'elenco di connessioni del model
		this.model.removeClientConnessi(this);
		
		
	}



	/**
	 * @return the toClose
	 */
	public boolean isToClose() {
		return toClose;
	}


	/**
	 * @param toClose the toClose to set
	 */
	public void setToClose(boolean toClose) {
		this.toClose = toClose;
	}



	/**
	 * @return the out
	 */
	public ObjectOutputStream getOut() {
		return out;
	}



	/**
	 * @param out the out to set
	 */
	public void setOut(ObjectOutputStream out) {
		this.out = out;
	}



	/**
	 * @return the sessionId
	 */
	public UUID getSessionId() {
		return sessionId;
	}



	/**
	 * @param sessionId the sessionId to set
	 */
	public void setSessionId(UUID sessionId) {
		this.sessionId = sessionId;
	}
	
	
}
