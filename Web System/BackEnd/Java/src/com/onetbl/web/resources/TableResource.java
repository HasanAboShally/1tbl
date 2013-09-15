package com.onetbl.web.resources;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.onetbl.web.utils.TablesUtil;
import com.onetbl.web.utils.UsersUtil;

/**
 * Table Resource Class
 * 
 * @author MGanayim
 * 
 */

@Path("projects/{projectID}/tables/{tableName}")
public class TableResource {
	
	@Context 
	private HttpServletRequest req;
	@PathParam("projectID") 
	private int projectID;
	@PathParam("tableName") 
	private String tableName;

	/**
	 * update table method :
	 * 
	 * updates a given table information in DB and return a boolean indication
	 * if the update was successful
	 * 
	 * @return true if the operation was successful , false if not
	 * 
	 *         <!--@author monir-->
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateTable(String newName){
		int userID = UsersUtil.getUserID(req);
		if (userID != -1 && UsersUtil.authorizeUser(userID, projectID)){
			if (TablesUtil.renameTable(projectID, tableName, newName)) {
				return Response.ok().build();
			} else {
				return Response.status(Response.Status.BAD_REQUEST).build();
			}
		} else {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}

	/**
	 * delete table method :
	 * 
	 * updates a given table information in DB and return a boolean indication
	 * if the update was successful
	 * 
	 * @return true if the operation was successful , false if not
	 * 
	 *         <!--@author monir-->
	 */
	@DELETE
	public Response deleteTable() {
		int userID = UsersUtil.getUserID(req);
		if (userID != -1 && UsersUtil.authorizeUser(userID, projectID)){
			if (TablesUtil.deleteTable(projectID, tableName)) {
				return Response.ok().build();
			} else {
				return Response.status(Response.Status.BAD_REQUEST).build();
			}
		} else {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
	
	@GET 
	@Path("csv")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCSV(){
		int userID = UsersUtil.getUserID(req);
		String url;
		if (userID != -1 && UsersUtil.authorizeUser(userID, projectID)){
			url = TablesUtil.getTableCSV(projectID,tableName);
					if(url != null){
			return Response.ok().build();
			} else {
				return Response.status(Response.Status.BAD_REQUEST).build();
			}
		} else {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
}
