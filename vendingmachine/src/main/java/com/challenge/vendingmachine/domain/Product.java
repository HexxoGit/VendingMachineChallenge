package com.challenge.vendingmachine.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter @Setter @NoArgsConstructor
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String name;
	
	private int amountAvailable;
	private int cost;

	public Product(String productName, int amountAvailable, int cost) {
		super();
		this.name = productName;
		this.amountAvailable = amountAvailable;
		this.cost = cost;
	}

}
