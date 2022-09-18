package com.challenge.vendingmachine.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.vendingmachine.DTO.UserRequest;
import com.challenge.vendingmachine.DTO.UserResponse;
import com.challenge.vendingmachine.domain.Role;
import com.challenge.vendingmachine.domain.User;
import com.challenge.vendingmachine.mapper.UserMapper;
import com.challenge.vendingmachine.repository.RoleRepository;
import com.challenge.vendingmachine.repository.UserRepository;
import com.challenge.vendingmachine.service.UserService;
import com.challenge.vendingmachine.utils.Utils;

@Service
@Transactional
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
	public UserResponse saveUser(UserRequest user) {
		UserResponse response = new UserResponse();
		
		if(userRepo.findByUsername(user.getUsername()) == null ) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			response = UserMapper.toResponse(userRepo.save(UserMapper.toEntity(user)));
		}
		return response;
	}

	@Override
	public void addRoleToUser(String username, String roleName) {
		User user = userRepo.findByUsername(username);
		Role role = roleRepo.findByName(roleName.toUpperCase());
		
		if(user == null)
			throw new UsernameNotFoundException("Username not found");
		if(role == null)
			throw new RuntimeException("Role not found");
		
		user.getRoles().add(role);
	}

	@Override
	public void addCoinToUser(String username, int coin) {
		User user = userRepo.findByUsername(username);
		
		int[] userCoins = user.getDeposit();
		
		if(Arrays.stream(Utils.allowedCoins).boxed().collect(Collectors.toList()).contains(coin)) {
			if(userCoins != null)
				user.setDeposit(Utils.addX(userCoins.length, userCoins, coin));
			else
				user.setDeposit(new int[] {coin});
			
			userRepo.save(user);
		}
	}

	@Override
	public void resetUserAmount(String username) {
		User user = userRepo.findByUsername(username);
		user.setDeposit(null);
		userRepo.save(user);
	}

	@Override
	public void deleteUser(String username) {
		User userToDelete = userRepo.findByUsername(username);
		
		if(userToDelete != null)
			userRepo.delete(userToDelete);
	}
}
