package resource;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import model.Model;
import model.User;

@Path("/login")
public class LoginResource {

	@Context
	ServletContext context;

	private Model model;

	@GET
	@Consumes("application/x-www-form-urlencoded")
	@Produces({ MediaType.APPLICATION_JSON })
	public User loginUser(@HeaderParam("username") String username,
			@HeaderParam("password") String password) {

		model = (Model) context.getAttribute("Model");

		if (model.authorizeUser(username, password) != null) {
			return model.getUserByName(username);
		} else {
			return null;
		}
	}
}
