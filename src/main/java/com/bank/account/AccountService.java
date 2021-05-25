package com.bank.account;

import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;

import com.bank.customexceptions.NegativeAmountException;
import com.bank.customexceptions.OverdraftException;

public class AccountService{

	private AccountDAO accDao = new AccountDAOImpl();
	
	static Logger log;

	public AccountService() {
		if (log == null) {
			log = LogManager.getLogger(AccountService.class);
		}
	}
	
	//will read in all accounts from PostgreSQL using JDBC
	public HashMap<Integer,Account> getAllAccounts() {
		return accDao.getAllAccounts();
	}

	// will read in a single account from PostgreSQL using JDBC
	public Account getAccount(int accNum) {
		return accDao.getAccount(accNum);
	}
	
	// Transfer money between accounts
	public void transfer(Account donor, Account recipient, double amount) {
		Account a1 = donor;
		Account a2 = recipient;
		try {
			if (a1.getBalance() >= amount) {
				if(amount >= 0.0d) {
					a1.setBalance(a1.getBalance() - amount);
					a2.setBalance(a2.getBalance() + amount);
					boolean success = accDao.transfer(a1.getAccountNumber(), a2.getAccountNumber(), amount);
					if (success) {
						System.out.println("Success. Account " + a1.getAccountNumber() + " has transferred " + amount
								+ " to account " + a2.getAccountNumber() + ".");
						log.warn("Transferred " + amount + " from account " + a1.getAccountNumber() +" to account " 
							+ a2.getAccountNumber() + ".");
					} else {
						log.error("Connection or sql statement error.");
					}
				} else {
					throw new NegativeAmountException("Error. Invalid amount. (this is an exception)");
				}
			} else {
				throw new OverdraftException("Insufficient Funds.(this is an exception)");				
			}
		} catch (OverdraftException oe) {
			System.out.println(oe.toString());
			log.error("Error. Insifficient funds to transfer " + amount + " from account " 
					+ a1.getAccountNumber() + " to account " + a2.getAccountNumber() + ".");
		} catch (NegativeAmountException nae) {
			System.out.println(nae.toString());
			log.error("Error. Cannot withdraw transfer amount.");
		}
		
	}
	
	// Deposit and chosen amount of money
	public void deposit(Account a1, double amount) {
		try {
			if (amount > 0.0d) {
				// add "amount" to balance
				a1.setBalance(a1.getBalance()+amount);
				boolean success = accDao.deposit(a1.getAccountNumber(), amount);
				if (success) {
					System.out.println("Success! New balance: " + a1.getBalance());
					log.warn("Deposited " + amount + " to account " + a1.getAccountNumber() +". New balance: " 
							+ a1.getBalance() + ".");
				} else {
					log.error("Connection or sql statement error.");
				}
			} else {
				throw new NegativeAmountException("Error. Invalid amount. (this is an exception)");
			}
		} catch (NegativeAmountException nae) {
			System.out.println(nae.toString());
			log.error("Error. Cannot deposit negative amount.");
		}
	}

	// Withdraw a chosen amount of money
	public void withdraw(Account a1, double amount) {
		try {
			if(amount > 0.0d) {
				if(amount <= a1.getBalance()) {
					// take "amount" from balance
					a1.setBalance(a1.getBalance()-amount);
					boolean success = accDao.withdraw(a1.getAccountNumber(), amount);
					if (success) {
						System.out.println("Success! New balance: " + a1.getBalance());
						log.warn("Withdrew " + amount + " from account " + a1.getAccountNumber() +". New balance: " 
								+ a1.getBalance() + ".");
					} else {
						log.error("Connection or sql statement error.");
					}
				} else {
					// prevent overdraft
					throw new OverdraftException("Insufficient Funds.");						
				}
			} else {
				throw new NegativeAmountException("Input is a less than 0.");
			}
		} catch (OverdraftException oe) {
			System.out.println(oe.toString());
			log.error("Error. Cannot withdraw amount " + amount + ". Available balance: " + a1.getBalance() + ".");
		} catch (NegativeAmountException nae) {
			System.out.println(nae.toString());
			log.error("Error. Cannot withdraw negative amount.");
		}
	}
	
	public ArrayList<Account> getMyAccounts(int userId){
		return accDao.getMyAccounts(userId);
	}
	
	public boolean addAccount(Account acc) {
		return accDao.addAccount(acc);
	}
	
	public boolean deleteAccount(int accNum) {
		return accDao.deleteAccount(accNum);
	}

	public void reviewApplication() {
		Scanner localScan = new Scanner(System.in);
		System.out.println("Select an account to review by the account number:");
		boolean success = accDao.getApplications();
		if(success) {
			int accNum = localScan.nextInt();
			localScan.nextLine();
			System.out.println("'Approve' or 'Deny' account " + accNum + "?");
			String input = localScan.nextLine();
			String status = "";
			if(input.toLowerCase().startsWith("app")) 
				status = "approved";
			else if (input.toLowerCase().startsWith("den")) 
				status = "denied";
			else 
				System.out.println("Invalid input. Exiting...");
			boolean appOrDen = accDao.approveOrDeny(accNum, status);
			if(appOrDen)
				System.out.println("Success! Account " + accNum + " has been " + status + ".");
			else
				log.error("Connection or sql statement error. Exiting...");
		} else {
			log.error("Connection or sql statement error. Exiting...");
		}
		localScan.close();
	}
	
}


