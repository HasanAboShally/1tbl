package com.onetbl.db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.mysql.jdbc.ResultSetMetaData;
import com.mysql.jdbc.StringUtils;
import com.onetbl.api.models.OneAggregate;
import com.onetbl.db.connection.DBconnection;
import com.onetbl.db.exceptions.DataBaseException;
import com.onetbl.db.exceptions.WrongNumberOfRowsException;
import com.onetbl.db.utils.DataBaseUtils;
import com.onetbl.web.beans.Column;

public class DynamicDML {

	private static DBconnection con = DBconnection.getInstance();
	private static int MAX_ROWS = 50;


	/**
	 * Inserts given values into a new row in the table
	 * 
	 * @param DBname
	 * @param tableName
	 * @param values
	 * @throws DataBaseException
	 */
	public static long insertIntoTable(String DBname, String tableName,
			String[] columns, String[] values) throws DataBaseException {

		StringBuilder sb1 = new StringBuilder(), sb2 = new StringBuilder();
		sb1.append("INSERT INTO " + DBname + "." + tableName + " (");
		sb1.append(DataBaseUtils.separateStringsByCommas(columns));
		sb1.append(") VALUES(");
		for (String v : values) {
			sb1.append("'" + v + "',");
		}
		sb1.deleteCharAt(sb1.length() - 1);
		sb1.append(");");
		sb2.append("select * from ");
		sb2.append(DBname + "." + tableName);
		sb2.append(" where _oneId = LAST_INSERT_ID() ;");
		
		try {
			PreparedStatement statement1 = con.getConnection().prepareStatement(sb1.toString());
			PreparedStatement statement2 = con.getConnection().prepareStatement(sb2.toString());
			 statement1.executeUpdate();
			 ResultSet rs = statement2.executeQuery();
			 if(rs.next()){
				 return rs.getLong(1);
			 }

		} catch (SQLException e) {
			throw new DataBaseException(e.getMessage());
		}
		return -1;
	}

	/**
	 * returns a list containing a hash map of strings for each row in the given
	 * page. each page contains 50 entries.
	 * 
	 * @param DBname
	 * @param tableName
	 * @param whereClause
	 * @param pageNumber
	 * @param columns
	 * @return
	 * @throws DataBaseException
	 */
	public static List<HashMap<String, String>> getRowsByPage(String DBname,
			String tableName, String whereClause, OneAggregate agg,
			int pageNumber,int rowsPerPage, String... columns) throws DataBaseException {

		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		int offset = (pageNumber - 1) * rowsPerPage;
		String selectClause;
		String groupBy = "";

		if (agg != null) {
			StringBuilder selectBuilder = new StringBuilder();
			selectBuilder.append(agg.getAggFunction()).append("(")
					.append(agg.getCast()).append(")")
					.append(" AS ").append(agg.getAggColumn());

			if (columns.length != 0) {
				String cols = DataBaseUtils.separateStringsByCommas(columns);
				selectBuilder.append(", ").append(cols);
				groupBy = " GROUP BY " + cols;
			}
			selectClause = selectBuilder.toString();
		} else {
			selectClause = DataBaseUtils.separateStringsByCommas(columns);
		}

		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder
				.append("SELECT ")
				.append(selectClause)
				.append(" FROM ")
				.append(DBname + "." + tableName)
				.append(((!whereClause.isEmpty()) ? (" WHERE " + whereClause)
						: ""))
				.append(groupBy);
				if(rowsPerPage != -1)// -1: all the result
					queryBuilder.append(" LIMIT "+ offset + ", " + rowsPerPage + ";");

		String query = queryBuilder.toString();
		System.out.println(query);
		
		try (PreparedStatement statement = con.getConnection()
				.prepareStatement(query);
				ResultSet rs = statement.executeQuery();) {

			ResultSetMetaData mt = (ResultSetMetaData) rs.getMetaData();
			int rsLen = mt.getColumnCount();
			String[] rsColumns = new String[rsLen];
			for (int i = 0; i < rsLen; i++) {
				rsColumns[i] = mt.getColumnName(i + 1);
			}

			for (int i = 0; rs.next(); i++) {
				list.add(new HashMap<String, String>());
				for (int j = 0; j < rs.getMetaData().getColumnCount(); j++) {
					list.get(i).put(rsColumns[j], rs.getString(rsColumns[j]));
				}
			}

			// System.out.println(columnRS.toString());
		} catch (SQLException e) {
			throw new DataBaseException(e.getMessage());
		}

		return list;

	}
	
