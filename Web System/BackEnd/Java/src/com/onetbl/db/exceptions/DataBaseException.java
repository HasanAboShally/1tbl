package com.onetbl.db.exceptions;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class DataBaseException extends Exception {

	private final static Logger DBLogger;
	private static final long serialVersionUID = 1L;
	private String message;
	static private FileHandler fileTxt;
	static private SimpleFormatter formatterTxt;
	
	static{
		DBLogger = Logger.getLogger("DB");
		try {
			fileTxt = new FileHandler("DBLogging.txt");
			 // Create txt Formatter
		    formatterTxt = new SimpleFormatter();
		    fileTxt.setFormatter(formatterTxt);
		    DBLogger.addHandler(fileTxt);
		} catch (SecurityException | IOException e) {
			DBLogger.log(Level.SEVERE, e.getMessage());
		}
	}
	public DataBaseException(String msg){
		super();
		message = msg;
		DBLogger.log(Level.SEVERE, msg);
	}
	
	public String getMessage(){
		return message;
	}
}
