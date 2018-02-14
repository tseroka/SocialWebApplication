package com.app.web.social.service;

import java.net.UnknownHostException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.app.web.social.model.SecurityIssues;
import com.app.web.social.model.UserAccount;

public interface IUserService {

	public void registerUser(UserAccount userAccount) throws UnknownHostException;

	public void editUser(UserAccount userAccount, SecurityIssues issue);
	
	public void editUser(UserAccount userAccount);
	
	public void changePassword(UserAccount userAccount, String rawPassword);
	
	public boolean checkPassword(String rawPassword, String encodedPassword);
	
	public UserAccount getUserById(long id);

	public UserAccount getUserAccount(String username);
	
	public UserAccount getAuthenticatedUserAccount();
	
	public UserAccount getUserByNickname(String nickname);

	public UserAccount getUserByEmail(String email);	
	
	
//---------------------------------- D E F A U L T     M E T H O D S ----------------------------------------------------------
	
	public default boolean isAuthenticated()
	{
		return !getAuthenticatedUserUsername().equals("anonymousUser");
	}
	
	public default long getAuthenticatedUserId()
	{
		return getUserAccount( getAuthenticatedUserUsername() ).getId();
	}
	
	public default String getAuthenticatedUserUsername()
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getName();
	}

}
