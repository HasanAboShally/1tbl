package com.onetbl.db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.onetbl.db.connection.DBconnection;
import com.onetbl.db.exceptions.DataBaseException;
import com.onetbl.db.exceptions.WrongNumberOfRowsException;

public class DMLQueries {

	private static DBconnection con = DBconnection.getInstance();
	

	/**
	 * Returns an array list of the names of the columns in a given table.
	 * @param DBname
	 * @param tableName
	 * @return
	 * @throws DataBaseException
	 */
	public static ArrayList<String> getTableColumns(String DBname,
													String tableName) 
													throws DataBaseException 
	{
		ArrayList<String> list = new ArrayList<String>();
		
		String query = "SELECT * FROM " + DBname + "." + tableName + ";";
		
		try(PreparedStatement statement = 
                con.getConnection().prepareStatement(query);
			ResultSet rs = statement.executeQuery();)
		{		
			
			for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
				list.add(rs.getMetaData().getColumnName(i + 1));
			}
			
		} catch (SQLException e) {
			throw new DataBaseException(e.getMessage());
		}
		
		return list;
	}
	

	/**
	 * Returns the names of the tables in a given database.
	 * @param DBname
	 * @return
	 * @throws DataBaseException
	 */
	public static ArrayList<String> getTableNames(String DBname)
												  throws DataBaseException 
	{
		ArrayList<String> list = new ArrayList<String>();
		String query = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES " + 
				       "WHERE TABLE_TYPE='BASE TABLE' and TABLE_SCHEMA = '" + 
				       DBname + "';";
		
		try(PreparedStatement statement = 
                con.getConnection().prepareStatement(query);
            ResultSet rs = statement.executeQuery();)
		{
			while (rs.next()) {
				list.add(rs.getString("TABLE_NAME"));
			}
			
		} catch (SQLException e) {
			throw new DataBaseException(e.getMessage());
		}
		
		return list;
	}

	public static void main(String[] args) throws WrongNumberOfRowsException, DataBaseException {
		
		//ArrayList<String> list = getTableColumns("hi", "test");
		//System.out.print(list.get(0) + list.get(1) + list.get(2) + list.get(3));
		
		//ArrayList<String> list = getTableNames("management");
		//System.out.print(list.get(0) + " " + list.get(1));
		
		
	}

}
