package model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "rating")
public class Rating {

	private Movie movie;
	private User user;
	private int rating;

	public Rating(User user, Movie movie, int rating) {
		this.user = user;
		this.movie = movie;
		this.rating = rating;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

}
