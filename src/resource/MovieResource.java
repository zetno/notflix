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
	public ArrayList<Movie> getMovies(@HeaderParam("token") String token) {

		model = (Model) context.getAttribute("Model");

		movies = new ArrayList<Movie>();

		// user has to be logged in for access
		if (model.verifyWithToken(token)) {

			for (Movie movie : model.getMovies()) {
				model.getMovies();
				movies.add(movie);
			}
			return movies;
		} else {
			return null;
			// throw new WebApplicationException();
		}
	}

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Movie getMovieByName(@HeaderParam("token") String token,
			@PathParam("id") int movieID) {

		model = (Model) context.getAttribute("Model");

		// user has to be logged in for access
		if (model.verifyWithToken(token)) {
			for (Movie movie : model.getMovies()) {
				if (movie.getMovieID() == movieID) {
					return movie;
				}
			}
			return null;
			// throw new WebApplicationException();
		} else {
			return null;
			// throw new WebApplicationException();
		}
	}
}