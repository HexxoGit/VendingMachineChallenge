package com.challenge.vendingmachine.mapper;

import com.challenge.vendingmachine.DTO.UserRequest;
import com.challenge.vendingmachine.DTO.UserResponse;
import com.challenge.vendingmachine.domain.User;

public class UserMapper {

	public static UserResponse toResponse(User user) {
		UserResponse response = new UserResponse();
		response.setUsername(user.getUsername());
		/*response.setAmount(user.getDeposit());
		if(user.getRoles() != null)
			response.setRoles(user.getRoles());*/
		return response;
	}
	
	public static User toEntity(UserRequest request) {
		User user = new User();
		user.setUsername(request.getUsername());
		user.setPassword(request.getPassword());
		return user;
	}
}
