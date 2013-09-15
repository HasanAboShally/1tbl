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

import com.onetbl.web.beans.Project;
import com.onetbl.web.utils.ColumnsUtil;
import com.onetbl.web.utils.ProjectsUtil;
import com.onetbl.web.utils.UsersUtil;

import javax.ws.rs.core.Response;

/**
 * Projects Resource Class.
 * 
 * @author MGanayim
 * 
 */

@Path("projects/{projectID}")
public class ProjectResource {

	@Context
	private HttpServletRequest req;
	@PathParam("projectID") 
	private int projectID;
	
	
	/**
	 * 
	 * @return the details of the current project
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response details(){
		int userID = UsersUtil.getUserID(req);
		if (userID != -1 && UsersUtil.authorizeUser(userID, projectID)) {
			return Response.ok().
					entity(ProjectsUtil.getProject(
							userID, projectID))
					.build();
		} else {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
		
	}
	/**
	 * the delete method :
	 * 
	 * deletes a project from DB under the current user
	 * 
	 * @return true if the operation was successful , false if not
	 * 
	 *         <!--Programmed by monir-->
	 */
	@DELETE
	public Response deleteProject() {
		int userID = UsersUtil.getUserID(req);
		if (userID != -1 && UsersUtil.authorizeUser(userID, projectID)) {
			if (ProjectsUtil.deleteProject(ProjectsUtil.getProject(userID,
					projectID))) {
				return Response.ok().build();
			} else {
				return Response.status(Response.Status.NOT_ACCEPTABLE).build();
			}
		} else {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}

	/**
	 * the update method :
	 * 
	 * deletes a project from DB under the current user
	 * 
	 * @return true if the operation was successful , false if not
	 * 
	 *         <!--Programmed by monir-->
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateProject(Project project) {
		int userID = UsersUtil.getUserID(req);
		if (userID != -1 && UsersUtil.authorizeUser(userID, projectID)) {
			if (ProjectsUtil.updateProject(userID, project)) {
				return Response.ok().build();
			} else {
				return Response.status(Response.Status.NOT_ACCEPTABLE).build();
			}

		} else {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
}
