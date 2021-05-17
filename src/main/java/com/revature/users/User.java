package com.revature.users;

import java.io.Serializable;

public class User implements Serializable{
	
	// Default serialVersionUID
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private String access = "null";
	private Integer accountNumber = 0;

	public User() {
		super();
	}
	
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
	public void setPassword(String password) {
		this.password = password;
	}

	public String getAccess() {
		return access;
	}
	public void setAccess(String access) {
		this.access = access;
	}
	
	public Integer getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(Integer accountNumber) {
		this.accountNumber = accountNumber;
	}
	
		
}
