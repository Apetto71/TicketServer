package it.aaperio.ticketserver.model;

/**
 * Oggetto che contiene i comandi che client e server possono scambiarsi
 * Non ha metodi, soltanto un tipo enum
 * @author alessandroa
 *
 */


	public enum Comandi {
		CONNECT,
		DISCONNECT,
		USER,
		POPUP,
		AGGIORNA_LISTA_TICKET,
		RICERCA
	};
	
	
	

