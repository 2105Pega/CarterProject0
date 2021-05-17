package com.revature.users;

import java.util.HashMap;
import java.util.Scanner;

import com.revature.account.Account;

public class Employee {
	
	public Employee() {
		super();
	}
	
	// View customer info from username
	public void viewCustomer(HashMap<String, User> users, String username) {
		User cust = null;
		if(users.containsKey(username)) {
			cust = users.get(username);
			System.out.println("User " + cust.getUsername() + " has " + cust.getAccess()
			+ " access. Their password is " + cust.getPassword() 
			+ ". The account associated with this user is " + cust.getAccountNumber() + ".");
		}
		else
			System.out.println("No customer with specified username.");
	}
	
	public void viewAccount(HashMap<Integer, Account> accounts, Integer accNum) {
		Account acc = null;
		if(accounts.containsKey(accNum)) {
			acc = accounts.get(accNum);
			System.out.println("The account " + acc.getAccountNumber() + " is a " + acc.getAccountType() 
			+ " account owned by " + acc.getAccountOwner() 
			+ ". The account is has an approval status of " + acc.getStatus() 
			+ " with a pending balance of " + acc.getBalance() + ".");
		}
		else
			System.out.println("No account with specified account number.");
		
	}
	
	public void reviewApplication(Scanner scan, HashMap<Integer, Account> accounts, Integer accNum) {
		do {
			System.out.println("Would you like to\n'Approve' | 'Deny'");
			String response = scan.nextLine();
			if(response.toLowerCase().startsWith("a")) {
				accounts.get(accNum).setStatus("approved");
				break;
			} else if (response.toLowerCase().startsWith("d")) {
				accounts.get(accNum).setStatus("denied");
				break;
			} else {
				System.out.println("Invalid action.");
				continue;
			}
		} while (true);
	}
	
	public void cancelAccount(Account accountNumber) {
		// cancel an account from account number
	}

}
