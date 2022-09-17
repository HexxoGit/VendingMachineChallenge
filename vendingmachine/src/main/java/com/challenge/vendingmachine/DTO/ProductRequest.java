package com.challenge.vendingmachine.DTO;

import lombok.Data;

@Data
public class ProductRequest {

	private String productName;
	private int amount;
	private int cost;
	private String username;
}
