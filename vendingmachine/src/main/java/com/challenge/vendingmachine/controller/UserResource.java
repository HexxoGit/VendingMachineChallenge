package com.challenge.vendingmachine.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.challenge.vendingmachine.DTO.UserRequest;
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
	public ResponseEntity<UserResponse> saveVMUser(@RequestBody UserRequest user) {
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user").toUriString());
		UserResponse response = userService.saveUser(user);
		if(response.getUsername() != null)
			return ResponseEntity.created(uri).body(response);
		else
			return ResponseEntity.badRequest().build();
	}
	
	@PostMapping("/user/addRole")
	public ResponseEntity<?> addRoleToUser(Authentication authentication, @RequestParam String role) {
		userService.addRoleToUser(authentication.getName(), role);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/user/reset")
	public ResponseEntity<?> resetCoinAmount(Authentication authentication) {
		userService.resetUserAmount(authentication.getName());
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/user/deposit")
	public ResponseEntity<?> depositCoin(Authentication authentication, @RequestParam int coin) {
		userService.addCoinToUser(authentication.getName(), coin);
		return ResponseEntity.ok().build();
	}
}
