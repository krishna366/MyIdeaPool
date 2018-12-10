package io.codementor.vetting.ideapool;

import static spark.Spark.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

import io.codementor.vetting.ideapool.model.Idea;
import io.codementor.vetting.ideapool.model.Token;
import io.codementor.vetting.ideapool.model.User;
import io.codementor.vetting.ideapool.service.IdeaServiceImpl;
import io.codementor.vetting.ideapool.service.JWTTokenServiceImpl;
import io.codementor.vetting.ideapool.service.UserServiceImpl;
import io.codementor.vetting.ideapool.util.StandardResponse;

public class Main {

	public static void main(String[] args) {
		 
		port(8080);
		Gson gson = new Gson();
		
		before((req,res)->{
			
			//System.out.println("req queryString "+req.queryParams("page"));
			//System.out.println("req body "+req.body());
			//System.out.println("req path id "+req.params(":id"));
			//System.out.println("req token "+req.headers("x-access-token"));
			//System.out.println("req url "+req.url());
		});

		after((req,res)->{
			//System.out.println("req body "+req.body());
			//System.out.println("res body "+res.body());
			//System.out.println("res status "+res.status());
		});
		
        post("/access-tokens/refresh", (req,res)->{
        	
        	String refreshToken = "";
        	int responseStatus = 500;
        	String responseMessage = "";
        	res.type("application/json");
        	
        	try {
        		JSONObject jsonReq = new JSONObject(req.body());
        		refreshToken = jsonReq.getString("refresh_token");
        		        		
        	}catch(Exception e) {
        		//responseStatus = 401;
        		//responseMessage = "Refresh Token Missing.";
        		halt(401,"Refresh Token Missing.");
        	}
        	
        	if(!"".equals(refreshToken)) {
        		JWTTokenServiceImpl tokenServiceImpl = new JWTTokenServiceImpl();
        		User u = tokenServiceImpl.getUserFromRequestToken(refreshToken);
        		
        		if(u == null) {
        			halt(402,"Refresh Token not mapped to valid value.");
        			//responseStatus = 402;
            		//responseMessage = "Refresh Token didn't mapped to valid user.";
            		
            		//return new Gson().toJson(
              		//      new StandardResponse(responseStatus,responseMessage));
        		}
        		
        		Token token = tokenServiceImpl.generateToken(u,false);
        		
        		Map<String, String> keyValuePair = new HashMap<>();
        		keyValuePair.put("refresh_token", token.getRefreshToken());
                keyValuePair.put("jwt", token.getJwt());
                
                
        		responseStatus = 200;
        		res.status(responseStatus);
        		//System.out.println(new Gson().toJson(keyValuePair));
        		return new Gson().toJson(keyValuePair);
        	}
        	
        	
        	return new Gson().toJson(
        		      new StandardResponse(responseStatus,responseMessage));
        	
        });
        
        post("/access-tokens", (req,res)->{
           	String email = "";
           	String password = "";
        	int responseStatus = 500;
        	String responseMessage = "";
        	res.type("application/json");
        	
        	try {
        		JSONObject jsonReq = new JSONObject(req.body());
        		email = jsonReq.getString("email");
        		password = jsonReq.getString("password");
        		
        	}catch(Exception e) {
        		responseStatus = 401;
        		responseMessage = "Required parameters are missing.";
        		halt(responseStatus,responseMessage);
        	}
        	
        	if(!("".equals(email) || "".equals(password))) {
        		
        		JWTTokenServiceImpl tokenServiceImpl = new JWTTokenServiceImpl();
        		UserServiceImpl userServiceImpl = new UserServiceImpl();
        		
        		User u = userServiceImpl.getUser(email, password);
        		
        		if(u == null) {
        			responseStatus = 402;
            		responseMessage = "Invalid Credentials.";
            		
            		halt(responseStatus,responseMessage);
        		}
        		
        		Token token = tokenServiceImpl.generateToken(u,true);
        		
        		
        		Map<String, String> keyValuePair = new HashMap<>();
        		keyValuePair.put("refresh_token", token.getRefreshToken());
                keyValuePair.put("jwt", token.getJwt());
                
                
        		responseStatus = 200;
        		res.status(responseStatus);
        		//System.out.println(new Gson().toJson(keyValuePair));
        		return new Gson().toJson(keyValuePair);
        	}
        	
        	responseStatus = 200;
        	return new Gson().toJson(
        		      new StandardResponse(responseStatus,responseMessage));
        });        
  
        delete("/access-tokens", (req,res)->{
            String jwt = "";
            String refreshToken = "";
            
        	int responseStatus = 500;
        	String responseMessage = "";
        	res.type("application/json");
            
        	try {
        		JSONObject jsonReq = new JSONObject(req.body());
        		refreshToken = jsonReq.getString("refresh_token");
        		jwt = req.headers("x-access-token");
        		
        	}catch(Exception e) {
        		responseStatus = 401;
        		responseMessage = "Required parameters are missing.";
        		halt(responseStatus,responseMessage);
        	}
        	
        	if(!("".equals(refreshToken) || "".equals(jwt))) {
        		
        		JWTTokenServiceImpl tokenServiceImpl = new JWTTokenServiceImpl();
        		UserServiceImpl userServiceImpl = new UserServiceImpl();
        		
        		User u = tokenServiceImpl.getUserFromJWTToken(jwt);
        		if(u == null) {
        			responseStatus = 402;
            		responseMessage = "Invalid Token.";
            		
            		halt(responseStatus,responseMessage);
        		}
        		
        		userServiceImpl.logoutUser(u);
        		
        		responseStatus = 200;
        		res.status(responseStatus);
        		responseMessage = "";
        		
        		return "";
        	}
        	
        	return new Gson().toJson(
      		      new StandardResponse(responseStatus,responseMessage));
        });

        post("/users", (req,res)->{
            String name = "";
           	String email = "";
           	String password = "";
           	
        	int responseStatus = 500;
        	String responseMessage = "";
        	res.type("application/json");
        	
        	try {
        		JSONObject jsonReq = new JSONObject(req.body());
        		name = jsonReq.getString("name");
        		email = jsonReq.getString("email");
        		password = jsonReq.getString("password");
        	}catch(Exception e) {
        		responseStatus = 401;
        		responseMessage = "Required parameters are missing.";
        		halt(responseStatus,responseMessage);
        	}
        	
        	if(!("".equals(email) || "".equals(password) || "".equals(name) )) {
        		
        		JWTTokenServiceImpl tokenServiceImpl = new JWTTokenServiceImpl();
        		UserServiceImpl userServiceImpl = new UserServiceImpl();
        		
        		if(userServiceImpl.emailAlreadyExists(email)) {
        			responseStatus = 422;
        			responseMessage = "Email already exists.";
        			halt(responseStatus,responseMessage);
        		}
        		
        		//System.out.println(name+" "+email+" "+password);
        		User u = userServiceImpl.createUser(name, email, password);
        		
        		if(u == null) {
        			responseStatus = 500;
            		responseMessage = "Unable to create user due to unexpected error.";
            		
            		halt(responseStatus,responseMessage);
        		}
        		
        		Token token = tokenServiceImpl.generateToken(u,true);
        		
        		Map<String, String> keyValuePair = new HashMap<>();
        		keyValuePair.put("refresh_token", token.getRefreshToken());
                keyValuePair.put("jwt", token.getJwt());
                
                
        		responseStatus = 200;
        		res.status(responseStatus);
        		//System.out.println(new Gson().toJson(keyValuePair));
        		return new Gson().toJson(keyValuePair);

        	}
        	
        	
        	return new Gson().toJson(
        		      new StandardResponse(responseStatus,responseMessage));
        	
        });  
        
        get("/me", (req,res)->{
            
            String jwt = "";
            
        	int responseStatus = 500;
        	String responseMessage = "";
        	res.type("application/json");
            
        	try {
        		jwt = req.headers("x-access-token");
        		
        	}catch(Exception e) {
        		responseStatus = 401;
        		responseMessage = "Invalid Token.";
        		halt(responseStatus,responseMessage);
        	}
        	
        	if(!("".equals(jwt))) {
        		
        		JWTTokenServiceImpl tokenServiceImpl = new JWTTokenServiceImpl();
        		
        		User u = tokenServiceImpl.getUserFromJWTToken(jwt);
        		if(u == null) {
        			responseStatus = 401;
            		responseMessage = "Invalid Token.";
            		
            		halt(responseStatus,responseMessage);
        		}
        		        		
        		Map<String, String> keyValuePair = new HashMap<>();
                keyValuePair.put("avatar_url", u.getAvatarUrl());
                keyValuePair.put("name", u.getName()); 
                keyValuePair.put("email", u.getEmail());
        		responseStatus = 200;
        		//System.out.println(new Gson().toJson(keyValuePair));
        		return new Gson().toJson(keyValuePair);
        		
        		
        		
        		//return new Gson().toJson(
          		//      new StandardResponse(responseStatus,new Gson().toJsonTree(u)));
        	}
        	
        	return new Gson().toJson(
      		      new StandardResponse(responseStatus,responseMessage));
        	
        });        
        
        post("/ideas", (req,res)->{
            
            String jwt = "";
            
        	int responseStatus = 500;
        	String responseMessage = "";
        	res.type("application/json");
            
        	try {
        		jwt = req.headers("x-access-token");
        		
        	}catch(Exception e) {
        		responseStatus = 401;
        		responseMessage = "Token is missing.";
        		halt(responseStatus,responseMessage);
        	}
        	
        	String content = "";
        	int impact = 0;
        	int ease = 0;
        	int confidence = 0;
        	
        	try {
        		JSONObject jsonReq = new JSONObject(req.body());        		
        		content = jsonReq.getString("content");
        		impact = jsonReq.getInt("impact");
        		ease = jsonReq.getInt("ease");
        		confidence = jsonReq.getInt("confidence");
        		//impact = Integer.parseInt(jsonReq.getString("impact"));
        		//ease = Integer.parseInt(jsonReq.getString("ease"));
        		//confidence = Integer.parseInt(jsonReq.getString("confidence"));
        		
        	}catch(Exception e) {
        		e.printStackTrace();
        		responseStatus = 402;
        		responseMessage = "Required parameters are missing.";
        		halt(responseStatus,responseMessage);
        	}
        	
        	if(!("".equals(content) || impact == 0 || ease == 0 || confidence == 0)) {
        		
        		JWTTokenServiceImpl tokenServiceImpl = new JWTTokenServiceImpl();
        		IdeaServiceImpl ideaServiceImpl = new IdeaServiceImpl();
        		
        		User u = tokenServiceImpl.getUserFromJWTToken(jwt);
        		if(u == null) {
        			responseStatus = 401;
            		responseMessage = "Invalid Token.";
            		
            		halt(responseStatus,responseMessage);
        		}
        		  
        		Idea idea = ideaServiceImpl.createIdea(content, impact, ease, confidence, u);
        		
          		if(idea == null) {
          			responseStatus = 403;
              		responseMessage = "Invalid Id.";
              		
              		halt(responseStatus,responseMessage);
          		} 
        		
          		/*
          		Map<String, String> keyValuePair = new HashMap<>();
                keyValuePair.put("id",idea.getId() );
                keyValuePair.put("content", idea.getContent());
                keyValuePair.put("impact", idea.getImpact()+"");
                keyValuePair.put("ease", idea.getEase()+"");
                keyValuePair.put("confidence",idea.getConfidence()+"" );
                keyValuePair.put("average_score", idea.getAverage()+"");
                keyValuePair.put("created_at",idea.getCreatedAt().toInstant().toString() );
        		
        		System.out.println(new Gson().toJson(keyValuePair));
        		return new Gson().toJson(keyValuePair);
        		*/
          		
          		responseStatus = 200;
          		res.status(responseStatus);
          		
          		JSONObject jsonObj = new JSONObject();
          		jsonObj.put("content", idea.getContent());
          		jsonObj.put("impact", idea.getImpact());
          		jsonObj.put("ease", idea.getEase());
          		jsonObj.put("confidence", idea.getConfidence());
          		jsonObj.put("id",idea.getId() );
          		jsonObj.put("average_score", idea.getAverage());
          		jsonObj.put("created_at",idea.getCreatedAt().toInstant().toEpochMilli());
          		
          		return jsonObj;
          		
        		//return new Gson().toJson(
          		//      new StandardResponse(responseStatus,new Gson().toJsonTree(idea)));
        	}
        	
        	return new Gson().toJson(
      		      new StandardResponse(responseStatus,responseMessage));
        	
        });
        
        delete("/ideas/:id", (req,res)->{
            String jwt = "";
            
        	int responseStatus = 500;
        	String responseMessage = "";
        	res.type("application/json");
            
        	try {
        		jwt = req.headers("x-access-token");
        		
        	}catch(Exception e) {
        		responseStatus = 401;
        		responseMessage = "Token is missing.";
        		halt(responseStatus,responseMessage);
        	}
        	
          	String id = "";

          	
          	try {
          		id = req.params(":id");

          	}catch(Exception e) {
          		responseStatus = 402;
          		responseMessage = "Required parameters are missing.";
          		halt(responseStatus,responseMessage);
          	}
          	
          	if(!("".equals(id))) {
        		
        		JWTTokenServiceImpl tokenServiceImpl = new JWTTokenServiceImpl();
        		IdeaServiceImpl ideaServiceImpl = new IdeaServiceImpl();
        		
        		User u = tokenServiceImpl.getUserFromJWTToken(jwt);
        		if(u == null) {
        			responseStatus = 402;
            		responseMessage = "Invalid Token.";
            		
            		halt(responseStatus,responseMessage);
        		}
        		
        		//Idea idea = ideaServiceImpl.getIdea(id);
        		ideaServiceImpl.deletIdea(id);
        		
        		responseStatus = 200;
        		res.status(responseStatus);
        		return "";
        		//return new Gson().toJson(
          		//      new StandardResponse(responseStatus,responseMessage));
        	}
        	
        	return new Gson().toJson(
      		      new StandardResponse(responseStatus,responseMessage));
        	
        });
        
        get("/ideas", (req,res)->{
            String jwt = "";
            
        	int responseStatus = 500;
        	String responseMessage = "";
        	res.type("application/json");
            
        	try {
        		jwt = req.headers("x-access-token");
        		
        	}catch(Exception e) {
        		responseStatus = 401;
        		responseMessage = "Token is missing.";
        		halt(responseStatus,responseMessage);
        	}
        	
        	int page = 0;
        	
        	try {
        		
        		page = Integer.parseInt(req.queryParams("page"));
        		//JSONObject jsonReq = new JSONObject(req.body());        		
        		//page = Integer.parseInt(req.queryParams("page"));
        		//page = Integer.parseInt( jsonReq.getString("page"));
        		//page = ((JSONObject)jsonReq.get("query")).getInt("page");
        	} catch(Exception e) {
        		//e.printStackTrace();
        		responseStatus = 402;
        		responseMessage = "Required params are missing";
        		//System.out.println("page "+page);
        		return new JSONArray();       		
        	}
        	
        	//System.out.println("page "+page);
        	if(page > 0) {
        		
        		JWTTokenServiceImpl tokenServiceImpl = new JWTTokenServiceImpl();
        		IdeaServiceImpl ideaServiceImpl = new IdeaServiceImpl();
        		
        		User u = tokenServiceImpl.getUserFromJWTToken(jwt);
        		if(u == null) {
        			responseStatus = 402;
            		responseMessage = "Invalid Token.";
            		
            		halt(responseStatus,responseMessage);
        		}
        		
        		List<Idea> ideas = ideaServiceImpl.getIdeas(page);
        		
        		responseStatus = 200;
        		
        		JSONArray jsonArray = new JSONArray();
        		
        		for(Idea i:ideas) {
        			//JSONObject jo = new JSONObject();
        			
        			JSONObject jsonObj = new JSONObject();
        			jsonObj.put("id", i.getId());
              		jsonObj.put("content", i.getContent());
              		jsonObj.put("impact", i.getImpact());
              		jsonObj.put("ease", i.getEase());
              		jsonObj.put("confidence", i.getConfidence());
              		jsonObj.put("average_score", i.getAverage());
              		jsonObj.put("created_at",i.getCreatedAt().toInstant().toEpochMilli());
              		jsonArray.put(jsonObj);
        		}
        		//System.out.println(jsonArray.toString());
        		return jsonArray;
        		//return new Gson().toJson(
          		//      new StandardResponse(responseStatus,new Gson().toJsonTree(ideas)));
        	}
        	
        	return new Gson().toJson(
      		      new StandardResponse(responseStatus,responseMessage));
        	
        });
        
        put("/ideas/:id", (req,res)->{
            String jwt = "";
            
          	int responseStatus = 500;
          	String responseMessage = "";
          	res.type("application/json");
              
          	try {
          		jwt = req.headers("x-access-token");
          		
          	}catch(Exception e) {
          		responseStatus = 401;
          		responseMessage = "Token is missing.";
          		halt(responseStatus,responseMessage);
          	}
          	
          	String id = "";
          	String content = "";
          	int impact = 0;
          	int ease = 0;
          	int confidence = 0;
          	
          	try {
          		JSONObject jsonReq = new JSONObject(req.body());
        		
          		id = req.params(":id");
          		content = jsonReq.getString("content");
          		impact = jsonReq.getInt("impact");
        		ease = jsonReq.getInt("ease");
        		confidence = jsonReq.getInt("confidence");
          		
          	}catch(Exception e) {
          		responseStatus = 402;
          		responseMessage = "Required parameters are missing.";
          		halt(responseStatus,responseMessage);
          	}
          	
          	if(!("".equals(id) || "".equals(content) || impact == 0 || ease == 0 || confidence == 0)) {
          		
          		JWTTokenServiceImpl tokenServiceImpl = new JWTTokenServiceImpl();
          		IdeaServiceImpl ideaServiceImpl = new IdeaServiceImpl();
          		
          		User u = tokenServiceImpl.getUserFromJWTToken(jwt);
          		if(u == null) {
          			responseStatus = 402;
              		responseMessage = "Invalid Token.";
              		
              		halt(responseStatus,responseMessage);
          		}
          		  
          		//Idea idea = ideaServiceImpl.getIdea(id);
          		Idea idea = ideaServiceImpl.updateIdea(id,content, impact, ease, confidence, u);
          		
          		if(idea == null) {
          			responseStatus = 403;
              		responseMessage = "Invalid Id.";
              		
              		halt(responseStatus,responseMessage);
          		}         		
          		
          		/*
          		Map<String, String> keyValuePair = new HashMap<>();
                //keyValuePair.put("id",idea.getId() );
                keyValuePair.put("content", idea.getContent());
                keyValuePair.put("impact", idea.getImpact()+"");
                keyValuePair.put("ease", idea.getEase()+"");
                keyValuePair.put("confidence",idea.getConfidence()+"" );
                //keyValuePair.put("average_score", idea.getAverage()+"");
                //keyValuePair.put("created_at",idea.getCreatedAt().toInstant().toString() );
        		responseStatus = 200;
        		System.out.println(new Gson().toJson(keyValuePair));
        		return new Gson().toJson(keyValuePair);
        		
        		*/
          		
          		JSONObject jsonObj = new JSONObject();
          		jsonObj.put("content", idea.getContent());
          		jsonObj.put("impact", idea.getImpact());
          		jsonObj.put("ease", idea.getEase());
          		jsonObj.put("confidence", idea.getConfidence());
          		jsonObj.put("average_score", idea.getAverage());
          		jsonObj.put("created_at",idea.getCreatedAt().toInstant().toEpochMilli());
          		return jsonObj;
          	}
          	
          	return new Gson().toJson(
        		      new StandardResponse(responseStatus,responseMessage));
        });
        
      
        
    }

}
