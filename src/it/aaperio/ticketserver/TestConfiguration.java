package it.aaperio.ticketserver;

public class TestConfiguration {

	Configuration config ;
	
	public static void main(String[] args) {
		TestConfiguration test = new TestConfiguration();
		test.Run(); 
	}

	private void Run() {
		config = Configuration.getConfiguration() ;
		System.out.println(config.getNUM_THREAD_PROTOCOL());
		
	}
}
