package com.onetbl.web.utils;

import java.util.Calendar;
import java.util.Random;
import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;

import com.onetbl.api.exceptions.UserNotFoundException;
import com.onetbl.db.dao.TempClass;
import com.onetbl.db.dao.UsersManagement;
import com.onetbl.db.exceptions.DataBaseException;
import com.onetbl.db.exceptions.NoSuchUserException;
import com.onetbl.db.exceptions.UserAlreadyExist;
import com.onetbl.web.beans.User;
import com.onetbl.web.exceptions.InvalidInputException;
import com.sun.xml.internal.fastinfoset.stax.events.Util;
import org.apache.commons.codec.digest.DigestUtils;

public class UsersUtil {
	/**
	 * @author Anas
	 */
	public static final int SALTSIZE = 10;
	public static final int MAXPASSWORDSIZE = 16;
	public static final int MAXUSERNAMESIZE = 16;

	static public int getUserID(HttpServletRequest req) {
		Object userID = req.getSession().getAttribute("userID");
		if (userID != null)
			return (int) userID;
		return -1;
	}

	static public boolean register(User user) throws UserAlreadyExist {
		String salt = getRandom(SALTSIZE);
		String encryptedPassword = enc(user.getEncryptedSaltedPassword() + salt);

		if (MAXPASSWORDSIZE < user.getEncryptedSaltedPassword().length()
				|| MAXUSERNAMESIZE < user.getUsername().length()
				|| !validateEmail(user.getEmail())){
			return false;
		}
		user.setSalt(salt);
		try{
		    Validation.alphanumeric(user.getUsername());
		    Validation.alphanumeric(user.getFirstName());
		    Validation.alphanumeric(user.getLastName());
			UsersManagement.addNewUser(user, encryptedPassword);
		} catch (DataBaseException | InvalidInputException e) {
			UtilLogger.getInstance().log(Level.SEVERE, e.getMessage());
			return false;
		}
		return true;
	}

	public static Integer login(String username, String password) {
		User userFromDB = null;

		try {
			Validation.alphanumeric(username);
			Validation.alphanumeric(password);
			if (username.length() > MAXUSERNAMESIZE
					|| password.length() > MAXPASSWORDSIZE)
				return null;

			userFromDB = UsersManagement.getUser(username);
			// Update last logged in field
		} catch (DataBaseException | InvalidInputException
				| NoSuchUserException e) {
			UtilLogger.getInstance().log(Level.SEVERE, e.getMessage());
			return null;
		}
		if (checkPassword(userFromDB.getEncryptedSaltedPassword(),
				userFromDB.getSalt(), password))
			return userFromDB.getID();

		return null;
	}

	private static boolean checkPassword(String encryptedSaltedPassword,
			String salt, String password) {

		return encryptedSaltedPassword.equals(enc(password + salt));
	}

	private static String getRandom(int lentgh) {
		Random random = new Random();
		final String chars = "asdfghjklqwertyuiopzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM0123456789";
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < lentgh; i++) {
			result.append(chars.charAt(random.nextInt(chars.length())));
		}
		return result.toString();
	}

	private static boolean validateEmail(String email) {
		String[] emailAddr = email.split("@");
		if (emailAddr.length != 2 || Util.isEmptyString(emailAddr[0])
				|| Util.isEmptyString(emailAddr[0])) {
			return false;
		}
		return true;
	}

	private static String enc(String toEncrypt) {

		return DigestUtils.md5Hex(toEncrypt);

	}

	/**
	 * 
	 * @param id
	 *            of the user
	 * @return the user bean
	 */
	public static User getDetails(int id) {
		try {
			return UsersManagement.getUser(id);
		} catch (DataBaseException | UserNotFoundException e) {
			UtilLogger.getInstance().log(Level.WARNING, e.getMessage());
			return null;
		}
	}

	public static boolean authorizeUser(int userID, int projectID) {
		
		if (TempClass.projectBelongsToUser(userID, projectID)) {
			return true;
		} else {
			return false;
		}
	}

	public static void updateLastLogin(int userID) {
     try {
		UsersManagement.updateLastLogin(userID);
	} catch (DataBaseException e) {
		UtilLogger.getInstance().log(Level.WARNING, e.getMessage());
		}		
	}
	
	
}
