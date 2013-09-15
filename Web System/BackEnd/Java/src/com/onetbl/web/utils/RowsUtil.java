package com.onetbl.web.utils;

import java.util.ArrayList;
import java.util.HashMap;
import com.onetbl.db.dao.DDLQueries;
import com.onetbl.db.dao.DynamicDML;
import com.onetbl.db.dao.ProjectsManagement;
import com.onetbl.db.exceptions.DataBaseException;

public class RowsUtil {

	public static ArrayList<HashMap<String, String>> getTableRows(
			int projectId, String tableName, int pageNumber, int rowsPerPage) {
		try {
			return (ArrayList<HashMap<String, String>>) DynamicDML
					.getRowsByPage(ProjectsManagement.getDBName(projectId),
							tableName, "", null, pageNumber,rowsPerPage, "*");
		} catch (DataBaseException e) {
			return null;
		}
	}

	public static long addRow(int projectId, String tableName,
			String[] columns, String... values) {
		try {
			return DynamicDML.insertIntoTable(ProjectsManagement.getDBName(projectId),
					tableName, columns, values) ;
		} catch (DataBaseException e) {
			return -1;
		}
	}

	public static boolean deleteRows(int projectId, String tableName,
			long[] indecies) {
		try {
			// TODO deleteRows must be fixed- details inside it
			DynamicDML.deleteRows(ProjectsManagement.getDBName(projectId),
					tableName, indecies);
			return true;
		} catch (DataBaseException e) {
			return false;
		}
	}

	public static boolean updateRow(int projectId, String tableName,
			long rowId, String[] columns, String... values) {
		try {
			DynamicDML.updateRow(ProjectsManagement.getDBName(projectId),
					tableName, columns, values, DDLQueries.ROW_IDENTIFIER
							+ " = " + rowId);
			return true;
		} catch (DataBaseException e) {
			return false;
		}
	}
	
	public static long getRowsCount(int projectId,String tableName){ 
		try{
	return DynamicDML.getRowsCount(ProjectsManagement.getDBName(projectId)
			, tableName); 
}catch (DataBaseException e) {
	return -1;
}
	}
	public static void main(String[] args){
		String[] columns = {"col19","col3","col88","tb10"};
		String[] values = {"19","3","81","10"};
		addRow(3,"tb4", columns, values);
	}
}
