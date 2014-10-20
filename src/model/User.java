package model;

public class User {

	private String username;
	private String password;
	private String accessToken;

	public User(String username, String password) {
		this.username = username;
		this.password = password;
		this.accessToken = "AAAA";
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getAccessToken() {
		return accessToken;
	}

}
