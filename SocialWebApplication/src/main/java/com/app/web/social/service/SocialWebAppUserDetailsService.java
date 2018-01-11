package com.app.web.social.service;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.app.web.social.model.UserAccount;
import com.app.web.social.dao.UserDAO;


@Service
public class SocialWebAppUserDetailsService implements UserDetailsService {

	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		UserAccount userAccount = userDAO.getUserAccount(username);
		if(userAccount == null) throw new UsernameNotFoundException("");
		GrantedAuthority authority = new SimpleGrantedAuthority(userAccount.getRole());
      
		UserDetails userDetails = (UserDetails)new User(userAccount.getUsername(),
  passwordEncoder.encode( userAccount.getPassword() ), userAccount.isEnabled(), true, true, userAccount.isNotLocked(), Arrays.asList(authority));
		return userDetails;
	}
	
}
 