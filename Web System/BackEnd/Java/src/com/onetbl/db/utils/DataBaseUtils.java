package com.onetbl.db.utils;

import java.util.HashMap;
import java.util.Random;

public class DataBaseUtils {

	
	/**
	 * Gets an Array of strings and appends them together separated
	 * by commas into a single string.
	 * @param strings
	 * @return
	 */
	public static String separateStringsByCommas(String[] strings){
		
		if(strings == null){
			return null;
		}
		
		StringBuilder sb = new StringBuilder();
		
		for(int i=0; i<strings.length; i++){
			sb.append(strings[i]);
			sb.append(",");
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();		
	}
	
	
	public static String columnsToUpdate(String[] columns,String[] values){
		
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<columns.length; i++){
			sb.append(columns[i]).append("=").append("'"+values[i]+"'");
			sb.append(",");
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
	

	
	
	
	/**
	 * Testing
	 * @param args
	 */
	public static void main(String[] args) {
		
		
	}

}
