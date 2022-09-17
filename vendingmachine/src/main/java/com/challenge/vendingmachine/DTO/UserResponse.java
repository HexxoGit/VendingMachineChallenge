package com.challenge.vendingmachine.DTO;

import java.util.Collection;

import com.challenge.vendingmachine.domain.Role;

import lombok.Data;

@Data
public class UserResponse {

	private String username;
	private int coinAmount;
	private Collection<Role> roles;
}
