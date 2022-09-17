package com.challenge.vendingmachine.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.vendingmachine.DTO.UserResponse;
import com.challenge.vendingmachine.domain.Role;
import com.challenge.vendingmachine.domain.User;
import com.challenge.vendingmachine.mapper.UserMapper;
import com.challenge.vendingmachine.repository.RoleRepository;
import com.challenge.vendingmachine.repository.UserRepository;
import com.challenge.vendingmachine.service.UserService;
import com.challenge.vendingmachine.utils.Utils;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

	private UserRepository userRepo;
	private RoleRepository roleRepo;
	private PasswordEncoder passwordEncoder;
	
	public UserServiceImpl(UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder passwordEncoder) {
		super();
		this.userRepo = userRepo;
		this.roleRepo = roleRepo;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username);
		if(user == null)
			throw new UsernameNotFoundException("Username not found");
		
		Collection<SimpleGrantedAuthority> authorities = new ArrayList();
		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		});
		
		return new org.springframework.security.core.userdetails.User(
						user.getUsername(), user.getPassword(), authorities);
	}
	
	@Override
	public List<User> getUsers() {
		return userRepo.findAll();
	}
	
	@Override
	public UserResponse saveUser(User user) {
		UserResponse response = new UserResponse();
		if(userRepo.findByUsername(user.getUsername()) == null) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			response = UserMapper.toResponse(userRepo.save(user));
		}
		return response;
	}

	@Override
	public void addRoleToUser(String username, String roleName) {
		User user = userRepo.findByUsername(username);
		Role role = roleRepo.findByName(roleName);
		
		if(user == null)
			throw new UsernameNotFoundException("Username not found");
		
		user.getRoles().add(role);
	}

	@Override
	public void addAmountToUser(String username, int coin) {
		User user = userRepo.findByUsername(username);
		
		if(user == null)
			throw new UsernameNotFoundException("Username not found");
		
		if(Arrays.stream(Utils.allowedCoins).boxed().collect(Collectors.toList()).contains(coin))
			user.setDeposit(user.getDeposit() + coin);
		userRepo.save(user);
	}

	@Override
	public void resetUserAmount(String username) {
		User user = userRepo.findByUsername(username);
		if(user == null)
			throw new UsernameNotFoundException("Username not found");
		
		log.info("USER PARA RESETAR {}", user.toString());
		user.setDeposit(0);
		userRepo.save(user);
	}

}
