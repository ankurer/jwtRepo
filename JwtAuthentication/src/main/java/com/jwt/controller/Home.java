package com.jwt.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Home {

	@RequestMapping("/welcome")
	public String welcome() {
		
		System.out.println("inside welcome------->");
		return "welocome page accessed after JWt Authetication ";
		
	}

	
}


