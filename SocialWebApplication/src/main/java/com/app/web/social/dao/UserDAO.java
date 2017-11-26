package com.app.web.social.dao;

import java.util.List;

import com.app.web.social.model.UserAccount;



public interface UserDAO {
	
	public void registerUser(UserAccount userAccount);

	public void editUser(String username);
	
	public void deleteUser(Integer id);
	
	public void disableUser(Integer id);
	
	public void enableUser(Integer id);
	
	public UserAccount getUserById(Integer id);
	
	public String getAuthenticatedUserUsername();
	
	public UserAccount getUserByUsername(String username);
	
	public UserAccount getUserAccount(String username);
	
	public UserAccount getUserByNickname(String nickname);
	
	public List<UserAccount> getUsersList();

	

}
