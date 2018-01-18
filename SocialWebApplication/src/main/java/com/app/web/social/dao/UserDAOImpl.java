package com.app.web.social.dao;

import java.util.List;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.app.web.social.model.Profile;
import com.app.web.social.model.Friends;
import com.app.web.social.model.UserAccount;
import com.app.web.social.model.SecurityIssues;
import com.app.web.social.service.SecurityService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Repository
@Transactional
public class UserDAOImpl extends SuperDAO<Long, UserAccount> implements UserDAO 
{	 
	@Autowired
	private PasswordEncoder passwordEncoder;
	 
	@Autowired
	private SecurityService security;
	
	public void registerUser(UserAccount userAccount) throws UnknownHostException 
	{  
		Session session = getSession();
		
		List<String> empty = new ArrayList<String>();
		
		String ip = InetAddress.getLocalHost().toString();
		HashSet<String> ipSet = new HashSet<String>(); ipSet.add(ip);
				
		session.persist( new Profile(userAccount.getNickname(), "", empty, "", false, false));
		
		session.persist( new Friends(userAccount.getNickname(), empty, empty , empty));
		
		userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
		userAccount.setCreationDate(new Timestamp(System.currentTimeMillis()));
		
		session.persist(userAccount);		
		
		session.persist( new SecurityIssues(userAccount.getUsername(), userAccount, security.generateActivationAndUnlockCode(), null, null, new Timestamp(System.currentTimeMillis()+300000L), ip , ipSet,new Timestamp(System.currentTimeMillis()),(byte) 0, null, null));
		
		security.sendEmailWithActivationCode(userAccount.getEmail(), userAccount.getUsername());
	}
   
	public void editUser(UserAccount userAccount, SecurityIssues issue) 
	{
		userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));		
		update(userAccount);
		
	}
	
	public void editUser(UserAccount userAccount) 
	{	
		update(userAccount);	
	}

	
	public UserAccount getUserAccount(String username) 
	{
		UserAccount userAccount = new UserAccount();
		Query<UserAccount> query = getSession()
		.createQuery("from UserAccount U where U.username =:user_username",UserAccount.class).setParameter("user_username",username);
	    try 
	    {
	    	userAccount = query.list().get(0);
	    }
	    catch(IndexOutOfBoundsException ex){userAccount=null;}
		return userAccount;
	}
	
	public UserAccount getAuthenticatedUserAccount()
	{
		return getUserAccount(getAuthenticatedUserUsername());
	}
	
	public void deleteUser(long id) 
	{
		Session session = getSession();
		
		UserAccount userAccount = loadEntityByPrimaryKey(id);
		String nickname = userAccount.getNickname();
		session.remove(userAccount);
		
		Friends friends = (Friends) session.load(Friends.class, nickname);
		session.remove(friends);
		
		Profile profile = (Profile) session.load(Profile.class, nickname);
		session.remove(profile);
		
		//TODO delete messages
		
	}
 

	public UserAccount getUserById(long id) 
	{
		return loadEntityByPrimaryKey(id);
	}

	public UserAccount getUserByNickname(String nickname) 
	{
	   return getSession().createQuery("from UserAccount U where U.nickname =:nickname",UserAccount.class).setParameter("nickname",nickname).list().get(0);
	}
	
	public UserAccount getUserByEmail(String email) 
	{
	   return getSession().createQuery("from UserAccount U where U.email =:email",UserAccount.class).setParameter("email",email).list().get(0);
	}
	

	public void clearSession()
	{
		Session session = getSession();
    	session.clear(); 
	}
	
}

