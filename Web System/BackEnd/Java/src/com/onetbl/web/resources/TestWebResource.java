package com.onetbl.web.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.onetbl.web.beans.Column;
import com.onetbl.web.beans.ReplyBean;
import com.onetbl.web.beans.WebTable;
import com.sun.jersey.api.view.Viewable;

@Path("/hello-web")
public class TestWebResource {

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sayHtmlHello(
			@DefaultValue("defVal") @QueryParam("q") String name) {
		return "Hello" + name;
	}

	@GET
	@Path("page")
	@Produces(MediaType.TEXT_HTML)
	public Viewable sayJSOHello(
			@DefaultValue("defVal") @QueryParam("q") String name) {
		return new Viewable("/WEB-INF/jsp/test.jsp", name);
	}

	@GET
	@Path("/kuku")
	@Produces(MediaType.TEXT_HTML)
	public String sayHtmlkuku(
			@DefaultValue("defname") @QueryParam("q") String name) {
		return "Hello kuku: " + name;
	}

	@GET
	@Path("/getJsonObject")
	@Produces(MediaType.APPLICATION_JSON)
	public ReplyBean getJsonObject() {
		ReplyBean rp = new ReplyBean();
		List<WebTable> l = new ArrayList<WebTable>();
		// l = ProjectsManagement.getProjectTables(projectID);
		if (123 == 123) {
			// WebTable c1 = new WebTable(10, "table1");
			// l.add(c1);
			// l.add(c2);
			// Gson gson = new Gson();
			// String jsonStr = gson.toJson(l);
			// rp.setSuccess(true);
			// //rp.setReturnedValue(jsonStr);
			//
			// System.out.println("befor add list to respons bean class");
			// //rp.setReturnedValue(l);
			// System.out.println("after respons list");
			//
			// rp.setMessage(ReplyBean.SUCCESS);
			//
			// /*
			// Map<String, Object> map = new HashMap<String, Object>();
			// map.put("user", "Atallah Sanallah ");
			// List<String> l2 = new ArrayList<String>();
			// l2.add("King Of The JSP");
			// l2.add("Returned Model Viewd In Jsp");
			// map.put("items", l2);
			// rp.setReturnedValue(map);*/
			//
			// System.out.println("befor return");

		}
		return rp;
	}

	public static void main(String[] args) {
	}

}
