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

		User u1 = new User("Jan", "ww");
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

	public void addRating(User user, Movie movie, int rating) {
		Rating r = new Rating(user, movie, rating);
		ratings.add(r);
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

}
