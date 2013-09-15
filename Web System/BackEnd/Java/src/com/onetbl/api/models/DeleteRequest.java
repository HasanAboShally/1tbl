package com.onetbl.api.models;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class is the delete request , extends request
 * @author Roxan Barakat
 *
 */

@XmlRootElement

public class DeleteRequest extends CredentialsRequest{

	private String whereClause;

	public DeleteRequest() {
	}

	/**
	 * @return the whereClause
	 */
	public String getWhereClause() {
		return whereClause;
	}

	/**
	 * @param whereClause the whereClause to set
	 */
	public void setWhereClause(String whereClause) {
		this.whereClause = whereClause;
	}

	
	

}
