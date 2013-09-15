package com.onetbl.web.beans;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {

	private int ID;
	private String firstName;
	private String lastName;
	private String email;
	private String creationDate;
	private String lastLogin;
	private String username;
	private String encryptedSaltedPassword;
	private String salt;

	public User(){}
	
	public User(String username, String encryptedSaltedPassword, String salt) {
		this.username = username;
		this.encryptedSaltedPassword = encryptedSaltedPassword;
		this.salt = salt;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEncryptedSaltedPassword() {
		return encryptedSaltedPassword;
	}

	public void setEncryptedSaltedPassword(String encryptedSaltedPassword) {
		this.encryptedSaltedPassword = encryptedSaltedPassword;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public String getEmail() {
		return this.email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
