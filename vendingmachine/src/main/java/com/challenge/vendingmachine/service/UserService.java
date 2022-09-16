package com.challenge.vendingmachine.service;

import java.util.List;

import com.challenge.vendingmachine.domain.User;

public interface UserService {

	List<User> getUsers();
	
	User saveUser(User user);
	
	void addRoleToUser(String username, String roleName);
	void addAmountToUser(String username, int amount);
	void resetUserAmount(String username);
}
