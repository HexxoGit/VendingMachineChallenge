package com.challenge.vendingmachine.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductRequest {

	private String productName;
	private int amount;
	private int cost;
}
