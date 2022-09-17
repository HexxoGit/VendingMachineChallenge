package com.challenge.vendingmachine.mapper;

import com.challenge.vendingmachine.DTO.UserResponse;
import com.challenge.vendingmachine.domain.User;

public class UserMapper {

	public static UserResponse toResponse(User user) {
		UserResponse response = new UserResponse();
		response.setUsername(user.getUsername());
		response.setCoinAmount(user.getDeposit());
		if(user.getRoles() != null)
			response.setRoles(user.getRoles());
		return response;
	}
}
