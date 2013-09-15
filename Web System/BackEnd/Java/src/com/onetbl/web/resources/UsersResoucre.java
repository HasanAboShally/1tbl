package com.onetbl.web.resources;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.onetbl.db.exceptions.UserAlreadyExist;
import com.onetbl.web.beans.User;
import com.onetbl.web.utils.UsersUtil;

/**
 * Users Resource Class.
 * 
 * @author Anas
 * 
 */

@Path("users")
public class UsersResoucre {
	@Context
	HttpServletRequest req;
	

	@POST
	@Path("login")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(@FormParam("username") String userName,
			@FormParam("password") String password) {

		Integer userID = null;
		if ((userID = UsersUtil.login(userName, password)) != null) {
			req.getSession().setAttribute("userID", userID);
			return Response.ok().entity(true).build();
		}
		return Response.ok().entity(false).build();
	}

	@GET
	@Path("logout")
	@Produces(MediaType.APPLICATION_JSON)
	public Response logout() {
		UsersUtil.updateLastLogin(
				UsersUtil.getUserID(req)); 
		req.getSession().invalidate();
		return Response.ok().entity(true).build();
	}



	@POST
	@Path("register")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(@FormParam("username") String username,
			@FormParam("password") String password,
			@FormParam("email") String email,
			@FormParam("firstname") String firstname,
			@FormParam("lastname") String lastname) {

		User user = new User();
		user.setUsername(username);
		user.setEncryptedSaltedPassword(password);
		user.setEmail(email);
		user.setFirstName(firstname);
		user.setLastName(lastname);
		try {
			if (UsersUtil.register(user)) {
				return Response.status(Status.CREATED).entity(true).build();
			} else {
				return Response.status(Status.BAD_REQUEST).entity(2).build();
				// 2 = invalid input (not alpha numeric)
			}
		} catch (UserAlreadyExist e) {
			// 1 = UserAlreadyExists
			
			return Response.status(Status.CONFLICT).entity(1).build();
		}
	}
	
	/**
	 * 
	 * @return whether the user is logged in or not 
	 */
	@GET
	@Path("isloggedin")
	@Produces(MediaType.APPLICATION_JSON)
	public Response isLogged(){
		int userID = UsersUtil.getUserID(req);
		if(userID != -1){
			return Response.ok().entity(true).build();
		}
		return Response.ok().entity(false).build();
	}
}
