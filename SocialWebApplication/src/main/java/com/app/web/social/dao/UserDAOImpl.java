package com.app.web.social.dao;

import java.util.List;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.app.web.social.model.Profile;
import com.app.web.social.model.Friends;
import com.app.web.social.model.UserAccount;
import com.app.web.social.model.SecurityIssues;
import com.app.web.social.service.SecurityService;

@Repository
@Transactional
public class UserDAOImpl implements UserDAO 
{


	  
	@Autowired
	private SessionFactory sessionFactory;
	 
	@Autowired
	private SecurityService security;
	
	public void registerUser(UserAccount userAccount) throws UnknownHostException 
	{  
		Session session = this.sessionFactory.getCurrentSession();
		
		List<String> empty = new ArrayList<String>();
		
		String ip = InetAddress.getLocalHost().toString();
		HashSet<String> ipSet = new HashSet<String>(); ipSet.add(ip);
				
		session.persist( new Profile(userAccount.getNickname(), "", empty, "", false, false));
		
		session.persist( new Friends(userAccount.getNickname(), empty, empty , empty));
		
		userAccount.setCreationDate(new Timestamp(System.currentTimeMillis()));
		
		session.persist(userAccount);		
		
		session.persist( new SecurityIssues(userAccount.getUsername(),userAccount, security.generateActivationAndUnlockCode(), null, null, ip , ipSet,new Timestamp(System.currentTimeMillis()),(byte) 0, null, null));
		
		security.sendEmailWithActivationCode(userAccount.getEmail(), userAccount.getUsername());
	}
   
	public void editUser(UserAccount userAccount, SecurityIssues issue) 
	{
		Session session = this.sessionFactory.getCurrentSession();
		
		session.update(userAccount);
		
		session.update(issue);
		
	}
	
	public void editUser(UserAccount userAccount) 
	{	
		this.sessionFactory.getCurrentSession().update(userAccount);	
	}

	public String getAuthenticatedUserUsername()
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getName();
	}
	
	public boolean isAuthenticated()
	{
		return !getAuthenticatedUserUsername().equals("anonymousUser");
	}
	
	public long getAuthenticatedUserId()
	{
		return getUserAccount( getAuthenticatedUserUsername() ).getId();
	}
	
	public UserAccount getUserByUsername(String username) 
	{
		Session session = this.sessionFactory.getCurrentSession();
		String hql = "from UserAccount U where U.username =:user_username";
		@SuppressWarnings("unchecked")
		Query<UserAccount> query = session.createQuery(hql).setParameter("user_username",username);
		UserAccount user = (UserAccount)query.list().get(0); 
	    user.setPassword("");
	    return user;
	}
	
    
	public UserAccount getUserAccount(String username) 
	{
		UserAccount userAccount = new UserAccount();
		@SuppressWarnings("unchecked")
		Query<UserAccount> query = this.sessionFactory.getCurrentSession()
		.createQuery("from UserAccount U where U.username =:user_username").setParameter("user_username",username);
	    try 
	    {
	    	userAccount = (UserAccount) query.list().get(0);
	    }
	    catch(IndexOutOfBoundsException ex){userAccount=null;}
		return userAccount;
	}
	
	
	public List<UserAccount> getUsersList() 
	{		
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		Query<UserAccount> query=session.createQuery("from UserAccount where role='ROLE USER'");
		List<UserAccount> userList = (List<UserAccount>)query.list(); 
		return userList;
	}
	
	
	public void deleteUser(long id) 
	{
		Session session = this.sessionFactory.getCurrentSession();
		
		UserAccount userAccount = (UserAccount) session.load(UserAccount.class, id);
		String nickname = userAccount.getNickname();
		session.delete(userAccount);
		
		Friends friends = (Friends) session.load(Friends.class, nickname);
		session.delete(friends);
		
		Profile profile = (Profile) session.load(Profile.class, nickname);
		session.delete(profile);
		
		//TODO delete messages
		
	}
 

	public UserAccount getUserById(long id) 
	{
		return (UserAccount) this.sessionFactory.getCurrentSession().load(UserAccount.class, id);
	}

	public UserAccount getUserByNickname(String nickname) 
	{
		@SuppressWarnings("unchecked")
		Query<UserAccount> query = this.sessionFactory.getCurrentSession()
		.createQuery("from UserAccount U where U.nickname =:user_nickname").setParameter("user_nickname",nickname);
		return (UserAccount) query.list().get(0);
	}
	

	
}

