package com.bank.users;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable{
	
	// Default serialVersionUID
	private static final long serialVersionUID = 1L;
	private Integer userId;
	private String username;
	private String password;
	private String fName;
	private String lName;
	private String access = "null";
	private List<Integer> accountNums = new ArrayList<Integer>();
	
	public User() {
		super();
	}	
	public User(Integer userId, String username, String password, String fName, String lName, String access, List<Integer> accounts) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.fName = fName;
		this.lName = lName;
		this.access = access;
		this.accountNums = accounts;
	}
	public User(Integer userId, String username, String password, String fName, String lName, String access) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.fName = fName;
		this.lName = lName;
		this.access = access;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	
	public List<Integer> getAccounts() {
		return accountNums;
	}
	public void setAccounts(List<Integer> accounts) {
		this.accountNums = accounts;
	}
	public void addAccount(int accountNum) {
		this.accountNums.add(accountNum);
	}
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}

	
	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username + ", password=" + password + ", fName=" + fName
				+ ", lName=" + lName + ", access=" + access + ", accountNums=" + accountNums + "]";
	}
	
}
