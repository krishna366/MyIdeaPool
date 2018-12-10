package io.codementor.vetting.ideapool.service;

import io.codementor.vetting.ideapool.model.Token;
import io.codementor.vetting.ideapool.model.User;

public interface JWTTokenService {
	
	public User getUserFromRequestToken(String refreshToken);
	public User getUserFromJWTToken(String jwt);
	public Token generateToken(User u,boolean modifyRefreshToken);
}
