package model;
import java.util.Date;

public class Movie {

	private int movieID;
	private int ttNr;
	private String title;
	private Date date;
	private int length;
	private String producer;
	private String description;

	public Movie(int movieID, int ttNr, String title, Date date, int length,
			String producer, String description) {

		this.movieID = movieID;
		this.ttNr = ttNr;
		this.title = title;
		this.date = date;
		this.length = length;
		this.producer = producer;
		this.description = description;

	}

	public int getMovieID() {
		return movieID;
	}

	public void setMovieID(int movieID) {
		this.movieID = movieID;
	}

	public int getTtNr() {
		return ttNr;
	}

	public void setTtNr(int ttNr) {
		this.ttNr = ttNr;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
