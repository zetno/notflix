package model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OverallRatingResponse extends ResponseMessage {

	private int overallRating;

	public OverallRatingResponse(int statusCode, int overallRating) {
		super(statusCode);
		this.overallRating = overallRating;
	}

	public int getOverallRating() {
		return overallRating;
	}

	public void setOverallRating(int overallRating) {
		this.overallRating = overallRating;
	}

}
