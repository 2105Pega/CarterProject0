package com.revature.users;

import java.io.Serializable;

public class User implements Serializable{
	
	// Default serialVersionUID
	private static final long serialVersionUID = 1L;
	public final String username;
	public final String password;
		
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
		
}
