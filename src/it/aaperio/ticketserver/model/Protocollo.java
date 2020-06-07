/**
 * Thread indipendente che prende un nuovo messaggio dalla coda, lo elabora ed esegue la richiesta del client
 * Nel caso sia necessaria una risposta la invia
 * 
 */
package it.aaperio.ticketserver.model;

import org.apache.log4j.Logger;

/**
 * @author aaperio 
 * Gestisce le richieste arrivate dai client una alla volta, analizzandone il contenuto 
 * e provvede a rispondere al client 
 * se la richiesta conporta il salvataggio di una modifica, si occupa di comunicare 
 * la modifica a tutti i client
 */
public class Protocollo extends Thread {
	
	Logger logger = Logger.getLogger(Protocollo.class) ;
	Messaggio msg = new Messaggio()	;
	Model m = Model.getModel()  ; 
	private static int protocolNumber = 0 ;
	private boolean toClose = false ;
	
	

	/**
	 * 
	 */
	public Protocollo() {
		protocolNumber = protocolNumber +1 ;
		logger.debug("Apro il Thread Protocollo: " + this.currentThread() + " - Numero: " + protocolNumber); 
	}

	public void run() {
		
		while (!toClose) {
			msg = m.pollMsgFromQueue() ;
			logger.debug("Prelevato dalla coda nuovo messaggio: " + msg.toString()) ;
			gestisciMsg();
		}
	}

	/**
	 * 
	 */
	private void gestisciMsg() {
		switch (this.msg.getComando()) {
		case AGGIORNA_LISTA_TICKET:
			break;
		case CONNECT:
			break;
		case DISCONNECT:
			break;
		case POPUP:
			break;
		case RICERCA:
			break;
		case USER:
			System.out.println(msg.getParametro().getClass());
			User u = new User( (User) msg.getParametro()) ; 
			Messaggio msgToSend = new Messaggio(msg.getSessionId()) ;
			logger.info ("Ricevuto nuovo User: " + u.toString()) ;
			logger.debug("Cerco l'utente nella mappa: " + m.getUserMap().containsKey(u.getUsername())) ;
			if (m.getUserMap().containsKey(u.getUsername())) {
				logger.debug("Ho trovato l'utente nella mappa di utenti") ;
				if (m.getUserMap().get(u.getUsername()).getPassword().equals(u.getPassword())) {
					logger.debug("La password corrisponde quindi è autorizzato: password in memoria " + m.getUserMap().get(u.getUsername()).getPassword() 
							+" - password ricevuta: " + u.getPassword()) ;
					msgToSend.setComando(Comandi.AUTORIZZATO);
					m.sendMsg(msgToSend, m.getMapOfClient().get(msg.getSessionId()));
				} else {
					logger.debug("La password utente non corrisponde, quindi NON AUTORIZZATO "  + m.getUserMap().get(u.getUsername()).getPassword() 
							+" - password ricevuta: " + u.getPassword()) ;
					msgToSend.setComando(Comandi.NON_AUTORIZZATO);
					m.sendMsg(msgToSend, m.getMapOfClient().get(msg.getSessionId()));
				}
			} else {
				logger.debug("Utente non trovato nella mappa utenti, quindi NON AUTORIZZATO") ;
				msgToSend.setComando(Comandi.NON_AUTORIZZATO);
				m.sendMsg(msgToSend, m.getMapOfClient().get(msg.getSessionId()));
			}
			break;
		default:
			break;
		}
	
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
	

	
}
