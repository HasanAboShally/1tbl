package com.onetbl.db.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.onetbl.api.exceptions.UserNotFoundException;
import com.onetbl.db.connection.DBconnection;
import com.onetbl.db.exceptions.DataBaseException;
import com.onetbl.db.exceptions.NoSuchUserException;
import com.onetbl.db.exceptions.ProjectNotFoundException;
import com.onetbl.db.exceptions.UserAlreadyExist;
import com.onetbl.web.beans.User;


public class UsersManagement {
	
	private static  DBconnection con = DBconnection.getInstance();
	static String DBname = "1tbldb";
	static String usersTableName = "users";
	static String projectsTableName = "projects";
	
	
	
	/**
	 * Gets a username and returns a User object with data from the database for
	 * that username.
	 * @param username
	 * @return a User object
	 * @throws NoSuchUserException if user is not in the database
	 * @throws DataBaseException 
	 */
	public static User getUser(String username) throws NoSuchUserException, DataBaseException{
		
		String  query;
		query = "SELECT * FROM " + DBname + "." + usersTableName +
				" WHERE username = ?";
		
		try{
			PreparedStatement statement = 
                con.getConnection().prepareStatement(query);
			statement.setString(1, username);
			ResultSet rs = statement.executeQuery();
		
			if(rs.next()){	
				User user = new User(rs.getString("username"), 
						             rs.getString("password"),
						             rs.getString("salt"));
				user.setID(rs.getInt("ID"));
				return user;
			}
			else{
				throw new NoSuchUserException();
			}
			
		} catch (SQLException e) {
			throw new DataBaseException(e.getMessage());
		}		
	}
	
	
	/**
	 * Gets a User object and writes its content to the database.
	 * @param user
	 * @throws DataBaseException 
	 * @throws UserAlreadyExist 
	 */
	public static void addNewUser(User user , String password) throws DataBaseException, UserAlreadyExist{
		
		String query = "INSERT INTO " + DBname + "." + usersTableName +
				"(firstName,lastName,email,username,password,salt,creationDate,lastlogin)" + 
  					   " VALUES(?,?,?,?,?,?,NOW(),NOW())";	   
		
		try{
			PreparedStatement statement = 
                con.getConnection().prepareStatement(query);
		
			statement.setString(1,user.getFirstName());
			statement.setString(2,user.getLastName());
			statement.setString(3,user.getEmail());
			statement.setString(4,user.getUsername());
			statement.setString(5,password);
			statement.setString(6,user.getSalt());
			statement.executeUpdate();
			
		} catch (SQLException e) {
			if(e.getMessage().startsWith("Duplicate entry")){
				throw new UserAlreadyExist();
			}
			throw new DataBaseException(e.getMessage());
		}		
	}
	
	
	/**
	 * Gets a username and returns the user ID for it.
	 * @param username
	 * @return
	 * @throws ProjectNotFoundException
	 * @throws DataBaseException 
	 */
	public static int getUserId(String username) throws ProjectNotFoundException, DataBaseException{
		
		String query = "SELECT id FROM " + DBname + "." + usersTableName + 
				" WHERE username = '" + username + "';";
		
		try(PreparedStatement statement = 
                con.getConnection().prepareStatement(query);
			ResultSet rs = statement.executeQuery())
		{
			rs.next();
			return rs.getInt("ID");
			
		} catch (SQLException e) {
			throw new DataBaseException(e.getMessage());
		}
	}
	
	
	/**
	 * Removes a user and all of its data and projects from the system.
	 * @param username
	 * @throws ProjectNotFoundException
	 * @throws DataBaseException
	 * @throws UserNotFoundException 
	 */
	public static void removeUser(int id) throws ProjectNotFoundException , DataBaseException, UserNotFoundException{
		
		String username = getUser(id).getUsername();
		
		String query = "SELECT id FROM " + DBname + "." + projectsTableName + 
					   " WHERE ownerID = " + id + ";";
		
		try(PreparedStatement statement = 
                con.getConnection().prepareStatement(query);
			ResultSet rs = statement.executeQuery())
		{
			while(rs.next()){
				ProjectsManagement.removeProject(rs.getLong("id"), id);
			}
			
		} catch (SQLException e) {
			throw new DataBaseException(e.getMessage());
		}
		
		query = "DELETE FROM " + DBname + "." + usersTableName + 
				" WHERE username = '" + username + "';";
		
		try(PreparedStatement statement = 
                con.getConnection().prepareStatement(query);)
		{
			statement.execute();
			
		} catch (SQLException e) {
			throw new DataBaseException(e.getMessage());
		}
		
	}
	
