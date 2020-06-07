package it.aaperio.ticketserver.model;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;


public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Algoritmo di cifratura della password
	private static final String ALGORITHM = "SHA-1";
	transient Logger logger = Logger.getLogger(User.class);
	
	
	private int userid;
	private String nome ;
	private String cognome ;
	private String username;
	private String password;
	private String emailAddress;
	private LocalDateTime dataCreazione;
	private Set<Group> setOfGroup;
	private boolean attivo ; 

	/**
	 * Construttore vuoto che per ora non deve fare niente
	 */
	public User() {
		super();
		this.setOfGroup = new HashSet<Group> ();
	}

	/**
	 * Costruttore con user e password. La password ricevuta viene criptata
	 * @param u
	 * @param p
	 */

	public User(String u, String p) {
		this() ;
		setUsername(u);
		setPassword(p);
		
	}

	/**
	 * Copy constructor
	 * @param u User da copiare
	 */
	public User (User u) {
		this(); 
		this.userid = u.userid ;
		this.nome = u.nome ;
		this.cognome = u.cognome ;
		this.username = u.username ;
		this.password = u.password ;
		this.emailAddress = u.emailAddress ;
		this.setOfGroup = u.setOfGroup ;
		this.attivo = u.attivo ; 
	}
	
	
	/** Costruisce istanza di User senza i campi che inserisce in automatico il DB
	 * @param nome
	 * @param cognome
	 * @param username
	 * @param password
	 * @param emailAddress
	 */
	public User(String nome, String cognome, String username, String password, String emailAddress) {
		this(); 
		this.nome = nome;
		this.cognome = cognome;
		this.username = username;
		setPassword(password) ;
		this.emailAddress = emailAddress;
	}

	/**
	 * Crea l'utente con i seguenti campi valorizzati. Funzione da usare lato server dove la 
	 * password è già criptata 
	 * @param userid
	 * @param nome
	 * @param cognome
	 * @param username
	 * @param password
	 * @param emailAddress
	 * @param attivo
	 * @param dataCreazione
	 */
	public User(int userid, String nome, String cognome, String username, String password, String emailAddress, 
			Boolean attivo, LocalDateTime dataCreazione) {
		this() ;
		this.userid = userid;
		this.nome = nome;
		this.cognome = cognome;
		this.username = username ;
		this.password = password ;
		this.emailAddress = emailAddress;
		this.attivo = attivo ;
		this.dataCreazione = dataCreazione ;
	}

	/**
	 * @return the userid
	 */
	public int getUserid() {
		return userid;
	}

	/**
	 * @param userid
	 *            the userid to set
	 */
	public void setUserid(int userid) {
		this.userid = userid;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username.toLowerCase();
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username.toLowerCase();
	}

	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @param emailAddress
	 *            the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress.toLowerCase();
	}

	/**
	 * @return the dataCreazione
	 */
	public LocalDateTime getDataCreazione() {
		return dataCreazione;
	}

	/**
	 * @param creationDate
	 *            the creationDate to set
	 */
	public void setCreationDate(LocalDateTime dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	
	
	

	

	/**
	 * @return the setOfGroup
	 */
	public Set<Group> getSetOfGroup() {
		return setOfGroup;
	}

	/**
	 * @param setOfGroup the setOfGroup to set
	 */
	public void setSetOfGroup(HashSet<Group> setOfGroup) {
		this.setOfGroup = setOfGroup;
	}
	
	

	/**
	 * @return the enable
	 */
	public boolean isAttivo() {
		return attivo;
	}

	/**
	 * @param enable the enable to set
	 */
	public void setEnable(boolean attivo) {
		this.attivo = attivo;
	}

	
		
	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the cognome
	 */
	public String getCognome() {
		return cognome;
	}

	/**
	 * @param cognome the cognome to set
	 */
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	

	@Override
	public String toString() {
		return "User [userid=" + userid + ", nome=" + nome + ", cognome=" + cognome + ", username=" + username
				+ ", password=" + password + ", emailAddress=" + emailAddress + ", dataCreazione=" + dataCreazione
				+ ", attivo=" + attivo + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + userid;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (userid != other.userid)
			return false;
		return true;
	}

	private String setHashedPassword(String p) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		String result;
		try {
			MessageDigest md = MessageDigest.getInstance( ALGORITHM );
			md.update( p.getBytes("UTF-8") );
			result =  toHex( md.digest() );
		} 
		catch (UnsupportedEncodingException e) {
			throw new UnsupportedEncodingException(e.getMessage());
			
		} catch (NoSuchAlgorithmException e)
		{
			throw new NoSuchAlgorithmException (e);
		}
		return result;
	}

	private static String toHex(byte[] data) {
		StringBuffer sb = new StringBuffer();
		for (byte b : data) {
			String digit = Integer.toString(b & 0xFF, 16);

			if (digit.length() == 1) {
				sb.append("0");
			}
			sb.append(digit);
		}
		return sb.toString();
	}
	
		
	public void addGroup (Group g) {
		this.setOfGroup.add(g);
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	
	/**
	 * Imposta il campo password criptato, deve usarlo il client che
	 * dopo aver ricevuto la password in chiaro dall'utente la cripta
	 * prima di inviarla al server
	 * @param password
	 *             
	 */
	public void setPassword(String password) {
		
		try {
			this.password = setHashedPassword(password);
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
			logger.error("Errore nell'impostazione della password in formato criptato\n", e);
			throw new RuntimeException() ;
		}
	}
	
	/**
	 * Imposta una nuova password
	 * @param nuova Nuova password da impostare
	 * @return
	 */
	public void changePsw (String nuova) {
		setPassword(nuova) ;
		
	}
}
