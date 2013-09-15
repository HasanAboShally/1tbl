package com.onetbl.web.resources;

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
import com.onetbl.web.utils.ColumnsUtil;
import com.onetbl.web.utils.UsersUtil;

/**
 * Column Resource Class
 * 
 * @author Monir
 * @Manger MGanayim
 * 
 */

@Path("projects/{projectID}/tables/{tableName}/columns/{columnName}")
@Produces(MediaType.TEXT_HTML)
public class ColumnResource {
	
	@Context HttpServletRequest req;
	@PathParam("projectID") int projectID;
	@PathParam("tableName") String tableName;
	@PathParam("columnName") String columnName;
	
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateTableColumn(String newName) {
		if (columnName.equals(newName)) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		int userID = UsersUtil.getUserID(req);
		if (userID != -1 && UsersUtil.authorizeUser(userID, projectID)){
			if (ColumnsUtil.updateTableColumn(projectID, tableName, columnName,
					newName)) {
				return Response.status(Response.Status.ACCEPTED).build();
			} else {
				return Response.status(Response.Status.BAD_REQUEST).build();
			}
		} else {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response removeTableColumn() {
		int userID = UsersUtil.getUserID(req);
		if (userID != -1 && UsersUtil.authorizeUser(userID, projectID)){
			if (ColumnsUtil.deleteTableColumn(projectID, tableName, columnName)) {
				return Response.ok().build();
			} else {
				return Response.status(Response.Status.BAD_REQUEST).build();
			}
		} else {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
}
