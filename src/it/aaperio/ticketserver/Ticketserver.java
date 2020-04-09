package it.aaperio.ticketserver;

import it.aaperio.ticketserver.model.*;
import java.util.Properties;
import java.io.*;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Ticketserver {

	public static void main(String[] args) {
		
				
		// Imposto parametri di configuarazione di default
		Properties sys_prop =  System.getProperties();
		final String FILE_SEPARATOR = sys_prop.getProperty("file.separator");
		final String LINE_SEPARATOR = sys_prop.getProperty("line.separator");
		final String OS_NAME = sys_prop.getProperty("os.name");
		final String OS_VERSION = sys_prop.getProperty("os.version");
		final String USER_DIR = sys_prop.getProperty("user.dir");
		final String USER_HOME = sys_prop.getProperty("user.home");
		
		// Verifico la presenza della directory e file di configurazione
		
		File work_dir = new File(USER_HOME + FILE_SEPARATOR + ".TicketServer");
		if (!work_dir.exists() && work_dir.isDirectory())		{
			work_dir.mkdir();
		}
		
		// Verifico che esista la configurazione del logger
		File log_file = new File (work_dir.toString() + FILE_SEPARATOR +"TKServerLog.cfg");
		if (!log_file.exists())	{
			try { 
				log_file.createNewFile();
				log_file.setWritable(true);
				FileWriter fw = new FileWriter (log_file.toString());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write("#log4j.properties\r\n" + 
						"#LOGGER\r\n" + 
						"log4j.rootLogger=INFO,  APPENDER_FILE \r\n" + 
						"#, APPENDER_OUT,\r\n" + 
						"#APPENDER_OUT\r\n" + 
						"//log4j.appender.APPENDER_OUT=org.apache.log4j.ConsoleAppender\r\n" + 
						"//log4j.appender.APPENDER_OUT.layout=org.apache.log4j.PatternLayout\r\n" + 
						"//log4j.appender.APPENDER_OUT.layout.ConversionPattern=%5p [%t] (%F:%L) - %m%n\r\n" + 
						"#APPENDER_FILE\r\n" + 
						"log4j.appender.APPENDER_FILE=org.apache.log4j.RollingFileAppender\r\n" + 
						"log4j.appender.APPENDER_FILE.File=TKServer.log\r\n" + 
						"log4j.appender.APPENDER_FILE.MaxFileSize=1000KB\r\n" + 
						"log4j.appender.APPENDER_FILE.MaxBackupIndex=1\r\n" + 
						"log4j.appender.APPENDER_FILE.layout=org.apache.log4j.PatternLayout\r\n" + 
						"log4j.appender.APPENDER_FILE.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %p [%C:%L] - %m%n");
				bw.flush();
			} catch (Exception e) {System.out.println(e);}  
		}
		// instanzio il logger e inizio a loggare
		PropertyConfigurator.configure(work_dir.toString() + FILE_SEPARATOR + "TKServerLog.cfg");
		Logger logger = Logger.getLogger(Ticketserver.class);
		logger.info("TicketServer attivo");
		
		// Configuro il model  
		logger.debug("Instanzio la classe Model per la gestione dei dati");
		Model m = Model.getModel();
		
	}

}
