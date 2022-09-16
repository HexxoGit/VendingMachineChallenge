package com.challenge.vendingmachine.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.challenge.vendingmachine.DTO.RoleToUserRequest;
import com.challenge.vendingmachine.domain.User;
import com.challenge.vendingmachine.service.UserService;

@RestController
@RequestMapping("/api")
public class UserResource {

	private UserService vmUserService;
	
	public UserResource(UserService vmUserService) {
		super();
		this.vmUserService = vmUserService;
	}

	@GetMapping("/users")
	public ResponseEntity<List<User>> getVMUser() {
		return ResponseEntity.ok().body(vmUserService.getUsers());
	}
	
	@PostMapping("/user")
	public ResponseEntity<User> saveVMUser(@RequestBody User user) {
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user").toUriString());
		return ResponseEntity.created(uri).body(vmUserService.saveUser(user));
	}
	
	@PostMapping("/user/addRole")
	public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserRequest request) {
		vmUserService.addRoleToUser(request.getUsername(), request.getRole());
		return ResponseEntity.ok().build();
	}
}
