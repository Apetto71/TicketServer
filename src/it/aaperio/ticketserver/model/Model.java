package it.aaperio.ticketserver.model;


import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import it.aaperio.ticketserver.ClientConnection;
import it.aaperio.ticketserver.Configuration;
import it.aaperio.ticketserver.db.DbUserGroup;

public class  Model {

	private static final UUID String = null;

	private static Model model = null;
	
	static private Logger logger;
	private Configuration config;
	private Queue<Messaggio> codamsginput = new LinkedBlockingQueue<> () ; 	// Coda dei messaggi ricevuti
	private Set<ClientConnection> clientConnessi ;							// Elenco dei client connessi 
	private Map<UUID, ClientConnection> mapOfClient = new HashMap<UUID, ClientConnection>() ;
	private ExecutorService protocol ;	
	private Map<String, User> userMap ;
	private DbUserGroup userGroup = new DbUserGroup () ;
	
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
		
		getUserGroup () ;
		logger.debug("inizializzo la lista utenti") ;
		
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
		this.mapOfClient.put(c.getSessionId(), c) ;
	}
	
	/**
	 * Rimuove un client dall'insieme dei client connessi
	 * @param c: Client da rimuovere
	 */
	public void removeClientConnessi(ClientConnection c) {
		this.clientConnessi.remove(c);
	}
	
	/**
	 * Invio di un messaggio ad un client. Il metodo completa il messaggio con la corretta sessionId
	 * @param m Messaggio da inviare
	 * @param c Client a cui inviare il messaggio
	 */
	public void sendMsg(Messaggio m, ClientConnection c) {
		m.setSessionId(c.getSessionId()) ;
		try {
			c.getOut().writeObject(m) ;
		} catch (IOException e) {
			logger.error("Errore in scrittura sulla socket", e) ;
		}
	}
	
	public void closeServer() {
		// Chiudo tutte le connessioni con tutti i client
		Messaggio m = new Messaggio(Comandi.CLOSEBYSRV) ;
		for (ClientConnection c: clientConnessi) {
			sendMsg(m, c) ;
		}
	}
	
	private void getUserGroup () {
		
		try {
			this.userMap = userGroup.listaUtenti() ;
		} catch (RuntimeException e) {
			logger.error ("Errore nell'esecuzione della query", e) ;
		}
		 
	}

	/**
	 * @return the userMap
	 */
	public Map<String, User> getUserMap() {
		return userMap;
	}

	/**
	 * @param userMap the userMap to set
	 */
	public void setUserMap(Map<String, User> userMap) {
		this.userMap = userMap;
	}
	
	
	
	/**
	 * @return the mapOfClient
	 */
	public Map<UUID, ClientConnection> getMapOfClient() {
		return mapOfClient;
	}

	

	public int creauser (User u) {
		int c = 0 ;
		try {
			c = userGroup.CreateUser(u) ;
		}	catch (RuntimeException e) {
			logger.error ("Utente non salvato su Db" ,e) ;
		}
		
		return c ;
	}
	
	public void setNewPassword (User u, String p) {
		u.changePsw(p);
		userGroup.changPassword(u, p);
	}
	
}
