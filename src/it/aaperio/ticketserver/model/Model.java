package it.aaperio.ticketserver.model;



public class  Model {

	private static Model model = null;
	// Elenco delle sessioni attive
	// elenco delle connessioni attive
	// tutti i ticket in stato aperto
	
	
	
	private  Model() {} 		// costruttore
	
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
		
	}
}
