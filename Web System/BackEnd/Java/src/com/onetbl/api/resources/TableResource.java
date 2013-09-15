package com.onetbl.api.resources;

import java.util.HashMap;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.xml.bind.JAXBElement;

import com.onetbl.api.exceptions.InvalidCredentialsException;
import com.onetbl.api.exceptions.InvalidDeleteQueryException;
import com.onetbl.api.exceptions.InvalidInsertQueryException;
import com.onetbl.api.exceptions.InvalidSelectQueryException;
import com.onetbl.api.exceptions.InvalidUpdateQueryException;
import com.onetbl.api.models.*;
import com.onetbl.api.utils.TableUtil;

/**
 * Table Resource Class. Receives API requests from the SDK. Responsible for DML
 * queries on a single table
 * 
 * @author MGanayim
 * 
 */

@Path("/")
// @Consumes(MediaType.APPLICATION_JSON)
// @Produces(MediaType.APPLICATION_JSON)
public class TableResource {

	/*
	 * @Context Request request;
	 */

	@GET
	@Path("/credentials")
	@Produces(MediaType.TEXT_HTML)
	public String sayHtmlHello() {
		return "Hello from credentials";
	}

	@POST
	@Path("/credentials")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public OneResponse checkCredentials(CredentialsRequest req) {
		// TODO userID
		if (TableUtil.validateCredentials(req.getUserID(), req.getToken(),
				req.getTableName())) {
			return new OneResponse(true, null);
		} else {

			return new OneResponse(false, "Invalid credentials");
		}
	}

	@POST
	@Path("/select")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public OneSelectResponse slelect(SelectRequest req) {

		try {
			String DBName = TableUtil.validateCredentialsAndGetDBByToken(req);

			List<HashMap<String, String>> result = TableUtil
					.select(req, DBName);

			return new OneSelectResponse(true, result);

		} catch (InvalidCredentialsException e) {
			return new OneSelectResponse(false,
					InvalidCredentialsException.API_ERROR);

		} catch (InvalidSelectQueryException e) {
			return new OneSelectResponse(false,
					InvalidSelectQueryException.API_ERROR);
		}

	}

	@POST
	@Path("/insert")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public OneResponse insert(InsertRequest req) {

		try {
			String DBName = TableUtil.validateCredentialsAndGetDBByToken(req);

			TableUtil.insert(req, DBName);

			return new OneResponse(true);

		} catch (InvalidCredentialsException e) {
			return new OneResponse(false, InvalidCredentialsException.API_ERROR);
		} catch (InvalidInsertQueryException e) {
			return new OneResponse(false, InvalidInsertQueryException.API_ERROR);
		}

	}

	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public OneResponse update(UpdateRequest req) {
		try {
			String DBName = TableUtil.validateCredentialsAndGetDBByToken(req);
			
			int rows = TableUtil.update(req, DBName);

			return new OneResponse(true,String.valueOf(rows));

		} catch (InvalidCredentialsException e) {
			return new OneResponse(false, InvalidCredentialsException.API_ERROR);
		} catch (InvalidUpdateQueryException e) {
			return new OneResponse(false, InvalidUpdateQueryException.API_ERROR);
		}
	}

	@POST
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public OneResponse delete(DeleteRequest req) {
		try {
			String DBName = TableUtil.validateCredentialsAndGetDBByToken(req);

			int rows = TableUtil.delete(req, DBName);

			return new OneResponse(true,String.valueOf(rows));

		} catch (InvalidCredentialsException e) {
			return new OneResponse(false, InvalidCredentialsException.API_ERROR);
		} catch (InvalidDeleteQueryException e) {
			return new OneResponse(false, InvalidDeleteQueryException.API_ERROR);
		}
	}

	public static void main(String[] args) {

	}

}
