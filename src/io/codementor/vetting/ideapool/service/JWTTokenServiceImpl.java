package io.codementor.vetting.ideapool.service;

import io.codementor.vetting.ideapool.database.UserCRUD;
import io.codementor.vetting.ideapool.model.Token;
import io.codementor.vetting.ideapool.model.User;
import io.codementor.vetting.ideapool.util.MD5Util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Random;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTTokenServiceImpl implements JWTTokenService {
	
	private static final int TOKEN_TIMEOUT_INMILISECOND = 10 * 60 * 1000;
	private static final String JWT_SECRET = "abfs283naa13d1mmkkj23jkj12cmk213lx13ml";
	
	public User getUserFromRequestToken(String refreshToken) {
		
		return UserCRUD.getUserFromRefreshToken(refreshToken);
	}
	
	public User getUserFromJWTToken(String jwt) {
		
		Object id = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(jwt)
                .getBody().get("id");

        Object refreshToken = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(jwt)
                .getBody().get("refreshToken");

        Object expireTime = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(jwt)
                .getBody().get("expireTime");
        
        //System.out.println("id = "+id.toString()+" refreshToken "+refreshToken.toString()+" expireTime "+expireTime.toString());
        
        Long currentTimeInMilisecond = getCurrentTime();
		
        if((Long) expireTime < currentTimeInMilisecond) return null;
        
        if(id == null || refreshToken == null) return null;
        
        User u = UserCRUD.getUserFromId(id.toString());
        
        if(!refreshToken.equals(u.getRefreshToken())) return null;
        
		return u;
	}
	public Token generateToken(User u,boolean modifyRefreshToken) {
		
		String refreshToken = "";
		
		if(modifyRefreshToken) { 
			refreshToken = generateRefreshToken(u.getId());
			UserCRUD.updateUser(u.getId(), refreshToken);
		}
		else refreshToken = u.getRefreshToken();
		
		SignatureAlgorithm hs512 = SignatureAlgorithm.HS512;
		
		 String jwt = Jwts.builder()
	                .claim("id", u.getId())
	                .claim("refreshToken", refreshToken)
	                .claim("expireTime", getCurrentTime() + TOKEN_TIMEOUT_INMILISECOND)
	                .signWith(hs512, JWT_SECRET)
	                .compact();
		
		return new Token(jwt,refreshToken);
	}
	
	public static void main(String[] args) {
		
		String id = UserCRUD.createUser("krishna", "k@k.com", "md5", "url1","rt1");
		User u2 = UserCRUD.getUserFromRefreshToken("rt1");
		
		JWTTokenServiceImpl impl = new JWTTokenServiceImpl();
		Token t1 = impl.generateToken(u2, true);
		
		System.out.println(t1);
		
		User u21 = impl.getUserFromJWTToken(t1.getJwt());
		
		System.out.println(u21);
		
		Token t2 = impl.generateToken(u21, false);
		
		System.out.println(t2);
		
		User u22 = impl.getUserFromJWTToken(t2.getJwt());
		System.out.println(u22);

		User u221 = impl.getUserFromJWTToken(t1.getJwt());
		System.out.println(u221);
		
		
		UserServiceImpl impl1 = new UserServiceImpl();
		impl1.logoutUser(u22);
		
		User u23 = impl.getUserFromJWTToken(t2.getJwt());
		System.out.println(u23);
		
		User u24 = impl.getUserFromJWTToken(t1.getJwt());
		System.out.println(u24);		
		
	}
	
	private String generateRefreshToken(String id) {
		
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		
		Random rnd = new Random();
		
        StringBuilder salt = new StringBuilder();
        
        while (salt.length() < 10) { 
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        
        saltStr+=MD5Util.md5Hex(id);
        
        salt = new StringBuilder();
        while (salt.length() < 10) { 
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        
        saltStr += salt.toString();
        
        return saltStr;
	}
	
	private Long getCurrentTime() {

        long result = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        return result;
    }
	
}
