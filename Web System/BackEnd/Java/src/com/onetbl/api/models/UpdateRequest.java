package com.onetbl.api.models;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class is the update request , extends the basic request
 * 
 * @author Roxan Barakat
 * 
 */

@XmlRootElement
public class UpdateRequest extends CredentialsRequest {

	private String whereClause;
	private String[] setColumns;
	private String[] setValues;

	public UpdateRequest() {
		super();
	}

	/**
	 * @return the whereClause
	 */
	public String getWhereClause() {
		return whereClause;
	}

	/**
	 * @param whereClause
	 *            the whereClause to set
	 */
	public void setWhereClause(String whereClause) {
		this.whereClause = whereClause;
	}

	/**
	 * @return the setColumns
	 */
	public String[] getSetColumns() {
		return setColumns;
	}

	/**
	 * @param setColumns
	 *            the setColumns to set
	 */
	public void setSetColumns(String[] setColumns) {
		this.setColumns = setColumns;
	}

	/**
	 * @return the setValues
	 */
	public String[] getSetValues() {
		return setValues;
	}

	/**
	 * @param setValues
	 *            the setValues to set
	 */
	public void setSetValues(String[] setValues) {
		this.setValues = setValues;
	}

}
