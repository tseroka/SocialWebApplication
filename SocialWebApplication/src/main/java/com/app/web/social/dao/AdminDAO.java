package com.app.web.social.dao;

import com.app.web.social.model.SecurityIssues;
import com.app.web.social.model.temp.LockAccount;

public interface AdminDAO 
{

	public void accountSelfUnlockAfterLockTimeout(SecurityIssues issue);
	
	public void lockUser(LockAccount lockAccount);
	
	public void unlockUser(long id);
}
