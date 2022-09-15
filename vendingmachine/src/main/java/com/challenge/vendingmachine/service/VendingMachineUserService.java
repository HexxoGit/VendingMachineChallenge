package com.challenge.vendingmachine.service;

import com.challenge.vendingmachine.domain.VendingMachineUser;

public interface VendingMachineUserService {

	VendingMachineUser saveUser(VendingMachineUser user);
	void addRoleToUser(String username, String roleName);
}
