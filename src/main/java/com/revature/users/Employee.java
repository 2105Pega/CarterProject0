package com.revature.users;

import com.revature.account.Account;

public class Employee {
	
	public Employee(User user) {
		user.setAccess("admin");
	}
	
	// view user information from account number
	public User viewCustomer(int accNum) {
		// obviously incorrect, just don't want compiler to yell at me
		User c = new User();
		return c;
	}
	
	// View customer info from username
	public User viewCustomer(String username) {
		// obviously incorrect, just don't want compiler to yell at me
		User c = new User();
		return c;
	}
	
	public void reviewApplication(Object app) {
		// take in application and either approve or deny
	}
	
	public void cancelAccount(Account accountNumber) {
		// cancel an account from account number
	}

}
