package com.onetbl.db.dao;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.onetbl.db.connection.DBconnection;
import com.onetbl.db.exceptions.DataBaseException;
import com.onetbl.web.beans.Column;


public class DDLQueries {

	private static  DBconnection con = DBconnection.getInstance();
	static String columnType = " varchar(255) ";
	public static final String ROW_IDENTIFIER = "_oneID";
	public static String PROJECT_DBName_PREFIX = "DB";
	
	/**
	 * Gets a name and creates a new database with that name
	 * @param DBname
	 * @throws DataBaseException
	 */
	public static void createDataBase(String DBname) throws DataBaseException{
		
		String query = null;
		
		try{
			Integer.parseInt(DBname);    // in case name is a number
			query = "CREATE DATABASE `" + DBname + "`;";
		}
		catch(NumberFormatException e){  // in case name is not a number
			query = "CREATE DATABASE " + DBname + ";";
		}
				
		try(PreparedStatement statement = 
			                  con.getConnection().prepareStatement(query);)
		{
			statement.execute();
			
		} catch (SQLException e) {
			throw new DataBaseException(e.getMessage());
		}
	}
	
	
	/**
	 * Creates a new table in the given database with the given columns
	 * @param DBName
	 * @param tableName
	 * @param columns
	 * @throws DataBaseException 
	 */
	public static void createTable(int projectID,String tableName , ArrayList<Column> colmuns) throws DataBaseException{
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE " + ProjectsManagement.getDBName(projectID) + "." + tableName + " ( ");
		sb.append("_oneId BIGINT NOT NULL AUTO_INCREMENT , ");
		for(Column str : colmuns){
			sb.append(str.columnName + columnType +", ");
		}
		sb.append("PRIMARY KEY (_oneId)  ");
		sb.delete(sb.length()-2, sb.length()-1);
		sb.append(" );");
		System.out.println(sb.toString());
		try(PreparedStatement statement = 
                con.getConnection().prepareStatement(sb.toString());)
		{
			statement.execute();
		
		} catch (SQLException e) {
			throw new DataBaseException(e.getMessage());
		}
	}
	
	
	/**
	 * Drops a table from a database.
	 * @param DBname
	 * @param tableName
	 * @throws DataBaseException 
	 */
	public static void dropTable(int projectID, String tableName ) throws DataBaseException{
		
		String query = "DROP TABLE " + ProjectsManagement.getDBName(projectID) + "." + tableName + ";";
		
		try(PreparedStatement statement = 
                con.getConnection().prepareStatement(query);)
		{
			statement.execute();
			
		} catch (SQLException e) {
			throw new DataBaseException(e.getMessage());
		}		
	}
	
	
	/**
	 * Clears the data in the given table.
	 * @param DBname
	 * @param tableName
	 * @throws DataBaseException 
	 */
	public static void clearTable(int projectID , String tableName ) throws DataBaseException{
		
		String query = "TRUNCATE TABLE " + ProjectsManagement.getDBName(projectID) + "." + tableName + ";";
		
		try(PreparedStatement statement = 
                con.getConnection().prepareStatement(query);)
		{
			statement.execute();
			
		} catch (SQLException e) {
			throw new DataBaseException(e.getMessage());
		}
	}

	/**
	 * Adds a column to a given table.
	 * @param DBname
	 * @param tableName
	 * @param columnName
	 * @throws DataBaseException 
	 */
	public static void addColumn(int projectID, String tableName , Column column) throws DataBaseException{

		String query = "ALTER TABLE " + ProjectsManagement.getDBName(projectID) + "." + tableName + 
					   " ADD "+ column.getName() + columnType + ";";
		
		try(PreparedStatement statement = 
                con.getConnection().prepareStatement(query);)
		{
			statement.execute();
			
		} catch (SQLException e) {
			throw new DataBaseException(e.getMessage());
		}
	}
	
	
	/**
	 * Renames an existing column in a given table.
	 * @param DBname
	 * @param tableName
	 * @param oldName
	 * @param newName
	 * @param type
	 * @throws DataBaseException 
	 */
	public static void renameColumn(int projectID, String tableName, String oldName, String newName) throws DataBaseException{
		String query = "ALTER TABLE " + ProjectsManagement.getDBName(projectID) + "." + tableName + 
				       " change " + oldName + " " + 
				       newName + " " + " varchar(100) ;" ;
		
		try(PreparedStatement statement = 
                con.getConnection().prepareStatement(query);)
		{
			statement.execute();
			
		} catch (SQLException e) {
			throw new DataBaseException(e.getMessage());
		}
	}
	
	
	/**
	 * Deletes an existing column from the given table.
	 * @param DBname
	 * @param tableName
	 * @param columnName
	 * @throws DataBaseException
	 */
	public static void deleteColumn(int projectID, String tableName , String columnName) throws DataBaseException{
		String query = "ALTER TABLE " + ProjectsManagement.getDBName(projectID) + "." + tableName +
				       " DROP COLUMN "+ columnName + ";";
		try(PreparedStatement statement = 
                con.getConnection().prepareStatement(query);)
		{
			statement.execute();
			
		} catch (SQLException e) {
			throw new DataBaseException(e.getMessage());
		}
	}
	
	
	/**
	 * Renames an existing table.
	 * @param DBname
	 * @param oldName
	 * @param newName
	 * @throws DataBaseException 
	 */
	public static void renameTable(int projectID, String oldName , String newName) throws DataBaseException{
		String query = "RENAME TABLE " + ProjectsManagement.getDBName(projectID) + "." + oldName +
				       " TO " + ProjectsManagement.getDBName(projectID) + "." + newName + ";";
		try(PreparedStatement statement = 
                con.getConnection().prepareStatement(query);)
		{
			statement.execute();
			
		} catch (SQLException e) {
			throw new DataBaseException(e.getMessage());
		}
	}
	
	/**
	 * Deletes an existing database.
	 * @param name
	 * @throws DataBaseException 
	 */
	public static void deleteDatabase(String name) throws DataBaseException{
		String query = "DROP DATABASE " +"`" +  name + "`;";
		try(PreparedStatement statement = 
                con.getConnection().prepareStatement(query);)
		{
			statement.executeUpdate();
			
		} catch (SQLException e) {
			throw new DataBaseException(e.getMessage());
		}
	}

/**
 * creates a CSV file of the table and returns its URL 
 * @param projectID
 * @param tableName
 * @return
 * @throws DataBaseException
 */
	public static String getTableCSV(int projectID, String tableName) throws DataBaseException {
		String path = "'c:/csvfiles/"  + projectID + tableName + ".csv'";
		String table = PROJECT_DBName_PREFIX + projectID + "." + tableName;
		String query = "SELECT * INTO OUTFILE"  + path +
				 "FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '\"'" +
				" LINES TERMINATED BY '\n' " +
				 "FROM " + table;
		File file = new File(path + table);
		file.delete();
		try(PreparedStatement statement = 
                con.getConnection().prepareStatement(query);)
		{
			statement.execute();
			
		} catch (SQLException e) {
			throw new DataBaseException(e.getMessage());
		}
		
		return path;
	}
	
	
	
	
}
