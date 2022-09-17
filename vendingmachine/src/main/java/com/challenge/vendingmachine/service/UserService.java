package com.challenge.vendingmachine.service;

import java.util.List;

import com.challenge.vendingmachine.DTO.UserRequest;
import com.challenge.vendingmachine.DTO.UserResponse;
import com.challenge.vendingmachine.domain.User;

public interface UserService {

	List<User> getUsers();
	
	UserResponse saveUser(UserRequest user);
	
	void addRoleToUser(String username, String roleName);
	void addCoinToUser(String username, int coin);
	void resetUserAmount(String username);
}
