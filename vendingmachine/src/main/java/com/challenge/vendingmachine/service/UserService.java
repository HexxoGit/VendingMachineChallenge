package com.challenge.vendingmachine.service;

import java.util.List;

import com.challenge.vendingmachine.DTO.UserResponse;
import com.challenge.vendingmachine.domain.User;

public interface UserService {

	List<User> getUsers();
	
	UserResponse saveUser(User user);
	
	void addRoleToUser(String username, String roleName);
	void addAmountToUser(String username, int coin);
	void resetUserAmount(String username);
}
