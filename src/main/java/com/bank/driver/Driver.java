package com.bank.driver;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.bank.account.Account;
import com.bank.account.AccountService;
import com.bank.users.User;
import com.bank.users.UserService;

public class Driver {

	public static void main(String[] args) {
		
		// Hash Map of Users (Primary key is userId) and rel info
		UserService uServ = new UserService();
		HashMap<Integer, User> users = new HashMap<Integer, User>();
		users = uServ.getAllUsers();
		User activeUser = null;
		
		// Hash Map of accounts (Primary key is accountNumber) with rel info
		AccountService accServ = new AccountService();
		HashMap<Integer, Account> accounts = new HashMap<Integer, Account>();
		accounts = accServ.getAllAccounts();
		Account activeAccount = null;
		
		// Log in or sign up on the app
		Scanner scan = new Scanner(System.in);
		System.out.println("Welcome!");
		boolean signedIn = false;
		do {
			// Implement a customer sign in and an employee sign in 
			// employee sign in will read from a properties file
			System.out.println("Would you like to\n'Log In' | 'Sign Up'?");
			String response = scan.nextLine();
			if (response.toLowerCase().startsWith("l")) {
				activeUser = SignInner.logIn(users);
				if (activeUser == null)
					continue;
				else
					signedIn = true;
			} else if (response.toLowerCase().startsWith("s")) {
				activeUser = SignInner.signUp();
				if (activeUser == null)
					continue;
				else {
					signedIn = true;
					users.put(activeUser.getUserId(), activeUser);
				}
			}
		} while (signedIn == false);
		
		// Start using the app.
		/*if (activeUser.getAccess().equals("admin")) {
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
					AccountService ad = new AccountService(a);
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
		} else */
		if (activeUser.getAccess().equals("customer")) {
			ArrayList<Account> userAccounts = accServ.getMyAccounts(activeUser.getUserId());
			boolean hasAccount = !userAccounts.isEmpty();
			if(hasAccount) {
				System.out.println("Welcome customer!"); 
				do {
					System.out.println("What would you like to do today?\n"
							+ "'Edit Account' | 'View Information' | 'Update User Information' | 'Log out'");
					String response = scan.nextLine();
					if(response.toLowerCase().equals("view information")) {
						System.out.println("First Name: " + activeUser.getfName() + "\nLast Name: " + activeUser.getlName() +
							"\nUsername: " + activeUser.getUsername()+ "\nPasssword : " + activeUser.getPassword() + 
							"\nUser ID: " + activeUser.getUserId() + "\nAccounts :\nAccount Number | Account Type | Account Balance");
						for (Account acc : userAccounts) {
							System.out.println(acc.getAccountNumber() + " | " + acc.getAccountType() + " | " + acc.getBalance());
						}
						continue;
					} else if(response.toLowerCase().equals("edit account")) {
						System.out.println("Select an Account to edit:\nAccount Number | Account Type | Account Balance");
						for (Account acc : userAccounts) {
							System.out.println(acc.getAccountNumber() + " | " + acc.getAccountType() + " | " + acc.getBalance());
						}
						Integer activeAccNum = scan.nextInt();
						scan.nextLine();
						activeAccount = accounts.get(activeAccNum);
						if (activeAccount.getStatus().equals("approved")) {
							System.out.println("Would you like to:\n'Depost' | 'Withdraw' | 'Transfer' | 'Exit'");
							String edit = scan.nextLine();
							if(edit.toLowerCase().equals("deposit")) {
								System.out.println("Enter and amount to deposit:");
								double amnt = scan.nextDouble();
								scan.nextLine();
								accServ.deposit(activeAccount, amnt);
								accounts.put(activeAccNum, activeAccount);
								continue;
							} else if(edit.toLowerCase().equals("withdraw")) {
								System.out.println("Enter and amount to withdraw:");
								double amnt = scan.nextDouble();
								scan.nextLine();
								accServ.withdraw(activeAccount, amnt);
								accounts.put(activeAccNum, activeAccount);
								continue;
							} else if(edit.toLowerCase().equals("transfer")) {
								System.out.println("Enter an account to tranfer to (account number):");
								Integer accNum2 = scan.nextInt();
								scan.nextLine();
								if (activeAccNum.equals(accNum2)) {
									System.out.println("Cannot transfer to the same account!");
									continue;
								} else if (accounts.containsKey(accNum2)){
									System.out.println("Enter an ammount to transfer from account" 
										+ activeAccNum + " to account " + accNum2);
									double amount = scan.nextDouble();
									scan.nextLine();
									Account a2 = accounts.get(accNum2);
									accServ.transfer(activeAccount, a2, amount);
									accounts.put(activeAccNum, activeAccount);
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
						} else if(activeAccount.getStatus().equals("pending")) {
							System.out.println("Your application has not yet "
								+ "been processed. Please sign back in later. Signing out...");
							break;
						} else if(activeAccount.getStatus().equals("denied")) {
							System.out.println("Your application for a " + activeAccount.getAccountType() 
								+ " account with an initial deposit of " + activeAccount.getBalance() 
								+ " has been denied. Please either:\n'Reapply' | 'Log Out'");
							accounts.remove(activeAccNum);
							accServ.deleteAccount(activeAccNum);
							String apply = scan.nextLine();
							if(apply.toLowerCase().equals("reapply")) {
								// here dumbass (5/24, 11 PM)
								Integer newAccount = Customer.applyForAccount(activeUser.getUsername(), accounts);
								activeUser.setAccountNumber(newAccount);
								users.put(activeUser.getUsername(), activeUser);
							} else if (apply.toLowerCase().equals("log out")) {
								System.out.println("Logging out..");
							}
						} else if(userAccount.getStatus().equals("canceled")) {
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
					} else if(response.toLowerCase().equals("log out")) {
						System.out.println("Logging out...");
						break;
					} else {
						System.out.println("Invalid command");
						continue;
					}
				} while(true);
				
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

		System.out.println("Closing...");
		scan.close();
		System.out.println("Closed");
	}

}
