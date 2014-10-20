package model;

import java.util.ArrayList;
import java.util.Date;

public class Model {

	private ArrayList<Movie> movies;
	private ArrayList<User> users;
	private ArrayList<Rating> ratings;

	private static Model model = null;

	public static Model getInstance() {
		if (model == null) {
			model = new Model();
		}
		return model;
	}

	private Model() {
		movies = new ArrayList<Movie>();
		users = new ArrayList<User>();
		ratings = new ArrayList<Rating>();

		Movie m1 = new Movie(1, 121232523, "The Movie", new Date(), 120,
				"M. Lemson", "A great movie");
		movies.add(m1);
		Movie m2 = new Movie(2, 221232523, "WERSFDWERWE", new Date(), 120,
				"M. Lemson", "A great movie");
		movies.add(m2);
		Movie m3 = new Movie(3, 321232523, "SKDLFSD", new Date(), 120,
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

}
