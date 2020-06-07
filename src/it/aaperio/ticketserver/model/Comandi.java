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
		CLOSEBYSRV,
		USER,
		POPUP,
		AGGIORNA_LISTA_TICKET,
		RICERCA,
		AUTORIZZATO,
		NON_AUTORIZZATO,
	};
	
	
	

