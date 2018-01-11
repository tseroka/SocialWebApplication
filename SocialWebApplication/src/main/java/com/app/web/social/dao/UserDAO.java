package com.app.web.social.dao;

import java.net.UnknownHostException;
import java.util.List;

import com.app.web.social.model.SecurityIssues;
import com.app.web.social.model.UserAccount;



public interface UserDAO {
	
	public void registerUser(UserAccount userAccount) throws UnknownHostException;

	public void editUser(UserAccount userAccount, SecurityIssues issue);
	
	public void editUser(UserAccount userAccount);
	
	public void deleteUser(long id);
	

	
	public UserAccount getUserById(long id);
	
	public String getAuthenticatedUserUsername();
	
	public boolean isAuthenticated();
	
	public long getAuthenticatedUserId();	
	
	public UserAccount getUserAccount(String username);
	
	public UserAccount getUserByNickname(String nickname);
	
	public List<UserAccount> getUsersList();
    
	
	public void clearSession();

	

}
