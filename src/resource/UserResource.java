package resource;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

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

		User user = new User(firstname, middlename, surname, username, password);

		model = (Model) context.getAttribute("Model");

		if (model.addUser(user)) {
			return user;
		}
		return new ResponseMessage(400);
	}
}