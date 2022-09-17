package com.challenge.vendingmachine.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.vendingmachine.DTO.ProductRequest;
import com.challenge.vendingmachine.DTO.ProductResponse;
import com.challenge.vendingmachine.domain.Product;
import com.challenge.vendingmachine.domain.User;
import com.challenge.vendingmachine.filter.CustomAuthorizationFilter;
import com.challenge.vendingmachine.mapper.ProductMapper;
import com.challenge.vendingmachine.repository.ProductRepository;
import com.challenge.vendingmachine.repository.UserRepository;
import com.challenge.vendingmachine.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ProductServiceImpl implements ProductService{

	private ProductRepository prodRepo;
	private UserRepository userRepo;
	
	public ProductServiceImpl(ProductRepository prodRepo, UserRepository userRepo) {
		super();
		this.prodRepo = prodRepo;
		this.userRepo = userRepo;
	}
	
	@Override
	public ProductResponse saveProduct(ProductRequest product) {
		User user = userRepo.findByUsername(product.getUsername());
		
		if(prodRepo.findByName(product.getProductName()) == null &&
				user != null) {
			Product prod = prodRepo.save(ProductMapper.toEntity(product));
			user.getProducts().add(prod);
			return ProductMapper.toResponse(prod);
		}
		return null;
	}

	@Override
	public ProductResponse getProduct(String productName) {
		log.info(productName);
		Product prod = prodRepo.findByName(productName);
		log.info("Product da db {}", prod);
		if(prod != null)
			return ProductMapper.toResponse(prod);
		return null;
	}

	@Override
	public void deleteProduct(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Product> getAllProducts() {
		return prodRepo.findAll();
	}

	
}
