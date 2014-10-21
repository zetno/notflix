package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class Model {

	private ArrayList<Movie> movies;
	private ArrayList<User> users;
	private ArrayList<Rating> ratings;

	private HashMap<String, String> tokens;

	public Model() {
		movies = new ArrayList<Movie>();
		users = new ArrayList<User>();
		ratings = new ArrayList<Rating>();
		tokens = new HashMap<String, String>();

		User u1 = new User();
		u1.setUsername("Jan");
		u1.setPassword("ww");
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

	public Object addRating(String username, int movieID, int rating) {
		if (doesMovieExists(movieID)) {
			if (!doesRatingExists(movieID, username)) {
				User user = getUserByName(username);
				Movie movie = getMovieByID(movieID);

				Rating r = new Rating(user, movie, rating);
				ratings.add(r);
				return r;
			}
		}
		ResponseMessage error = new ResponseMessage();
		error.setStatusCode(404);
		return error;
	}

	public Object removeRating(String username, int movieID) {

		// TODO: controleren of accesstoken bij gebruiker hoort

		if (doesMovieExists(movieID)) {
			if (doesRatingExists(movieID, username)) {
				for (Rating r : ratings) {
					if (r.getUser().getUsername().equals(username)
							&& r.getMovie().getMovieID() == movieID) {
						ratings.remove(r);
						ResponseMessage success = new ResponseMessage();
						success.setStatusCode(200);
						return success;
					}
				}
			}
		}
		ResponseMessage error = new ResponseMessage();
		error.setStatusCode(404);
		return error;
	}

	public Object editRating(String username, int movieID, int newRating) {
		if (doesMovieExists(movieID)) {
			if (doesRatingExists(movieID, username)) {
				for (Rating r : ratings) {
					if (r.getUser().getUsername().equals(username)
							&& r.getMovie().getMovieID() == movieID) {
						r.setRating(newRating);
						return r;
					}
				}
			}
		}
		ResponseMessage error = new ResponseMessage();
		error.setStatusCode(404);
		return error;
	}

	public boolean addUser(User newUser) {
		for (User user : users) {
			if (user.getUsername().equals(newUser.getUsername())) {
				return false;
			}
		}

		users.add(newUser);

		System.out.println("user added");

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

	public Object getRatingsFromMovie(int movieID) {

		if (doesMovieExists(movieID)) {
			ArrayList<Rating> ratingList = new ArrayList<Rating>();

			for (Rating rating : ratings) {
				if (rating.getMovie().getMovieID() == movieID) {
					ratingList.add(rating);
				}
			}
			return ratingList;
		}
		ResponseMessage error = new ResponseMessage();
		error.setStatusCode(404);

		return error;
	}

	public String generateAccessToken() {
		String token = "";

		Boolean check = true;

		do {
			token = Double.toString(Math.floor((Math.random() * 1000)));

			if (!tokens.containsKey(token)) {
				check = false;
			}
		} while (check);

		return token;
	}

	public String authorizeUser(String username, String password) {

		for (User u : users) {
			if (u.getUsername().equals(username)
					&& u.getPassword().equals(password)) {

				String token = generateAccessToken();
				tokens.put(token, username);

				return token;
			}
		}
		return null;
	}

	public boolean verifyWithToken(String token) {
		if (tokens.containsKey(token)) {
			return true;
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
