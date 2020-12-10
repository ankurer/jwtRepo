package com.jwt.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.model.JwtRequest;
import com.jwt.model.JwtResponse;
import com.jwt.model.User;
import com.jwt.service.UserServiceInt;
import com.jwt.util.JwtUtil;

@RestController
public class JwtController {

	@Autowired
	private UserServiceInt userService;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@PostMapping("/save")
	public User save(@RequestBody  User user){
		
		return userService.saveUser(user);
		
	}
	
	
	
	@RequestMapping(value = "/token",method = RequestMethod.POST)
	public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception{
	
		System.out.println("jwtrequest---->"+jwtRequest);
		
		
		try {
			
		  this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(),jwtRequest.getPassword()));	
			
		}catch(UsernameNotFoundException e) {
			
			e.printStackTrace();
			throw new Exception("Bad Credentials");
		}catch(Exception e) {
			e.printStackTrace();
			throw new Exception("Bad Credentials exception");
		}
		
		// If all fine then generate token
		
		UserDetails userDetails= this.userDetailsService.loadUserByUsername(jwtRequest.getUsername());
		
		String token=jwtUtil.generateToken(userDetails);
	
	    System.out.println("JWT token generated-----> "+token);
	
	
	    // Now we have to send this token in JSON form so.... 
	    
	    return ResponseEntity.ok(new JwtResponse(token));
	    
	}
	
	
	@GetMapping("/all")
	public List getAll() {
		
		return userService.getAllUsers();
		
	}
	
	@GetMapping("/all/{id}")
	public Optional<User> getSingleUser(@PathVariable Long id)
	{
		
		return userService.findUserById(id);
	}
	
	
	@PostMapping("/update/{id}")
	public String updateUser(@PathVariable Long id,@RequestBody User user)
	{
	
		String data = userService.updateUser(id, user);
		
		return data;
	}
	
}
