package com.app.web.social.service;

import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.web.social.dao.UserDAO;
import com.app.web.social.model.UserAccount;
import com.app.web.social.model.SecurityIssues;

@Service
public class UserService {
	
	@Autowired
	private UserDAO userDAO;

	
	public void registerUser(UserAccount userAccount) throws UnknownHostException 
	{
		this.userDAO.registerUser(userAccount);
	}

	
	public void editUser(UserAccount userAccount, SecurityIssues issue)
	{
		this.userDAO.editUser(userAccount, issue);
	}
	
	public void editUser(UserAccount userAccount)
	{
		this.userDAO.editUser(userAccount);
	}
	
	public UserAccount getUserById(long id)
	{
		return this.userDAO.getUserById(id);
	}
	
	public String getAuthenticatedUserUsername()
	{
		return this.userDAO.getAuthenticatedUserUsername();
	}
	
	public boolean isAuthenticated()
	{
		return this.userDAO.isAuthenticated();
	}
	
	public long getAuthenticatedUserId()
	{
		return this.userDAO.getAuthenticatedUserId();
	}	
	
	public UserAccount getUserByNickname(String nickname)
	{
		return this.userDAO.getUserByNickname(nickname);
	}
	
	public UserAccount getUserByEmail(String email) 
	{
	    return this.userDAO.getUserByEmail(email);
	}
	public UserAccount getUserAccount(String username)
	{
		return this.userDAO.getUserAccount(username);
    }
	
	public UserAccount getAuthenticatedUserAccount() 
	{
	    return this.userDAO.getAuthenticatedUserAccount();	
	}

	
	public void clearSession()
	{
		this.userDAO.clearSession();
	}
}
