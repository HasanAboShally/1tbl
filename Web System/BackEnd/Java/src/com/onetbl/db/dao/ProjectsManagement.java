package com.onetbl.db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.onetbl.db.connection.DBconnection;
import com.onetbl.db.exceptions.DataBaseException;
import com.onetbl.db.exceptions.ProjectNotFoundException;
import com.onetbl.web.beans.Column;
import com.onetbl.web.beans.Project;
import com.onetbl.web.beans.SystemTable;
import com.onetbl.web.beans.WebTable;

public class ProjectsManagement {

	private static DBconnection con = DBconnection.getInstance();
	// static String query;
	private static String DBname = "1tbldb";
	private static String PROJECTS = "projects";
	private static String DB = "DB";

	/**
	 * Adds a new project to the system.
	 * 
	 * @param project
	 * @throws DataBaseException
	 */
	public static void addNewProject(Project project) throws DataBaseException {
		// add project to Projects table
		String query = "INSERT INTO "
				+ DBname
				+ "."
				+ PROJECTS
				+ " (ownerID, name, description, token,iconUrl, creationdate, lastmodified) "
				+ "VALUES(?,?,?,?,?,NOW(),NOW() );";

		try (PreparedStatement statement = con.getConnection()
				.prepareStatement(query);) {// TODO - real preparedStatement
			statement.setInt(1, project.getOwnerId());
			statement.setString(2, project.getName());
			statement.setString(3, project.getDescription());
			statement.setString(4, project.getToken());
			statement.setString(5, project.getIconUrl());
			statement.execute();

		} catch (SQLException e) {
			throw new DataBaseException(e.getMessage());
		}

		// get project ID
		query = "SELECT id FROM " + DBname + "." + PROJECTS
				+ " WHERE token = '" + project.getToken() + "' AND ownerID = "
				+ project.getOwnerId() + ";";// TODO transaction ?
												// Last-Insert-ID ?

		int id = 0;
		try (PreparedStatement statement = con.getConnection()
				.prepareStatement(query);
				ResultSet rs = statement.executeQuery();) {
			rs.next();
			id = rs.getInt("ID");
		} catch (SQLException e) {
			throw new DataBaseException(e.getMessage());
		}

		// create database
		query = "CREATE SCHEMA `" + DDLQueries.PROJECT_DBName_PREFIX + id
				+ "`;";

		try (PreparedStatement statement = con.getConnection()
				.prepareStatement(query);) {
			statement.execute();

		} catch (SQLException e) {
			throw new DataBaseException(e.getMessage());
		}
	}

	public static String getDBName(long projectId) {
		return "DB" + projectId;
	}

	/**
	 * Removes a project and all of its data from the system.
	 * 
	 * @param name
	 * @param ownerID
	 * @throws ProjectNotFoundException
	 * @throws DataBaseException
	 */
	public static void removeProject(long projectId, int ownerID)
			throws ProjectNotFoundException, DataBaseException {

		// String getProject = "SELECT id FROM " + DBname + "." + projects +
		// " WHERE name = ? AND ownerID = ? ;";

		String deleteProject = "DELETE FROM " + DBname + "." + PROJECTS
				+ " WHERE id = ? ;";

		try (PreparedStatement deleteStatement = con.getConnection()
				.prepareStatement(deleteProject);) {
			deleteStatement.setLong(1, projectId);

			if (deleteStatement.executeUpdate() != 1) {
				throw new ProjectNotFoundException();
			}
			DDLQueries.deleteDatabase(DB + projectId); // delete the DB

		} catch (SQLException e) {
			throw new DataBaseException(e.getMessage());
		}

	}

	/**
	 * Renames a given project.
	 * 
	 * @param orginalName
	 * @param newName
	 * @param ownerID
	 * @throws DataBaseException
	 */
	public static void updateProject(int projectID, String name,
			String description, String url) throws DataBaseException {
		final String query = "UPDATE " + DBname + "." + PROJECTS
				+ " SET name = ?, description = ?, iconURL = ? WHERE id = ?";

		try (PreparedStatement statement = con.getConnection()
				.prepareStatement(query);) {
			statement.setString(1, name);
			statement.setString(2, description);
			statement.setString(3, url);
			statement.setInt(4, projectID);
			statement.executeUpdate();
			updateLastModify(projectID);

		} catch (SQLException e) {
			throw new DataBaseException(e.getMessage());
		}
	}

