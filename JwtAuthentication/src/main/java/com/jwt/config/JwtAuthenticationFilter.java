package com.jwt.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import com.jwt.util.JwtUtil;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	// get Header
	// get jwt
	// check if not null
	// get username from token
	// check if username not null
	
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		    String requestHeaderToken = request.getHeader("Authorization");
		    
		    String username=null;
		    String jwtToken=null;
		    
		    
		    if(requestHeaderToken!=null && requestHeaderToken.startsWith("Bearer "))
		    {
		    	
		    	jwtToken=requestHeaderToken.substring(7);
		    	
		    	
		    	try {
		    		
		    		username=this.jwtUtil.getUsernameFromToken(jwtToken);
		    	}catch(Exception e) {
		    		
		    		e.printStackTrace();
		    	}
		    	
		    	  UserDetails userDetails= this.userDetailsService.loadUserByUsername(username);
		    	
		    	//validate token
		    	if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
		    	{
		    		
		    		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
		    		usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		    		SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		    		
		    		
		    	}else {
		    		System.out.println("token is not valid");
		    	}
		    	
		    	
		    }
		    
		    
		    filterChain.doFilter(request,response);
		
	}

}
