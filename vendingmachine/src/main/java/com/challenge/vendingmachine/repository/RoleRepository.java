package com.challenge.vendingmachine.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.challenge.vendingmachine.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

	Role findByName(String name);
}
