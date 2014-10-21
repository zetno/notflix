package resource;

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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import model.ResponseMessage;
import model.Model;
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
			@HeaderParam("ttID") int ttID, @PathParam("rating") int rating) {

		model = (Model) context.getAttribute("Model");

		if (model.verifyWithToken(token)) {
			return model.addRating(token, ttID, rating);
		}
		return new ResponseMessage(401);
	}

	@DELETE
	@Path("/delete")
	@Produces({ MediaType.APPLICATION_JSON })
	public Object postDeleteRating(@HeaderParam("token") String token,
			@HeaderParam("movieID") int ttID) {

		model = (Model) context.getAttribute("Model");

		if (model.verifyWithToken(token)) {
			return model.removeRating(token, ttID);
		}
		return new ResponseMessage(401);
	}

	@PUT
	@Path("/edit/{newRating}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Object getEditRating(@HeaderParam("token") String token,
			@PathParam("newRating") int newRating, @HeaderParam("ttID") int ttID) {

		model = (Model) context.getAttribute("Model");

		if (model.verifyWithToken(token)) {
			return model.editRating(token, ttID, newRating);

		}
		return new ResponseMessage(401);
	}

	@GET
	@Path("/filmratings")
	@Produces({ MediaType.APPLICATION_JSON })
	public Object getRatings(@HeaderParam("token") String token,
			@HeaderParam("ttID") int ttID) {

		model = (Model) context.getAttribute("Model");

		if (model.verifyWithToken(token)) {
			return model.getRatingsFromMovie(ttID);
		}
		return new ResponseMessage(401);
	}

	@GET
	@Path("/ratedfilms")
	@Produces({ MediaType.APPLICATION_JSON })
	public Object getRatedFilms(@HeaderParam("token") String token) {

		model = (Model) context.getAttribute("Model");

		// TODO: get user by acccesstoken
		if (model.verifyWithToken(token)) {
			User user = model.getUserWithToken(token);
			return model.getRatingsFromUser(user);
		}
		return new ResponseMessage(401);
	}

	@GET
	@Path("/overallfilmrating")
	@Produces({ MediaType.APPLICATION_JSON })
	public Object getOverallRating(@HeaderParam("token") String token,
			@HeaderParam("ttID") int ttID) {

		model = (Model) context.getAttribute("Model");

		if (model.verifyWithToken(token)) {
			return model.getOverallRatingFromMovie(ttID);
		}
		return new ResponseMessage(401);
	}
}