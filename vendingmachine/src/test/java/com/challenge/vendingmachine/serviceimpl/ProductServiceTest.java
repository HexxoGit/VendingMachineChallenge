package com.challenge.vendingmachine.serviceimpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.challenge.vendingmachine.DTO.ProductResponse;
import com.challenge.vendingmachine.domain.Product;
import com.challenge.vendingmachine.domain.User;
import com.challenge.vendingmachine.repository.ProductRepository;
import com.challenge.vendingmachine.repository.UserRepository;
import com.challenge.vendingmachine.serviceImpl.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

	@Mock
	private ProductRepository prodRepo;
	@Mock
	private UserRepository userRepo;
	
	@InjectMocks
	private ProductServiceImpl prodService;
	
	@Test
	void givenProductToAddReturnProductResponse() {
		
		final Product productToSave = new Product("RedBull", 10, 15);
		final User userToSave = new User("TestUser", "password", new int[0]);
		userToSave.setProducts(new ArrayList<Product>());
		
		when(prodRepo.save(productToSave)).thenReturn(productToSave);
		when(userRepo.findByUsername(userToSave.getUsername())).thenReturn(userToSave);

		ProductResponse response = prodService.saveProduct("TestUser", productToSave);
		
		assertThat(response.getName()).isEqualTo(productToSave.getName());
		assertThat(response.getAmount()).isEqualTo(productToSave.getAmountAvailable());
		assertThat(response.getCost()).isEqualTo(productToSave.getCost());
		assertThat(userToSave.getProducts().size()).isEqualTo(1);
		verify(prodRepo, times(1)).save(any(Product.class));
	}
	
	@Test
	void givenProductToAddWhenProductAlreadyExist() {
		
		final Product productToSave = new Product("RedBull", 10, 15);
		final Product productToSave2 = new Product("RedBull", 30, 15);
		final User userToSave = new User("TestUser", "password", new int[0]);
		userToSave.setProducts(new ArrayList<Product>());
		
		when(prodRepo.save(productToSave)).thenReturn(productToSave);
		when(userRepo.findByUsername(userToSave.getUsername())).thenReturn(userToSave);
		
		prodService.saveProduct("TestUser", productToSave);
		
		when(prodRepo.findByName(productToSave2.getName())).thenReturn(productToSave);
		
		ProductResponse response2 = prodService.saveProduct("TestUser", productToSave2);
		
		verify(prodRepo, times(1)).save(any(Product.class));
		assertThat(response2).isEqualTo(null);
	}

	@Test
	void givenProductNameReturnProductResponse() {
		
		final Product productToSave = new Product("RedBull", 10, 15);
		
		when(prodRepo.findByName("RedBull")).thenReturn(productToSave);
		
		ProductResponse response = prodService.getProduct(productToSave.getName());
		
		verify(prodRepo, times(1)).findByName(any(String.class));
		assertThat(response.getName()).isEqualTo(productToSave.getName());
		assertThat(response.getAmount()).isEqualTo(productToSave.getAmountAvailable());
		assertThat(response.getCost()).isEqualTo(productToSave.getCost());
	}
	
	@Test
	void givenAmountToChangeProductReturnProductResponse() {
		
		final Product productSaved = new Product("RedBull", 10, 15);
		final User userSaved = new User("TestUser", "password", new int[0]);
		userSaved.setProducts(new ArrayList<Product>());
		userSaved.getProducts().add(productSaved);
		
		when(prodRepo.findByName(productSaved.getName())).thenReturn(productSaved);
		when(prodRepo.save(productSaved)).thenReturn(productSaved);
		when(userRepo.findByUsername(userSaved.getUsername())).thenReturn(userSaved);
		
		ProductResponse responseChange = prodService.updateProductAmount(
				"TestUser", productSaved.getName(), 20);
		
		verify(prodRepo, times(1)).save(any(Product.class));
		assertThat(responseChange.getAmount()).isEqualTo(20);
	}
	
	@Test
	void givenCostToChangeProductReturnProductResponse() {
		
		final Product productSaved = new Product("RedBull", 10, 15);
		final User userSaved = new User("TestUser", "password", new int[0]);
		userSaved.setProducts(new ArrayList<Product>());
		userSaved.getProducts().add(productSaved);
		
		when(prodRepo.findByName(productSaved.getName())).thenReturn(productSaved);
		when(prodRepo.save(productSaved)).thenReturn(productSaved);
		when(userRepo.findByUsername(userSaved.getUsername())).thenReturn(userSaved);
		
		ProductResponse responseChange = prodService.updateProductCost(
				"TestUser", productSaved.getName(), 12);
		
		verify(prodRepo, times(1)).save(any(Product.class));
		assertThat(responseChange.getCost()).isEqualTo(12);
	}
	
	@Test
	void givenProductToDeleteShouldDelete() {
		
		final Product productSaved = new Product("RedBull", 10, 15);
		final User userSaved = new User("TestUser", "password", new int[0]);
		userSaved.setProducts(new ArrayList<Product>());
		userSaved.getProducts().add(productSaved);
		
		when(prodRepo.findByName(productSaved.getName())).thenReturn(productSaved);
		when(userRepo.findByUsername(userSaved.getUsername())).thenReturn(userSaved);
		
		prodService.deleteProduct(userSaved.getUsername(), productSaved.getName());
		
		verify(prodRepo, times(1)).delete(any(Product.class));
		assertThat(userSaved.getProducts().isEmpty());
	}
}
