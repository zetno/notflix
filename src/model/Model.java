package model;

import java.util.ArrayList;

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
