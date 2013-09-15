package com.onetbl.api.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OneSelectResponse {

	private boolean status;
	private List<HashMap<String, String>> result;
	private String errorMessage;

	public OneSelectResponse() {
	}

	public OneSelectResponse(boolean status,
			List<HashMap<String, String>> returnedValue) {
		this.status = status;
		this.result = returnedValue;
	}

	public OneSelectResponse(boolean status, String errorMessage) {
		this.status = status;
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the status
	 */
	public boolean isStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

	/**
	 * @return the result
	 */
	public List<HashMap<String, String>> getResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(List<HashMap<String, String>> result) {
		this.result = result;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage
	 *            the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
