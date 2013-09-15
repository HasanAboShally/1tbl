package com.onetbl.api.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.onetbl.api.exceptions.InvalidCredentialsException;
import com.onetbl.api.exceptions.InvalidDeleteQueryException;
import com.onetbl.api.exceptions.InvalidInsertQueryException;
import com.onetbl.api.exceptions.InvalidSelectQueryException;
import com.onetbl.api.exceptions.InvalidUpdateQueryException;
import com.onetbl.api.models.CredentialsRequest;
import com.onetbl.api.models.DeleteRequest;
import com.onetbl.api.models.InsertRequest;
import com.onetbl.api.models.SelectRequest;
import com.onetbl.api.models.UpdateRequest;
import com.onetbl.db.dao.DynamicDML;
import com.onetbl.db.dao.TempClass;
import com.onetbl.db.exceptions.DataBaseException;

public class TableUtil {

	public static int delete(DeleteRequest req, String DBName)
			throws InvalidDeleteQueryException {

		try {
			String wc = req.getWhereClause();
			wc = (wc == null) ? "" : wc;

			return DynamicDML.deleteRow(DBName, req.getTableName(), wc);

		} catch (DataBaseException e) {
			throw new InvalidDeleteQueryException();
		}

	}

	public static int update(UpdateRequest req, String DBName)
			throws InvalidUpdateQueryException {

		try {

			String[] columns = req.getSetColumns();
			String[] values = req.getSetValues();
			if (columns == null || values == null
					|| columns.length != values.length || columns.length == 0)
				throw new InvalidUpdateQueryException();

			String wc = req.getWhereClause();
			wc = (wc == null) ? "" : wc;

			return DynamicDML.updateRow(DBName, req.getTableName(), columns,
					values, wc);

		} catch (DataBaseException e) {
			throw new InvalidUpdateQueryException();
		}

	}

	public static int insert(InsertRequest req, String DBName)
			throws InvalidInsertQueryException {

		try {

			String[] columns = req.getColumns();
			String[] values = req.getValues();
			if (columns == null || values == null
					|| columns.length != values.length || columns.length == 0)
				throw new InvalidInsertQueryException();

			if(DynamicDML.insertIntoTable(DBName, req.getTableName(),
					columns, values) != -1)
				return 1;

		} catch (DataBaseException e) {
			throw new InvalidInsertQueryException();
		}
      return 0;
	}

	public static List<HashMap<String, String>> select(SelectRequest req,
			String DBName) throws InvalidSelectQueryException {

		try {

			String[] columns = req.getColumns();
			if (columns == null /*|| columns.length == 0*/)// TODO shoulf allow 0 if agg exists
				throw new InvalidSelectQueryException();

			String wc = req.getWhereClause();
			wc = (wc == null) ? "" : wc;

			return DynamicDML.getRowsByPage(DBName, req.getTableName(), wc,
					req.getAggregate(), 1,-1, columns);

		} catch (DataBaseException e) {
			throw new InvalidSelectQueryException();
		}

	}

	public static String validateCredentialsAndGetDBByToken(CredentialsRequest r)
			throws InvalidCredentialsException {

		if (!validateCredentials(r.getUserID(), r.getToken(), r.getTableName())) {

			throw new InvalidCredentialsException();
		}
		return getDBNameByToken(r.getToken());

	}

	public static boolean validateCredentials(String userID,
			String projectToken, String tableName) {

		if (TempClass.tableExists(Long.parseLong(userID), projectToken,
				tableName))
			return true;

		/*
		 * if (userID.equals("userID") && projectToken.equals("thisIsAToken") &&
		 * tableName.equals("SDKTestTable")) { return true; }
		 */
		return false;

	}

	public static String getDBNameByToken(String token) {

		return TempClass.getProjectNameByToken(token);
		// return "test";

	}

	public static String listHashMaptoJson(List<HashMap<String, String>> l) {

		Writer strWriter = new StringWriter();
		ObjectMapper om = new ObjectMapper();

		try {
			om.writeValue(strWriter, l);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String userDataJSON = strWriter.toString();

		return userDataJSON;
	}

	public static void main(String[] args) {

		System.out.println(validateCredentials("3", "tokenA", "SDKTestTable"));

		System.out.println(getDBNameByToken("tokenA"));
	}

}
