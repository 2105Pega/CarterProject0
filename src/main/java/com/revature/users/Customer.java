package com.revature.users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.revature.account.Account;
import com.revature.account.AccountDriver;

public class Customer {
	
	public Customer() {
		super();
	}
	
	public static Integer applyForAccount(String name, HashMap<Integer, Account> accounts) {
		Scanner scan = new Scanner(System.in);
		do {
			System.out.println("What is the initial balance you would like to deposit?");
			double initBalance = scan.nextDouble();
			scan.nextLine();
			if(initBalance>0.0d) {
				System.out.println("What type of account would you like to open?\n'Single' | 'Joint'");
				String accountType = scan.nextLine();
				if(accountType.toLowerCase().equals("single") || accountType.toLowerCase().equals("joint") ) {
					Integer newAccNum = AccountDriver.generateAccountNumber();
					while(accounts.containsKey(newAccNum)) {
						newAccNum = AccountDriver.generateAccountNumber();
					}
					Account newAcc = new Account(newAccNum, initBalance, accountType, name);
					if(accountType.equals("joint")) {
						System.out.println("Who else is opening this account with you? "
								+ "(Please enter their usernames with one space separating them)");
						String input = scan.nextLine();
						String[] jointUsersArr = input.split(" ");
						ArrayList<String> jointUsers = new ArrayList<String>();
						for (String singleUser : jointUsersArr) {
							jointUsers.add(singleUser);
						}
						jointUsers.add(name);
						newAcc.setAccountOwner(jointUsers);
						scan.close();
					}
					newAcc.setStatus("pending");
					accounts.put(newAccNum, newAcc);
					return newAccNum;
				} else {
					System.out.println("Invalid account type.");
					continue;
				}
			} else {
				System.out.println("Invalid balance.");
				continue;
			}
		} while(true);
	}	
	
}
