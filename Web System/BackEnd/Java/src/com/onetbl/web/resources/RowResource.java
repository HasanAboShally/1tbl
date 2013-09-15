package com.onetbl.web.resources;

import java.util.Arrays;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.onetbl.web.utils.RowsUtil;
import com.onetbl.web.utils.UsersUtil;

/**
 * Row Resource Class
 * 
 * @author MGanayim
 * 
 */

@Path("projects/{projectID}/tables/{tableName}/rows/{rowID}")
public class RowResource {
	@Context
	private HttpServletRequest req;
	@PathParam("projectID")
	private int projectID;
	@PathParam("tableName")
	private String tableName;
	@PathParam("rowID")
	private int rowID;

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateTableRow(HashMap<String,String> ur) {
		int userID = UsersUtil.getUserID(req);
		if (userID != -1 && UsersUtil.authorizeUser(userID, projectID)){
			String[] columns = Arrays.copyOf(ur.keySet().toArray(), ur.keySet().toArray().length, String[].class);
			String[] values = Arrays.copyOf(ur.values().toArray(), ur.values().toArray().length, String[].class);

			if (RowsUtil
					.updateRow(projectID, tableName, rowID, columns , values)) {
				return Response.ok().build();
			} else {
				return Response.status(Response.Status.BAD_REQUEST).build();
			}
					} else {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response removeTableRows() {
		long[] index = { rowID };
		int userID = UsersUtil.getUserID(req);
		if (userID != -1 && UsersUtil.authorizeUser(userID, projectID)){
			if (RowsUtil.deleteRows(projectID, tableName, index)) {
				return Response.ok().build();
			} else {
				return Response.status(Response.Status.BAD_REQUEST).build();
			}
		} else {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}

}
