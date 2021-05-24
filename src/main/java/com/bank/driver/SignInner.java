package com.bank.driver;

import java.util.HashMap;
import java.util.Scanner;

import com.bank.users.User;

public class SignInner {
	Scanner scan;
	HashMap<String, User> users;

	public SignInner(Scanner _scan, HashMap<String, User> _users) {
		this.scan = _scan;
		this.users = _users;
	}

	public User logIn() {
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
				return users.get(username);
			} else {
				System.out.println("Incorrect password :(");
				return null;
			}

		} else {
			// no user with that username detected
			System.out.println("No account associated with that username.\n"
					+ "Would you like to create and acocunt with that username?\n'Yes'| 'No'");
			String response = scan.nextLine();
			if (response.toLowerCase().startsWith("y"))
				return signUp(username);
			else
				return null;
		}
	}

	public User signUp() {
		// create new username
		System.out.println("Enter new username: ");
		String username = scan.nextLine();
		if(users.containsKey(username)) {
			System.out.println("Username already taken.");
			return null;
		} else {
			System.out.println("Enter new Password: ");
    		String password = scan.nextLine();
    		User temp = new User(username, password);
    		System.out.println("Are you and employee?\n'Yes'| 'No'");
    		String employee = scan.nextLine();
    		if (employee.toLowerCase().startsWith("y"))
    			temp.setAccess("admin");
    		else if(employee.toLowerCase().startsWith("n"))
    			temp.setAccess("customer");
    		users.put(username, temp);
    		System.out.println("New User Created!\nSigning you in...");
    		return temp;
		}
	}
	
	public User signUp(String _username) {
		System.out.println("Enter new Password: ");
		String password = scan.nextLine();
		User temp = new User(_username, password);
		System.out.println("Are you and employee?\n'Yes'| 'No'");
		String employee = scan.nextLine();
		if (employee.toLowerCase().startsWith("y"))
			temp.setAccess("admin");
		else if(employee.toLowerCase().startsWith("n"))
			temp.setAccess("customer");
		users.put(_username, temp);
		System.out.println("New User Created!\nSigning you in...");
		return temp;
	}
}
