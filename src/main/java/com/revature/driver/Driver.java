package com.revature.driver;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

import com.revature.account.Account;
import com.revature.account.AccountDriver;
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
		
		// Start using the app.
		if (activeUser.getAccess().equals("admin")) {
			Employee admin = new Employee(scan, users, accounts);
			System.out.println("\nWelcome employee!");
			while(true) {
				System.out.println("What would you like to do?\n"
						+ "'View Customer' | 'View Account' | 'Edit Accounts' | 'View Applicatoins' | 'Log Out'");
				String response = scan.nextLine();
				if(response.toLowerCase().equals("view customer")) {
					System.out.println("Please input customer username:");
					String uname = scan.next();
					admin.viewCustomer(uname);
					continue;
				} else if (response.toLowerCase().equals("view account")) {
					System.out.println("Please input account number:");
					Integer accNum = Integer.parseInt(scan.next());
					admin.viewAccount(accNum);
					continue;
				} else if (response.toLowerCase().equals("edit accounts")) {
					System.out.println("Select an account to edit:");
					accounts.values().forEach(acc -> {
						if(acc.getStatus().equals("approved")) {
							System.out.println(acc.getAccountNumber() + " | " + acc.getAccountType()
								+ " | " + acc.getBalance());
						}
					});
					Integer accNum = scan.nextInt();
					Account a = accounts.get(accNum);
					AccountDriver ad = new AccountDriver(a);
					while(true) {
						System.out.println("Would you like to:\n'Withdraw' | 'Deposit' | 'Transfer' | "
								+ "'Cancel Account' | 'Exit'");
						String input = scan.next();
						if (input.toLowerCase().equals("withdraw")) {
							System.out.println("Enter an amount to withdraw from account " + accNum);
							double amount = scan.nextDouble();
							ad.withdraw(amount);
							accounts.put(accNum, a);
							continue;
						} else if (input.toLowerCase().equals("deposit")) {
							System.out.println("Enter an amount to deposit to account " + accNum);
							double amount = scan.nextDouble();
							ad.deposit(amount);
							accounts.put(accNum, a);
							continue;
						} else if (input.toLowerCase().equals("transfer")) {
							System.out.println("Select an account to tranfer to:");
							accounts.values().forEach(acc -> {
								if(acc.getStatus().equals("approved")) {
									System.out.println(acc.getAccountNumber() + " | " + acc.getAccountType()
										+ " | " + acc.getBalance());
								}
							});
							Integer accNum2 = scan.nextInt();
							if (accNum.equals(accNum2)) {
								System.out.println("Cannot transfer to the same account!");
								continue;
							} else if (accounts.containsKey(accNum2)){
								System.out.println("Enter an ammount to transfer from account" 
									+ accNum + " to account " + accNum2);
								double amount = scan.nextDouble();
								Account a2 = accounts.get(accNum2);
								ad.transfer(a2, amount);
								accounts.put(accNum, a);
								accounts.put(accNum2, a2);
								continue;
							} else {
								System.out.println("Invalid account number.");
								continue;
							}							
						} else if (input.toLowerCase().equals("cancel account")) {
							System.out.println("Are you sure you want to cancel account "
								+ accNum + "?\n'Yes' | 'No'");
							String cancel = scan.next();
							if (cancel.toLowerCase().startsWith("y")) {
								ArrayList<String> owners = accounts.get(accNum).getAccountOwner();
								for(String owner:owners) {
									users.get(owner).setAccess("pending");
									users.get(owner).setAccountNumber(0);
								}
								accounts.remove(accNum);
								System.out.println("Account deleted.");
								continue;
							} else if (cancel.toLowerCase().startsWith("n")) {
								System.out.println("Exiting account canceler...");
								continue;
							}
						} else if (input.toLowerCase().equals("exit")) {
							System.out.println("Exiting account editor...");
							break;
						}
					}
				} else if (response.toLowerCase().equals("view applications")) {
					System.out.println("Accounts with open applications:");
					accounts.values().forEach(acc -> {
						if(acc.getStatus().equals("pending")) {
							System.out.println(acc.getAccountNumber() + " | " + acc.getAccountType()
								+ " | " + acc.getBalance());
						}
					});
					System.out.println("Choose an account to view and approve/deny, or 'Exit'.");
					String input = scan.next();
					if (input.toLowerCase().startsWith("cancel")) {
						System.out.println("Canceling application review");
					} else {
						Integer accNum = Integer.parseInt(input);
						admin.reviewApplication(accNum);
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
