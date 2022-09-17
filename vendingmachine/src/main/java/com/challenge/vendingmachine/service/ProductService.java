package com.challenge.vendingmachine.service;

import java.util.List;

import com.challenge.vendingmachine.DTO.BuyerResponse;
import com.challenge.vendingmachine.DTO.ProductResponse;
import com.challenge.vendingmachine.domain.Product;

public interface ProductService {

	ProductResponse saveProduct(String username, Product product);
	ProductResponse getProduct(String productName);
	List<Product> getAllProducts();
	
	ProductResponse updateProductAmount(String username, String productName, int amount);
	ProductResponse updateProductCost(String username, String productName, int coin);
	void deleteProduct(String username, String productName);
	
	BuyerResponse buyProduct(String username, int productId, int amount);
}
