package com.onetbl.web.resources;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.onetbl.web.beans.Column;
import com.onetbl.web.beans.WebTable;
import com.onetbl.web.utils.TablesUtil;
import com.onetbl.web.utils.UsersUtil;

/**
 * Tables Resource Class
 * 
 * @author MGanayim
 * 
 */

@Path("projects/{projectID}/tables")
public class TablesResource {
	
	@Context 
	private HttpServletRequest req;
	@PathParam("projectID") 
	private int projectID;

	/**
	 * get tables method :
	 * 
	 * return list of tables per project
	 * 
	 * @return ArrayList of tables
	 * 
	 *         <!--@author monir-->
	 */

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTables() {
		int userID = UsersUtil.getUserID(req);
		if (userID != -1 && UsersUtil.authorizeUser(userID, projectID)){
			return Response.ok()
					.entity(TablesUtil.getProjectTables(projectID)).build();
		} else {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}

	/**
	 * insert table method :
	 * get a String array where the first element has the name of the table and the other elements has the columns names 
	 * adds a new table to DB and return a boolean indicating if the addition
	 * was successful
	 * 
	 * @return true if the operation was successful , false if not
	 * 
	 *         <!--@author monir-->
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addTable(ArrayList<String> names) {
		ArrayList<Column> colums = new ArrayList<Column>();
		int userID = UsersUtil.getUserID(req);
		if (userID != -1 && UsersUtil.authorizeUser(userID, projectID)){
			for(String string : names)
				colums.add(new Column(string));
			WebTable table = new WebTable(colums.remove(0).getName(),colums);
			if (TablesUtil.addTable(projectID, table)) {
				return Response.ok().build();
			} else {
				return Response.status(Response.Status.BAD_REQUEST).build();
			}
		} else {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
}
