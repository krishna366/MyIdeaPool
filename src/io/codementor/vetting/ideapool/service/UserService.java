package io.codementor.vetting.ideapool.service;

import io.codementor.vetting.ideapool.model.User;

public interface UserService {
	
	public User createUser(String name, String email,String password);
	public User getUser(String email,String password);
	public void logoutUser(User u);
	public boolean emailAlreadyExists(String email);
}
