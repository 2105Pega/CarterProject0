package com.revature.users;

import com.revature.account.*;

public class Customer {
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private int accountNumber;
	private final String access = "customer";
	
	public Customer() {
		System.out.println("User created!");
	}
	
	public Customer(String username, String password) {
		this.username = username;
		this.password = password;
		System.out.println("User created!");
	}
	
	public void applyForAccount(double initBalance) {
		
		//  write out application
	}
	
	public void applyForAccount(double initBalance, String accountType) {
		
		//  write out application
	}
	
	public void addJointAccount(int accountNumber) {
		// request to join a joint account
		// will not allow to join and add balance at same time
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

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	
	
}
