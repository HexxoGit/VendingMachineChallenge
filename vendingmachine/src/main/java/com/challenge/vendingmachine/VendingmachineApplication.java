package com.challenge.vendingmachine;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.challenge.vendingmachine.domain.User;
import com.challenge.vendingmachine.service.UserService;


@SpringBootApplication
public class VendingmachineApplication {

	public static void main(String[] args) {
		SpringApplication.run(VendingmachineApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			userService.saveUser(new User("SuperUser2", "super", 0));
			userService.saveUser(new User("SuperUser3", "super", 0));
			
			userService.addRoleToUser("SuperUser2", "BUYER");
			userService.addRoleToUser("SuperUser3", "SELLER");
		};
	}
}
