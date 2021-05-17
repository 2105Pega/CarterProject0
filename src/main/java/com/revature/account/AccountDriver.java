package com.revature.account;

import java.util.ArrayList;

public class AccountDriver {
	private Account a1;
	private Account a2;

	public AccountDriver(Account account) {
		this.a1 = account;
	}

	// Transfer money between accounts
	public void transfer(Account recipient, double amount) {
		this.a2 = recipient;
		if (a1.getBalance() >= amount) {
			a1.setBalance(a1.getBalance() - amount);
			a2.setBalance(a2.getBalance() + amount);
			// something to display new amounts to admin, just customer's balance after success
			System.out.println("Success. Account " + a1.getAccountNumber() + " has transferred " + amount
					+ " to account " + a2.getAccountNumber() + ".");
		} else {
			System.out.println("Account " + a1.getAccountNumber() + " has insufficient funds.");
		}
	}

	// Deposit and chosen amount of money
	public void deposit(double amount) {
		if (amount > 0.0d) {
			// add "amount" to balance
			a1.setBalance(a1.getBalance()+amount);
			System.out.println("Success! New balance: " + a1.getBalance());
		} else {
			System.out.println("Error. Invalid amount.");
		}
	}

	// Withdraw a chosen amount of money
	public void withdraw(double amount) {
		// input > 0
		if(amount > 0.0d) {
			if(amount <= a1.getBalance()) {
				// take "amount" from balance
				a1.setBalance(a1.getBalance()-amount);
				System.out.println("Success! New balance: " + a1.getBalance());
			}
			else {
				// prevent overdraft
				System.out.println("Error. Amount exceeds balance. Available balance: " + a1.getBalance());
			}
		}
		else {
			System.out.println("Error. Invalid amount.");
		}
	}
		
	// Add and owner to an account
	public void addOwner(String newOwner) {
		ArrayList<String> owners = new ArrayList<String>();
		owners = a1.getAccountOwner();
		owners.add(newOwner);
		a1.setAccountOwner(owners);
	}
}
