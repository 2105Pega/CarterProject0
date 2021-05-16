package com.revature.account;

import java.io.Serializable;
import java.util.ArrayList;

public class Account implements Serializable{
	
	// Default serialVersionUID
	private static final long serialVersionUID = 1L;
	private double balance;
	private int accountNumber;
	private ArrayList<String> accountOwner = new ArrayList<String>();
	private String accountType;
	
	public Account(int _accountNumber, double _balance, String _accountType, String _accountOwner) {
		this.balance = _balance;
		this.accountNumber = _accountNumber;
		this.accountType = _accountType;
		this.accountOwner.add(_accountOwner);
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

	public ArrayList<String> getAccountOwner() {
		return accountOwner;
	}
	public void setAccountOwner(ArrayList<String> accountOwner) {
		this.accountOwner = accountOwner;
	}

	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	
	
	
}
