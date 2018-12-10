package io.codementor.vetting.ideapool.util;

import com.google.gson.JsonElement;

public class StandardResponse {
	
	private int status;
    private String message;
    private JsonElement data;
    
	public StandardResponse(int status, JsonElement data) {
		this.status = status;
		this.data = data;
	}
	
	public StandardResponse(int status, String message) {
		this.status = status;
		this.message = message;
	}
}
