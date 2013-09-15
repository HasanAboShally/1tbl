package com.onetbl.web.utils;

import java.util.ArrayList;
import com.onetbl.db.dao.DDLQueries;
import com.onetbl.db.dao.DMLQueries;
import com.onetbl.db.dao.ProjectsManagement;
import com.onetbl.db.exceptions.DataBaseException;
import com.onetbl.web.beans.Column;

public class ColumnsUtil {

	public static ArrayList<String> getTableColumns(int projId , String tblName){
		try {
			return DMLQueries.getTableColumns("DB" + projId, tblName);
		} catch (DataBaseException e) {
			return null ;
		}
	}

	public static boolean addTableColumn(int projId , String tableName , Column c){
		try {
			DDLQueries.addColumn(projId, tableName, c);
			return true ;
		} catch (DataBaseException e) {
			return false ;
		}
	}
	
	public static boolean updateTableColumn(int projId ,String tableName , String oldName , String newName){
		try {
			DDLQueries.renameColumn(projId, tableName, oldName, newName) ;
			return true ;
		} catch (DataBaseException e) {
			return false ;
		}
	}
	
	public static boolean deleteTableColumn(int projId, String tableName , String colName){
		try {
			DDLQueries.deleteColumn(projId, tableName, colName);
			return true ;
		} catch (DataBaseException e) {
			return false ;
		}
	}
	
}
