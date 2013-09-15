package com.onetbl.api.models;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This is the insert request , also extends the basic request
 * 
 * @author Roxan Barakat
 * 
 */

@XmlRootElement
public class InsertRequest extends CredentialsRequest {

	private String[] columns;
	private String[] values;

	public InsertRequest() {
	}

	/**
	 * @return the columns
	 */
	public String[] getColumns() {
		return columns;
	}

	/**
	 * @param columns
	 *            the columns to set
	 */
	public void setColumns(String[] columns) {
		this.columns = columns;
	}

	/**
	 * @return the values
	 */
	public String[] getValues() {
		return values;
	}

	/**
	 * @param values
	 *            the values to set
	 */
	public void setValues(String[] values) {
		this.values = values;
	}

}
