package it.aaperio.ticketserver.model;

import java.io.Serializable;

/**
 * Oggetto per trasferire messaggi ed informazioni da server a client
 * @author aaperio
 *
 */


public class Messaggio implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String messaggio = null;
	private String[] parametri = null;
	private Long sessionId = null;
	
	// Costruttore del messaggio vuoto
	public Messaggio() {}
	
	
	// Costruttore di messsaggio con tutti i parametri
	public Messaggio(Long sid, String m, String[] p) {
		this.sessionId = sid;
		this.messaggio = m;
		this.parametri = p;
	}


	/**
	 * @return the messaggio
	 */
	public String getMessaggio() {
		return messaggio;
	}


	/**
	 * @param messaggio the messaggio to set
	 */
	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}


	/**
	 * @return the parametri
	 */
	public String[] getParametri() {
		return parametri;
	}


	/**
	 * @param parametri the parametri to set
	 */
	public void setParametri(String[] parametri) {
		this.parametri = parametri;
	}


	/**
	 * @return the sessionId
	 */
	public Long getSessionId() {
		return sessionId;
	}


	/**
	 * @param sessionId the sessionId to set
	 */
	public void setSessionId(Long sessionId) {
		this.sessionId = sessionId;
	}
	
	
	
}
