package com.bank.users;

import java.util.HashMap;

public interface UserDAO {
	public HashMap<Integer, User> getAllUsers();
	public User getUser(int userId);
	public boolean addUser(User user);
	public boolean updateUser(User user);
	public boolean deleteUser(int userId);
}
