package com.bank.users;

import java.util.HashMap;

public class UserService {
	private UserDAO uDao = new UserDAOImpl();
	
	public HashMap<Integer, User> getAllUsers(){
		return uDao.getAllUsers();
	}
	
	public User getUser(int userId) {
		return uDao.getUser(userId);
	}
	
	public boolean addUser(User user) {
		return uDao.addUser(user);
	}
	
	public boolean updateUser(User user) {
		return uDao.updateUser(user);
	}
	
	public boolean deleteUser(int userId) {
		return uDao.deleteUser(userId);
	}
}
