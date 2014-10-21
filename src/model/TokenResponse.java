package model;

public class TokenResponse extends ResponseMessage {

	private String token;
	private User user;

	public TokenResponse(int statusCode, String token, User user) {
		super(statusCode);
		this.token = token;
		this.user = user;
		
	}

	public TokenResponse() {
		
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
