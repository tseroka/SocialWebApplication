package com.app.web.social.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Encode 
{

	
	public static void main(String[] args) 
	{
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			System.out.println(encoder.encode("Pgzgz411"));
	}
	
}

