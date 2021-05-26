package com.bank.driver;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

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
				activeUser = SignInner.logIn(users,scan);
				if (activeUser == null)
					continue;
				else
					signedIn = true;
			} else if (response.toLowerCase().startsWith("s")) {
				activeUser = SignInner.signUp(scan);
				if (activeUser == null)
					continue;
				else {
					signedIn = true;
					users.put(activeUser.getUserId(), activeUser);
				}
			}
		} while (signedIn == false);
		
		// Start using the app.
		if (activeUser.getAccess().equals("admin")) {
			System.out.println("\nWelcome employee!");
			do {
				System.out.println("What would you like to do?\n"
						+ "'View Customer' | 'Edit Customers' | 'View Account' | 'Edit Accounts' | 'View Applications' | 'Log Out'");
				String response = scan.nextLine();
				if(response.toLowerCase().equals("view customer")) {
					System.out.println("Please input customer id:");
					Integer custUserId = scan.nextInt();
					scan.nextLine();
					User customer = users.get(custUserId);
					System.out.println(customer.toString());
					continue;
				} else if (response.toLowerCase().equals("edit customers")) {
					System.out.println("Select a customer to edit (by userId), or 'Create New User'");
					users.values().forEach(u -> {
						System.out.println(u.toString());
					});
					String input = scan.nextLine();
					boolean isNumber = false;
					try {
						Integer.parseInt(input);
						isNumber = true;
					} catch (NumberFormatException e) {
						isNumber = false;
					}
					if(input.toLowerCase().equals("create new user")) {
						SignInner.signUp(scan);
						continue;
					} else if (isNumber) {
						Integer userId = Integer.parseInt(input);
						User customer = users.get(userId);
						System.out.println("What would you like to do to user " + userId + "?\n"
								+ "'Update User' | 'Delete User' | 'Exit'");
						String updateOrDelete = scan.nextLine();
						if(updateOrDelete.toLowerCase().startsWith("update")) {
							uServ.updateUser(customer);
							users.put(userId, customer);
							System.out.println("User updated!: " + customer.toString());
							continue;
						} else if (updateOrDelete.toLowerCase().startsWith("delete")){
							uServ.deleteUser(userId);
							System.out.println("Customer " + userId + " deleted.");
							continue;
						} else if (updateOrDelete.toLowerCase().startsWith("exit")) {
							System.out.println("Exiting...");
							continue;
						}
					} else {
						System.out.println("Invalid response.");
						continue;
					}
				} else if (response.toLowerCase().equals("view account")) {
					System.out.println("Please input account number:");
					Integer accNum = scan.nextInt();
					scan.nextLine();
					Account custAccount = accounts.get(accNum);
					System.out.println(custAccount.toString());
					continue;
				} else if (response.toLowerCase().equals("edit accounts")) {
					System.out.println("Select an account to edit:");
					accounts.values().forEach(acc -> {
						if(acc.getStatus().equals("approved")) {
							System.out.println(acc.getAccountNumber() + " | " + acc.getAccountType()
								+ " | " + acc.getBalance());
						}
					});
					Integer accNum1 = scan.nextInt();
					scan.nextLine();
					
					Account acc1 = accounts.get(accNum1);
					do {
						System.out.println("Would you like to:\n'Withdraw' | 'Deposit' | 'Transfer' | "
								+ "'Cancel Account' | 'Exit'");
						String input = scan.nextLine();
						if (input.toLowerCase().equals("withdraw")) {
							System.out.println("Enter an amount to withdraw from account " + accNum1);
							double amount = scan.nextDouble();
							scan.nextLine();
							accServ.withdraw(acc1,amount);
							accounts.put(accNum1, acc1);
							continue;
						} else if (input.toLowerCase().equals("deposit")) {
							System.out.println("Enter an amount to deposit to account " + accNum1);
							double amount = scan.nextDouble();
							scan.nextLine();
							accServ.deposit(acc1,amount);
							accounts.put(accNum1, acc1);
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
							if (accNum1.equals(accNum2)) {
								System.out.println("Cannot transfer to the same account!");
								continue;
							} else if (accounts.containsKey(accNum2)){
								Account acc2 = accounts.get(accNum2);
								System.out.println("Enter an ammount to transfer from account " 
									+ accNum1 + " to account " + accNum2);
								double amount = scan.nextDouble();
								scan.nextLine();
								accServ.transfer(acc1, acc2, amount);
								accounts.put(accNum1, acc1);
								accounts.put(accNum2, acc2);
								continue;
							} else {
								System.out.println("Invalid account number.");
								continue;
							}							
						} else if (input.toLowerCase().equals("cancel account")) {
							System.out.println("Are you sure you want to cancel account "
								+ accNum1 + "?\n'Yes' | 'No'");
							String cancel = scan.nextLine();
							if (cancel.toLowerCase().startsWith("y")) {
								accounts.get(accNum1).setStatus("canceled");
								accServ.cancelAccount(accNum1);
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
					accServ.reviewApplication(scan);
					continue;
				} else if (response.toLowerCase().equals("log out")) {
					System.out.println("Goodbye!");
					break;
				} else 
					System.out.println("Invalid choice. Try again.");
					continue;
			} while(true);
		} else if (activeUser.getAccess().equals("customer")) {
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
								userAccounts = accServ.getMyAccounts(activeUser.getUserId());
								continue;
							} else if(edit.toLowerCase().equals("withdraw")) {
								System.out.println("Enter and amount to withdraw:");
								double amnt = scan.nextDouble();
								scan.nextLine();
								accServ.withdraw(activeAccount, amnt);
								accounts.put(activeAccNum, activeAccount);
								userAccounts = accServ.getMyAccounts(activeUser.getUserId());
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
									userAccounts = accServ.getMyAccounts(activeUser.getUserId());
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
								+ "been processed, and therefore this account cannot be edited."
								+ " Please sign back in later. Signing out...");
							break;
						} else if(activeAccount.getStatus().equals("denied")) {
							System.out.println("Your application for a " + activeAccount.getAccountType() 
								+ " account with an initial deposit of " + activeAccount.getBalance() 
								+ " has been denied. Please either:\n'Reapply' | 'Log Out'");
							accounts.remove(activeAccNum);
							accServ.deleteAccount(activeAccNum);
							String apply = scan.nextLine();
							if(apply.toLowerCase().equals("reapply")) {
								Account newAccount = accServ.applyForAccount(activeUser.getUserId(),scan);
								activeUser.addAccount(newAccount.getAccountNumber());
								users.put(activeUser.getUserId(), activeUser);
							} else if (apply.toLowerCase().equals("log out")) {
								System.out.println("Logging out..");
								break;
							}
							
						} else if(activeAccount.getStatus().equals("canceled")) {
							System.out.println("Your account " + activeAccount.getAccountNumber() 
								+ " with a balance of " + activeAccount.getBalance() 
								+ " has been canceled. Please either:\n'Reapply' | 'Log Out'");
							accounts.remove(activeAccNum);
							accServ.deleteAccount(activeAccNum);
							String apply = scan.nextLine();
							if(apply.toLowerCase().equals("reapply")) {
								Account newAccount = accServ.applyForAccount(activeUser.getUserId(),scan);
								activeUser.addAccount(newAccount.getAccountNumber());
								users.put(activeUser.getUserId(), activeUser);
							} else if (apply.toLowerCase().equals("log out")) {
								System.out.println("Logging out..");
								break;
							}
						}
					} else if(response.toLowerCase().equals("update user information")) {
						String newUsername;
						String newPassword;
						String newFName;
						String newLName;
						System.out.println("Please input new information:\nUsername:");
						newUsername = scan.nextLine();
						System.out.println("Password:");
						newPassword = scan.nextLine();
						System.out.println("First Name:");
						newFName = scan.nextLine();	
						System.out.println("Last Name:");
						newLName = scan.nextLine();
						activeUser.setUsername(newUsername);
						activeUser.setPassword(newPassword);
						activeUser.setfName(newFName);
						activeUser.setlName(newLName);
						users.put(activeUser.getUserId(), activeUser);
						uServ.updateUser(activeUser);
						continue;
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
					Account newAccount = accServ.applyForAccount(activeUser.getUserId(),scan);
					activeUser.addAccount(newAccount.getAccountNumber());
					users.put(activeUser.getUserId(), activeUser);
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
