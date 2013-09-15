package com.onetbl.api.models;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This is the basic Request class that contains all Global parameters that are
 * sent in all types of requests .
 * 
 * @author Roxan Barakat
 * 
 */

@XmlRootElement
public class CredentialsRequest {

	protected String version;
	protected String token;
	protected String userID;
	protected String tableName;

	public CredentialsRequest() {
	}

	public boolean validate() {
		return version != null && token != null && userID != null
				&& tableName != null;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token
	 *            the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the userID
	 */
	public String getUserID() {
		return userID;
	}

	/**
	 * @param userID
	 *            the userID to set
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}

	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName
	 *            the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}
