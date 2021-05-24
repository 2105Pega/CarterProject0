package com.bank.account;

import java.util.HashMap;

public interface AccountDAO {
	public HashMap<Integer,Account> getAllAccounts(); // eventually will read in all accounts from PostgreSQL using JDBC
	public Account getAccount(int accNum); // will read in a single account from PostgreSQL using JDBC
	public boolean transfer(int givingAccNum, int receivingAccNum, double amount);
	public boolean deposit(int accNum, double amount);
	public boolean withdraw(int accNum, double amount);
	public HashMap<String,Account> getMyAccounts(int userId);
	public boolean addAccount(Account acc);
	public boolean deleteAccount(int accNum);
	public boolean getApplications();
	public boolean approveOrDeny(int accNum, String status);
}
