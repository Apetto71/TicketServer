package it.aaperio.ticketserver.model;


import java.util.concurrent.ConcurrentLinkedDeque;

import org.apache.log4j.Logger;
import it.aaperio.ticketserver.Configuration;

public class  Model {

	private static Model model = null;
	
	static private Logger logger;
	private Configuration config;
	private ConcurrentLinkedDeque<String> codamsginput;
	
	// Elenco delle sessioni attive
	// elenco delle connessioni attive
	// tutti i ticket in stato aperto
	
	
	private  Model() {
		logger = Logger.getLogger(Model.class);
		logger.info("Inizializzazione del Model");
		codamsginput = new ConcurrentLinkedDeque ();
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

	public void addMsgToQueue (String msg) {
		this.codamsginput.add(msg);
		logger.info("Aggiunto alla coda messaggi il messaggio " + msg); 
	}

}
