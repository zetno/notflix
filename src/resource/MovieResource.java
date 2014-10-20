package resource;

import javax.ws.rs.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.Model;
import model.Movie;

@Path("/movies")
public class MovieResource {

	@GET
	@Path("/movielist")
	@Produces({ MediaType.TEXT_PLAIN })
	public String getMovies() {

		String movies = "movies: \n";

		for (Movie movie : Model.getInstance().getMovies()) {
			Model.getInstance().getMovies();
			movies += movie.getTitle() + "\n";
		}
		return movies;
	}

	@GET
	@Path("/getmovie/{id}")
	@Produces({ MediaType.TEXT_PLAIN })
	public String getMovieByName(@PathParam("id") int movieID) {

		for (Movie movie : Model.getInstance().getMovies()) {
			if (movie.getMovieID() == movieID) {
				return movie.getTitle();
			}
		}
		return "Movie not found";
	}

}