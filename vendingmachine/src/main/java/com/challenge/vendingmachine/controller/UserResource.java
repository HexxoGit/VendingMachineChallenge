package com.challenge.vendingmachine.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.challenge.vendingmachine.DTO.RoleToUserRequest;
import com.challenge.vendingmachine.DTO.UserDepositRequest;
import com.challenge.vendingmachine.DTO.UserResponse;
import com.challenge.vendingmachine.domain.User;
import com.challenge.vendingmachine.service.UserService;

@RestController
@RequestMapping("/api")
public class UserResource {

	private UserService userService;
	
	public UserResource(UserService vmUserService) {
		super();
		this.userService = vmUserService;
	}

	@GetMapping("/users")
	public ResponseEntity<List<User>> getVMUser() {
		return ResponseEntity.ok().body(userService.getUsers());
	}
	
	@PostMapping("/user")
	public ResponseEntity<UserResponse> saveVMUser(@RequestBody User user) {
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user").toUriString());
		UserResponse response = userService.saveUser(user);
		if(response.getUsername() != null)
			return ResponseEntity.created(uri).body(response);
		else
			return ResponseEntity.badRequest().build();
	}
	
	@PostMapping("/user/addRole")
	public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserRequest request) {
		userService.addRoleToUser(request.getUsername(), request.getRole());
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/reset")
	public ResponseEntity<?> resetCoinAmount(@RequestParam String username) {
		userService.resetUserAmount(username);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/deposit")
	public ResponseEntity<?> depositCoin(@RequestBody UserDepositRequest request) {
		userService.addAmountToUser(request.getUsername(), request.getAmount());
		return ResponseEntity.ok().build();
	}
}
