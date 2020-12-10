package com.jwt.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jwt.model.User;
import com.jwt.repository.UserRepository;

@Service
public class UserServiceImpl implements UserServiceInt,UserDetailsService {

	@Autowired
	public UserRepository repo;
	
	
	// Method to give Hardcode username & password
	/*
	 * 
	 * public UserDetails loadUserByUsername(String userName) throws
	 * UsernameNotFoundException {
	 * 
	 * if(userName.equals("Ankur")) {
	 * 
	 * return new User("Ankur","Ankur12",new ArrayList<>()); }else {
	 * 
	 * throw new UsernameNotFoundException("User does not exist"); }
	 * 
	 * }
	 */
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = repo.findByUsername(username);
		
		System.out.println("username is"+user.getUsername());
		
		 if(username.equals(user.getUsername())) {
		  UserDetails userDetails=(UserDetails) new User(user.getUsername(),user.getPassword());
		
		
		return userDetails;
		 }else {
			 throw new UsernameNotFoundException("User does not exist");

		 }
}
	
	
	@Override
	public User saveUser(User user) {
		
		return repo.save(user);
	}

	@Override
	public Optional<User> findUserById(Long id) {
		
		return repo.findById(id);
	}

	@Override
	public List<User> getAllUsers() {
		
		return repo.findAll();
	}

	@Override
	public String updateUser(long id, User user) {
		
		Optional<User> data = findUserById(id);
		
		if(data!=null) {
			
			User user1 = data.get();
			
			user1.setFirstName(user.getFirstName());
			user1.setLastName(user.getLastName());
			user1.setUsername(user.getUsername());
			user1.setPassword(user.getPassword());
			
			repo.save(user1);
			
			return "user updated successfully";
			
		}else {
			
			return "no record exist";
		}
		
		
	}

	

}
