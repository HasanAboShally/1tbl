package com.onetbl.api.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.onetbl.api.exceptions.InvalidCredentialsException;
import com.onetbl.api.models.OneResponse;
import com.onetbl.api.models.OneSelectResponse;
import com.onetbl.api.models.SelectRequest;
import com.onetbl.api.utils.TableUtil;

@Path("/hello-api")

public class TestAPIResource {

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sayHtmlHello(
			@DefaultValue(" default") @QueryParam("q") String name){
		return "Hello" + name;
	}
	
	@POST
	@Path("/select")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public OneSelectResponse select() {
		
		ArrayList<HashMap<String, String>> l = new ArrayList<HashMap<String, String>>();

		
		for (int i = 0; i < 2; i++) {
			HashMap<String, String> h = new HashMap<String, String>();
			for (int j = 0; j < 3; j++) {
				h.put("column" + j, "value" + j);
			}
			l.add(h);
		}
		
		HashMap<String, String> h = new HashMap<String, String>();
		for (int j = 0; j < 3; j++) {
			h.put("column" + j, "value" + j);
		}
		
		return new OneSelectResponse(true, l);
	}
	
	public static void main(String[] args) {

	}

}
