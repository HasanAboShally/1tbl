package com.onetbl.web.resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.onetbl.web.beans.ArrayBean;
import com.onetbl.web.beans.Column;
import com.onetbl.web.utils.ColumnsUtil;
import com.onetbl.web.utils.RowsUtil;
import com.onetbl.web.utils.UsersUtil;

/**
 * Rows Resource Class
 * 
 * @author Monir
 * @author MGanayim
 * 
 */

@Path("projects/{projectID}/tables/{tableName}/rows")
public class RowsRasource {
	@Context
	private HttpServletRequest req;
	@PathParam("projectID")
	private int projectID;
	@PathParam("tableName")
	private String tableName;
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTableRows(@DefaultValue("1") @QueryParam("pageNumbe") int pageNumber,
			@DefaultValue("50") @QueryParam("rowsPerPage") int rowsPerPage) {
		int userID = UsersUtil.getUserID(req);
		if (userID != -1 && UsersUtil.authorizeUser(userID, projectID)){
			return Response.ok()
					.entity(RowsUtil.getTableRows(projectID, tableName, pageNumber,rowsPerPage))
					.build();
		} else {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
	
	
	@GET
	@Path("count")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCount(){
		int userID = UsersUtil.getUserID(req);
		long numOfRows;
		if (userID != -1 && UsersUtil.authorizeUser(userID, projectID)){
		numOfRows = RowsUtil.getRowsCount(projectID,tableName);
		if(numOfRows != -1)
			return Response.ok().entity(numOfRows).build();
		
		return Response.status(Response.Status.BAD_REQUEST).build();
	}else{
		return Response.status(Response.Status.UNAUTHORIZED).build();
	}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addTableRow(HashMap<String,String> row) {
		String[] columns = Arrays.copyOf(row.keySet().toArray(), row.keySet().toArray().length, String[].class);
		String[] values = Arrays.copyOf(row.values().toArray(), row.values().toArray().length, String[].class);
		int userID = UsersUtil.getUserID(req);
		long rowID;
		if (userID != -1 && UsersUtil.authorizeUser(userID, projectID)){
			rowID = RowsUtil.addRow(projectID, tableName, columns, values);
			System.out.println(userID);
			if (rowID != -1) {
				return Response.ok().entity(rowID).build();
			} else {
				return Response.status(Response.Status.BAD_REQUEST).build();
			}
		} else {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}

	}
	
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	public Response removeTableRows(ArrayBean ids) {
		
		int userID = UsersUtil.getUserID(req);
		if (userID != -1 && UsersUtil.authorizeUser(userID, projectID)){
			
			if (RowsUtil.deleteRows(projectID, tableName, ids.getLongArr())) {
				return Response.ok().build();
			} else {
				return Response.status(Response.Status.BAD_REQUEST).build();
			}
		} else {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
	
}
