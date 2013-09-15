package com.onetbl.web.beans;


import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;


import com.onetbl.db.utils.DataBaseUtils;

@XmlRootElement
public class WebTable {
	private String name;
	private ArrayList<Column> colNames = new ArrayList<Column>();
	public WebTable() {}
	
	public WebTable(String name) {
		this.name = name;
	}
	
	public WebTable(String name, ArrayList<Column> arr) {
		this.name = name;
		colNames = arr ;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public ArrayList<Column> getColumns() {
		return colNames;
	}

	public void setColumns(ArrayList<Column> arr) {
		this.colNames = arr ;
	}

	
}
