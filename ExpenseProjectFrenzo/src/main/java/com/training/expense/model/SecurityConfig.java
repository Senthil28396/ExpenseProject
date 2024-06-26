package com.training.expense.model;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {

	@Bean
	SecurityFilterChain defaultSecurityFilter(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests().requestMatchers("/logins").authenticated()
		.requestMatchers("/users").permitAll()
		.requestMatchers("/users/{id}").permitAll()
		.requestMatchers("/forgot-password").permitAll()
		.requestMatchers("/reset-password").permitAll()
		.requestMatchers("/categories").permitAll()
		.requestMatchers("/categories/{id}").permitAll()
		.requestMatchers("/paymentmode").permitAll()
		.requestMatchers("/paymentmode/{id}").permitAll()
		.requestMatchers("/transaction").permitAll()
		.requestMatchers("/transaction/{id}").permitAll()
		.requestMatchers("/swagger-ui/**","/v3/api-docs/**").permitAll();
		http.formLogin();
		http.httpBasic();
		http.csrf().disable();
		return http.build();
	}

//	  @Bean public PasswordEncoder passwordEncoder() 
//	  { 
//		  return  NoOpPasswordEncoder.getInstance(); 
//	  }
	  
	  @Bean public PasswordEncoder bcryptPasswordEncoder() 
	  { 
		  return new  BCryptPasswordEncoder(); 
	  }

}
