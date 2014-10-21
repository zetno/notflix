package model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResponseMessage {

	private int statusCode;

	public ResponseMessage(int statusCode) {
		this.statusCode = statusCode;
	}

	public ResponseMessage() {
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

}
