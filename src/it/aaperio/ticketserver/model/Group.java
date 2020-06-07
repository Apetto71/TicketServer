package it.aaperio.ticketserver.model;
 
import java.util.HashSet;

public class Group {
	
	private int idGroup ;
	private String groupName ;
	private HashSet<User> setOfUser ;
	private boolean attivo ;
	
	
	/**
	 * Costruttore vuoto che inizializza le variabili che ne hanno necessità
	 */
	Group() {
		super();
		setOfUser =new HashSet<User>();
	}
	
	/**
	 * @param groupName
	 */
	public Group(String groupName) {
		this();
		this.groupName = groupName;
	}
	
	
	
	
	/** Costruttore con tutti i campi tranne l'insieme degli utenti appartenenti
	 * @param idGroup
	 * @param groupName
	 * @param attivo
	 */
	public Group(int idGroup, String groupName, boolean attivo) {
		super();
		this.idGroup = idGroup;
		this.groupName = groupName;
		this.attivo = attivo;
	}

	/** Costruttore con tutti i campi 
	 * @param idGroup
	 * @param groupName
	 * @param setOfUser
	 * @param attivo
	 */
	public Group(int idGroup, String groupName, HashSet<User> setOfUser, boolean attivo) {
		super();
		this.idGroup = idGroup;
		this.groupName = groupName;
		this.setOfUser = setOfUser;
		this.attivo = attivo;
	}

	/**
	 * @return the idGroup
	 */
	public int getIdGroup() {
		return idGroup;
	}



	/**
	 * @param idGroup the idGroup to set
	 */
	public void setIdGroup(int idGroup) {
		this.idGroup = idGroup;
	}



	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}
	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	/**
	 * @return the setOfUser
	 */
	public HashSet<User> getSetOfUser() {
		return setOfUser;
	}
	/**
	 * @param setOfUser the setOfUser to set
	 */
	public void setSetOfUser(HashSet<User> setOfUser) {
		this.setOfUser = setOfUser;
	}
	
	public void addUser (User u) {
		this.setOfUser.add(u);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idGroup;
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
		Group other = (Group) obj;
		if (idGroup != other.idGroup)
			return false;
		return true;
	}
	
	
	
	

}
