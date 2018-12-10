package io.codementor.vetting.ideapool.service;

import io.codementor.vetting.ideapool.database.UserCRUD;
import io.codementor.vetting.ideapool.model.User;
import io.codementor.vetting.ideapool.util.MD5Util;

public class UserServiceImpl implements UserService{

	public User createUser(String name, String email,String password) {
		String avatarUrl = "https://www.gravatar.com/avatar/"+MD5Util.md5Hex(email)+"?d=mm&s=200";
		
		String md5Password = MD5Util.md5Hex(password);
		String id = UserCRUD.createUser(name, email, md5Password, avatarUrl,email);
		
		return id == null ? null : UserCRUD.getUserFromId(id);
	}
	public User getUser(String email,String password) {
		
		String md5Password = MD5Util.md5Hex(password);
		return UserCRUD.getUserFromEmailPassword(email, md5Password);
	
	}
	public void logoutUser(User u) {
		
		UserCRUD.updateUser(u.getId(), MD5Util.md5Hex(u.getId()));
		
	}
	
	public boolean emailAlreadyExists(String email) {
		User u = UserCRUD.getUserFromEmail(email);
		
		return u==null ? false : true;
	}
}
