package it.aaperio.ticketserver;

import it.aaperio.ticketserver.model.*;
import java.util.Properties;

public class Ticketserver {

	public static void main(String[] args) {
		
		//Imposto parametri di configuarazione di default
		  Properties sys_prop =  System.getProperties();
		  final String FILE_SEPARATOR = sys_prop.getProperty("file.separator");
		  final String LINE_SEPARATOR = sys_prop.getProperty("line.separator");
		  final String OS_NAME = sys_prop.getProperty("os.name");
		  final String OS_VERSION = sys_prop.getProperty("os.version");
		  final String USER_DIR = sys_prop.getProperty("user.dir");
		  final String USER_HOME = sys_prop.getProperty("user.home");
		  

		  
		Model m = Model.getModel();
		System.out.println(m);
			
		
	}

}
