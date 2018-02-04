package com.app.web.social.dao;

import java.util.List;

import com.app.web.social.model.SecurityIssues;
import com.app.web.social.model.UserAccount;
import com.app.web.social.model.temp.LockAccount;

public interface AdminDAO 
{
	public List<String> getActiveUsersFromSessionRegistry();
	
	public List<UserAccount> getUsersList(int pageNumber);
	public Long countUsers();
	
	public List<UserAccount> getUsersList();
		
	public void accountSelfUnlockAfterLockTimeout(SecurityIssues issue);
	
	public void lockUser(LockAccount lockAccount);
	
	public void unlockUser(long id);
	
	public void deleteUser(long id); 
	
}
