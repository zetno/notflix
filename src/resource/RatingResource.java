package resource;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
@Path("/rating")
public class RatingResource {

	@Context
	ServletContext context;

	private Model model;

	@POST
	@Path("/add/{rating}")
	@Produces({ MediaType.APPLICATION_XML })
	public String postNewRating(@HeaderParam("token") String token,
			@HeaderParam("username") String username,
			@HeaderParam("movieID") int movieID, @PathParam("rating") int rating) {

		model = (Model) context.getAttribute("Model");

		if (model.verifyWithToken(token)) {
			return model.addRating(username, movieID, rating);
		}

		return "failed";
	}

	@DELETE
	@Path("/delete")
	@Produces({ MediaType.APPLICATION_XML })
	public String postDeleteRating(@HeaderParam("token") String token,
			@HeaderParam("username") String username,
			@HeaderParam("movieID") int movieID) {

		model = (Model) context.getAttribute("Model");

		if (model.verifyWithToken(token)) {
			return model.removeRating(username, movieID);
		}

		return "failed";
	}

	@GET
	@Path("/edit")
	@Produces({ MediaType.APPLICATION_XML })
	public String getEditRating(@HeaderParam("token") String token) {

		model = (Model) context.getAttribute("Model");

		return "successfull";
	}

	@GET
	@Path("/filmratings")
	@Produces({ MediaType.APPLICATION_XML })
	public String getRatings(@HeaderParam("token") String token) {

		model = (Model) context.getAttribute("Model");

		return "successfull";
	}

	@GET
	@Path("/ratedfilms")
	@Produces({ MediaType.APPLICATION_XML })
	public String getRatedFilms(@HeaderParam("token") String token) {

		model = (Model) context.getAttribute("Model");

		return "successfull";
	}

}