	public static void deleteRows(String DBName , String tableName ,long[] ides) throws DataBaseException{
		String query = "DELETE FROM " + DBName + "." + tableName + " WHERE " + DDLQueries.ROW_IDENTIFIER+ " IN (";
		Statement statement = null;
		String[] stringsIdes = new String[ides.length];

		for(int i = 0; i < ides.length; i++){
			stringsIdes[i] = String.valueOf(ides[i]);
		}
		query += DataBaseUtils.separateStringsByCommas(stringsIdes) + ")";
		
		try {
				statement = con.getConnection().createStatement();
				statement.executeUpdate(query);
			
		} catch (SQLException e) {
			throw new DataBaseException(e.getMessage());
		}
		try {
			if(statement != null)
			statement.close();
		} catch (SQLException e) {
			throw new DataBaseException(e.getMessage());		}
	}
		
	/**
	 * 
	 * @param DBName
	 * @param tableName
	 * @return the nu,ber of rows the tables has 
	 * @throws DataBaseException
	 */
public static long getRowsCount(String DBName,String tableName) throws DataBaseException{
	Statement getCount = null;
	ResultSet rs = null;
	String query = "SELECT count(*) FROM " + DBName + "." + tableName;
	try{
		getCount = con.getConnection().createStatement();
		rs = getCount.executeQuery(query);		
		 if(rs.next())
				return rs.getLong(1);
		 return -1;
	}catch (SQLException e) {
		throw new DataBaseException(e.getMessage());
	}
}
	/**
	 * Updates the values in a a row in a given table. returns the number of
	 * rows affected.
	 * 
	 * @param DBname
	 * @param tableName
	 * @param columns
	 * @param values
	 * @param whereClause
	 * @return
	 * @throws DataBaseException
	 */
	public static int updateRow(String DBname, String tableName,
			String[] columns, String[] values, String whereClause)
			throws DataBaseException {
		String query = "UPDATE " + DBname + "." + tableName + " " + "SET "
				+ DataBaseUtils.columnsToUpdate(columns, values) + " "
				+ ((!whereClause.isEmpty()) ? (" WHERE " + whereClause) : "")
				+ ";";

		try (PreparedStatement statement = con.getConnection()
				.prepareStatement(query);) {
			return statement.executeUpdate(query);

		} catch (SQLException e) {
			throw new DataBaseException(e.getMessage());
		}
	}

	/**
	 * deletes rows in a given table by a given where clause. returns the number
	 * of rows affected
	 * 
	 * @param DBname
	 * @param tableName
	 * @param where
	 * @return number of rows affected
	 * @throws DataBaseException
	 */
	public static int deleteRow(String DBname, String tableName,
			String whereClause) throws DataBaseException {

		String query = "DELETE FROM " + DBname + "." + tableName
				+ ((!whereClause.isEmpty()) ? (" WHERE " + whereClause) : "")
				+ ";";

		try (PreparedStatement statement = con.getConnection()
				.prepareStatement(query);) {
			return statement.executeUpdate(query);

		} catch (SQLException e) {
			throw new DataBaseException(e.getMessage());
		}
	}

	public static void main(String[] args) throws WrongNumberOfRowsException,
			DataBaseException {

		

		// String[] values = { "1", "2", "3", "4" };
		// DDLQueries.createTable("newdatabase", "test", new Column("a"),new
		// Column("b"),new Column("c"),new Column("d"));
		// insertIntoTable("newdatabase", "test", columns, values);
		//List<HashMap<String, String>> list = getRowsByPage("test",
			//	"SDKTestTable", "", null, 1, columns);
		// new OneAggregate("MAX", "CAST(age AS SIGNED)")
		//System.out.println(list);
		/*
		 * ArrayList<HashMap<String, String>> a =
		 * (ArrayList<HashMap<String,String>>) getRowsByPage("management",
		 * "users", "", 1, "username");
		 * 
		 * System.out.println(a.get(0).get("username"));
		 * 
		 * String[] columns = {"a"}; String[] values = {"100"}; updateRow("hi",
		 * "test", columns, values, "a = '1'"); deleteRow("hi", "test",
		 * "a = '100'");
		 */
	}

}
