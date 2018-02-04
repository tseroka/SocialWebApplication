package com.app.web.social.dao;

import java.sql.Timestamp;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.web.social.dao.UserDAO;
import com.app.web.social.model.Friends;
import com.app.web.social.model.Profile;
import com.app.web.social.model.SecurityIssues;
import com.app.web.social.model.UserAccount;
import com.app.web.social.model.temp.LockAccount;

@Repository
@Transactional
public class AdminDAOImpl extends SuperDAO<Long, SecurityIssues > implements AdminDAO 
{
	
	@Autowired  
	private UserDAO userDAO;
	
	@Autowired
	private MessagesDAO messagesDAO;
	
	@Autowired
	private SessionRegistry sessionRegistry;
	 
	public List<String> getActiveUsersFromSessionRegistry() 
	{
	    return sessionRegistry.getAllPrincipals().stream()
	      .filter(u -> !sessionRegistry.getAllSessions(u, false).isEmpty())
	      .map(Object::toString)
	      .collect(Collectors.toList());
	}
	
	public List<UserAccount> getUsersList(int pageNumber) 
	{		
	   int pageSize = 10; 
	   return getSession().createQuery("from UserAccount where role='ROLE USER'", UserAccount.class)
	   .setFirstResult((pageNumber - 1) * pageSize).setMaxResults(pageSize).list();
	}
	
    public Long countUsers()
    {     
      return getSession().createQuery("select count(U.id) from UserAccount U where role='ROLE USER'", Long.class).uniqueResult();
    }
    
    
    public List<UserAccount> getUsersList() 
	{		
	   return getSession().createQuery("from UserAccount where role='ROLE USER'", UserAccount.class).list();
	}
	
    
	public void accountSelfUnlockAfterLockTimeout(SecurityIssues issue)
	{
	
		issue.setUnlockDate(null);
		issue.setLockReason(null);
		issue.setNumberOfLoginFails((byte)0);
		
		UserAccount userAccount = userDAO.getUserAccount(issue.getUsername());
		userAccount.setNotLocked(true);
		update(issue);
	} 
	
	public void lockUser(LockAccount lockAccount)
	{
		
		UserAccount userAccount = userDAO.getUserById(lockAccount.getId());
		
		if(userAccount.getRole().equals("ROLE USER"))
		{
		  userAccount.setNotLocked(false);
		
		  SecurityIssues issue = loadEntityByPrimaryKey(lockAccount.getId());
		  issue.setLockReason(lockAccount.getLockReason());
		
		  if(lockAccount.getLockTime()==0L) 
		  {
			issue.setUnlockDate(null);
		  }
		  else 
		  {
		    issue.setUnlockDate( new Timestamp( System.currentTimeMillis() + 86400000L*lockAccount.getLockTime() ) );
		  }
		  
		update(issue);
		}
	}
	
	public void unlockUser(long id)
	{ 
	   UserAccount userAccount = userDAO.getUserById(id);
	   
	   if(userAccount.getRole().equals("ROLE USER"))
	   {
	   userAccount.setNotLocked(true);
	   
	   SecurityIssues issue = loadEntityByPrimaryKey(id);
	   issue.setLockReason(null);
	   issue.setUnlockDate(null);
       update(issue);
	   }
	}
	
	public void deleteUser(long id) 
	{
		Session session = getSession();
	
		UserAccount userAccount = session.load(UserAccount.class, id);
		
		if(userAccount.getRole().equals("ROLE USER"))
		{
		  String nickname = userAccount.getNickname();
		
		  session.evict(userAccount);
		  
		  messagesDAO.removeAllMessages(nickname);
		  
		  SecurityIssues issue = session.load(SecurityIssues.class, id);
		  session.remove(issue);
		  
		  Friends friends = session.load(Friends.class, nickname);
		  session.remove(friends);
		
		  Profile profile = session.load(Profile.class, nickname);
		  session.remove(profile);
		    
	   }
	}
	
}
