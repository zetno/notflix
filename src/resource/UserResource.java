package resource;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.PathParam;

import model.Model;
import model.ResponseMessage;
import model.User;

@Consumes("application/x-www-form-urlencoded")
@Path("/user")
public class UserResource {

	@Context
	ServletContext context;

	private Model model;

	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/add")
	public Object postNewUser(@HeaderParam("firstname") String firstname,
			@HeaderParam("middlename") String middlename,
			@HeaderParam("surname") String surname,
			@HeaderParam("username") String username,
			@HeaderParam("password") String password) {

		if (middlename == null) {
			middlename = "";
		}
		if (surname != null && firstname != null && username != null
				&& password != null) {
			User user = new User(firstname, middlename, surname, username,
					password);

			model = (Model) context.getAttribute("Model");

			if (model.addUser(user)) {
				return user;
			}
		}

		return new ResponseMessage(400);
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/{username}")
	public Object getUserByUsername(@HeaderParam("token") String token,
			@PathParam("username") String username) {

		model = (Model) context.getAttribute("Model");

		if (model.verifyWithToken(token)) {
			return model.getUserByUsername(username);
		}
		return new ResponseMessage(401);
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/userlist")
	public Object getUserlist(@HeaderParam("token") String token) {

		model = (Model) context.getAttribute("Model");

		if (model.verifyWithToken(token)) {
			return model.getUsers();
		}
		return new ResponseMessage(401);
	}
}