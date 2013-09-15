package com.onetbl.api.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OneAggregate {
	
	public enum AGGTYPE{
		COUNT , SUM  , MAX , MIN  ,AVERAGE
	}
	
	private String aggFunction;
	private String aggColumn;
	private String cast;
	
	
	public OneAggregate() {
	
	}
	public OneAggregate(String aggFunction, String aggColumn, String cast) {
		this.aggFunction = aggFunction;
		this.aggColumn = aggColumn;
		this.cast = cast;
	}
	/**
	 * @return the aggFunction
	 */
	public String getAggFunction() {
		return aggFunction;
	}
	/**
	 * @param aggFunction the aggFunction to set
	 */
	public void setAggFunction(String aggFunction) {
		this.aggFunction = aggFunction;
	}
	/**
	 * @return the aggColumn
	 */
	public String getAggColumn() {
		return aggColumn;
	}
	/**
	 * @param aggColumn the aggColumn to set
	 */
	public void setAggColumn(String aggColumn) {
		this.aggColumn = aggColumn;
	}
	/**
	 * @return the cast
	 */
	public String getCast() {
		return cast;
	}
	/**
	 * @param cast the cast to set
	 */
	public void setCast(String cast) {
		this.cast = cast;
	}
	
	
	
	
	
}