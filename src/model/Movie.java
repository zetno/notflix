package model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "movie")
public class Movie {

	private int movieID;

	private int ttNr;

	private String title;

	private Date date;

	private int length;

	private String producer;

	private String description;

	@XmlAttribute
	public int getMovieID() {
		return movieID;
	}

	public void setMovieID(int movieID) {
		this.movieID = movieID;
	}

	@XmlElement
	public int getTtNr() {
		return ttNr;
	}

	public void setTtNr(int ttNr) {
		this.ttNr = ttNr;
	}

	@XmlElement
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@XmlElement
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@XmlElement
	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	@XmlElement
	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	@XmlElement
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
