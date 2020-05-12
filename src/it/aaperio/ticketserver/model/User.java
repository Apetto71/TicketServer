package it.aaperio.ticketserver.model;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
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
	private String username;
	private String password;
	private String emailAddress;
	private LocalDateTime creationDate;
	private LocalDate validTo;
	private boolean changePasswordRequired = false;
	private boolean Authenticated = false;
	private Set<Group> setOfGroup;
	private boolean enable = true; 

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
		this();
		setUsername(u);
		setPassword(p);
		
	}

	/**
	 * Copy constructor
	 * @param u User da copiare
	 */
	public User (User u) {
		this.userid = u.userid ;
		this.username = u.username ;
		this.password = u.password ;
		this.emailAddress = u.emailAddress ;
		this.creationDate = u.creationDate ;
		this.validTo = u.validTo ;
		this.changePasswordRequired = u.changePasswordRequired ;
		this.Authenticated = u.Authenticated ;
		this.setOfGroup = u.setOfGroup ;
		this.enable = u.enable ; 
	}
	
	/**
	 * @param userid
	 * @param username
	 * @param password
	 * @param emailAddress
	 * @param creationDate
	 * @param validTo
	 * @param changePasswordRequired
	 * Costruttore che imposta tutti i parametri
	 */
	public User(int userid, String username, String password, String emailAddress, LocalDateTime creationDate,
			LocalDate validTo, boolean changePasswordrequired) {
		this();
		this.userid = userid;
		this.username = username;
		this.password = password;
		this.emailAddress = emailAddress;
		this.creationDate = creationDate;
		this.validTo = validTo;
		this.changePasswordRequired = changePasswordRequired;
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
		this.username = username;
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
	 * @return the creationDate
	 */
	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate
	 *            the creationDate to set
	 */
	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return the validTo
	 */
	public LocalDate getValidTo() {
		return validTo;
	}

	/**
	 * @param validTo
	 *            the validTo to set
	 */
	public void setValidTo(LocalDate validTo) {
		this.validTo = validTo;
	}



	

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	
	/**
	 * @param password
	 *            Imposta il campo password criptato, deve usarlo il client che
	 *            dopo aver ricevuto la password in chiaro dall'utente la cripta
	 *            prima di inviarla al server 
	 */
	public void setPassword(String password) {
		
		try {
			this.password = setHashedPassword(password);
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			logger.error("Errore nell'impostazione della password in formato criptato\n", e);
		}
	}
	
	

	/**
	 * @return the changePasswordRequired
	 */
	public boolean isChangePasswordRequired() {
		return changePasswordRequired;
	}



	/**
	 * @param changePasswordRequired the changePasswordRequired to set
	 */
	public void setChangePasswordRequired(boolean changePasswordRequired) {
		this.changePasswordRequired = changePasswordRequired;
	}

	

	/**
	 * @return the autenticated
	 */
	public boolean isAuthenticated() {
		return Authenticated;
	}



	/**
	 * @param autenticated the autenticated to set
	 */
	public void setAuthenticated(boolean authenticated) {
		Authenticated = authenticated;
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
	public boolean isEnable() {
		return enable;
	}

	/**
	 * @param enable the enable to set
	 */
	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [userid=" + userid + ", username=" + username + ", password=" + password
				+ ", emailAddress=" + emailAddress + ", changePasswordRequired=" + changePasswordRequired + ", Authenticated="
				+ Authenticated + "]";
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
	
	/** 
	 * Funzione chiamata dal Server
	 * L'utente viene autenticato se la password dell'utente coincide con 
	 * quella dell'omonimo utente che il server conosce
	 * @param u: Utente su cui fare la verifica
	 * @return
	 */
	public boolean checkAuthentication (User u) {
		// Restituisce true se le password inviata dall'utente coincide con 
		// quella sul DB
		boolean result = false;
		if (this.password.equals(u.password)){
			this.setAuthenticated(true);
			result = true;
		} else {
			result = false; 
		} 
		
		return result;
	}
	
	
	public void addGroup (Group g) {
		this.setOfGroup.add(g);
	}

	
}
