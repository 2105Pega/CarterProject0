package com.revature.users;

public class Customer {
	
	public Customer(User user) {
		user.setAccess("customer");
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
	
	
}
