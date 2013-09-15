package com.onetbl.web.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SystemTable {
	private String name;
	private int id ;
	private HashMap<String , Column> columns ;
	
	public SystemTable() {
		columns = new HashMap <String , Column> ();
	}

	public SystemTable(String name, int id) {
		this();
		this.name = name;
		this.id = id;
	}
	
	public SystemTable(String name) {
		this();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public HashMap<String, Column> getColumns() {
		return columns;
	}

	public void setColumns(HashMap<String, Column> columns) {
		this.columns = columns;
	}
	
	public void addColumn(Column c){
		this.columns.put(c.getName(), c);
	}
	
	public void removeColumn(Column c){
		this.columns.put(c.getName(), c);
	}
	
	public int getCount(){
		return this.columns.size() ;
	}
	
	public String[] getColumnNames(){
		String[] result = new String[columns.size()];
		for(int i = 0; i < columns.size(); i++){
			result[i] = columns.values().iterator().toString();
		}
		return result;
}
}