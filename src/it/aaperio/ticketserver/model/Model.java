package it.aaperio.ticketserver.model;


import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;

import it.aaperio.ticketserver.ClientConnection;
import it.aaperio.ticketserver.Configuration;

public class  Model {

	private static Model model = null;
	
	static private Logger logger;
	private Configuration config;
	private Queue<Messaggio> codamsginput = new ConcurrentLinkedQueue<> () ; // Coda dei messaggi ricevuti
	private Set<ClientConnection> clientConnessi ;				// Elenco dei client connessi 
	// elenco delle connessioni attive
	// tutti i ticket in stato aperto
	
	
	private  Model() {
		logger = Logger.getLogger(Model.class);
		logger.info("Inizializzazione del Model");
		codamsginput = new ConcurrentLinkedQueue<> ();
		clientConnessi = new HashSet<ClientConnection>();
		Model.inizialization();
	} 		
	
	// Costruttore Singleton
	public static Model getModel() {
		Model m;
		
		if (model == null) {
			m = new Model();
			model = m;
			
		} else m = model;
		
		return m;
	}
	
	private static void inizialization() {
		// Inserire l'inizializzazione di tutti i dati
		
		
		// Caricamento del Database e preparazione della struttura dati
		
		// Preparazione del pool di connesioni
		
	}

	public Configuration getConfig() {
		return config;
	}

	public void setConfig(Configuration config) {
		this.config = config;
	}

	/**
	 * Aggiunge alla coda il messaggio ricevuto 
	 * @param msg: MEssaggio ricevuto da inserire
	 */
	public void addMsgToQueue (Messaggio msg) {
		this.codamsginput.add(msg);
		logger.info("Aggiunto alla coda messaggi il messaggio " + msg); 
	}

	public Set<ClientConnection> getClientConnessi() {
		return clientConnessi;
	}

	public void setClientConnessi(Set<ClientConnection> clientConnessi) {
		this.clientConnessi = clientConnessi;
	}

	/**
	 * Aggiunge un client all'insieme dei client connessi
	 * @param c: Client da aggiungere
	 */
	public void addClientConnessi(ClientConnection c) {
		this.clientConnessi.add(c);
	}
	
	/**
	 * Rimuove un client dall'insieme dei client connessi
	 * @param c: Client da rimuovere
	 */
	public void removeClientConnessi(ClientConnection c) {
		this.clientConnessi.remove(c);
	}
}
