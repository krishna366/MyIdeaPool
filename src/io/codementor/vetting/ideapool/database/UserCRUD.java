package io.codementor.vetting.ideapool.database;

import org.sql2o.Connection;

import io.codementor.vetting.ideapool.model.User;

public class UserCRUD extends AbstractCRUD{
	
	private final static String SQL_INSERT = "INSERT INTO `ideapool`.`users` (`name`,`email`,`password`,`avatar_url`,`refresh_token`) " +
            								" VALUES(:name,:email,:password,:avatarUrl,:refreshToken)"; 
	
	private final static String SQL_UPDATE = "UPDATE `ideapool`.`users` SET `refresh_token` = :refreshToken,"+
											"last_modified_at = CURRENT_TIMESTAMP()"+
											"WHERE id = :id";
	
	private final static String SQL_SELECT_SINGLE = "SELECT id,name,email,avatar_url,refresh_token FROM `ideapool`.`users` "+
													"WHERE id = :id";
	
	private final static String SQL_SELECT_VALIDATE = "SELECT id,name,email,avatar_url,refresh_token FROM `ideapool`.`users` "+
			"WHERE email = :email and password = :password";
	
	private final static String SQL_SELECT_RT = "SELECT id,name,email,avatar_url,refresh_token FROM `ideapool`.`users` "+
			"WHERE refresh_token = :refreshToken";
	
	private final static String SQL_SELECT_VALIDATE2 = "SELECT id,name,email,avatar_url,refresh_token FROM `ideapool`.`users` "+
			"WHERE email = :email";
	
	public static String createUser(String name,String email,String password,String avatarUrl,String refreshToken) {
		
		Connection connection = getOpenedConnection();
		
        try{
            
            Object insertedId = connection.createQuery(SQL_INSERT, true)
                    .addParameter("name", name)
                    .addParameter("email", email )
                    .addParameter("password", password)
                    .addParameter("avatarUrl",avatarUrl)
                    .addParameter("refreshToken", refreshToken)
                    .executeUpdate()
                    .getKey();
            	
            //connection.commit();
            return insertedId.toString();

        } finally {
            connection.close();
        }
		
	}
	
	public static String updateUser(String id,String refreshToken) {
		
		Connection connection = getOpenedConnection();
		
        try{
            
            Object insertedId = connection.createQuery(SQL_UPDATE, true)
                    .addParameter("id", Integer.parseInt(id))
                    .addParameter("refreshToken", refreshToken )
                    .executeUpdate()
                    .getKey();
            	
           // connection.commit();
            //return insertedId.toString();
            
            return id;
        } finally {
            connection.close();
        }
		
	}
	
	public static User getUserFromId(String id) {
		Connection connection = getOpenedConnection();
		
		try {
			
			User u = connection.createQuery(SQL_SELECT_SINGLE)
					.addParameter("id", id)
					.executeAndFetchFirst(User.class);
			
			return u;
			
		} finally {
            connection.close();
        }
	}
	
	public static User getUserFromRefreshToken(String refreshToken) {
		Connection connection = getOpenedConnection();
		
		try {
			
			User u = connection.createQuery(SQL_SELECT_RT)
					.addParameter("refreshToken", refreshToken)
					.executeAndFetchFirst(User.class);
			
			return u;
			
		} finally {
            connection.close();
        }
	}
	
	public static User getUserFromEmailPassword(String email,String password) {
		Connection connection = getOpenedConnection();
		
		try {
			
			User u = connection.createQuery(SQL_SELECT_VALIDATE)
					.addParameter("email", email)
					.addParameter("password", password)
					.executeAndFetchFirst(User.class);
			
			return u;
			
		} finally {
            connection.close();
        }
	}

	public static User getUserFromEmail(String email) {
		Connection connection = getOpenedConnection();
		
		try {
			
			User u = connection.createQuery(SQL_SELECT_VALIDATE2)
					.addParameter("email", email)
					.executeAndFetchFirst(User.class);
			
			return u;
			
		} finally {
            connection.close();
        }
	}
	
	public static void main(String[] args) {
		String id = UserCRUD.createUser("krishna", "k@k.com", "md5", "url1","rt1");
		
		User u1 = UserCRUD.getUserFromEmail("k@k.com");
		System.out.println("u1 = "+u1);
		
		/*
		id = UserCRUD.updateUser(id,"rt2");
		
		System.out.println("id = "+id);
		
		User u1 = UserCRUD.getUserFromId(id);
		
		System.out.println("u1 = "+u1);
		
		User u2 = UserCRUD.getUserFromRefreshToken("rt2");
		
		System.out.println("u2 = "+u2);	
		
		User u3 = UserCRUD.getUserFromEmailPassword("k@k.com", "md5");
		
		System.out.println("u3 = "+u3);	
		*/
	}
	
}
