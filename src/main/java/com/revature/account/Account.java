package com.revature.account;

public class Account {
	
	private double balance;
	private int accountNumber;
	private String accountType;
	
	public Account(int accountNumber, double balance, String accountType) {
		this.balance = balance;
		this.accountNumber = accountNumber;
		this.accountType = accountType;
	}
	
	// Deposit and chosen amount of money
	public void deposit(double amount) {
		if(amount > 0.0d) {
			// add "amount" to balance
			this.balance = this.balance + amount;
			System.out.println("Success! New balance: " + this.balance);
		}
		else {
			System.out.println("Error. Invalid amount.");
		}
	}
	
	// Withdraw a chosen amount of money
	public void withdraw(double amount) {
		// input > 0
		if(amount > 0.0d) {
			if(amount <= this.balance) {
				// take "amount" from balance
				this.balance = this.balance - amount;
				System.out.println("Success! New balance: " + this.balance);
			}
			else {
				// prevent overdraft
				System.out.println("Error. Amount exceeds balance. Available balance: " + this.balance);
			}
		}
		else {
			System.out.println("Error. Invalid amount.");
		}
	}

	public double getBalance() {
		return balance;
	}
	
	
}
