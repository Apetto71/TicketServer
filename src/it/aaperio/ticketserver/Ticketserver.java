package it.aaperio.ticketserver;

import it.aaperio.ticketserver.model.*;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Ticketserver {

	public static void main(String[] args) throws IOException {
		
		ServerSocket serverSocket;
		Socket clientSocket;
		
		Configuration config = Configuration.getConfiguration();
		Logger logger = Logger.getLogger(Ticketserver.class);
		
		
		// Preparo le socket per accettare connessioni dai client
		logger.info("Preparo la ricezione di connessioni dai client su porta TCP: " + config.getPORTA());
		serverSocket = new ServerSocket(Integer.parseInt(config.getPORTA()));
		clientSocket = new Socket();
		
		
		// Configuro il model  
		logger.debug("Instanzio la classe Model per la gestione dei dati");
		Model m = Model.getModel();
		m.setConfig(config);
		
		logger.info("Mi metto in ascolto per accettare nuove connessioni sulla porta " + config.getPORTA());
		while (true) {
			try {
				clientSocket = serverSocket.accept();
			} catch (IOException e) {
				logger.error("Errore Input/Output nell'accettazione della connessione", e);
			} catch (Exception e) {
				logger.error("Errore nella mettersi in ascolto sulla porta, errore del metodo accept", e);
			} 
			logger.info("Connessione accettata da: " + clientSocket.getInetAddress());
			/*ExecutorService client = Executors.newCachedThreadPool() ; 
			client.submit(new ClientConnection(clientSocket)) ;
			logger.info ("Esecuzione del thread di gestione del client: ");*/
			
			ClientConnection client = new ClientConnection(clientSocket);
			client.setModel(m);
		    client.start();
		}
		
		 
	}

}
