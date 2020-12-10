package com.jwt.service;

import java.util.List;
import java.util.Optional;

import com.jwt.model.User;

public interface UserServiceInt {

	
	public User saveUser(User user);

	public Optional<User> findUserById(Long id);
	
	public List<User> getAllUsers();
	
	public String updateUser(long id,User user);
	
}
