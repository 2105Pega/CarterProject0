package com.revature.driver;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.revature.account.Account;
import com.revature.account.AccountDriver;
import com.revature.users.Customer;
import com.revature.users.Employee;
import com.revature.users.User;

public class Driver {

	//@SuppressWarnings("unchecked")
	@SuppressWarnings("unchecked")
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
			do {
				System.out.println("What would you like to do?\n"
						+ "'View Customer' | 'View Account' | 'Edit Accounts' | 'View Applicatoins' | 'Log Out'");
				String response = scan.nextLine();
				if(response.toLowerCase().equals("view customer")) {
					System.out.println("Please input customer username:");
					String uname = scan.nextLine();
					admin.viewCustomer(uname);
					continue;
				} else if (response.toLowerCase().equals("view account")) {
					System.out.println("Please input account number:");
					Integer accNum = scan.nextInt();
					scan.nextLine();
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
					scan.nextLine();
					
					Account a = accounts.get(accNum);
					AccountDriver ad = new AccountDriver(a);
					do {
						System.out.println("Would you like to:\n'Withdraw' | 'Deposit' | 'Transfer' | "
								+ "'Cancel Account' | 'Exit'");
						String input = scan.nextLine();
						if (input.toLowerCase().equals("withdraw")) {
							System.out.println("Enter an amount to withdraw from account " + accNum);
							double amount = scan.nextDouble();
							scan.nextLine();
							ad.withdraw(amount);
							accounts.put(accNum, a);
							continue;
						} else if (input.toLowerCase().equals("deposit")) {
							System.out.println("Enter an amount to deposit to account " + accNum);
							double amount = scan.nextDouble();
							scan.nextLine();
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
							scan.nextLine();
							if (accNum.equals(accNum2)) {
								System.out.println("Cannot transfer to the same account!");
								continue;
							} else if (accounts.containsKey(accNum2)){
								System.out.println("Enter an ammount to transfer from account " 
									+ accNum + " to account " + accNum2);
								double amount = scan.nextDouble();
								scan.nextLine();
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
							String cancel = scan.nextLine();
							if (cancel.toLowerCase().startsWith("y")) {
								accounts.get(accNum).setStatus("canceled");
								System.out.println("Account canceled.");
								continue;
							} else if (cancel.toLowerCase().startsWith("n")) {
								System.out.println("Exiting account canceler...");
								continue;
							}
						} else if (input.toLowerCase().equals("exit")) {
							System.out.println("Exiting account editor...");
							break;
						} else {
							System.out.println("Invalid option.");
							continue;
						}
					} while(true);
				} else if (response.toLowerCase().equals("view applications")) {
					System.out.println("Accounts with open applications:");
					accounts.values().forEach(acc -> {
						if(acc.getStatus().equals("pending")) {
							System.out.println(acc.getAccountNumber() + " | " + acc.getAccountOwner() 
							+ " | " + acc.getAccountType() + " | " + acc.getBalance());
						}
					});
					System.out.println("Choose an account to view and approve/deny, or 'Exit'.");
					String input = scan.nextLine();
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
			} while(true);
		} else if (activeUser.getAccess().equals("customer")) {
			boolean hasAccount = false;
			Iterator<Entry<Integer, Account>> accIterator = accounts.entrySet().iterator();
			while(accIterator.hasNext()) {
				Map.Entry<Integer, Account> acc = (Map.Entry<Integer, Account>) accIterator.next();
				ArrayList<String> owners = acc.getValue().getAccountOwner();
				if (owners.contains(activeUser.getUsername())) {
					activeUser.setAccountNumber(acc.getKey());
					hasAccount = true;
					break;
				}
			}
			if(hasAccount) {
				Integer accNum = activeUser.getAccountNumber();
				Account userAccount = accounts.get(accNum);
				if (userAccount.getStatus().equals("approved")) {
					System.out.println("Welcome customer!"); 
					do {
						System.out.println("What would you like to do today?\n"
								+ "'Edit Account' | 'View Information' | 'Log out'");
						String response = scan.nextLine();
						if(response.toLowerCase().equals("view information")) {
							System.out.println("You have a " + userAccount.getAccountType() 
								+ " account with the number " + accNum + ", owned by " 
								+ userAccount.getAccountOwner() + ". The current balance is $" + userAccount.getBalance() + ".");
							continue;
						} else if(response.toLowerCase().equals("edit account")) {
							AccountDriver ad = new AccountDriver(userAccount);
							System.out.println("Would you like to:\n'Depost' | 'Withdraw' | 'Transfer' | 'Exit'");
							String edit = scan.nextLine();
							if(edit.toLowerCase().equals("deposit")) {
								System.out.println("Enter and amount to deposit:");
								double amnt = scan.nextDouble();
								scan.nextLine();
								ad.deposit(amnt);
								accounts.put(accNum, userAccount);
								continue;
							} else if(edit.toLowerCase().equals("withdraw")) {
								System.out.println("Enter and amount to withdraw:");
								double amnt = scan.nextDouble();
								scan.nextLine();
								ad.withdraw(amnt);
								accounts.put(accNum, userAccount);
								continue;
							} else if(edit.toLowerCase().equals("transfer")) {
								System.out.println("Enter an account to tranfer to:");
								Integer accNum2 = scan.nextInt();
								scan.nextLine();
								if (accNum.equals(accNum2)) {
									System.out.println("Cannot transfer to the same account!");
									continue;
								} else if (accounts.containsKey(accNum2)){
									System.out.println("Enter an ammount to transfer from account" 
										+ accNum + " to account " + accNum2);
									double amount = scan.nextDouble();
									scan.nextLine();
									Account a2 = accounts.get(accNum2);
									ad.transfer(a2, amount);
									accounts.put(accNum, userAccount);
									accounts.put(accNum2, a2);
									continue;
								} else {
									System.out.println("Invalid account number.");
									continue;
								}
							} else if(edit.toLowerCase().equals("exit")) {
								System.out.println("Exiting account editor...");
								break;
							}
						} else if(response.toLowerCase().equals("log out")) {
							System.out.println("Logging out...");
							break;
						} else {
							System.out.println("Invalid command");
							continue;
						}
					} while(true);
				} else if(userAccount.getStatus().equals("pending")) {
					System.out.println("Your application has not yet "
							+ "been processed. Please sign back in later. Signing out...");
				} else if(userAccount.getStatus().equals("denied")) {
					System.out.println("Your application for a " + userAccount.getAccountType() 
						+ " account with an initial deposit of " + userAccount.getBalance() 
						+ " has been denied. Please either:\n'Reapply' | 'Log Out'");
					accounts.remove(accNum);
					String apply = scan.nextLine();
					if(apply.toLowerCase().equals("reapply")) {
						Integer newAccount = Customer.applyForAccount(activeUser.getUsername(), accounts);
						activeUser.setAccountNumber(newAccount);
						users.put(activeUser.getUsername(), activeUser);
					} else if (apply.toLowerCase().equals("log out")) {
						System.out.println("Logging out..");
					}
				} else if(userAccount.getStatus().equals("denied")) {
					System.out.println("Your account " + userAccount.getAccountNumber() 
						+ " with a balance of " + userAccount.getBalance() 
						+ " has been canceled. Please either:\n'Reapply' | 'Log Out'");
					accounts.remove(accNum);
					String apply = scan.nextLine();
					if(apply.toLowerCase().equals("reapply")) {
						Integer newAccount = Customer.applyForAccount(activeUser.getUsername(), accounts);
						activeUser.setAccountNumber(newAccount);
						users.put(activeUser.getUsername(), activeUser);
					} else if (apply.toLowerCase().equals("log out")) {
						System.out.println("Logging out..");
					}
				}
			} else {
				System.out.println("Welcome new customer! Would you like to apply for an account?\n"
						+ "'Yes' | 'No'");
				String apply = scan.nextLine();
				if(apply.toLowerCase().startsWith("y")) {
					Integer newAccount = Customer.applyForAccount(activeUser.getUsername(), accounts);
					activeUser.setAccountNumber(newAccount);
					users.put(activeUser.getUsername(), activeUser);
				} else if (apply.toLowerCase().startsWith("n")) {
					System.out.println("If you have already applied, your application has not "
							+ "been processed. Please sign back in later. Signing out...");
				}
			}
		} else {
			System.out.println("Internal error.");
		}

		// Serialize out
		userSerial.serializeMe(users, "users.txt.");
		accountSerial.serializeMe(accounts, "accounts.txt");

		System.out.println("Closing...");
		scan.close();
		System.out.println("Closed");
	}

}
