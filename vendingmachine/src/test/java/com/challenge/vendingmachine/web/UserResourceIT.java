package com.challenge.vendingmachine.web;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.challenge.vendingmachine.DTO.UserRequest;
import com.challenge.vendingmachine.domain.User;
import com.challenge.vendingmachine.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserResourceIT {
	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private UserRepository userRepository;
	
	void registerUser() throws Exception {
		UserRequest testUser = new UserRequest("testuser1", "password");
		
		mockMvc.perform(null);
	}
}
