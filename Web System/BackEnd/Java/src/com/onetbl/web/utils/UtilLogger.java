package com.onetbl.web.utils;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class UtilLogger {
	private final static Logger utiLogger;
	static private FileHandler fileTxt;
	static private SimpleFormatter formatterTxt;
	
	static{
		utiLogger = Logger.getLogger("util");
		try {
			fileTxt = new FileHandler("DBLogging.txt");
			 // Create txt Formatter
		    formatterTxt = new SimpleFormatter();
		    fileTxt.setFormatter(formatterTxt);
		    utiLogger.addHandler(fileTxt);
		} catch (SecurityException | IOException e) {
			utiLogger.log(Level.SEVERE, e.getMessage());
		}
		
	}
	
	public synchronized static Logger getInstance(){
		return utiLogger;
	}
}
