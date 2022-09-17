package com.challenge.vendingmachine.service;

import java.util.List;

import com.challenge.vendingmachine.DTO.ProductRequest;
import com.challenge.vendingmachine.DTO.ProductResponse;
import com.challenge.vendingmachine.domain.Product;

public interface ProductService {

	ProductResponse saveProduct(ProductRequest product);
	ProductResponse getProduct(String productName);
	List<Product> getAllProducts();
	void deleteProduct(String name);
}
