package com.challenge.vendingmachine.DTO;

import lombok.Data;

@Data
public class UserDepositRequest {

	private String username;
	private int amount;
}
