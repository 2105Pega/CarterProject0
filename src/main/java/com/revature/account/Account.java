package com.revature.account;

import java.io.Serializable;

public class Account implements Serializable{
	
	// Default serialVersionUID
	private static final long serialVersionUID = 1L;
	private double balance;
	private int accountNumber;
	private String[] accountOwner;
	private String accountType;
	
	public Account(int accountNumber, double balance, String accountType, String accountOwner) {
		this.balance = balance;
		this.accountNumber = accountNumber;
		this.accountType = accountType;
		this.accountOwner[0] = accountOwner;
	}

	public double getBalance() {
		return balance;
	}
	public void setBalance(double amt) {
		this.balance = amt;
	}

	public int getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String[] getAccountOwner() {
		return accountOwner;
	}
	public void setAccountOwner(String[] accountOwner) {
		this.accountOwner = accountOwner;
	}

	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	
	
	
}
