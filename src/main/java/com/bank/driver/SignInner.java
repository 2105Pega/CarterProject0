package com.bank.driver;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import com.bank.users.User;
import com.bank.users.UserService;

public class SignInner {

	public static User logIn(HashMap<Integer, User> users) {
		Scanner signInScan = new Scanner(System.in);
		System.out.println("Enter username: ");
		String username = signInScan.nextLine();
		Collection<User> usersColl = users.values();
		Iterator<User> collIt = usersColl.iterator();
		boolean hasUsername = false;
		User activeUser = null;
		while(collIt.hasNext()) {
			String storedUsername = collIt.next().getUsername();
			if (username.equals(storedUsername)) {
				activeUser = collIt.next();
				hasUsername = true;
				break;
			}
		}
		// If the username is associated with a user:
		if (hasUsername) {
			// compare password associated with username
			System.out.println("Enter password:");
			String password = signInScan.nextLine();
			String expectedPassword = activeUser.getPassword();
			if (password.equals(expectedPassword)) {
				System.out.println("Logging in...");
				signInScan.close();
				return activeUser;
			} else {
				System.out.println("Incorrect password :(");
				signInScan.close();
				return null;
			}

		} else {
			// no user with that username detected
			System.out.println("No account associated with that username.\n"
					+ "Would you like to create and acocunt?\n'Yes'| 'No'");
			String response = signInScan.nextLine();
			if (response.toLowerCase().startsWith("y")) {
				signInScan.close();
				return signUp();
			} else {
				signInScan.close();
				return null;
			}
		}
	}

	public static User signUp() {
		UserService uServ = new UserService();
		Scanner signInScan = new Scanner(System.in);
		// create new username
		System.out.println("Enter new username: ");
		String username = signInScan.nextLine();
		System.out.println("Enter new Password: ");
		String password = signInScan.nextLine();
		System.out.println("Please input some personal info:\nFirst Name:");
		String fName = signInScan.nextLine();
		System.out.println("Last Name:");
		String lName = signInScan.nextLine();
		User temp = new User(0, username, password, fName, lName, "customer");
		uServ.addUser(temp);
		System.out.println("New User Created!\nSigning you in...");
		signInScan.close();
		return temp;
	}

}
