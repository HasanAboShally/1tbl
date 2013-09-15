package com.onetbl.db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.onetbl.db.connection.DBconnection;
import com.onetbl.db.exceptions.DataBaseException;

public class TempClass {

	private static DBconnection con = DBconnection.getInstance();
	private static final String PROJECTS = "1tbldb.projects";
	private static final String USERS = "1tbldb.users";

	public static String getProjectNameByToken(String token)  {

		String query = "SELECT id FROM " + PROJECTS + "  WHERE token = ? ";
		try (PreparedStatement stmt = con.getConnection().prepareStatement(
				query)) {
			stmt.setString(1, token);
			ResultSet rs = stmt.executeQuery();
			if (rs.next())
				return DDLQueries.PROJECT_DBName_PREFIX+rs.getString(1);
		} catch (SQLException e) {
			return null;
		}
		return null;

	}
	public static boolean projectBelongsToUser(long userId , long projectId) {
		String query = "SELECT ownerId FROM "+ PROJECTS +" WHERE  id = ?";
		try (PreparedStatement stmt = con.getConnection().prepareStatement(
				query)) {
			stmt.setLong(1, projectId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				if(rs.getLong(1) == userId)
					return true;
				}
		} catch (SQLException e) {
			return false;
		}
		return false;
	}
	
	
	public static boolean tableExists(long userId, String token,
			String tableName) {
		int projectID = -1;
		ResultSet rs;
		String query = "select p.id from " + PROJECTS + " as p join " + USERS
				+ " as u " + " on p.ownerId =  u.ID WHERE token = ? AND u.ID = ? ";
		String showQuery;
		try (PreparedStatement stmt = con.getConnection().prepareStatement(
				query)) {
			stmt.setString(1, token);
			stmt.setLong(2, userId);
			rs = stmt.executeQuery();
			if (rs.next())
				projectID = rs.getInt("ID");
		
		} catch (SQLException e) {
			return false;
		}
		
		showQuery = "SHOW tables FROM " + DDLQueries.PROJECT_DBName_PREFIX + projectID + ";";
		try (Statement stmt = con.getConnection().createStatement()) {
			rs = stmt.executeQuery(showQuery);
			while (rs.next()) {
				if (rs.getString(1).equalsIgnoreCase(tableName))
					return true;
			}

		} catch (SQLException e) {
			return false;
		}
		return false;
	}
	
	public static boolean tableBelongsToUser(long userId, long projectId,
			String tableName) {
		int projectID = -1 ;
		ResultSet rs;
		String query = "select p.id from " + PROJECTS + " as p join " + USERS
				+ " as u " + " on p.ownerId  =  u.ID WHERE p.Id = ? AND u.ID = ? ";
		String showQuery;
		try (PreparedStatement stmt = con.getConnection().prepareStatement(
				query)) {
			stmt.setLong(1, projectId);
			stmt.setLong(2, userId);
			rs = stmt.executeQuery();
			if (rs.next())
				projectID = rs.getInt("ID");
		
		} catch (SQLException e) {
			return false;
		}
		
		
		showQuery = "SHOW tables from `" + DDLQueries.PROJECT_DBName_PREFIX + projectID + ";";
		try (Statement stmt = con.getConnection().createStatement()) {
			rs = stmt.executeQuery(showQuery);
			while (rs.next()) {
				if (rs.getString(1).equalsIgnoreCase(tableName))
					return true;
			}

		} catch (SQLException e) {
			return false;
		}
		return false;
	}
}