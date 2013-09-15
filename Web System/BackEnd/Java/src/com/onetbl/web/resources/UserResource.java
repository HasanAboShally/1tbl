package com.onetbl.web.resources;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.onetbl.api.exceptions.UserNotFoundException;
import com.onetbl.db.dao.UsersManagement;
import com.onetbl.db.exceptions.DataBaseException;
import com.onetbl.db.exceptions.ProjectNotFoundException;
import com.onetbl.web.beans.User;
import com.onetbl.web.utils.UsersUtil;

/**
 * Users Resource Class.
 * 
 * @author MGanayim
 * 
 */

@Path("user")
public class UserResource {
	
	@Context 
	private HttpServletRequest req;
	/**
	 * get user info method :
	 * 
	 * return User bean holding user info
	 * 
	 * @return User bean
	 * 
	 *         <!--@author monir-->
	 */


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response details() {
		int userID = UsersUtil.getUserID(req);
		if (userID != -1) {
			return Response.ok().entity(UsersUtil.getDetails(userID))
					.build();
		} else {
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}
 
	
	/**
	 * delete user method :
	 * 
	 * deletes a user from the DB including all his projects
	 * 
	 * @return true if the operation was successful , false if not
	 * 
	 *         <!--@author monir-->
	 */
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response removeUser() {
		int userId = UsersUtil.getUserID(req);
		try {
			if (userId != -1) {
				UsersManagement.removeUser(userId);
				return Response.ok().build();
			} else {
				return Response.status(Response.Status.UNAUTHORIZED).build();
			}
		} catch (DataBaseException | UserNotFoundException
				| ProjectNotFoundException e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}

}
