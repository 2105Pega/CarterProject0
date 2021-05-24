package com.bank.users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import com.bank.util.ConnectionUtils;

public class UserDAOImpl implements UserDAO {

	@Override
	public HashMap<Integer, User> getAllUsers() {
		try (Connection conn = ConnectionUtils.getConnection()) {

			// Pull all users
			Statement stmnt1 = conn.createStatement();
			ResultSet rs = stmnt1.executeQuery("select * from users");
			// write them into hash map
			HashMap<Integer, User> users = new HashMap<Integer, User>();
			while (rs.next()) {
				User u = new User(
					rs.getInt("user_id"), 
					rs.getString("user_username"),
					rs.getString("user_password"),
					rs.getString("user_fname"),
					rs.getString("user_lname"),
					rs.getString("user_access"),
					getAccounts(rs.getInt("user_id"))
				);
				users.put(rs.getInt("user_id"), u);
			}

			return users;

		} catch (SQLException exp) {
			exp.printStackTrace();
		}
		return null;
	}

	@Override
	public User getUser(int userId) {
		try (Connection conn = ConnectionUtils.getConnection()){
			
			String sql = "select * from users where user_id = ?";
			PreparedStatement stmnt = conn.prepareStatement(sql);
			stmnt.setInt(1, userId);
			ResultSet r = stmnt.executeQuery();
			User u;
			if(r.next()) {
				u = new User(
					r.getInt("user_id"), 
					r.getString("user_username"),
					r.getString("user_password"),
					r.getString("user_fname"),
					r.getString("user_lname"),
					r.getString("user_access"),
					getAccounts(r.getInt("user_id"))
				);
			} else {
				u = null;
			}
			return u;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean addUser(User user) {
		try (Connection conn = ConnectionUtils.getConnection()){
			String sql = "insert into users (user_username, user_password, "
					+ "user_access, user_fname, user_lname) values (?,?,?,?,?)";
			PreparedStatement stmnt = conn.prepareStatement(sql);
			stmnt.setString(1, user.getUsername());
			stmnt.setString(2, user.getPassword());
			stmnt.setString(3, user.getAccess());
			stmnt.setString(4, user.getfName());
			stmnt.setString(5, user.getlName());
			stmnt.execute();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateUser(User user) {
		try (Connection conn = ConnectionUtils.getConnection()){
			String sql = "update users \r\n"
					+ "set user_username = ?,\r\n"
					+ "user_password = ?,\r\n"
					+ "user_access = ?,\r\n"
					+ "user_fname = ?,\r\n"
					+ "user_lname = ?\r\n"
					+ "where user_id = ?";
			PreparedStatement stmnt = conn.prepareStatement(sql);
			stmnt.setString(1, user.getUsername());
			stmnt.setString(2, user.getPassword());
			stmnt.setString(3, user.getAccess());
			stmnt.setString(4, user.getfName());
			stmnt.setString(5, user.getlName());
			stmnt.setInt(6, user.getUserId());
			stmnt.execute();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteUser(int userId) {
		try (Connection conn = ConnectionUtils.getConnection()){
			String sql = "delete from users_accounts where user_id = ?;\r\n"
					+ "delete from users where user_id = ?;";
			PreparedStatement stmnt = conn.prepareStatement(sql);
			stmnt.setInt(1, userId);
			stmnt.setInt(2, userId);
			stmnt.execute();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	// helper function to get all accounts owned by user with userId
	public ArrayList<Integer> getAccounts(int userId) {
		try(Connection conn = ConnectionUtils.getConnection()){
			String sql = "select account_number from users_accounts where user_id = ?";
			PreparedStatement stmnt = conn.prepareStatement(sql);
			stmnt.setInt(1, userId);
			ResultSet usersAccounts = stmnt.executeQuery();
			ArrayList<Integer> accNums = new ArrayList<Integer>();
			while(usersAccounts.next()) {
				accNums.add(usersAccounts.getInt("account_number"));
			}
			return accNums;
		} catch (SQLException exp) {
			exp.printStackTrace();
		}
		return null;
	}

}
