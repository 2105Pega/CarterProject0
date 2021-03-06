package com.revature.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import java.util.ArrayList;

import com.revature.account.Account;
import com.revature.account.AccountDriver;

public class AccountTests {
	
	static Account a1;
	static Account a2;
	static AccountDriver ad;
	static ArrayList<String> users = new ArrayList<String>();
	
	@BeforeAll
	public static void makeAccountDriver() {
		users.add("user1");
		a1 = new Account(1, 100.00d, "joint", "user1");
		a2 = new Account(2, 500.00d, "single", "user2");
		ad = new AccountDriver(a1);
	}
	
	@BeforeEach
	public void resetAccount() {
		a1.setBalance(100.0d);
		a2.setBalance(500.0d);
	}

	@Test
	public void testDeposit() {
		System.out.println("testDeposit()");
		ad.deposit(100.00d);
		assertEquals(a1.getBalance(), 200.00d);
		ad.deposit(-0.25);
		assertEquals(a1.getBalance(), 200.00d);
		System.out.println();
	}
	
	@Test
	public void testWithdraw() {
		System.out.println("testWithdraw()");
		ad.withdraw(50.00d);
		assertEquals(a1.getBalance(), 50.00d);
		ad.withdraw(-0.25);
		assertEquals(a1.getBalance(), 50.00d);
		ad.withdraw(150.00d);
		assertEquals(a1.getBalance(), 50.00d);
		System.out.println();
	}
	
	@Test
	public void testTransfer() {
		System.out.println("testTransfer()");
		ad.transfer(a2, 50.0d);
		assertEquals(50.0d, a1.getBalance());
		assertEquals(550.0d, a2.getBalance());
		ad.transfer(a2, -50.0d);
		assertEquals(50.0d, a1.getBalance());
		assertEquals(550.0d, a2.getBalance());
		System.out.println();
	}
	
	@Test
	public void testTransferCustomException() {
		System.out.println("testTransferCustomException()");
		ad.transferCustomException(a2, 50.0d);
		assertEquals(50.0d, a1.getBalance());
		assertEquals(550.0d, a2.getBalance());
		ad.transferCustomException(a2, -50.0d);
		assertEquals(50.0d, a1.getBalance());
		assertEquals(550.0d, a2.getBalance());
		System.out.println();
	}
	
	@Test
	public void testDepositCustomException() {
		System.out.println("testDepositCustomException()");
		ad.deposit(100.00d);
		assertEquals(a1.getBalance(), 200.00d);
		ad.deposit(-0.25);
		assertEquals(a1.getBalance(), 200.00d);
		System.out.println();
	}
	
	@Test
	public void testWithdrawCustomException() {
		System.out.println("testWithdrawCustomException()");
		ad.withdraw(50.00d);
		assertEquals(a1.getBalance(), 50.00d);
		ad.withdraw(-0.25);
		assertEquals(a1.getBalance(), 50.00d);
		ad.withdraw(150.00d);
		assertEquals(a1.getBalance(), 50.00d);
		System.out.println();
	}
	
}