	/**
	 * changes the password for a username.
	 * @param userID
	 * @param password
	 * @param salt
	 * @throws ProjectNotFoundException
	 * @throws DataBaseException 
	 */
	public static void changePassword(int userID,String password,String salt) throws ProjectNotFoundException, DataBaseException{
		
		String query = "UPDATE " + DBname + "." + usersTableName + 
					   " SET " + DBname + "." + usersTableName + ".password = ?"  +
					   ", salt = ?" + 
					   " WHERE id = ? " + userID;
		// TODO fix - dont concatenate strings
		try{
			PreparedStatement statement = con.getConnection().prepareStatement(query);
			statement.setString(1, password);
			statement.setString(2, salt);
			statement.setInt(3, userID);
			statement.executeUpdate();
			
		} catch (SQLException e) {
			throw new DataBaseException(e.getMessage());
		}
	}
	
	/**
	 * Gets a user object and updates its info into the database.
	 * @param user
	 * @return True if successful
	 * @throws DataBaseException
	 */
	public static boolean updateUser(User user) throws DataBaseException{
		String[] columns = {"firstName", "lastName", "email", "username", "password", "salt", "creationDate", "lastLogin"};
		String[] values = {user.getFirstName(), user.getLastName(), user.getEmail(), user.getUsername(),
						   user.getEncryptedSaltedPassword(), user.getSalt(), user.getCreationDate().toString(), user.getLastLogin().toString()};
		int rowsAffected = DynamicDML.updateRow("management", "users", columns, values, "id = " + user.getID());
		if(rowsAffected == 1){
			return true;
		}
		return false;
	}
	
	/**
	 * Gets a user ID and returns a User object with the data for that user.
	 * @param ID
	 * @return
	 * @throws DataBaseException
	 * @throws UserNotFoundException
	 */
	public static User getUser(int ID) throws DataBaseException, UserNotFoundException{
		User user = new User();
		ArrayList<HashMap<String,String>> list = (ArrayList<HashMap<String, String>>)
				 DynamicDML.getRowsByPage(DBname, 
								 usersTableName, 
								 "ID = " + ID, 
								 null,1, -1,
								 "firstName", 
								 "lastName", 
								 "email", 
								 "username", 
								 "password", 
								 "salt", 
								 "creationDate", 
								 "lastLogin");
		// TODO user Prepared statement
		if(list.size() == 0){
			throw new UserNotFoundException();
		}
		user.setFirstName(list.get(0).get("firstName"));
		user.setLastName(list.get(0).get("lastName"));
		user.setEmail(list.get(0).get("email"));
		user.setUsername(list.get(0).get("username"));
		//user.setEncryptedSaltedPassword(list.get(0).get("password"));
		//user.setSalt(list.get(0).get("salt"));
		user.setCreationDate(list.get(0).get("creationDate"));
		user.setLastLogin(list.get(0).get("lastLogin"));
		
		return user;
		
	}
	
	
	
	public static void updateLastLogin(int userID) throws DataBaseException{
			String query = "UPDATE " + DBname + "." + usersTableName + 
						   " SET " + DBname + "." + usersTableName + ".lastlogin = NOW()" +
						   " WHERE id = ? ";
			try{
				PreparedStatement statement = con.getConnection().prepareStatement(query);	
				statement.setInt(1, userID);
				statement.executeUpdate();
			}catch(SQLException e){
				throw new DataBaseException(e.getMessage());	
			}
			
	}
	
	public static void main(String[] args) throws SQLException, ProjectNotFoundException, DataBaseException {
		//addNewUser("user name", "password", "1111111111", "a@a.com");
		//System.out.print(getUserId("user name"));
		//removeUser("user name");
		//changePassword(1, "pwd", "salt");
	}
	
}
