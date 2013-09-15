package com.onetbl.web.beans;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;

@XmlRootElement
@JsonAutoDetect
public class Column {
	@JsonProperty
	public String columnName;
	@JsonProperty
	public boolean notNull;
	@JsonProperty
	public boolean unique;
	@JsonProperty
	public boolean primaryKey;
	@JsonProperty
	public boolean foreignKey;
	@JsonProperty
	public boolean check;
	@JsonProperty
	public String defaultVal;

	public Column(){}
	
	/**
	 * generate column with all the constraints
	 */
	public Column(String columnName, boolean notNull, boolean unique,
			boolean primaryKey, boolean foreignKey, boolean check,
			String Default) {
		this.columnName = columnName;
		this.notNull = notNull;
		this.unique = unique;
		this.primaryKey = primaryKey;
		this.foreignKey = foreignKey;
		this.check = check;
		this.defaultVal = Default;
	}

	/**
	 * generate column without default
	 */
	public Column(String columnName, boolean notNull, boolean unique,
			boolean primaryKey, boolean foreignKey, boolean check) {

		this.columnName = columnName;
		this.notNull = notNull;
		this.unique = unique;
		this.primaryKey = primaryKey;
		this.foreignKey = foreignKey;
		this.check = check;
	}

	/**
	 * generate column just with column name
	 * 
	 * @param columnName
	 */
	public Column(String columnName) {

		this.columnName = columnName;
	}

	public String getName() {
		return columnName;
	}

	public void setName(String columnName) {
		this.columnName = columnName;
	}

	public String getNotNull() {
		return "notNull";
	}

	public void setNotNull(boolean notNull) {
		this.notNull = notNull;
	}

	public String getUnique() {
		return "UNIQUE";
	}

	public void setUnique(boolean unique) {
		this.unique = unique;
	}

	public String getPrimaryKey() {
		return "PRIMARY KEY";
	}

	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	public String getForeignKey() {
		return "FOREIGN KEY";
	}

	public void setForeignKey(boolean foreignKey) {
		this.foreignKey = foreignKey;
	}

	public String getCheck() {
		return "CHECK";
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public String getDefault() {
		return "DEFAULT";
	}

	public void setDefault(String default1) {
		defaultVal = default1;
	}

	@Override
	public String toString() {
		return columnName ;
	}

}
