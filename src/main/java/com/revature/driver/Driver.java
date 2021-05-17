package com.revature.driver;

import java.util.Scanner;
import java.util.HashMap;
import java.util.Iterator;

import com.revature.account.Account;
import com.revature.users.Customer;
import com.revature.users.Employee;
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
		boolean signedIn = false;
		do {
			System.out.println("Would you like to\n'Log In' | 'Sign Up'?");
			SignInner signInMenu = new SignInner(scan, users);
			String response = scan.nextLine();
			if (response.toLowerCase().startsWith("l")) {
				activeUser = signInMenu.logIn();
				if (activeUser == null)
					continue;
				else
					signedIn = true;
			} else if (response.toLowerCase().startsWith("s")) {
				activeUser = signInMenu.signUp();
				if (activeUser == null)
					continue;
				else
					signedIn = true;
			}
		} while (signedIn == false);
		System.out.println(activeUser.getAccess());
		// Start using the app.
		if (activeUser.getAccess().equals("admin")) {
			Employee admin = new Employee();
			System.out.println("Welcome employee!");
			while(true) {
				System.out.println("What would you like to do today?\n"
						+ "'View Customer' | 'View Account' | 'Edit Accounts' | 'View Applicatoins' | 'Log Out'");
				String response = scan.nextLine();
				if(response.toLowerCase().equals("view customer")) {
					System.out.println("Please input customer username:");
					String uname = scan.nextLine();
					admin.viewCustomer(users, uname);
					continue;
				} else if (response.toLowerCase().equals("view account")) {
					System.out.println("Please input account number:");
					Integer accNum = Integer.parseInt(scan.nextLine());
					admin.viewAccount(accounts, accNum);
					continue;
				} else if (response.toLowerCase().equals("edit accounts")) {
					System.out.println("Under Construction!");
					continue;
				} else if (response.toLowerCase().equals("view applications")) {
					System.out.println("Accounts with open applications:");
					accounts.values().forEach(acc -> {
						if(acc.getStatus().equals("pending")) {
							System.out.println(acc.getAccountNumber() + " | " + acc.getAccountType()
								+ " | " + acc.getBalance());
						}
					});
					System.out.println("Choose an account to view and approve/deny, or 'Cancel'.");
					String input = scan.nextLine();
					if (input.toLowerCase().startsWith("cancel")) {
						System.out.println("Canceling application review");
					} else {
						Integer accNum = Integer.parseInt(input);
						admin.reviewApplication(scan, accounts, accNum);
					}
					continue;
				} else if (response.toLowerCase().equals("log out")) {
					System.out.println("Goodbye!");
					break;
				} else 
					System.out.println("Invalid choice. Try again.");
					continue;
			}
			
		} else if (activeUser.getAccess().equals("customer")) {
			Customer cust = new Customer(activeUser);
			System.out.println("Welcome customer! What would you like to do today?\n"
					+ "'Edit Account' | 'View Information'");
		} else if (activeUser.getAccountNumber().equals(0)) {
			System.out.println("Welcome new customer! Would you like to apply for an account?\n"
					+ "'Yes' | 'No'");
		}

		// Serialize out
		userSerial.serializeMe(users, "users.txt.");
		accountSerial.serializeMe(accounts, "accounts.txt");

		System.out.println("Closing...");
		scan.close();
		System.out.println("Closed");
	}

}
