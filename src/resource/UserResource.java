package resource;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import model.Model;
import model.Movie;

@Consumes("application/x-www-form-urlencoded")
@Path("/user")
public class UserResource {

	@Context
	ServletContext context;

	private Model model;


	@POST
	@Path("/add")
	@Produces({ MediaType.APPLICATION_XML })
	public String postNewUser(@HeaderParam("token") String token) {
		
		model = (Model) context.getAttribute("Model");
		
		return "successfull";
	}
}