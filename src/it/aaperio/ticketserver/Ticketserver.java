package it.aaperio.ticketserver;

import it.aaperio.ticketserver.model.*;
import org.apache.log4j.Logger;

public class Ticketserver {

	public static void main(String[] args) {
		
		Configuration config = Configuration.getConfiguration();
		Logger logger = Logger.getLogger(Ticketserver.class);
				
		// Configuro il model  
		logger.debug("Instanzio la classe Model per la gestione dei dati");
		Model m = Model.getModel();
		m.setConfig(config);
	}

}