	/**
	 * Returns an array list of all of the given user's project.
	 * 
	 * @param ownerID
	 * @return
	 * @throws DataBaseException
	 */
	public static ArrayList<Project> getUserProjects(int ownerID)
			throws DataBaseException {
		String query = "SELECT id,name,iconUrl FROM " + DBname + "." + PROJECTS
				+ " WHERE ownerID = ? ;";

		ArrayList<Project> userProjectsArray = new ArrayList<Project>();
		ResultSet rs = null;

		try (PreparedStatement statement = con.getConnection()
				.prepareStatement(query);) {
			statement.setInt(1, ownerID);
			rs = statement.executeQuery();
			while (rs.next()) {

				userProjectsArray.add(new Project(rs.getInt(1),
						rs.getString(2), rs.getString(3)));
			}
		} catch (SQLException e) {
			throw new DataBaseException(e.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {

				}
			}
		}
		return userProjectsArray;
	}

	/**
	 * Returns an array list of all of the given project's tables.
	 * 
	 * @param dbName
	 * @return
	 * @throws DataBaseException
	 */
	public static ArrayList<WebTable> getProjectTables(String dbName)
			throws DataBaseException {
		String getTablesNames = "SHOW TABLES FROM " + dbName;
		String getTableColumns = "SHOW COLUMNS FROM ", tableName, columnName;
		ResultSet tablesRS = null, columnsRS = null;
		ArrayList<WebTable> result = new ArrayList<WebTable>();
		Statement columnsStatement = null, tablesNamesStatement = null;

		try {
			tablesNamesStatement = con.getConnection().createStatement();
			columnsStatement = con.getConnection().createStatement();

			tablesRS = tablesNamesStatement.executeQuery(getTablesNames);
			while (tablesRS.next()) { // for each table
				ArrayList<Column> columns = new ArrayList<Column>();
				tableName = tablesRS.getString(1);
				columnsRS = columnsStatement.executeQuery(getTableColumns
						+ tableName + " FROM " + dbName);

				while (columnsRS.next()) { // for each column
					columnName = columnsRS.getString(1);
					columns.add(new Column(columnName));
				}
				result.add(new WebTable(tableName, columns));

			}
		} catch (SQLException e) {
			throw new DataBaseException(e.getMessage());
		} finally {
			try {
				if (tablesRS != null)
					tablesRS.close();
				if (columnsRS != null)
					columnsRS.close();
				if (columnsStatement != null)
					columnsStatement.close();
				if (tablesNamesStatement != null)
					tablesNamesStatement.close();
			} catch (SQLException e) {
				throw new DataBaseException(e.getMessage());
			}
		}
		return result;
	}

	public static Project getLastProject(int ownerID) {
		String sql = "SELECT MAX(ID) FROM " + DBname + "." + PROJECTS
				+ " WHERE ownerID = ?;";
		int maxId = -1;
		ResultSet rs = null;
		try {
			PreparedStatement statement = con.getConnection().prepareStatement(
					sql);
			statement.setInt(1, ownerID);
			rs = statement.executeQuery();
			if (rs.next()) {
				maxId = rs.getInt(1);
				return getProjectDetails(ownerID, maxId);
			} else {
				return null;
			}
		} catch (SQLException | DataBaseException e) {
			return null;
		}

	}

	/**
	 * 
	 * @param ownerID
	 * @param projectID
	 * @return the project details
	 * @throws DataBaseException
	 */
	public static Project getProjectDetails(int ownerID, int projectID)
			throws DataBaseException {
		String query = "SELECT * FROM " + DBname + "." + PROJECTS
				+ " WHERE ownerID = ? AND ID = ?;";
		Project project = new Project();
		try {
			PreparedStatement statement = con.getConnection().prepareStatement(
					query);

			statement.setInt(1, ownerID);
			statement.setInt(2, projectID);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				project.setId(rs.getInt(1));
				project.setOwnerId(rs.getInt(2));
				project.setName(rs.getString(3));
				project.setDescription(rs.getString(4));
				project.setToken(rs.getString(5));
				project.setIconUrl(rs.getString(6));
				project.setCreationDate(rs.getString(7));
				project.setLastModified(rs.getString(8));
			}
			return project;
		} catch (SQLException e) {
			throw new DataBaseException(e.getMessage());
		}
	}

	public static void updateLastModify(int projectID) throws SQLException {
		final String query = "UPDATE " + DBname + "." + PROJECTS
				+ " SET lastModified = NOW() WHERE id = ? ;";

		try (PreparedStatement statement = con.getConnection()
				.prepareStatement(query);) {
			statement.setInt(1, projectID);
			statement.executeUpdate();
		}
	}

	public static void main(String[] args) throws ProjectNotFoundException {

	}

}
