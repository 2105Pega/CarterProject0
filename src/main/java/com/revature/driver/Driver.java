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
		
		// Log in or sign up on the app
		Scanner scan = new Scanner(System.in);
		System.out.println("Welcome!\nWould you like to 'Log In' or 'Sign Up'?");
		String response = scan.nextLine();
		if (response.toLowerCase().startsWith("l")) {
    		System.out.println("Enter username: ");
    		String username = scan.nextLine();
    		// If the username is associated with a user:
    	    if (users.containsKey(username)) {
    	    	// compare password associated with username
    	    	System.out.println("Enter password:");
    	    	String password = scan.nextLine();
    	    	String expectedPassword = users.get(username).getPassword();
    	    	if (password.equals(expectedPassword)) {
    	    		System.out.println("Logging in...");
    	    		browse(users.get(username));
    	    	} else {
    	    		System.out.println("Incorrect password :(");
    	    		// go back and try again 
    	    	}
    	    	
    	    } else {
    	    	// no user with that username detected
    	    	System.out.println("No account associated with that username.");
    	    	// go back and try again
    	    }
    	} else if (response.toLowerCase().startsWith("s")) {
    		// create new username
    		System.out.println("Enter new username: ");
    		String username = scan.nextLine();
    		System.out.println("Enter new Password: ");
    		String password = scan.nextLine();
    		User temp = new User(username, password);
    		users.put(username, temp);
    		System.out.println("New User Created!");
    		// auto-sign in and browse
    		browse(temp);
    	}
	    
	    
	    
	    System.out.println("Closing...");
	    scan.close();
	    System.out.println("Closed");
	}
	
	public static void browse(User user) {
		
	}

}
