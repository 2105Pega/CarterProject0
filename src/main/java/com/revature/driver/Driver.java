package com.revature.driver;

import java.util.Scanner;
import java.util.HashMap;

import com.revature.account.Account;
import com.revature.users.User;

public class Driver {

	//@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		// Serialize in
		HashMap<String, User> users = new HashMap<String, User>();
		HashMap<Integer, Account> accounts = new HashMap<Integer, Account>();
		// Hash Map of Users (Primary key is username) and rel info
		ObjectSerializer<HashMap<String, User>> userSerial = new ObjectSerializer<HashMap<String, User>>();
		users = (HashMap<String, User>) userSerial.deserializeMe("users.txt");
		User activeUser = null;
		// Hash Map of accounts (Primary key is accountNumber) with rel info
		ObjectSerializer<HashMap<Integer, Account>> accountSerial = new ObjectSerializer<HashMap<Integer, Account>>();
		accounts = (HashMap<Integer, Account>) accountSerial.deserializeMe("accounts.txt");
		
		// Log in or sign up on the app
		Scanner scan = new Scanner(System.in);
		System.out.println("Welcome!");
		boolean exit = false;
		do {
			System.out.println("Would you like to 'Log In' or 'Sign Up'?");
			SignInner signInMenu = new SignInner(scan, users);
			String response = scan.nextLine();
			if (response.toLowerCase().startsWith("l")) {
				activeUser = signInMenu.logIn();
				if (activeUser == null)
					continue;
				else
					exit = true;
			} else if (response.toLowerCase().startsWith("s")) {
				activeUser = signInMenu.signUp();
				if (activeUser == null)
					continue;
				else
					exit = true;
			}
		} while (exit == false);

		// Serialize out
		userSerial.serializeMe(users, "users.txt.");
		accountSerial.serializeMe(accounts, "accounts.txt");

		System.out.println("Closing...");
		scan.close();
		System.out.println("Closed");
	}

}
