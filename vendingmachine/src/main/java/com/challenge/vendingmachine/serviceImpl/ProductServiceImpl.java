package com.challenge.vendingmachine.serviceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.vendingmachine.DTO.BuyerResponse;
import com.challenge.vendingmachine.DTO.ProductResponse;
import com.challenge.vendingmachine.domain.Product;
import com.challenge.vendingmachine.domain.User;
import com.challenge.vendingmachine.mapper.ProductMapper;
import com.challenge.vendingmachine.repository.ProductRepository;
import com.challenge.vendingmachine.repository.UserRepository;
import com.challenge.vendingmachine.service.ProductService;
import com.challenge.vendingmachine.utils.Utils;

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
	public ProductResponse saveProduct(String username, Product product) {
		User user = userRepo.findByUsername(username);
		
		if(prodRepo.findByName(product.getName()) == null) {
			Product prod = prodRepo.save(product);
			user.getProducts().add(prod);
			return ProductMapper.toResponse(prod);
		}
		return null;
	}

	@Override
	public ProductResponse getProduct(String productName) {
		Product prod = prodRepo.findByName(productName);
		
		if(prod != null)
			return ProductMapper.toResponse(prod);
		return null;
	}

	@Override
	public List<Product> getAllProducts() {
		return prodRepo.findAll();
	}

	@Override
	public void deleteProduct(String username, String productName) {
		Product prod = prodRepo.findByName(productName);
		
		if(prod != null) {
			if(userRepo.findByUsername(username).getProducts().remove(prod))
				prodRepo.delete(prod);
		}
	}

	@Override
	public ProductResponse updateProductAmount(String username, String productName, int amount) {
		Product prod = prodRepo.findByName(productName);
		
		if(prod != null && userRepo.findByUsername(username).getProducts().contains(prod)) {
			prod.setAmountAvailable(amount);
			prodRepo.save(prod);
			return ProductMapper.toResponse(prod);
		}
		return null;
	}

	@Override
	public ProductResponse updateProductCost(String username, String productName, int cost) {
		Product prod = prodRepo.findByName(productName);
		
		if(prod != null && userRepo.findByUsername(username).getProducts().contains(prod)) {
			prod.setCost(cost);;
			prodRepo.save(prod);
			return ProductMapper.toResponse(prod);
		}
		return null;
	}

	@Override
	public BuyerResponse buyProduct(String username, int productId, int amount) {
		Optional<Product> prod = prodRepo.findById((long)productId);
		User user = userRepo.findByUsername(username);
		
		//product exists and amount is equal or greater then pretend by user
		if(prod != null) {
			int amountAvailable = prod.get().getAmountAvailable();
			if(amountAvailable >= amount) {
				int userTotal = Arrays.stream(user.getDeposit()).sum();
				int productTotal = prod.get().getCost() * amount;
				int checkTotal = userTotal-productTotal;
				log.info("userTotal: {}", userTotal);
				log.info("productTotal: {}", productTotal);
				log.info("checkTotal: {}", checkTotal);
				
				if(checkTotal < 0) {
					return null;
				}else if(checkTotal > 0) {
					int[] userCoins = Utils.sortDesc(user.getDeposit());
					log.info("Descending Order userCoins {}", (Object)userCoins);
					log.info("userCoins length {}", userCoins.length);

					int coinValueCounter = 0;
					int i = 0;
					for(; i < userCoins.length; i++) {
						coinValueCounter += userCoins[i];
						if(coinValueCounter >= productTotal) {
							i++;
							break;
						}
					}
					log.info("i after first for: {}", i);
					log.info("coinValueCounter: {}", coinValueCounter);
					
					int changeArr[] = Utils.coinConvertor(coinValueCounter - productTotal);
					log.info("changeArray {}", (Object)changeArr);
					
					for(int j = i; j < userCoins.length; j++) {
						changeArr = Utils.addX(changeArr.length, changeArr, userCoins[j]);
						log.info("inside addX");
					}
					Arrays.sort(changeArr);
					log.info("changeArray after addX{}", (Object)changeArr);
					
					prod.get().setAmountAvailable(amountAvailable-amount);
					prodRepo.save(prod.get());

					user.setDeposit(new int[]{});
					userRepo.save(user);
					
					return new BuyerResponse(prod.get().getName(), productTotal, changeArr);
				} else {
					prod.get().setAmountAvailable(amountAvailable-amount);
					prodRepo.save(prod.get());

					user.setDeposit(new int[]{});
					userRepo.save(user);

					return new BuyerResponse(prod.get().getName(), productTotal, user.getDeposit());
				}
			}
		}	
		return null;
	}
}
