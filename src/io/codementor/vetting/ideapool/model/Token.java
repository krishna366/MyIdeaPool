package io.codementor.vetting.ideapool.model;

public class Token {
	public Token(String jwt, String refreshToken) {
		super();
		this.jwt = jwt;
		this.refreshToken = refreshToken;
	}
	public String getJwt() {
		return jwt;
	}
	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	private String jwt;
	private String refreshToken;
	
	@Override
	public String toString() {
		return "jwt "+jwt+" , refreshToken "+refreshToken;
	}
	
}
