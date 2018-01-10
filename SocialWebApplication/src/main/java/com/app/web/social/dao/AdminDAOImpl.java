package com.app.web.social.dao;

import java.sql.Timestamp;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.web.social.dao.SecurityDAO;
import com.app.web.social.dao.UserDAO;
import com.app.web.social.model.SecurityIssues;
import com.app.web.social.model.UserAccount;
import com.app.web.social.model.temp.LockAccount;

@Repository
@Transactional
public class AdminDAOImpl implements AdminDAO 
{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private SecurityDAO securityDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	Session session = null;

	public void accountSelfUnlockAfterLockTimeout(SecurityIssues issue)
	{
		session = this.sessionFactory.getCurrentSession();
		
		issue.setUnlockDate(null);
		issue.setLockReason(null);
		issue.setNumberOfLoginFails((byte)0);
		session.update(issue);
		
		UserAccount userAccount = userDAO.getUserAccount(issue.getUsername());
		userAccount.setNotLocked(true);
		session.update(userAccount);
	}
	
	
	public void lockUser(LockAccount lockAccount)
	{
		session = this.sessionFactory.getCurrentSession();
		
		UserAccount userAccount = (UserAccount) session.load(UserAccount.class, lockAccount.getId());
		
		if(userAccount.getRole().equals("ROLE USER"))
		{
		userAccount.setNotLocked(false);
		session.update(userAccount);
		
		SecurityIssues issue = (SecurityIssues) session.load(SecurityIssues.class, lockAccount.getId());
		issue.setLockReason(lockAccount.getLockReason());
		
		if(lockAccount.getLockTime()==0) { issue.setUnlockDate(null);}
		
		System.out.println("lock time: "+lockAccount.getLockTime());
		
		if(lockAccount.getLockTime()!=0) {issue.setUnlockDate( new Timestamp( System.currentTimeMillis() + 86400000L*lockAccount.getLockTime() ) );}
		
		System.out.println("lock time: "+lockAccount.getLockTime());
		session.update(issue);
		}
	}
	
	public void unlockUser(long id)
	{ 
	   session = this.sessionFactory.getCurrentSession();
	   
	   UserAccount userAccount = (UserAccount) session.load(UserAccount.class, id);
	   
	   if(userAccount.getRole().equals("ROLE USER"))
	   {
	   userAccount.setNotLocked(true);
	   session.update(userAccount);
	   
	   SecurityIssues issue = (SecurityIssues) session.load(SecurityIssues.class, id);
	   issue.setLockReason(null);
	   issue.setUnlockDate( null);
	   session.update(issue);
	   
	   }
	}
}
