package com.challenge.vendingmachine.serviceImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.vendingmachine.domain.Role;
import com.challenge.vendingmachine.domain.User;
import com.challenge.vendingmachine.repository.RoleRepository;
import com.challenge.vendingmachine.repository.UserRepository;
import com.challenge.vendingmachine.service.UserService;

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
	public User saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepo.save(user);
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
	public void addAmountToUser(String username, int amount) {
		User user = userRepo.findByUsername(username);
		
		if(user == null)
			throw new UsernameNotFoundException("Username not found");
		
		user.setDeposit(amount);
		userRepo.save(user);
	}

	@Override
	public void resetUserAmount(String username) {
		User user = userRepo.findByUsername(username);
		
		if(user == null)
			throw new UsernameNotFoundException("Username not found");
		
		user.setDeposit(0);
		userRepo.save(user);
	}

}
