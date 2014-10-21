package model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@XmlRootElement(name = "movie")
public class Movie {

	private int movieID;
	private int ttNr;
	private String title;
	private Date date;
	private int length;
	private String producer;
	private String description;

	public Movie() {

	}

	public Movie(int movieID, int ttNr, String title, Date date, int lenght,
			String producer, String description) {
		this.movieID = movieID;
		this.ttNr = ttNr;
		this.title = title;
		this.date = date;
		this.length = lenght;
		this.description = description;
		this.producer = producer;
	}

	@XmlAttribute
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@XmlTransient
	@JsonIgnore
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
