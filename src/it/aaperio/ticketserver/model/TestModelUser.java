package it.aaperio.ticketserver.model;



import it.aaperio.ticketserver.Configuration;

public class TestModelUser {

	Model m ;
	Configuration c; 
	
	public void run() {
		c = Configuration.getConfiguration() ;
		m = Model.getModel() ;
		
		
		User u1 = new User( "May", "Papa",  "mpapa", "mpapa" , "mary.papa@area.it") ;
		//User u2 = new User("Luca", "Radrizzani",  "lradrizzani", "lradrizzani" , "luca.radrizzani@area.it") ;
		//User u3 = new User("Alessandro", "Aperio",  "aaperio", "aaperio" , "alessandro.aperio@area.it") ;
		m.creauser(u1) ;
		//m.creauser(u2) ;
		//m.creauser(u3) ;
		
		for (String s : m.getUserMap().keySet()) {
			System.out.println(m.getUserMap().get(s).toString());
		}
		
		/*for (String s : m.getUserMap().keySet()) {
			m.setNewPassword(m.getUserMap().get(s), m.getUserMap().get(s).getUsername());
		}
		
		for (String s : m.getUserMap().keySet()) {
			System.out.println(m.getUserMap().get(s).toString());
		}*/
		
		
	}
	
	
	public static void main(String[] args) {
		TestModelUser test = new TestModelUser() ;
		test.run() ;

	}

}
