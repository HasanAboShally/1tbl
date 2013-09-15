package com.onetbl.web.resources;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.onetbl.web.beans.Project;
import com.onetbl.web.utils.ProjectsUtil;
import com.onetbl.web.utils.UsersUtil;

/**
 * Projects Resource Class.
 * 
 * @author MGanayim
 * 
 */

@Path("projects")
public class ProjectsResource {
	@Context
	private HttpServletRequest req;

	/**
	 * the select method :
	 * 
	 * gets all projects list of this user
	 * 
	 * @return the projects list for the current user
	 * 
	 *         <!--Programmed by monir-->
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProjects() {

		int userId = UsersUtil.getUserID(req);
		if (userId > 0) {
			ArrayList<Project> l = ProjectsUtil.getUserProjects(userId);
			return Response.ok().entity(l).build();
		}

		return Response.status(Response.Status.UNAUTHORIZED).build();

	}

	/**
	 * the insert method :
	 * 
	 * adds new project to DB under the current user
	 * 
	 * @return true if the operation was successful , false if not
	 * 
	 *         <!--Programmed by monir-->
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addProject(Project p) {
		int userId = UsersUtil.getUserID(req);
		Project newProj = null;
		if (userId > 0) {
			p.setOwnerId(userId);
			newProj = ProjectsUtil.addProject(p) ;
			if (newProj != null) {
				return Response.status(Response.Status.CREATED).entity(newProj)
						.build();
			} else {
				return Response.status(Response.Status.NOT_ACCEPTABLE).build();
			}

		}
		return Response.status(Response.Status.UNAUTHORIZED).build();

	}

}
