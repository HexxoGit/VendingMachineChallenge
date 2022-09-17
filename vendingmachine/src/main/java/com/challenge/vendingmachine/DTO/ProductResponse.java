package com.challenge.vendingmachine.DTO;

import lombok.Data;

@Data
public class ProductResponse {

	private String name;
	private int amount;
	private int cost;
}
