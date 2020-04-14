package it.aaperio.ticketserver.model;


import org.apache.log4j.Logger;
import it.aaperio.ticketserver.Configuration;

public class  Model {

	private static Model model = null;
	
	static private Logger logger;
	private Configuration config;
	// Elenco delle sessioni attive
	// elenco delle connessioni attive
	// tutti i ticket in stato aperto
	
	
	
	private  Model() {} 		// costruttore vuoto
	
	
	// Costruttore Singleton
	public static Model getModel() {
		Model m;
		
		if (model == null) {
			m = new Model();
			model = m;
			Model.inizialization();
		} else m = model;
		
		return m;
	}
	
	private static void inizialization() {
		// Inserire l'inizializzazione di tutti i dati
		logger = Logger.getLogger(Model.class);
		logger.info("Inizializzazione del Model");
		
		// Caricamento del Database e preparazione della struttura dati
		
		// Preparazione del pool di connesioni
		
	}


	public Configuration getConfig() {
		return config;
	}


	public void setConfig(Configuration config) {
		this.config = config;
	}


}
