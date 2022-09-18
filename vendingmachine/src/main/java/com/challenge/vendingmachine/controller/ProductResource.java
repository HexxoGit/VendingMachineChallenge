package com.challenge.vendingmachine.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.challenge.vendingmachine.DTO.BuyerResponse;
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
	public ResponseEntity<ProductResponse> saveProduct(Authentication authentication, @RequestBody ProductRequest request) {
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
						.path("/api/product/add").toUriString());
		ProductResponse response = prodService.saveProduct(authentication.getName(), request);
		if(response != null)
			return ResponseEntity.created(uri).body(response);
		else
			return ResponseEntity.badRequest().build();
		
	}
	
	@DeleteMapping("/product/delete")
	public ResponseEntity<?> deleteProduct(Authentication authentication, @RequestParam String productName) {
		prodService.deleteProduct(authentication.getName(), productName);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/product/update/amount")
	public ResponseEntity<ProductResponse> updateProductAmount(Authentication authentication, 
			@RequestParam String productName, @RequestParam int amount) {
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/api/product/update/amount").toUriString());
		ProductResponse response = prodService.updateProductAmount(authentication.getName(),
				productName, amount);
		if(response != null)
			return ResponseEntity.created(uri).body(response);
		else
			return ResponseEntity.badRequest().build();
	}
	
	@PutMapping("/product/update/cost")
	public ResponseEntity<ProductResponse> updateProductCost(Authentication authentication, 
			@RequestParam String productName, @RequestParam int cost) {
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/api/product/update/cost").toUriString());
		ProductResponse response = prodService.updateProductCost(authentication.getName(),
				productName, cost);
		if(response != null)
			return ResponseEntity.created(uri).body(response);
		else
			return ResponseEntity.badRequest().build();
	}
	
	@PostMapping("/product/buy")
	public ResponseEntity<BuyerResponse> buyProduct(Authentication authentication, 
				@RequestParam int productId, @RequestParam int amount) {
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/api/product/buy").toUriString());
		BuyerResponse response = prodService.buyProduct(authentication.getName(), productId, amount);
		if(response != null)
			return ResponseEntity.created(uri).body(response);
		else
			return ResponseEntity.badRequest().build();
	}
}
