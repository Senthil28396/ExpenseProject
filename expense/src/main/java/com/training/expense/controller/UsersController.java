package com.training.expense.controller;


	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.http.HttpStatus;
	import org.springframework.http.HttpStatusCode;
	import org.springframework.http.ResponseEntity;
	import org.springframework.security.crypto.password.PasswordEncoder;
	import org.springframework.web.bind.annotation.DeleteMapping;
	import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.PathVariable;
	import org.springframework.web.bind.annotation.PostMapping;
	import org.springframework.web.bind.annotation.PutMapping;
	import org.springframework.web.bind.annotation.RequestBody;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RequestParam;
	import org.springframework.web.bind.annotation.RestController;

import com.training.expense.model.Users;
import com.training.expense.repository.UsersRepository;
import com.training.expense.service.UsersService;

	

	@RestController
	public class UsersController {
		@Autowired
		public UsersService userservice;
		
		@Autowired
		public UsersRepository userrepository;
		
		@PostMapping("/users")
		public String createUser(@RequestBody Users users)
		{
			userservice.createUser(users);
			return "Sucessfully Created Your Account";
		}
		@GetMapping("/logins")
		public String loginUsers()
		{
			return "Login Successfully";
		}
		@PutMapping("/users/{id}") 
		public ResponseEntity<Users> updateUserDetails(@RequestBody Users users,@PathVariable int id) 
		{
			userservice.updateUserDetails(users,id);
			return new ResponseEntity<Users>(HttpStatus.CREATED);
		}
		
		@DeleteMapping("/users/{id}") 
		public HttpStatus deleteUsers(@PathVariable int id)		  
		{
		    userservice.deleteUser(id);
			return HttpStatus.OK;
		}
		
		@PostMapping("/forgot-password")
		public String forgotPassword(@RequestParam String email) {

			String response = userservice.forgotPassword(email);

			if (!response.startsWith("Invalid")) {
				response = "http://localhost:8080/reset-password?token=" + response;
			}
			return response;
		}

		@PutMapping("/reset-password")
		public String resetPassword(@RequestParam String token,
				@RequestParam String password) {

			return userservice.resetPassword(token, password);
		}	 
	}

