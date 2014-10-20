package resource;

import javax.servlet.ServletContext;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import model.Model;
import model.Movie;

@Path("/movie")
public class MovieResource {

	@Context
	ServletContext context;

	private Model model;

	@GET
	@Path("/movielist")
	@Produces({ MediaType.TEXT_PLAIN })
	public String getMovies(@HeaderParam("token") String token) {

		model = (Model) context.getAttribute("Model");

		// user has to be logged in for access
		if (model.verifyWithToken(token)) {
			String movies = "movies: \n";

			for (Movie movie : model.getMovies()) {
				model.getMovies();
				movies += movie.getTitle() + "\n";
			}
			return movies;
		} else {
			return "Not authorized";
		}

	}

	@GET
	@Path("/{id}")
	@Produces({ MediaType.TEXT_PLAIN })
	public String getMovieByName(@HeaderParam("token") String token,
			@PathParam("id") int movieID) {

		model = (Model) context.getAttribute("Model");

		// user has to be logged in for access
		if (model.verifyWithToken(token)) {
			for (Movie movie : model.getMovies()) {
				if (movie.getMovieID() == movieID) {
					return movie.getTitle();
				}
			}
			return "Movie not found";
		} else {
			return "Not authorized";
		}
	}
}