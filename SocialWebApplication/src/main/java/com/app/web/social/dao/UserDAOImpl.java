package com.app.web.social.dao;

import java.util.List;
import java.sql.Timestamp;
import java.util.ArrayList;

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

@Repository
@Transactional
public class UserDAOImpl implements UserDAO {


	  
	@Autowired
	private SessionFactory sessionFactory;
	 
	
	public void registerUser(UserAccount userAccount) 
	{  
		Session session = this.sessionFactory.getCurrentSession();
		List<String> empty = new ArrayList<String>();
		session.persist( new Profile(userAccount.getNickname(), "", empty, "", false, false));
		session.persist( new Friends(userAccount.getNickname(), empty, empty , empty));
		session.persist( new SecurityIssues(userAccount.getUsername(),userAccount,"activationCode","",new Timestamp(System.currentTimeMillis()),""));
		userAccount.setCreationDate(new Timestamp(System.currentTimeMillis()));
		session.persist(userAccount);		
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
 
	
	public void lockUser(long id)
	{
		Session session = this.sessionFactory.getCurrentSession();
		UserAccount userAccount = (UserAccount) session.load(UserAccount.class, id);
		if(userAccount.getRole().equals("ROLE USER"))
		{
		userAccount.setNotLocked(false);
		session.update(userAccount);
		}
	}
	
	public void unlockUser(long id)
	{ 
	   Session session = this.sessionFactory.getCurrentSession();
	   UserAccount userAccount = (UserAccount) session.load(UserAccount.class, id);
	   if(userAccount.getRole().equals("ROLE USER"))
	   {
	   userAccount.setNotLocked(true);
	   session.update(userAccount);
	   }
	}

	public UserAccount getUserById(long id) {
		Session session = this.sessionFactory.getCurrentSession();
		UserAccount userAccount = (UserAccount) session.load(UserAccount.class, id);
		return userAccount;
	}

	public UserAccount getUserByNickname(String nickname) 
	{
		Session session = this.sessionFactory.getCurrentSession();
		String hql = "from UserAccount U where U.nickname =:user_nickname";
		@SuppressWarnings("unchecked")
		Query<UserAccount> query = session.createQuery(hql).setParameter("user_nickname",nickname);
		List<UserAccount> result = (List<UserAccount>)query.list(); 
		return result.get(0);
	}
	

	
}

