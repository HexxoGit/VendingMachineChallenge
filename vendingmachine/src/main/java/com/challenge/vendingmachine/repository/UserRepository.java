package com.challenge.vendingmachine.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.challenge.vendingmachine.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByUsername(String username);
}
