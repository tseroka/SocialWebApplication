package com.app.web.social.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.app.web.social.model.UserAccount;
import com.app.web.social.model.temp.LockAccount;

public interface IAdminService {

    public List<String> getActiveUsersFromSessionRegistry();
	
	public Page<UserAccount> getUsersList(int pageNumber);
	public Long countUsers();
	
	public List<UserAccount> getUsersList();
	
	public void lockUser(LockAccount lockAccount);
	
	public void unlockUser(long id);
	
	public void deleteUser(long id); 
	
	public default boolean isManagable(UserAccount userAccount)
	{
		return userAccount.getRole().equals("ROLE_USER");
	}
	
}
