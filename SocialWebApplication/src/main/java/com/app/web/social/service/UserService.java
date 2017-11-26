package com.app.web.social.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.web.social.dao.UserDAO;
import com.app.web.social.model.UserAccount;

@Service
public class UserService {
	
	@Autowired
	private UserDAO userDAO;

	
	public void registerUser(UserAccount userAccount) 
	{
		this.userDAO.registerUser(userAccount);
	}

	
	public void editUser(String username)
	{
		this.userDAO.editUser(username);
	}
	
	
	public void deleteUser(Integer id) 
	{
		this.userDAO.deleteUser(id);
	}
	
	
	public void disableUser(Integer id)
	{
		this.userDAO.disableUser(id);
	}
	
	
	public void enableUser(Integer id)
	{
		this.userDAO.enableUser(id);
	}
	
	
	public UserAccount getUserById(Integer id)
	{
		return this.userDAO.getUserById(id);
	}
	
	public String getAuthenticatedUserUsername()
	{
		return this.userDAO.getAuthenticatedUserUsername();
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
