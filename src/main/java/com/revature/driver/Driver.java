package com.revature.driver;

import java.util.Scanner;
import java.util.HashMap;

import com.revature.account.Account;
import com.revature.users.User;

public class Driver {

	public static void main(String[] args) {
		// Hash Map of Users (Primary key is username) and rel info
		HashMap<String, User> users =  new HashMap<String, User>();
		User admin = new User("admin", "admin");
		users.put("admin", admin);
		
		// Hash Map of accounts (Primary key is accountNumber) with rel info
		HashMap<Integer, Account> accounts = new HashMap<Integer, Account>();
		
		Scanner scan = new Scanner(System.in);
	    System.out.println("Enter username:");
	    
	    String username = scan.nextLine();
	    if(users.containsKey(username)) {
	    	
	    	System.out.println("Enter password:");
	    	String password = scan.nextLine();
	    } else {
	    	System.out.println("No account associated with that username.");
	    	System.out.println("Would you like to make and account? Y or N: ");
	    	String response = scan.next();
	    	if(response.toLowerCase().startsWith("y")) {
	    		System.out.println("Gimme a minute...");
	    	} 
	    	else if (response.toLowerCase().startsWith("n")) {
	    		System.out.println("Fine, be like that..");
	    	}
	    }
	    
	    System.out.println("Closing...");
	    scan.close();
	    System.out.println("Closed");
	}

}
