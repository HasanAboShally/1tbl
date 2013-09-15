package com.onetbl.web.beans;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author Monir
 *
 */

@XmlRootElement
public class ReplyBean<T>{
	// Strings representing the different messages the Reply object can
	// Contain
	public static final String NOT_FORMATED_REQUEST = "Uknown request format";
	public static final String UNSUPPORTED_COMMAND = "Unupported commnd";
	public static final String INVALID_PARAMETER = "Invalid parameter - parameter missing or misspelled";
	public static final String NUMBER_FORMAT_ERROR = "Number format error";
	public static final String NUMBER_OUT_OF_RANGE = "Nmber out of rane";
	public static final String LOGIN_FAILED = "Invalid userName and/or password";
	public static final String USER_NOT_LOGGED_IN = "API Calld when user is not logged in";
	public static final String CLIENT_NOT_FOUND = "Id Not Found - attempt login with no such Id";
	public static final String PROJECT_NOT_FOUND = "No such project name";
	public static final String LOGIN_SUCCESS = "login successful";
	public static final String SUCCESS = "success";
	public static final String FAILED = "failed";

	private String message;
	private boolean success;
	private ArrayList<T> returnedValue;
	//private String returnedValue;
	
	public ReplyBean(){}
	
	/**
	 * constructs a reply that contains a returnedValue
	 * 
	 * @param value
	 *            the results of the executed request
	 * @param message
	 *            the message that will be sent to the client
	 * @param success
	 *            boolean indication about the execution results of a request
	 */
	public ReplyBean(ArrayList<T> value, String message, boolean success) {
		this.returnedValue = value;
		this.success = success;
		this.message = message;
	}

	/**
	 * constructs a reply that has no returned value , only message and boolean
	 * flag
	 * 
	 * @param message
	 *            the message that will be sent to the client
	 * @param success
	 *            boolean indication about the execution results of a request
	 */
	public ReplyBean(String message, boolean success) {
		this.message = message;
		this.success = success;
		this.returnedValue = null;
	}

	public String getMessage() {
		return message;
	}

	public boolean isSuccess() {
		return success;
	}

	public ArrayList<T> getReturnedValue(){
		return returnedValue;
	}

	public void setReturnedValue(ArrayList<T> returnedValue) {
		this.returnedValue = returnedValue;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
}
