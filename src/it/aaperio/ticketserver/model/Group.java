package it.aaperio.ticketserver.model;
 
import java.util.HashSet;

public class Group {
	
	private int idGroup;
	private String groupName;
	private HashSet<User> setOfUser;
	
	
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
	
	
	
	/**
	 * @param idGroup
	 * @param groupName
	 */
	public Group(int idGroup, String groupName) {
		this();
		this.idGroup = idGroup;
		this.groupName = groupName;
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
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((groupName == null) ? 0 : groupName.hashCode());
		return result;
	}
	/* (non-Javadoc)
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
		Group other = (Group) obj;
		if (groupName == null) {
			if (other.groupName != null)
				return false;
		} else if (!groupName.equals(other.groupName))
			return false;
		return true;
	}
	

}
