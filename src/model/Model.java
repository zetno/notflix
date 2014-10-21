package model;

import java.util.ArrayList;
import java.util.Date;

public class Model {

	private ArrayList<Movie> movies;
	private ArrayList<User> users;
	private ArrayList<Rating> ratings;

	public Model() {
		movies = new ArrayList<Movie>();
		users = new ArrayList<User>();
		ratings = new ArrayList<Rating>();

		User u1 = new User();
		u1.setUsername("Jan");
		u1.setPassword("ww");
		u1.setAccessToken("AAAA");
		users.add(u1);

		Movie m1 = new Movie();
		m1.setTitle("The Movie");
		m1.setMovieID(1);
		m1.setTtNr(123);
		m1.setLength(120);
		m1.setProducer("M. Lemson");
		m1.setDescription("A great movie");

		Movie m2 = new Movie();
		m2.setTitle("The Movie Part twos");
		m2.setMovieID(2);
		m2.setTtNr(123);
		m2.setLength(120);
		m2.setProducer("M. Lemson");
		m2.setDescription("Another great movie");

		Movie m3 = new Movie();
		m3.setTitle("Movie the 3th");
		m3.setMovieID(3);
		m3.setTtNr(123);
		m3.setLength(120);
		m3.setProducer("M. Lemson");
		m3.setDescription("Bad");
		movies.add(m1);
		movies.add(m2);
		movies.add(m3);

	}

	public String addRating(String username, int movieID, int rating) {
		if (doesMovieExists(movieID)) {
			if (!doesRatingExists(movieID, username)) {
				User user = getUserByName(username);
				Movie movie = getMovieByID(movieID);

				Rating r = new Rating(user, movie, rating);
				ratings.add(r);
				return "rated";
			} else {
				return "already rated";
			}
		} else {
			return "movie doesn't exist";
		}

	}

	public String removeRating(String username, int movieID) {

		// TODO: controleren of accesstoken bij gebruiker hoort

		if (doesMovieExists(movieID)) {
			if (doesRatingExists(movieID, username)) {
				for (Rating r : ratings) {
					System.out.println("MOVIEID  " + r.getMovie().getMovieID());

					if (r.getUser().getUsername().equals(username)
							&& r.getMovie().getMovieID() == movieID) {
						ratings.remove(r);
						return "succesfull";
					}
				}
			} else {
				return "no rating";
			}

		} else {
			return "movie doesn't exists";
		}
		return "failed";
	}
	
	public boolean addUser(User newUser){
		for (User user : users) {
			if(user.getUsername().equals(newUser.getUsername())){
				return false;
			}
		}
		
		users.add(newUser);
		return true;
	}

	public ArrayList<Movie> getMovies() {
		return movies;
	}

	public ArrayList<User> getUsers() {
		return users;
	}

	public ArrayList<Rating> getRatings() {
		return ratings;
	}

	public ArrayList<Rating> getRatingsFromUser(User user) {

		ArrayList<Rating> ratingList = new ArrayList<Rating>();

		for (Rating rating : ratings) {
			if (rating.getUser().equals(user)) {
				ratingList.add(rating);
			}
		}
		return ratingList;
	}

	public ArrayList<Rating> getRatingsFromMovie(Movie movie) {

		ArrayList<Rating> ratingList = new ArrayList<Rating>();

		for (Rating rating : ratingList) {
			if (rating.getMovie().equals(movie)) {
				ratingList.add(rating);
			}
		}
		return ratingList;
	}

	public String authorizeUser(String username, String password) {

		for (User u : users) {
			if (u.getUsername().equals(username)
					&& u.getPassword().equals(password)) {

				String token = "ABCD";

				u.setAccessToken(token);
				return token;
			}
		}
		return null;

	}

	public boolean verifyWithToken(String token) {
		for (User u : users) {
			if (u.getAccessToken() != null) {
				if (u.getAccessToken().equals(token)) {
					return true;
				}
			}
		}
		return false;
	}

	public User getUserByName(String name) {
		for (User u : users) {
			if (u.getUsername().equals(name)) {
				return u;
			}
		}
		return null;
	}

	public Movie getMovieByID(int movieID) {
		for (Movie m : movies) {
			if (m.getMovieID() == movieID) {
				return m;
			}
		}
		return null;
	}

	public boolean doesMovieExists(int movieID) {
		for (Movie movie : movies) {
			if (movie.getMovieID() == movieID) {
				return true;
			}
		}
		return false;
	}

	public boolean doesRatingExists(int movieID, String username) {
		if (ratings.size() != 0) {
			for (Rating rating : ratings) {
				if (rating.getMovie().getMovieID() == movieID
						&& rating.getUser().getUsername().equals(username)) {
					return true;
				}
			}
		}
		return false;
	}
}
