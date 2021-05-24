package com.bank.account;

import java.io.Serializable;
import java.util.ArrayList;

public class Account implements Serializable{
	
	// Default serialVersionUID
	private static final long serialVersionUID = 1L;
	private double balance;
	private int accountNumber;
	private ArrayList<Integer> accountOwnerIds = new ArrayList<Integer>();
	private String accountType;
	private String status;
	
	
	public Account() {
		super();
	}
	public Account(int _accountNumber, double _balance, String _accountType, int _accountOwner, String _status) {
		this.balance = _balance;
		this.accountNumber = _accountNumber;
		this.accountType = _accountType;
		this.accountOwnerIds.add(_accountOwner);
		this.status = _status;
	}
	public Account(int _accountNumber, double _balance, String _accountType, ArrayList<Integer> _accountOwners, String _status) {
		this.balance = _balance;
		this.accountNumber = _accountNumber;
		this.accountType = _accountType;
		this.accountOwnerIds = _accountOwners;
		this.status = _status;
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

	public ArrayList<Integer> getAccountOwner() {
		return accountOwnerIds;
	}
	public void setAccountOwner(ArrayList<Integer> accountOwner) {
		this.accountOwnerIds = accountOwner;
	}
	public void addAccountOwner(int newAccountOwner) {
		this.accountOwnerIds.add(newAccountOwner);
	}

	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "Account [balance=" + balance + ", accountNumber=" + accountNumber + ", accountOwnerIds="
				+ accountOwnerIds + ", accountType=" + accountType + ", status=" + status + "]";
	}
	
}
