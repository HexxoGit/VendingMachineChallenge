package com.challenge.vendingmachine.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.challenge.vendingmachine.filter.CustomAuthenticationFilter;
import com.challenge.vendingmachine.filter.CustomAuthorizationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	private final UserDetailsService userDetailsService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public SecurityConfig(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		super();
		this.userDetailsService = userDetailsService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		CustomAuthenticationFilter customAuthenticationFilter = 
				new CustomAuthenticationFilter(authenticationManagerBean());
		customAuthenticationFilter.setFilterProcessesUrl("/api/login");
		
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests().antMatchers("/api/user").permitAll()
			.and().authorizeRequests().antMatchers("/api/users").permitAll()
			.and().authorizeRequests().antMatchers("/api/product/add").permitAll()
			.and().authorizeRequests().antMatchers("/api/product").permitAll()
			.and().authorizeRequests().antMatchers("/api/products").permitAll()
			.and().authorizeRequests().antMatchers("/api/user/reset").permitAll()
			.and().authorizeRequests().antMatchers("/api/user/addRole").permitAll()
			.and().authorizeRequests().antMatchers("/api/login/**").permitAll()
			.and().authorizeRequests().antMatchers(HttpMethod.POST, "/api/deposit").permitAll()
			//.and().authorizeRequests().antMatchers(HttpMethod.POST, "/api/user/reset").hasAnyAuthority("SELLER")
			//.and().authorizeRequests().antMatchers(HttpMethod.GET, "/api/users").hasAnyAuthority("BUYER")
			.and().authorizeRequests().anyRequest().authenticated();
		http.addFilter(customAuthenticationFilter);
		http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
}
