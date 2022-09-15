package com.challenge.vendingmachine.serviceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.vendingmachine.domain.VendingMachineUser;
import com.challenge.vendingmachine.repository.VendingMachineUserRepository;
import com.challenge.vendingmachine.service.VendingMachineUserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class VendingMachineUserServiceImpl implements VendingMachineUserService {

	//private final VendingMachineUserRepository vmUserRepo;
	
	
	
	@Override
	public VendingMachineUser saveUser(VendingMachineUser user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addRoleToUser(String username, String roleName) {
		// TODO Auto-generated method stub
		
	}

}
