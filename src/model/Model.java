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

		Movie m1 = new Movie(1, 121232523, "The Movie", new Date(), 120,
				"M. Lemson", "A great movie");
		movies.add(m1);
		Movie m2 = new Movie(2, 221232523, "Movie Part 2", new Date(), 120,
				"M. Lemson", "A great movie");
		movies.add(m2);
		Movie m3 = new Movie(3, 321232523, "3th Film", new Date(), 120,
				"M. Lemson", "A great movie");
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
