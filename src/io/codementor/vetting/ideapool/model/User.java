package io.codementor.vetting.ideapool.model;

public class User {

	private String id;


	private String name;
	private String email;
	private String avatarUrl;
	private String refreshToken;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAvatarUrl() {
		return avatarUrl;
	}
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
	@Override
	public String toString() {
		return "id= "+id+", name= "+name+", email= "+email+", avatarUrl"+avatarUrl+", refreshToken= "+refreshToken;
	}
	
	
}
