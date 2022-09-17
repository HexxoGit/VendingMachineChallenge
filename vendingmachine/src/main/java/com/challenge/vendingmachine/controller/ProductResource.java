package com.challenge.vendingmachine.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.challenge.vendingmachine.DTO.ProductRequest;
import com.challenge.vendingmachine.DTO.ProductResponse;
import com.challenge.vendingmachine.domain.Product;
import com.challenge.vendingmachine.service.ProductService;

@RestController
@RequestMapping("/api")
public class ProductResource {

	ProductService prodService;

	public ProductResource(ProductService prodService) {
		super();
		this.prodService = prodService;
	}
	
	@GetMapping("/products")
	public ResponseEntity<List<Product>> getAllProducts() {
		return ResponseEntity.ok().body(prodService.getAllProducts());
	}
	
	@GetMapping("/product")
	public ResponseEntity<ProductResponse> getProduct(@RequestParam String productName) {
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/api/product").toUriString());
		ProductResponse response = prodService.getProduct(productName);
		if(response != null)
			return ResponseEntity.created(uri).body(response);
		else
			return ResponseEntity.badRequest().build();
	}
	
	@PostMapping("/product/add")
	public ResponseEntity<ProductResponse> saveProduct(@RequestBody ProductRequest request) {
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
						.path("/api/product/add").toUriString());
		ProductResponse response = prodService.saveProduct(request);
		if(response != null)
			return ResponseEntity.created(uri).body(response);
		else
			return ResponseEntity.badRequest().build();
		
	}
}
