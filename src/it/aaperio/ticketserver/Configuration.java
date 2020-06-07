package it.aaperio.ticketserver;

import java.util.Properties;
import java.io.*;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Configuration {
	
	private static Configuration configurazione;
	private static Logger logger ;
	private static Properties sys_prop =  System.getProperties();
	private static Properties config;
	
	private static boolean modificato = false ; 
	
	// Imposto parametri di configuarazione di default
	private static final String FILE_SEPARATOR = sys_prop.getProperty("file.separator");
	private static final String LINE_SEPARATOR = sys_prop.getProperty("line.separator");
	private static final String OS_NAME = sys_prop.getProperty("os.name");
	private static final String OS_VERSION = sys_prop.getProperty("os.version");
	private static final String USER_DIR = sys_prop.getProperty("user.dir");
	private static final String USER_HOME = sys_prop.getProperty("user.home");
	private static final String WORK_DIR = new String(USER_HOME + FILE_SEPARATOR + ".TicketServer");
			
	// Parametri di connessione al server ed al DB
	private static String PORTA = new String("30500");
	private static String DB_HOST = new String();
	private static String DB_PORT = new String("3306") ;
	private static String NUM_THREAD_PROTOCOL = new String() ; 		// Numero di thread aperti per l'esecuzione delle richieste dei client
	private static String DB_SCHEMA ;
	private static String DB_USER ;
	private static String DB_PSW ;
	
	// Costruttore vuoto perch� classe singleton		
	private Configuration() {super();}; 
	
	public static Configuration getConfiguration() {
		Configuration c;		
		if (configurazione != null) {
				c = configurazione;
				}
				else {
					c = new Configuration();
					configurazione = c;
				}
					
				// Verifico la presenza della directory e file di configurazione del logger
				File work_dir = new File(WORK_DIR);
				if (!work_dir.exists())		{
					work_dir.mkdir();
				}
				
				// Verifico che esista la configurazione del logger, se non esiste la creo
				File log_file = new File (WORK_DIR + FILE_SEPARATOR +"TKServerLog.cfg");
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
						bw.close();
					} catch (IOException e) {System.out.println("Errore nella creazione del file di configurazione logger");}
				}
				// instanzio il logger e inizio a loggare
				PropertyConfigurator.configure(WORK_DIR + FILE_SEPARATOR + "TKServerLog.cfg");
				logger = Logger.getLogger(Configuration.class);
				logger.info("TicketServer attivo");
				// Verifico che esista il file di configurazione dell'applicazione
				config = new Properties();
				File config_file = new File (WORK_DIR + FILE_SEPARATOR + "TKServer.cfg");
				
				if (!config_file.exists()) {
					try {
						config_file.createNewFile();
						logger.debug("Definisco il file di configurazione del server: " + config_file.toString());
					} catch (IOException e) {
						logger.error("Errore nella creazione del file di configurazione",e);
					}
					
					//Definisco i parametri di default che vengono salvati nel file
					logger.debug("Imposto i parametri di configurazione di default") ;
					config.setProperty("PORTA", "30500") ;
					config.setProperty("DB_HOST", "localhost") ;
					config.setProperty("DB_PORT", "3306") ;
					config.setProperty("DB_SCHEMA", "tkserver") ;
					config.setProperty("DB_USER", "tkserver") ;
					config.setProperty("DB_PSW", "tkserver") ;
					config.setProperty("NUM_THREAD_PROTOCOL", "3") ;
					
										
					try {
						logger.debug("Definisco lo Stream per salvare i valori di default");
						FileOutputStream out;
						out = new FileOutputStream(config_file);
						config.store(out, "Commento");
						out.close();
					} catch (IOException e) {
						logger.error("Errore nel salvataggio dei parametri di configurazione",e);
					}
					
					}
				
				FileInputStream in = null;
				try {
					logger.debug("Definisco lo Stream in input per leggere il file di configurazione");
					in = new FileInputStream (config_file);
					logger.debug("Inizializzato il FileInputStream in: " + in.toString());
				} catch (FileNotFoundException e) {
					logger.error("Errore nella creazione dello Stream di input File",e);
				}
				
				try {
					config.load(in);
					logger.debug("Caricati i parametri di configurazione: " + config.toString());
				} catch (IOException e) {
					logger.error("Errore in lettura file configurazione", e);
				}
				try {
					logger.debug("Chiudo lo stream per la lettura del file di configurazione");
					in.close();
				} catch (IOException e) {
					logger.error("Errore in chiusura del file di configurazione", e);
				}
				
				
				//  imposto le variabili di classe con il contenuto di quanto letto
				//dal file di configurazione. Se il valore è null imposto il valore di default
				if (config.getProperty("PORTA") == null) {
					config.setProperty("PORTA", "30500") ;
					modificato = true ;
				}
				if (config.getProperty("DB_HOST") == null) {
					config.setProperty("DB_HOST", "localhost") ;
					modificato = true ;
				}
				if (config.getProperty("DB_PORT") == null) {
					config.setProperty("DB_PORT", "30500") ;
					modificato = true ;
				}
				if (config.getProperty("NUM_THREAD_PROTOCOL") == null) {
					config.setProperty("NUM_THREAD_PROTOCOL", "3") ;
					modificato = true ;
				}
				if (config.getProperty("DB_USER") == null) {
					config.setProperty ("DB_USER", "tkserver") ;
					modificato = true ;
				}	
				if (config.getProperty("DB_PSW") == null) {
					config.setProperty("DB_PSW" , "tkserver") ;
					modificato = true ;
				}
				if (config.getProperty("DB_SCHEMA") == null) {
					config.setProperty("DB_SCHEMA", "tkserver") ;
					modificato = true ;
				}
				
				setPORTA(config.getProperty("PORTA")) ;
				setDB_HOST(config.getProperty("DB_HOST"));
				setDB_PORT(config.getProperty("DB_PORT"));
				setNUM_THREAD_PROTOCOL(config.getProperty("NUM_THREAD_PROTOCOL")) ;
				setDB_USER(config.getProperty("DB_USER"));
				setDB_PSW(config.getProperty("DB_PSW")) ;
				setDB_SCHEMA(config.getProperty("DB_SCHEMA")) ;

				// Se qualche parametro è stato modificato vado a salvare il file di configurazione
				try {
					logger.debug("Definisco lo Stream per salvare i valori di default");
					FileOutputStream out;
					out = new FileOutputStream(config_file);
					config.store(out, "Commento");
					out.close();
				} catch (IOException e) {
					logger.error("Errore nel salvataggio dei parametri di configurazione",e);
				}
				
				
				
				return c;
				}

			public String getPORTA() {
				logger.debug("Ritorno la porta: " + PORTA);
				return PORTA;
			}

			public static void setPORTA(String pORTA) {
				logger.debug("Imposto la porta: " + pORTA);
				PORTA = pORTA;
			}

			public String getDB_HOST() {
				logger.debug("Ritorno il DB_HOST: " + DB_HOST);
				return DB_HOST;
			}

			public static void setDB_HOST(String dB_HOST) {
				logger.debug("Imposto il DB_HOST: " + dB_HOST);
				DB_HOST = dB_HOST;
			}

			public String getDB_PORT() {
				logger.debug("Ritorno il DB_PORT: " + DB_PORT);
				return DB_PORT;
			}

			public static void setDB_PORT(String dB_PORT) {
				logger.debug("Imposto il DB_PORT: " + dB_PORT);
				DB_PORT = dB_PORT;
			}

			/**
			 * @return the nUM_THREAD_PROTOCOL
			 */
			public static String getNUM_THREAD_PROTOCOL() {
				logger.debug("Ritorno il NUM_THREAD_PROTOCOL: " + NUM_THREAD_PROTOCOL);
				return NUM_THREAD_PROTOCOL;
			}

			/**
			 * @param nUM_THREAD_PROTOCOL the nUM_THREAD_PROTOCOL to set
			 */
			public static void setNUM_THREAD_PROTOCOL(String nUM_THREAD_PROTOCOL) {
				NUM_THREAD_PROTOCOL = nUM_THREAD_PROTOCOL;
			}

			/**
			 * @return the dB_SCHEMA
			 */
			public static String getDB_SCHEMA() {
				return DB_SCHEMA;
			}

			/**
			 * @param dB_SCHEMA the dB_SCHEMA to set
			 */
			public static void setDB_SCHEMA(String dB_SCHEMA) {
				DB_SCHEMA = dB_SCHEMA;
			}

			/**
			 * @return the dB_USER
			 */
			public static String getDB_USER() {
				return DB_USER;
			}

			/**
			 * @param dB_USER the dB_USER to set
			 */
			public static void setDB_USER(String dB_USER) {
				DB_USER = dB_USER;
			}

			/**
			 * @return the dB_PSW
			 */
			public static String getDB_PSW() {
				return DB_PSW;
			}

			/**
			 * @param dB_PSW the dB_PSW to set
			 */
			public static void setDB_PSW(String dB_PSW) {
				DB_PSW = dB_PSW;
			}

			
				
			
			
	
}
