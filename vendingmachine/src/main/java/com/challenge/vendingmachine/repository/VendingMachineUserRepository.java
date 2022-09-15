package com.challenge.vendingmachine.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.challenge.vendingmachine.domain.VendingMachineUser;

public interface VendingMachineUserRepository extends JpaRepository<VendingMachineUser, Long>{

}
