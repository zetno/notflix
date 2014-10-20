package resource;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import listener.ContextListener;
import model.Model;

@Path("/login")
public class LoginResource {

	@Context
	ServletContext context;

	private Model model;

	@GET
	@Produces({ MediaType.TEXT_PLAIN })
	public String loginUser(@HeaderParam("username") String username,
			@HeaderParam("password") String password) {

		model = (Model) context.getAttribute("Model");

		if (model.authorizeUser(username, password) != null) {
			return "GELUKT met token:"
					+ model.authorizeUser(username, password);
		} else {
			return "login failed";
		}
	}
}
