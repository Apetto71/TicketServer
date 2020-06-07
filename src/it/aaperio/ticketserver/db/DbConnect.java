package it.aaperio.ticketserver.db;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import it.aaperio.ticketserver.Configuration;


public class DbConnect {
	private static Configuration conf ;
	private static Logger logger = Logger.getLogger(DbConnect.class);
	private static HikariDataSource ds = null ;
	private static String jdbcURL  ;
		
	public static Connection getConnection() {
		if (ds == null) {
			// initialize DataSource
			logger.info("Apro una istanza del file di configurazione");
			conf = Configuration.getConfiguration();
			
			jdbcURL = "jdbc:mysql://" + conf.getDB_HOST() + ":" + conf.getDB_PORT() + "/" + conf.getDB_SCHEMA() ;
			logger.info("Impostata la stringa di connessione al DB " + jdbcURL);
			
			HikariConfig config = new HikariConfig();
			config.setJdbcUrl(jdbcURL);
			config.setUsername(conf.getDB_USER());
			config.setPassword(conf.getDB_PSW());
			
			//configurazione mysql
			config.addDataSourceProperty("cachePrepStmts", "true");
			config.addDataSourceProperty("preprStmtChacheSize", "250");
			config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
			ds = new HikariDataSource(config);
			}
		
		try {
			logger.debug("preparo il pool di connessioni") ;
			return ds.getConnection() ;
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e); 
		}
		
	}
}
