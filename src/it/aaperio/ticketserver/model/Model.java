package it.aaperio.ticketserver.model;


import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import it.aaperio.ticketserver.ClientConnection;
import it.aaperio.ticketserver.Configuration;

public class  Model {

	private static Model model = null;
	
	static private Logger logger;
	private Configuration config;
	private Queue<Messaggio> codamsginput = new LinkedBlockingQueue<> () ; // Coda dei messaggi ricevuti
	private Set<ClientConnection> clientConnessi ;				// Elenco dei client connessi 
	private ExecutorService protocol ;	
	
	private  Model() {
		
		
	} 		
	
	// Costruttore Singleton
	public static Model getModel() {
		Model m;
		
		if (model == null) {
			m = new Model();
			model = m;
			m.inizialization();
		} else m = model;
		
		return m;
	}
	
	private void inizialization() {
		logger = Logger.getLogger(Model.class);
		logger.info("Inizializzazione del Model");
		clientConnessi = new HashSet<ClientConnection>();
		protocol = Executors.newFixedThreadPool(Integer.parseInt(config.getNUM_THREAD_PROTOCOL())) ;
		for (int i = 0; i < Integer.parseInt(config.getNUM_THREAD_PROTOCOL()); i++) {
			protocol.submit(new Protocollo()) ;
		}
		
		
		
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
	
	/**
	 * Funzione che prende un elemento dalla coda e lo ritorna
	 * @return Messaggio prelevato dalla coda
	 */
	public Messaggio pollMsgFromQueue () {
		Messaggio m = new Messaggio() ;
		
		try {
			m = ((LinkedBlockingQueue<Messaggio>) this.codamsginput).take();
		} catch (InterruptedException e) {
			logger.error("Errore nell'accesso alla coda dei messaggi" + e) ;
		} 
		return m ;
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
		logger.info ("Aggiungo il client al set dei client connessi") ;
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
