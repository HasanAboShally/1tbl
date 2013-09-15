package com.onetbl.web.utils;

import java.util.ArrayList;
import com.onetbl.db.dao.DDLQueries;
import com.onetbl.db.dao.ProjectsManagement;
import com.onetbl.db.exceptions.DataBaseException;
import com.onetbl.web.beans.Column;
import com.onetbl.web.beans.SystemTable;
import com.onetbl.web.beans.WebTable;

public class TablesUtil {

	public static ArrayList<WebTable> getProjectTables(int projId) {
		try {
			return  ProjectsManagement.getProjectTables(DDLQueries.PROJECT_DBName_PREFIX + projId);
		} catch (DataBaseException e) {
			return null;
		}
	}

	public static boolean addTable(int projectID, WebTable table) {
		try {
			DDLQueries.createTable(projectID, table.getName(), table.getColumns());
			return true;
		} catch (DataBaseException e) {
			return false;
		}
	}

	public static boolean renameTable(int projectID, String oldName,
			String newName) {
		try {
			DDLQueries.renameTable(projectID, oldName, newName);
			return true;
		} catch (DataBaseException e) {
			return false;
		}
	}
	
	public static boolean deleteTable(int projectID,String tableName) {
		try {
			DDLQueries.dropTable(projectID, tableName);
			return true;
		} catch (DataBaseException e) {
			return false;
		}
	}

	public static String getTableCSV(int projectID, String tableName) {
		try {
			return DDLQueries.getTableCSV(projectID, tableName);
		} catch (DataBaseException e) {
			return null;
		}
	}
	public static void main(String[] args){
		getTableCSV(6,"persons");
		}
}
