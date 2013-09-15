package com.onetbl.web.utils;

import com.onetbl.web.exceptions.InvalidInputException;

public class Validation {
	
	public static void alphanumeric(String name) throws InvalidInputException{
		int ascii ;
		if(name.isEmpty()) 
			throw new InvalidInputException();
		for(int i=0; i < name.length(); i++){
		   ascii = (int) name.charAt(i);
		    if(ascii < 48 || ascii > 57 && ascii < 65 ||
		    		ascii > 90 && ascii < 97|| ascii > 122){
		    	throw new InvalidInputException();
		    }
		}
}
	
	
		
	}
