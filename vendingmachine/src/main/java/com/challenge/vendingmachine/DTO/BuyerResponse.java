package com.challenge.vendingmachine.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BuyerResponse {

	private String productName;
	private int amountSpent;
	private int[] change;
}
