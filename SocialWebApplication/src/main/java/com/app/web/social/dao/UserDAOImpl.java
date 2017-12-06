package com.app.web.social.dao;

import java.util.List;
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
		session.persist(userAccount);		
	}
   
	public void editUser(String username) 
	{
		Session session = this.sessionFactory.getCurrentSession();		
		UserAccount userAccount = getUserByUsername(username);
		session.update(userAccount);
	}

	public String getAuthenticatedUserUsername()
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getName();
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
		Session session = this.sessionFactory.getCurrentSession();
		String hql = "from UserAccount U where U.username =:user_username";
		@SuppressWarnings("unchecked")
		Query<UserAccount> query = session.createQuery(hql).setParameter("user_username",username);
	//	List<UserAccount> result = (List<UserAccount>)query.list(); 
		return (UserAccount) query.list().get(0);
	}
	
	
	public List<UserAccount> getUsersList() 
	{		
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		Query<UserAccount> query=session.createQuery("from UserAccount");
		List<UserAccount> userList = (List<UserAccount>)query.list(); 
		return userList;
	}
	
	
	public void deleteUser(Integer id) 
	{
		Session session = this.sessionFactory.getCurrentSession();
		UserAccount userAccount = (UserAccount) session.load(UserAccount.class, id);
		Profile profile = (Profile) session.load(Profile.class, userAccount.getNickname());
		if(null != userAccount  &&  null != profile)
		{
			session.delete(userAccount);
			session.delete(profile);
		}
	}
 
	
	public void disableUser(Integer id)
	{
		Session session = this.sessionFactory.getCurrentSession();
		UserAccount userAccount = (UserAccount) session.load(UserAccount.class, id);
		userAccount.setEnabled(false);
		session.update(userAccount);
	}
	
	public void enableUser(Integer id)
	{ 
	   Session session = this.sessionFactory.getCurrentSession();
	   UserAccount userAccount = (UserAccount) session.load(UserAccount.class, id);
	   userAccount.setEnabled(true);
	   session.update(userAccount);
	}

	public UserAccount getUserById(Integer id) {
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

