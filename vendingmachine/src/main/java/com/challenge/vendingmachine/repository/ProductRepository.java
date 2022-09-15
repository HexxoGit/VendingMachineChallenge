package com.challenge.vendingmachine.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.challenge.vendingmachine.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
