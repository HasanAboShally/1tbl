package com.onetbl.web.resources;

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
import com.onetbl.web.utils.ColumnsUtil;
import com.onetbl.web.utils.UsersUtil;

/**
 * Columns Resource Class
 * 
 * @author Monir
 * @Manger MGanayim
 * 
 */

@Path("projects/{projectID}/tables/{tableName}/columns")
public class ColumnsResource {
	@Context private HttpServletRequest req;
	@PathParam("projectID") private int projectID;
	@PathParam("tableName") private String tableName;
	
	/*
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTableColumns() {
		int userID = UsersUtil.getUserID(req);
		if (userID != -1 && UsersUtil.authorizeUser(userID, projectID)){
			return Response.ok()
					.entity(ColumnsUtil.getTableColumns(projectID, tableName))
					.build();
		} else {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
*/
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addTableColumn(Column c) {
		int userID = UsersUtil.getUserID(req);
		if (userID != -1 && UsersUtil.authorizeUser(userID, projectID)){
			if (ColumnsUtil.addTableColumn(projectID, tableName, c)) {
				return Response.ok().build();
			} else {
				return Response.status(Response.Status.BAD_REQUEST).build();
			}
		} else {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}

	}
}
