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

import com.app.web.social.model.UserAccount;

@Service
public class SocialWebAppUserDetailsService implements UserDetailsService {

	
	@Autowired
	private IUserService userService;
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		UserAccount userAccount = userService.getUserAccount(username);
		if(userAccount == null) throw new UsernameNotFoundException("");
		GrantedAuthority authority = new SimpleGrantedAuthority(userAccount.getRole());
      
		UserDetails userDetails = (UserDetails)new User(userAccount.getUsername(),
        userAccount.getPassword(), userAccount.isEnabled(), true, true, userAccount.isNotLocked(), Arrays.asList(authority));
		return userDetails;
	}
	
}
 