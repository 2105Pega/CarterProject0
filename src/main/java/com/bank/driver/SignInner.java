package com.bank.driver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Scanner;

import com.bank.users.User;
import com.bank.users.UserService;

public class SignInner {

	public static User logIn(HashMap<Integer, User> users, Scanner signInScan) {
		FileInputStream fis;
		String adminUsername = "";
		String adminPassword = "";
		try {
			fis = new FileInputStream("src/main/resources/banking_db_properties.properties");
			Properties prop = new Properties();
			prop.load(fis);
			adminUsername = prop.getProperty("ADMIN_USERNAME");
			adminPassword = prop.getProperty("ADMIN_PASSWORD");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean hasUsername = false;
		System.out.println("Enter username: ");
		String username = signInScan.nextLine();
		if (username.equals(adminUsername)) {
			System.out.println("Enter password:");
			String password = signInScan.nextLine();
			String expectedPassword = adminPassword;
			if (password.equals(expectedPassword)) {
				System.out.println("Logging in...");
				User activeUser = new User(0, "admin", "admin", "admin", "admin", "admin");
				return activeUser;
			} else {
				System.out.println("Incorrect password :(");
				return null;
			}
		}
		Collection<User> usersColl = users.values();
		Iterator<User> collIt = usersColl.iterator();
		User activeUser = null;
		while(collIt.hasNext()) {
			activeUser = collIt.next();
			String storedUsername = activeUser.getUsername();
			if (username.equals(storedUsername)) {
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
				return activeUser;
			} else {
				System.out.println("Incorrect password :(");
				return null;
			}

		} else {
			// no user with that username detected
			System.out.println("No account associated with that username.\n"
					+ "Would you like to create and acocunt?\n'Yes'| 'No'");
			String response = signInScan.nextLine();
			if (response.toLowerCase().startsWith("y")) {
				return signUp(signInScan);
			} else {
				return null;
			}
		}
	}

	public static User signUp(Scanner signInScan) {
		UserService uServ = new UserService();
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
		System.out.println("New User Created!");
		return temp;
	}

}
