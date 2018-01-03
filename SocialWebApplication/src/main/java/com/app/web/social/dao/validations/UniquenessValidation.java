package com.app.web.social.dao.validations;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.web.social.model.UserAccount;

@Repository
@Transactional
public class UniquenessValidation implements Uniqueness
{   
	@Autowired
	private SessionFactory sessionFactory;

    public boolean isUsernameNotBusy(String username)
    {
  		Session session = this.sessionFactory.openSession();
  		String hql = "from UserAccount U where U.username =:user_username";
  		Query<?> query = session.createQuery(hql).setParameter("user_username",username);	
  		return query.list().isEmpty();
    }
 
    public boolean isNicknameNotBusy(String nickname)
    {
  	    Session session = this.sessionFactory.openSession();
  		String hql = "from Profile P where P.nickname =:profileNickname";
  		Query<?> query = session.createQuery(hql).setParameter("profileNickname",nickname);
  		return query.list().isEmpty();
    }
    
    public boolean isEmailNotBusy(String email)
    {
    	  Session session = this.sessionFactory.openSession();
    	  String hql = "from UserAccount U where U.email =:userEmail";
    	  Query<?> query = session.createQuery(hql).setParameter("userEmail",email);
    	  return query.list().isEmpty();
    }

    public boolean isPasswordUsernameEmailNicknameAreNotTheSame(UserAccount userAccount)
    {   
        String password = userAccount.getPassword();    String username = userAccount.getUsername();
        String email = userAccount.getEmail().split("@")[0];     String nickname = userAccount.getNickname();
    	return 
    	        (
    			!(username.equals(password)) && 
    			!(email.equals(password)) &&
    			!(nickname.equals(password))  &&
    			!(username.equals(nickname)) &&
    			!(username.equals(email)) &&
    			!(nickname.equals(email))
    			);
    }
    
    
}