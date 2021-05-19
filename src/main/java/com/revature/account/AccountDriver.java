package com.revature.account;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.logging.log4j.Logger;

import com.revature.customexceptions.NegativeAmountException;
import com.revature.customexceptions.OverdraftException;

import org.apache.logging.log4j.LogManager;


public class AccountDriver {

	private Account a1;
	private Account a2;
	static Logger log;

	public AccountDriver(Account account) {
		this.a1 = account;
		if (log == null) {
			log = LogManager.getLogger(AccountDriver.class);
		}
	}

	// Transfer money between accounts
	public void transfer(Account recipient, double amount) {
		Account a2 = recipient;
		if (a1.getBalance() >= amount) {
			if(amount >= 0.0d) {
				a1.setBalance(a1.getBalance() - amount);
				a2.setBalance(a2.getBalance() + amount);
				System.out.println("Success. Account " + a1.getAccountNumber() + " has transferred " + amount
						+ " to account " + a2.getAccountNumber() + ".");
				log.warn("Transferred " + amount + " from account " + a1.getAccountNumber() +" to account " 
					+ a2.getAccountNumber() + ".");
			} else {
				System.out.println("Error. Invalid amount.");
				log.error("Error. Cannot withdraw transfer amount.");
			}
		} else {
			System.out.println("Account " + a1.getAccountNumber() + " has insufficient funds.");
			log.error("Error. Insifficient funds to transfer " + amount + " from account " 
				+ a1.getAccountNumber() + " to account " + a2.getAccountNumber() + ".");
		}
	}

	// Deposit and chosen amount of money
	public void deposit(double amount) {
		if (amount > 0.0d) {
			// add "amount" to balance
			a1.setBalance(a1.getBalance()+amount);
			System.out.println("Success! New balance: " + a1.getBalance());
			log.warn("Deposited " + amount + " to account " + a1.getAccountNumber() +". New balance: " 
					+ a1.getBalance() + ".");
		} else {
			System.out.println("Error. Invalid amount.");
			log.error("Error. Cannot deposit negative amount.");
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
				log.warn("Withdrew " + amount + " from account " + a1.getAccountNumber() +". New balance: " 
						+ a1.getBalance() + ".");
			}
			else {
				// prevent overdraft
				System.out.println("Error. Amount exceeds balance. Available balance: " + a1.getBalance());
				log.error("Error. Cannot withdraw amount " + amount + ". Available balance: " + a1.getBalance() + ".");
			}
		}
		else {
			System.out.println("Error. Invalid amount.");
			log.error("Error. Cannot withdraw negative amount.");
		}
	}
		
	// Add and owner to an account
	public void addOwner(String newOwner) {
		ArrayList<String> owners = new ArrayList<String>();
		owners = a1.getAccountOwner();
		owners.add(newOwner);
		a1.setAccountOwner(owners);
	}
	
	public static Integer generateAccountNumber() {
		ArrayList<Integer> accNums = new ArrayList<Integer>();
		for (int i = 1; i < 999; i++) {
			  accNums.add(i);
		}
		Collections.shuffle(accNums);
		return accNums.get(0);
	}
	
	
	public void transferCustomException(Account recipient, double amount) {
		a2 = recipient;
		try {
			if (a1.getBalance() >= amount) {
				if(amount >= 0.0d) {
					a1.setBalance(a1.getBalance() - amount);
					a2.setBalance(a2.getBalance() + amount);
					System.out.println("Success. Account " + a1.getAccountNumber() + " has transferred " + amount
							+ " to account " + a2.getAccountNumber() + ".");
					log.warn("Transferred " + amount + " from account " + a1.getAccountNumber() +" to account " 
						+ a2.getAccountNumber() + ".");
				} else {
					throw new NegativeAmountException("Error. Invalid amount. (this is an exception)");
				}
			} else {
				throw new OverdraftException("Insufficient Funds.(this is an exception)");				
			}
		} catch (OverdraftException oe) {
			oe.toString();
			log.error("Error. Insifficient funds to transfer " + amount + " from account " 
					+ a1.getAccountNumber() + " to account " + a2.getAccountNumber() + ".");
		} catch (NegativeAmountException nae) {
			nae.toString();
			log.error("Error. Cannot withdraw transfer amount.");
		}
		
	}
	
	// Deposit and chosen amount of money
		public void depositCustomException(double amount) {
			try {
				if (amount > 0.0d) {
					// add "amount" to balance
					a1.setBalance(a1.getBalance()+amount);
					System.out.println("Success! New balance: " + a1.getBalance());
					log.warn("Deposited " + amount + " to account " + a1.getAccountNumber() +". New balance: " 
							+ a1.getBalance() + ".");
				} else {
					throw new NegativeAmountException("Error. Invalid amount. (this is an exception)");
				}
			} catch (NegativeAmountException nae) {
				nae.toString();
				log.error("Error. Cannot deposit negative amount.");
			}
		}

		// Withdraw a chosen amount of money
		public void withdrawCustomException(double amount) {
			try {
				if(amount > 0.0d) {
					if(amount <= a1.getBalance()) {
						// take "amount" from balance
						a1.setBalance(a1.getBalance()-amount);
						System.out.println("Success! New balance: " + a1.getBalance());
						log.warn("Withdrew " + amount + " from account " + a1.getAccountNumber() +". New balance: " 
								+ a1.getBalance() + ".");
					} else {
						// prevent overdraft
						throw new OverdraftException("Insufficient Funds.(this is an exception)");						
					}
				} else {
					throw new NegativeAmountException("Input is a less than 0.");
				}
			} catch (OverdraftException oe) {
				oe.toString();
				log.error("Error. Cannot withdraw amount " + amount + ". Available balance: " + a1.getBalance() + ".");
			} catch (NegativeAmountException nae) {
				nae.toString();
				log.error("Error. Cannot withdraw negative amount.");
			}
		}
}
