package com.app.web.social.service;

import java.net.UnknownHostException;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.web.social.dao.UserDAO;
import com.app.web.social.model.UserAccount;

@Service
public class UserService {
	
	@Autowired
	private UserDAO userDAO;

	
	public void registerUser(UserAccount userAccount) throws UnknownHostException 
	{
		this.userDAO.registerUser(userAccount);
	}

	
	public void editUser(UserAccount userAccount)
	{
		this.userDAO.editUser(userAccount);
	}
	
	
	public void deleteUser(long id) 
	{
		this.userDAO.deleteUser(id);
	}
	
	
	public void lockUser(long id)
	{
		this.userDAO.lockUser(id);
	}
	
	
	public void unlockUser(long id)
	{
		this.userDAO.unlockUser(id);
	}
	
	
	public UserAccount getUserById(long id)
	{
		return this.userDAO.getUserById(id);
	}
	
	public String getAuthenticatedUserUsername()
	{
		return this.userDAO.getAuthenticatedUserUsername();
	}
	
	public long getAuthenticatedUserId()
	{
		return this.userDAO.getAuthenticatedUserId();
	}
	
	public UserAccount getUserByUsername(String username)
	{
		return this.userDAO.getUserByUsername(username);
	}
	
	
	public UserAccount getUserByNickname(String nickname)
	{
		return this.userDAO.getUserByNickname(nickname);
	}
	
	public UserAccount getUserAccount(String username)
	{
		return this.userDAO.getUserAccount(username);
    }
	
	
	public List<UserAccount> getUsersList()
	{
		return this.userDAO.getUsersList();
	}
	
}
