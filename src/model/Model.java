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

		User u1 = new User("Jan", "", "Henk", "jan123", "ww");
		users.add(u1);
		User u2 = new User("Kees", "van", "Boom", "keesie", "boom");
		users.add(u2);
		User u3 = new User("Peter", "", "Honing", "peter", "123");
		users.add(u3);

		Movie m1 = new Movie(1, "0096895", "The Batman Movie", new Date(), 120, "Jan Henk",
				"The Dark Knight of Gotham City begins his war on crime with his first major enemy being the clownishly homicidal Joker.");
		Movie m2 = new Movie(2, "0059968", "The Batman Movie Part two", new Date(), 120,
				"Jan Kees", "Another great Movie");
		Movie m3 = new Movie(3, "0035665", "Batman Movie the 3th", new Date(), 120,
				"Jan Klaar", "Bad");
		Movie m4 = new Movie(4, "0078346", "Superman", new Date(), 120,
				"Jan Klaac", "Bad");
		Movie m5 = new Movie(5, "2975590", "Batman v Superman: Dawn of Justice", new Date(), 120,
				"Jan Klaam", "Bad");
		Movie m6 = new Movie(6, "0348150", "Superman Returns", new Date(), 120,
				"Jan Klaaz", "Bad");
		Movie m7 = new Movie(7, "0770828", "Man of Steel", new Date(), 120,
				"Jan Klaven", "Bad");

		movies.add(m1);
		movies.add(m2);
		movies.add(m3);
		movies.add(m4);
		movies.add(m5);
		movies.add(m6);
		movies.add(m7);

		ratings.add(new Rating(u1, m1, 10));
		ratings.add(new Rating(u2, m1, 9));
		ratings.add(new Rating(u3, m1, 9));
		
		ratings.add(new Rating(u1, m2, 3));
		ratings.add(new Rating(u2, m2, 5));
		
		ratings.add(new Rating(u1, m3, 2));
		
		ratings.add(new Rating(u1, m4, 5));
		
		ratings.add(new Rating(u1, m5, 6));
		
		ratings.add(new Rating(u1, m6, 3));
		
		ratings.add(new Rating(u1, m7, 8));
	}

	public Object addRating(String token, String ttID, int rating) {

		User user = getUserWithToken(token);
		String username = user.getUsername();

		if (rating > 0 && rating <= 10) {
			if (doesMovieExists(ttID)) {
				if (!doesRatingExists(ttID, username)) {
					Movie movie = getMovieByID(ttID);

					Rating r = new Rating(user, movie, rating);
					ratings.add(r);
					return r;
				}
			}
		}
		return new ResponseMessage(404);
	}

	public Object removeRating(String token, String ttID) {

		User user = getUserWithToken(token);
		String username = user.getUsername();

		if (doesMovieExists(ttID)) {
			if (doesRatingExists(ttID, username)) {
				for (Rating r : ratings) {
					if (r.getUser().getUsername().equals(username)
							&& r.getMovie().getTtNr().equals(ttID)) {
						ratings.remove(r);
						ResponseMessage success = new ResponseMessage();
						success.setStatusCode(200);
						return success;
					}
				}
			}
		}
		return new ResponseMessage(404);
	}

	public Object editRating(String token, String ttID, int newRating) {

		User user = getUserWithToken(token);
		String username = user.getUsername();

		if (newRating > 0 && newRating <= 10) {
			if (doesMovieExists(ttID)) {
				if (doesRatingExists(ttID, username)) {
					for (Rating r : ratings) {
						if (r.getUser().getUsername().equals(username)
								&& r.getMovie().getTtNr().equals(ttID)) {
							r.setRating(newRating);
							return r;
						}
					}
				}
			}
		}
		return new ResponseMessage(404);
	}

	public boolean addUser(User newUser) {

		if (newUser.getUsername().length() > 0
				&& newUser.getPassword().length() > 0) {
			for (User user : users) {
				if (user.getUsername().equals(newUser.getUsername())) {
					return false;
				}
			}
			users.add(newUser);
			System.out.println("user added");

			return true;
		}
		return false;

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

	public Object getRatingsFromMovie(String ttID) {

		if (doesMovieExists(ttID)) {
			ArrayList<Rating> ratingList = new ArrayList<Rating>();

			for (Rating rating : ratings) {
				if (rating.getMovie().getTtNr().equals(ttID)) {
					ratingList.add(rating);
				}
			}
			return ratingList;
		}
		return new ResponseMessage(404);
	}

	public Object getOverallRatingFromMovie(String ttID) {

		if (doesMovieExists(ttID)) {

			int counter = 0;
			int overallRating = 0;

			for (Rating rating : ratings) {
				if (rating.getMovie().getTtNr().equals(ttID)) {
					counter++;
					overallRating += rating.getRating();
				}
			}
			if (counter >= 3) {
				return new OverallRatingResponse(200, overallRating / counter);
			}
		}
		return new ResponseMessage(404);
	}

	public String generateAccessToken() {
		String token = "";

		Boolean check = true;

		do {
			token = Integer.toString((int) (Math.floor((Math.random() * 100000000))));

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

	public User getUserWithToken(String token) {
		String username = tokens.get(token);
		User user = getUserByName(username);

		return user;
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

	public Object getUserByUsername(String username) {
		for (User u : users) {
			if (u.getUsername().equals(username)) {
				return u;
			}
		}
		return new ResponseMessage(404);
	}

	public Movie getMovieByID(String ttnr) {
		for (Movie m : movies) {
			if (m.getTtNr().equals(ttnr)) {
				return m;
			}
		}
		return null;
	}

	public boolean doesMovieExists(String ttID) {
		for (Movie movie : movies) {
			if (movie.getTtNr().equals(ttID)) {
				return true;
			}
		}
		return false;
	}

	public boolean doesRatingExists(String ttID, String username) {
		if (ratings.size() != 0) {
			for (Rating rating : ratings) {
				if (rating.getMovie().getTtNr().equals(ttID)
						&& rating.getUser().getUsername().equals(username)) {
					return true;
				}
			}
		}
		return false;
	}
}
