package com.app.web.social.dao.validations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.web.social.model.UserAccount;
import com.app.web.social.service.ProfileService;
import com.app.web.social.service.UserService;

@Repository
@Transactional
public class ValidationsDAOImpl implements ValidationsDAO
{   
	@Autowired
	UserService userService;
	
	@Autowired
	ProfileService profileService;
	
	@Autowired
	SessionFactory sessionFactory;
	
    public boolean validateRegistration(UserAccount userAccount)
    {
    	return 
        ( 
    		   validateUsername( userAccount.getUsername() ) &&
    		   validateNickname( userAccount.getNickname() ) &&
    		   validateEmail( userAccount.getEmail() ) &&
    		   validatePassword( userAccount.getPassword() ) &&
    		   isPasswordUsernameEmailNicknameAreNotTheSame(userAccount)
    		   
        );
    }
    
 // ---------------------------------------REGULAR EXPRESSIONS--------------------------------------------------------
    
    // ONLY LETTERS, NUMBERS, NO WHITESPACE, 6-25 LENGTH
    private final static Pattern VALID_USERNAME_REGEX = Pattern.compile("^[a-zA-Z0-9]{6,25}$");
    
 // // AT LEAST ONE UPPERCASE, LOWERCASE, DIGIT, SPECIAL CHARACTER FROM [@#$%], 8-40 LENGTH
    private final static Pattern VALID_PASSWORD_REGEX = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
    
    //ONLY LETTERS AND WHITESPACES, 3-25 LENGTH
    private final static Pattern VALID_NICKNAME_REGEX = Pattern.compile("^[a-zA-Z\\\\s]{3,25}$");
    
    //STANDARD EMAIL VALIDATION
    private final static Pattern VALID_EMAIL_REGEX 
  =Pattern.compile("^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$");
    
   // ONLY LETTERS AND WHITESPACES, 2-40 LENGTH
    private final static Pattern VALID_COUNTRY_REGEX = Pattern.compile("[/^[a-zA-Z\\s]*$/]{2,40}");
    
    
 // ---------------------------------------USERNAME--------------------------------------------------------
    private boolean validateUsername(String username)
    {   
    	return ( isUsernameMatching(username) && isUsernameNotBusy(username)  );
    }
    
    private boolean isUsernameMatching(String username)
    {
    	Matcher matcher = VALID_USERNAME_REGEX.matcher(username);
    	return matcher.matches();
    }
    
    private boolean isUsernameNotBusy(String username)
    {
  		Session session = this.sessionFactory.openSession();
  		String hql = "from UserAccount U where U.username =:user_username";
  		Query query = session.createQuery(hql).setParameter("user_username",username);	
  		
  		return query.list().isEmpty();
    }
    
   
    
 // ---------------------------------------PASSWORD--------------------------------------------------------
    private boolean validatePassword(String password)
    {
    	Matcher matcher = VALID_PASSWORD_REGEX.matcher(password);
    	return matcher.matches(); 
    }
    
     
    private boolean isPasswordUsernameEmailNicknameAreNotTheSame(UserAccount userAccount)
    {   
        String password = userAccount.getPassword();
        String username = userAccount.getUsername();
        String email = userAccount.getEmail().split("@")[0];
        String nickname = userAccount.getNickname();
    	return (
    			!(password.equals(username)) && 
    			!(password.equals(email)) &&
    			!(password.equals(nickname))  &&
    			!(username.equals(nickname)) &&
    			!(username.equals(email)) &&
    			!(nickname.equals(email))
    			);
    }
    
    // ---------------------------------------NICKNAME------------------------------------------------------
    private boolean validateNickname(String nickname)
    {
    	return ( isNicknameMatching(nickname) && isNicknameNotBusy(nickname)  ) ;
    }
    
    private boolean isNicknameMatching(String nickname)
    {
    	
    	Matcher matcher = VALID_NICKNAME_REGEX.matcher(nickname);
    	return ( matcher.matches()  ) ;
    }
    
    private boolean isNicknameNotBusy(String nickname)
    {
  	  Session session = this.sessionFactory.openSession();
  		String hql = "from Profile P where P.nickname =:profileNickname";
  		Query query = session.createQuery(hql).setParameter("profileNickname",nickname);
  		return query.list().isEmpty();
    }
    
    // ---------------------------------------EMAIL--------------------------------------------------------
    private boolean validateEmail(String email)
    { 
    	return ( isEmailMatching(email) && isEmailNotBusy(email) );
    }
    
    private boolean isEmailMatching(String email)
    {
    	Matcher matcher = VALID_EMAIL_REGEX.matcher(email);
    	return matcher.matches();
    }
    
    private boolean isEmailNotBusy(String email)
    {
    	  Session session = this.sessionFactory.openSession();
    	  String hql = "from UserAccount U where U.email =:userEmail";
    	  Query query = session.createQuery(hql).setParameter("userEmail",email);
    	  return query.list().isEmpty();
    }
    
 // ---------------------------------------COUNTRY--------------------------------------------------------
    private boolean validateCountry(String country)
    {
    	Matcher matcher = VALID_COUNTRY_REGEX.matcher(country);
    	return (matcher.matches() || country.equals("") );
    }
    
}