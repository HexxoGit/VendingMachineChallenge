package com.challenge.vendingmachine.mapper;

import com.challenge.vendingmachine.DTO.ProductRequest;
import com.challenge.vendingmachine.DTO.ProductResponse;
import com.challenge.vendingmachine.domain.Product;

public class ProductMapper {

	public static Product toEntity(ProductRequest request) {
		Product entity = new Product();
		entity.setName(request.getProductName());
		entity.setAmountAvailable(request.getAmount());
		entity.setCost(request.getCost());
		return entity;
	}
	
	public static ProductResponse toResponse(Product prod) {
		ProductResponse response = new ProductResponse();
		response.setName(prod.getName());
		response.setAmount(prod.getAmountAvailable());
		response.setCost(prod.getCost());
		return response;
	}
}
