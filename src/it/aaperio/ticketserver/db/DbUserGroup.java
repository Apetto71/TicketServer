package it.aaperio.ticketserver.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import it.aaperio.ticketserver.model.Group;
import it.aaperio.ticketserver.model.User;

public class DbUserGroup {
	
	private Map<String, User> usersMap ;
	private Logger logger = Logger.getLogger(DbUserGroup.class);
	private Connection conn = DbConnect.getConnection() ;
	
	public HashMap<String, User> listaUtenti() {
	
		usersMap = new HashMap<String, User>() ;
		String sqlUser = "select u.iduser, u.nome,u.cognome,u.username,u.password,\n" + 
				"u.datacreazione, u.emailaddress, u.attivo as user_attivo, \n" + 
				"g.idgroup, g.groupname, g.attivo as group_attivo\n" + 
				"from tkserver.user u\n" + 
				"left join tkserver.usergroup as ug on (u.iduser = ug.iduser)\n" + 
				"left join tkserver.group as g on (ug.idgroup = g.idgroup and g.attivo = ?)\n" + 
				"where u.attivo = ?" ;
		
		try {
			logger.debug("Pereparo il preparedstatment " + sqlUser) ;
			PreparedStatement stUser = conn.prepareStatement(sqlUser) ;
			stUser.setBoolean(1, true) ;
			stUser.setBoolean(2, true) ;
			logger.debug("Preparedstatment per tabella user preparato " + stUser.toString()) ;
			ResultSet rsUser = stUser.executeQuery() ;
			
			logger.debug ("Eseguita la query: " + stUser.toString()) ;
			
			while (rsUser.next()) {
				Group g = new Group(rsUser.getInt("idgroup"), rsUser.getString("groupname"), rsUser.getBoolean("group_attivo")) ;
				if (!usersMap.containsKey(rsUser.getString("username")))   {
					User u = new User(rsUser.getInt("iduser"), rsUser.getString("nome"), rsUser.getString("cognome"),
							rsUser.getString("username"), rsUser.getString("password"), rsUser.getString("emailaddress"), true,
							rsUser.getTimestamp("datacreazione").toLocalDateTime()) ;
					
					u.addGroup(g) ;
					usersMap.put(rsUser.getString("username"), u) ;
					
				} else {
					usersMap.get(rsUser.getString("username")).addGroup(g) ;
				}
				
			}
			conn.close() ;
		} catch (SQLException e) {
			logger.error ("Errore di esecuzione query: " , e) ;
			throw new RuntimeException(e); 
			}
		
		
		return (HashMap<String, User>) usersMap ; 
	}
	
	
	/**
	 * Salva un nuovo utente sul DB e ritorna 1 in caso di successo, 0 in caso di errore
	 * @param u Utente da aggiungere. L'utente deve esse completo di tutti i campi
	 * @return 1: in caso di successo
	 * 		   0: in caso di errore
	 */
	public int CreateUser (User u)  {
		int c = 0 ;
		String sql ;
		sql = "insert into tkserver.user (nome, cognome, username, password, attivo, emailaddress) "
				+ "values (?, ?, ?, ?, ?, ?);" ;
		try {
		Connection conn = DbConnect.getConnection() ;
		PreparedStatement st;
		
			st = conn.prepareStatement(sql);
			st.setString(1, u.getNome());
			st.setString(2, u.getCognome()) ;
			st.setString(3,u.getUsername()) ;
			st.setString(4, u.getPassword()) ;
			st.setBoolean(5, true) ;
			st.setString(6, u.getEmailAddress()) ;
			
			c = st.executeUpdate() ;
			conn.close();
		} catch (SQLException e) {
			logger.error("Errore SQL: ", e ) ;
			throw new RuntimeException() ;
		}
	
		
		return c; 
	}
	
	/**
	 * Imposta una nuova password per l'utente
	 * @param u	Utente a cui viene cambiata la password
	 * @param nuova Password nuova che viene impostata
	 */
	public void changPassword (User u, String nuova) {
		String sql ;
		sql = "update tkserver.user set password = ? where iduser = ?" ;
		try {
		Connection conn = DbConnect.getConnection() ;
		PreparedStatement st;
		
			st = conn.prepareStatement(sql) ;
			st.setString(1, u.getPassword()) ;
			st.setInt(2, u.getUserid()) ;
			
			st.executeUpdate() ;
			conn.close();
		} catch (SQLException e) {
			logger.error("Errore SQL: ", e ) ;
			throw new RuntimeException() ;
		}
	
	}

}
