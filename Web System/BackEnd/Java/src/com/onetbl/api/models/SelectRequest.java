package com.onetbl.api.models;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This is the request sent in Select which also contains the global parameters
 * so the extends the basic request class
 * 
 * @author Roxan Barakat
 * 
 */

@XmlRootElement
public class SelectRequest extends CredentialsRequest {

	private String[] columns;
	private String whereClause;
	private OneAggregate aggregate;

	public SelectRequest() {
	}

	/**
	 * @return the aggregate
	 */
	public OneAggregate getAggregate() {
		return aggregate;
	}

	@Override
	public boolean validate() {

		if (!super.validate())
			return false;

		if ((aggregate == null 
				&& (columns == null || columns.length == 0)))
			return false;

		if (aggregate != null
				&& (aggregate.getAggFunction() == null
					|| aggregate.getAggFunction().isEmpty()
					|| aggregate.getAggColumn() == null 
					|| aggregate.getAggColumn().isEmpty()))
			return false;

		return true;
	}

	/**
	 * @param aggregate
	 *            the aggregate to set
	 */
	public void setAggregate(OneAggregate aggregate) {
		this.aggregate = aggregate;
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

	public static void main(String[] args){
		SelectRequest sr = new SelectRequest();
		
		sr.setVersion("ss");
		sr.setUserID("ui");
		sr.setToken("tk");
		sr.setTableName("tn");
		
		//sr.setColumns(columns);
		sr.setWhereClause("");
		//sr.setAggregate(new OneAggregate("MIN","cl"));
	}
}
