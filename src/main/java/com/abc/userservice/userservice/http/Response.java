package com.abc.userservice.userservice.http;

import java.util.List;

import com.abc.userservice.userservice.beans.User;

import lombok.Data;
import lombok.var;

@Data
public class Response {

	private boolean success;
	private int code;
	private String message;
	private User userPayLoad;
	private List<User> userPayLoadList;

	public Response() {
	
		super();
	}
	
	public Response(boolean success) {
		super();
		this.success = success;
		this.code = success ? 200 : 400;
		this.message = "";
	}
	
	public Response(boolean success, String message) {
		super();
		this.success = success;
		this.code = success ? 200 : 400;
		this.message = message;
	}

	public Response(boolean success, int code, String message) {
		super();
		this.success = success;
		this.code = code;
		this.message = message;
	}

	public Response(boolean success, User u) {
		this.success = success;
		this.code = success ? 200 : 400;
		this.userPayLoad = u;
	}
	
	public Response(boolean success, List<User> list) {
		this.success = success;
		this.code = success ? 200 : 400;
		this.userPayLoadList = list;
	}

	public boolean isSuccess() {
		return success;
	}

	

}
