package com.training.expense.service;


	import java.time.Duration;
	import java.time.LocalDateTime;
	import java.util.ArrayList;
	import java.util.List;
	import java.util.Optional;
	import java.util.UUID;

	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.security.core.GrantedAuthority;
	import org.springframework.security.core.authority.SimpleGrantedAuthority;
	import org.springframework.security.core.userdetails.User;
	import org.springframework.security.core.userdetails.UserDetails;
	import org.springframework.security.core.userdetails.UserDetailsService;
	import org.springframework.security.core.userdetails.UsernameNotFoundException;
	import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
	import org.springframework.security.crypto.password.PasswordEncoder;
	import org.springframework.stereotype.Service;

import com.training.expense.model.UserNotFoundException;
import com.training.expense.model.Users;
import com.training.expense.repository.UsersRepository;

	

@Service
public class UsersService implements UserDetailsService 
{
	private static final long EXPIRE_TOKEN_AFTER_MINUTES = 30;

	@Autowired
	private UsersRepository usersrepository;
	
	@Autowired
	public PasswordEncoder passwordencoder;
	
	public Users createUser(Users users) 
	{
		users.setUserId(users.getUserId());
		users.setUserName(users.getUserName());
		users.setPassword(passwordencoder.encode(users.getPassword()));
		users.setMobile(users.getMobile());
		users.setEmail(users.getEmail());
		users.setRole(users.getRole());
		usersrepository.save(users);
		return usersrepository.save(users);
	}

	
	   @Override 
		public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException 
	     {
			List<Users> user = usersrepository.findByEmail(email);
			Users selectedUser = user.get(0);
			if (user.size() == 0) 
			{
				throw new UsernameNotFoundException("user details not found for the user" + email);
			}
			UserDetails userinfo = User.withUsername(user.get(0).getEmail()).password(user.get(0).getPassword())
					.authorities(user.get(0).getRole()).build();
			List<GrantedAuthority> grantedAuthority = new ArrayList<GrantedAuthority>();
			SimpleGrantedAuthority simpleAuthority = new SimpleGrantedAuthority("admin");
			grantedAuthority.add(simpleAuthority);
			return new User(selectedUser.getEmail(), selectedUser.getPassword(), grantedAuthority);
		}
	 

	public Users updateUserDetails(Users users, int userId) 
	{
		Optional<Users> user = usersrepository.findById(userId);
		if (user.isPresent()) 
		{
			Users newUser = user.get();
			newUser.setUserName(users.getUserName());
			if (!users.getPassword().isEmpty()) 
			{
				String hashedPassword = new BCryptPasswordEncoder().encode(users.getPassword());
				newUser.setPassword(hashedPassword);
			}
			newUser.setMobile(users.getMobile());
			newUser.setEmail(users.getEmail());
			newUser.setRole(users.getRole());
			Users savedUser = usersrepository.save(newUser);
			return savedUser;
		} 
		else 
		{
			throw new UserNotFoundException("No User Record Exist for given id");
		}
	}
		
	public void deleteUser(int userId)
		{
			Optional<Users> users=usersrepository.findById(userId);
			if(users.isPresent())
			{
				usersrepository.deleteById(userId);
			}
			else
			{
				throw new UserNotFoundException("No User Record Exist for given id");
			}
	}

	public String forgotPassword(String email) {

		Optional<Users> userOptional =Optional.ofNullable(usersrepository.getByEmail(email));

		if(!userOptional.isPresent()) {
			return "Invalid email id.";
		}

		Users user = userOptional.get();
		user.setToken(generateToken());
		user.setTokenCreationDate(LocalDateTime.now());

		user = usersrepository.save(user);

		return user.getToken();
	}

	public String resetPassword(String token,String password) {

		Optional<Users> userOptional = Optional
				.ofNullable(usersrepository.findByToken(token));

		if (!userOptional.isPresent()) {
			return "Invalid token.";
		}

		LocalDateTime tokenCreationDate = userOptional.get().getTokenCreationDate();

		if (isTokenExpired(tokenCreationDate)) {
			return "Token expired.";

		}

		Users user = userOptional.get();
		user.setPassword(passwordencoder.encode(password));
		user.setToken(null);
		user.setTokenCreationDate(null);

		usersrepository.save(user);

		return "Your password successfully updated.";
	}

	private String generateToken() {
		StringBuilder token = new StringBuilder();

		return token.append(UUID.randomUUID().toString())
				.append(UUID.randomUUID().toString()).toString();
	}
	
	private boolean isTokenExpired(final LocalDateTime tokenCreationDate) {

		LocalDateTime now = LocalDateTime.now();
		Duration diff = Duration.between(tokenCreationDate, now);

		return diff.toMinutes() >= EXPIRE_TOKEN_AFTER_MINUTES;
	}
}
