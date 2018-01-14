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
	
	public UserAccount getUserAccount(String username) 
	{
		UserAccount userAccount = new UserAccount();
		Query<UserAccount> query = this.sessionFactory.openSession()
		.createQuery("from UserAccount U where U.username =:user_username",UserAccount.class).setParameter("user_username",username);
	    try 
	    {
	    	userAccount = query.list().get(0);
	    }
	    catch(IndexOutOfBoundsException ex){userAccount=null;}
		return userAccount;
	}
	
	
	public List<UserAccount> getUsersList() 
	{		
	   return this.sessionFactory.getCurrentSession().createQuery("from UserAccount where role='ROLE USER'", UserAccount.class).list();
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
		return this.sessionFactory.getCurrentSession().load(UserAccount.class, id);
	}

	public UserAccount getUserByNickname(String nickname) 
	{
	   return this.sessionFactory.getCurrentSession().createQuery("from UserAccount U where U.nickname =:nickname",UserAccount.class).setParameter("nickname",nickname).list().get(0);
	}
	
	public UserAccount getUserByEmail(String email) 
	{
	   return this.sessionFactory.getCurrentSession().createQuery("from UserAccount U where U.email =:email",UserAccount.class).setParameter("email",email).list().get(0);
	}
	

	public void clearSession()
	{
		Session session = this.sessionFactory.getCurrentSession();
    	session.clear(); 
	}
	
}

