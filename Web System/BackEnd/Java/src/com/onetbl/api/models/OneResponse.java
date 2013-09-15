package com.onetbl.api.models;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This is the response class that all returned values from the server must be in.
 * @author Roxan Barakat
 *
 */

@XmlRootElement
public class OneResponse {

	/**
	 * This class have two feilds , 
	 * the status (boolean ) that indecates whether the operation was successful or not
	 * and the returned value from the server (error code or values)
	 * 
	 */
	private boolean status ;
	private String returnedValue ;
	
	
	
	public OneResponse() {
	}

	public OneResponse(boolean status) {
		this.status = status;
	}
	public OneResponse(boolean status, String returnedValue) {
		this.status = status;
		this.returnedValue = returnedValue;
	}

	/**
	 * @return the status
	 */
	public boolean isStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

	/**
	 * @return the returnedValue
	 */
	public String getReturnedValue() {
		return returnedValue;
	}

	/**
	 * @param returnedValue the returnedValue to set
	 */
	public void setReturnedValue(String returnedValue) {
		this.returnedValue = returnedValue;
	}
	
	
	 

}
