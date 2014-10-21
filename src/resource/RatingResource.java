package resource;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import model.ResponseMessage;
import model.Model;
import model.Movie;
import model.Rating;
import model.User;

@Consumes("application/x-www-form-urlencoded")
@Path("/rating")
public class RatingResource {

	@Context
	ServletContext context;

	private Model model;

	@POST
	@Path("/add/{rating}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Object postNewRating(@HeaderParam("token") String token,
			@HeaderParam("username") String username,
			@HeaderParam("movieID") int movieID, @PathParam("rating") int rating) {

		model = (Model) context.getAttribute("Model");

		if (model.verifyWithToken(token)) {
			return model.addRating(username, movieID, rating);
		}

		ResponseMessage error = new ResponseMessage();
		error.setStatusCode(401);

		return error;
	}

	@DELETE
	@Path("/delete")
	@Produces({ MediaType.APPLICATION_JSON })
	public Object postDeleteRating(@HeaderParam("token") String token,
			@HeaderParam("username") String username,
			@HeaderParam("movieID") int movieID) {

		model = (Model) context.getAttribute("Model");

		if (model.verifyWithToken(token)) {
			return model.removeRating(username, movieID);
		}

		ResponseMessage error = new ResponseMessage();
		error.setStatusCode(401);

		return error;
	}

	@PUT
	@Path("/edit/{newRating}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Object getEditRating(@HeaderParam("token") String token,
			@PathParam("newRating") int newRating,
			@HeaderParam("username") String username,
			@HeaderParam("movieID") int movieID) {

		model = (Model) context.getAttribute("Model");

		if (model.verifyWithToken(token)) {
			return model.editRating(username, movieID, newRating);

		}

		ResponseMessage error = new ResponseMessage();
		error.setStatusCode(401);

		return error;
	}

	@GET
	@Path("/filmratings")
	@Produces({ MediaType.APPLICATION_JSON })
	public Object getRatings(@HeaderParam("token") String token,
			@HeaderParam("movieID") int movieID) {

		model = (Model) context.getAttribute("Model");

		if (model.verifyWithToken(token)) {
			return model.getRatingsFromMovie(movieID);
		}

		ResponseMessage error = new ResponseMessage();
		error.setStatusCode(401);

		return error;
	}

	@GET
	@Path("/ratedfilms")
	@Produces({ MediaType.APPLICATION_JSON })
	public Object getRatedFilms(@HeaderParam("token") String token,
			@HeaderParam("username") String username) {

		model = (Model) context.getAttribute("Model");

		// TODO: get user by acccesstoken
		if (model.verifyWithToken(token)) {
			User user = model.getUserByName(username);
			return model.getRatingsFromUser(user);
		}

		ResponseMessage error = new ResponseMessage();
		error.setStatusCode(401);

		return error;
	}
}