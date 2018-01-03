package com.app.web.social.dao;

import java.util.List;

import com.app.web.social.model.UserAccount;



public interface UserDAO {
	
	public void registerUser(UserAccount userAccount);

	public void editUser(UserAccount userAccount);
	
	public void deleteUser(long id);
	
	public void lockUser(long id);
	
	public void unlockUser(long id);
	
	public UserAccount getUserById(long id);
	
	public String getAuthenticatedUserUsername();
	
	public long getAuthenticatedUserId();
	
	public UserAccount getUserByUsername(String username);
	
	public UserAccount getUserAccount(String username);
	
	public UserAccount getUserByNickname(String nickname);
	
	public List<UserAccount> getUsersList();

	

}
