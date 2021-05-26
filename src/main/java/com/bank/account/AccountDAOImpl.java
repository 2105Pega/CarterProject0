package com.bank.account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import com.bank.util.ConnectionUtils;

public class AccountDAOImpl implements AccountDAO {

	@Override
	public HashMap<Integer, Account> getAllAccounts() {
		try (Connection conn = ConnectionUtils.getConnection()) {

			// Pull all accounts
			Statement stmnt1 = conn.createStatement();
			ResultSet rs = stmnt1.executeQuery("select * from accounts");
			// write them into hash map
			HashMap<Integer, Account> accs = new HashMap<Integer, Account>();
			while (rs.next()) {
				Account a = new Account(rs.getInt("account_number"), rs.getDouble("account_balance"),
						rs.getString("account_type"), getOwners(rs.getInt("account_number")),
						rs.getString("account_status"));
				// can't get owners yet
				accs.put(rs.getInt("account_number"), a);
			}

			return accs;

		} catch (SQLException exp) {
			exp.printStackTrace();
		}
		return null;
	}

	@Override
	public Account getAccount(int accNum) {
		try (Connection conn = ConnectionUtils.getConnection()) {

			String sql = "select * from accounts where account_number = ?";
			PreparedStatement stmnt = conn.prepareStatement(sql);
			stmnt.setInt(1, accNum);
			ResultSet rs = stmnt.executeQuery();
			Account a;
			if (rs.next()) {
				a = new Account(rs.getInt("account_number"), rs.getDouble("account_balance"),
						rs.getString("account_type"), getOwners(rs.getInt("account_number")),
						rs.getString("account_status"));
			} else {
				a = null;
			}
			return a;
		} catch (SQLException exp) {
			exp.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean transfer(int givingAccNum, int receivingAccNum, double amount) {
		try (Connection conn = ConnectionUtils.getConnection()) {
			// Withdraw
			String sql1 = "update accounts set account_balance = account_balance - ? where account_number = ?";
			PreparedStatement stmnt1 = conn.prepareStatement(sql1);
			stmnt1.setDouble(1, amount);
			stmnt1.setInt(2, givingAccNum);
			// deposit
			String sql2 = "update accounts set account_balance = account_balance + ? where account_number = ?";
			PreparedStatement stmnt2 = conn.prepareStatement(sql2);
			stmnt2.setDouble(1, amount);
			stmnt2.setInt(2, receivingAccNum);

			stmnt1.execute();
			stmnt2.execute();

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deposit(int accNum, double amount) {
		try (Connection conn = ConnectionUtils.getConnection()) {
			String sql = "update accounts set account_balance = account_balance + ? where account_number = ?";
			PreparedStatement stmnt = conn.prepareStatement(sql);
			stmnt.setDouble(1, amount);
			stmnt.setInt(2, accNum);

			stmnt.execute();

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean withdraw(int accNum, double amount) {
		try (Connection conn = ConnectionUtils.getConnection()) {
			String sql = "update accounts set account_balance = account_balance - ? where account_number = ?";
			PreparedStatement stmnt = conn.prepareStatement(sql);
			stmnt.setDouble(1, amount);
			stmnt.setInt(2, accNum);

			stmnt.execute();

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public ArrayList<Account> getMyAccounts(int userId) {
		try (Connection conn = ConnectionUtils.getConnection()) {

			String sql = "select * from accounts a \r\n"
					+ "inner join users_accounts ua on a.account_number = ua.account_number \r\n"
					+ "where ua.user_id = ?";
			PreparedStatement stmnt1 = conn.prepareStatement(sql);
			stmnt1.setInt(1, userId);
			ResultSet rs = stmnt1.executeQuery();
			// write them into hash map
			ArrayList<Account> accs = new ArrayList<Account>();
			while (rs.next()) {
				Account a = new Account(rs.getInt("account_number"), 
					rs.getDouble("account_balance"),
					rs.getString("account_type"), 
					getOwners(rs.getInt("account_number")),
					rs.getString("account_status")
				);
				accs.add(a);
			}

			return accs;

		} catch (SQLException exp) {
			exp.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean addAccount(Account acc) {
		try (Connection conn = ConnectionUtils.getConnection()){
			
			// add account to database
			String sql1 = "insert into accounts (account_balance, account_type, account_status) values(?,?,?)";
			PreparedStatement stmnt1 = conn.prepareStatement(sql1);
			stmnt1.setDouble(1, acc.getBalance());
			stmnt1.setString(2, acc.getAccountType());
			stmnt1.setString(3, "new"); // set status to new to identify the account we just made
			stmnt1.execute();
			
			// get the new account number
			Statement stmnt2 = conn.createStatement();
			ResultSet rs = stmnt2.executeQuery("select account_number from accounts where account_status = 'new'");
			rs.next();
			int newAccNum = rs.getInt("account_number");
			acc.setAccountNumber(newAccNum);
			
			// update the status to something we'd expect
			PreparedStatement stmnt3 = conn.prepareStatement("update accounts set account_status = ? where account_status = 'new'");
			stmnt3.setString(1, acc.getStatus());
			stmnt3.execute();
			
			// Add account and owners to the joint table if the account has owners
			String sql4 = "insert into users_accounts (user_id,account_number) values (?,?)";
			PreparedStatement stmnt4 = conn.prepareStatement(sql4);
			stmnt4.setInt(2, acc.getAccountNumber());
			for(int x : acc.getAccountOwner()) {
				stmnt4.setInt(1, x);
				stmnt4.execute();
			}
			return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteAccount(int accNum) {
		try (Connection conn = ConnectionUtils.getConnection()){
			String sql = "delete from users_accounts where account_number = ?;\r\n"
					+ "delete from accounts where account_number = ?;";
			PreparedStatement stmnt = conn.prepareStatement(sql);
			stmnt.setInt(1, accNum);
			stmnt.setInt(2, accNum);
			stmnt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean getApplications() {
		try (Connection conn = ConnectionUtils.getConnection()){
			String sql = "select * from accounts where account_status = 'pending'";
			Statement stmnt = conn.createStatement();
			ResultSet rs = stmnt.executeQuery(sql);
			System.out.println("Account Number | Balance | Account Type");
			while (rs.next()) {
				System.out.println(rs.getInt("account_number") + " | " + rs.getDouble("account_balance") 
					+ " | " + rs.getString("account_type"));
			}
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean approveOrDeny(int accNum, String status) {
		try (Connection conn = ConnectionUtils.getConnection()){
			String sql = "update accounts set account_status = ? where account_number = ?";
			PreparedStatement stmnt = conn.prepareStatement(sql);
			stmnt.setString(1, status);
			stmnt.setInt(2, accNum);

			stmnt.execute();
			
			return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	// Helper function to access the joint table holding the information on who owns
	// which account and return those owners
 	public ArrayList<Integer> getOwners(int accNum) {
		try (Connection conn = ConnectionUtils.getConnection()) {
			String sql = "select user_id from users_accounts where account_number = ?";
			PreparedStatement stmnt = conn.prepareStatement(sql);
			stmnt.setInt(1, accNum);
			ResultSet usersAccounts = stmnt.executeQuery();
			ArrayList<Integer> ownerIds = new ArrayList<Integer>();
			// update accounts hash map with account owners
			while (usersAccounts.next()) {
				ownerIds.add(usersAccounts.getInt("user_id"));
			}
			return ownerIds;
		} catch (SQLException exp) {
			exp.printStackTrace();
		}
		return null;
	}

	
}
