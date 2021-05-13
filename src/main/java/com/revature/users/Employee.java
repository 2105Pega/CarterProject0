package com.revature.users;

import com.revature.account.Account;

public class Employee {
	private String username;
	private final String password = "admin";
	private String firstName;
	private String lastName;
	private final String access = "admin";
	
	public Employee(String username) {
		this.username = username;
	}
	
	// view customer information from account number
	public Customer viewCustomer(int accountNumber) {
		// obviously incorrect, just don't want compiler to yell at me
		Customer c = new Customer();
		return c;
	}
	
	// View customer info from username
	public Customer viewCustomer(String username) {
		// obviously incorrect, just don't want compiler to yell at me
		Customer c = new Customer();
		return c;
	}
	
	public void reviewApplication(Object app) {
		// take in application and either approve or deny
	}
	
	public void cancelAccount(Account accountNumber) {
		// cancel an account from account number
	}

	
	// Getters and setters for relevant vars
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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

}
