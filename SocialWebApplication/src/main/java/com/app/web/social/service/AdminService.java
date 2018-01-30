package com.app.web.social.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.web.social.dao.AdminDAO;
import com.app.web.social.model.SecurityIssues;
import com.app.web.social.model.UserAccount;
import com.app.web.social.model.temp.LockAccount;

@Service
public class AdminService 
{

	@Autowired
	private AdminDAO adminDAO;
	
	public List<UserAccount> getUsersList(int pageNumber)
	{
		return this.adminDAO.getUsersList(pageNumber);
	}
	
	public Long countUsers()
	{
	   return this.adminDAO.countUsers();	
	}
	
	
	public List<UserAccount> getUsersList()
	{
		return this.adminDAO.getUsersList();
	}
	
	public void accountSelfUnlockAfterLockTimeout(SecurityIssues issue)
	{
		this.adminDAO.accountSelfUnlockAfterLockTimeout(issue);
	}
	
	public void lockUser(LockAccount lockAccount)
	{
		this.adminDAO.lockUser(lockAccount);
	}
	
	public void unlockUser(long id)
	{
		this.adminDAO.unlockUser(id);
	}
	
	
	public void deleteUser(long id) 
	{
		this.adminDAO.deleteUser(id);
	}
	
}
