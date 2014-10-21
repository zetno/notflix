package resource;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import model.Model;
import model.Movie;
import model.ResponseMessage;

@Consumes("application/x-www-form-urlencoded")
@Path("/movie")
public class MovieResource {

	@Context
	ServletContext context;

	private Model model;

	private ArrayList<Movie> movies;

	@GET
	@Path("/movielist")
	@Produces({ MediaType.APPLICATION_JSON })
	public Object getMovies(@HeaderParam("token") String token) {

		model = (Model) context.getAttribute("Model");

		// user has to be logged in for access
		if (model.verifyWithToken(token)) {
			return model.getMovies();
		} else {
			return new ResponseMessage(401);
		}
	}

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Object getMovieByName(@HeaderParam("token") String token,
			@PathParam("id") int ttNr) {

		model = (Model) context.getAttribute("Model");

		// user has to be logged in for access
		if (model.verifyWithToken(token)) {
			return model.getMovieByID(ttNr);
		} else {
			return new ResponseMessage(401);

		}
	}
}