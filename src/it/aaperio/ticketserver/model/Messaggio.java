package it.aaperio.ticketserver.model;

import java.io.Serializable;
import java.util.UUID;

/**
 * Oggetto per trasferire messaggi ed informazioni da server a client
 * @author aaperio
 *
 */


public class Messaggio implements Serializable {

	private static final long serialVersionUID = 1L;
	private Comandi comando = null;
	private UUID sessionId = null;
	private Object parametro;
	
	
	
		
	/**
	 * Costruttore di un messaggio vuoto.
	 */
	public Messaggio() {
		super();
		// TODO Auto-generated constructor stub
	}


	/**
	 * Construttore di un nuovo messaggio specificando il sessionId
	 * @param sessionId: ID della sessione 
	 */

	public Messaggio(UUID s) {
		super();
		this.sessionId = s;
		this.comando = Comandi.CONNECT ;
	}

	
	/**
	 * Costruttore di un nuovo messaggio specificando sessiomId e comando da eseguire
	 * @param comando: Comando da eseguire
	 * @param sessionId: Sessione a cui � destinato o da cui proviene il comando 
	 */
	public Messaggio(Comandi comando, UUID sessionId) {
		super();
		this.comando = comando;
		this.sessionId = sessionId;
	}


	public Messaggio(Comandi comando, Object parametro) {
		this.comando =  comando ;
		this.parametro = parametro ;
	}

	public Comandi getComando() {
		return comando;
	}



	public void setComando(Comandi comando) {
		this.comando = comando;
	}



	public UUID getSessionId() {
		return sessionId;
	}



	public void setSessionId(UUID sessionId) {
		this.sessionId = sessionId;
	}



	public Object getParametro() {
		return parametro;
	}



	public void setParametro(Object parametro) {
		this.parametro = parametro;
	}


	@Override
	public String toString() {
		return "Messaggio [sessionId=" + sessionId + ", comando=" + comando + ", parametro=" +  parametro + "]";
	}

	
	
}
