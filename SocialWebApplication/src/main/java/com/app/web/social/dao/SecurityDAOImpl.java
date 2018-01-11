package com.app.web.social.dao;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.web.social.model.SecurityIssues;
import com.app.web.social.model.UserAccount;
//import com.app.web.social.utilities.EmailUtility;

@Repository
@Transactional
public class SecurityDAOImpl implements SecurityDAO
{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private UserDAO userDAO;
	
//	@Autowired 
//	private EmailUtility mail;
	
	Session session = null;
	
	public SecurityIssues getSecurityIssuesAccountByUsername(String username)
	{
		 session = this.sessionFactory.getCurrentSession();
		 
		 return session.createQuery("from SecurityIssues S where S.username=:username",SecurityIssues.class)
		.setParameter("username",username).list().get(0);
	}
	 
	

//------------------ON LOGIN SUCCESS ----------------------------------------------------------------
	
	public void loginSuccess(String username) throws UnknownHostException
	{

		SecurityIssues issue = getSecurityIssuesAccountByUsername(username);
		
		issue.setNumberOfLoginFails((byte)0);
		
		issue.setLastLoginDate(new Timestamp(System.currentTimeMillis()));
		
		String ip = InetAddress.getLocalHost().toString();
		
		issue.setLastIPAddress(ip);
		issue.addIPAddress(ip);
		
		this.sessionFactory.getCurrentSession().update(issue);
		
		System.out.println(ip);
	}

//----------------ACCOUNT LOCK REASON------------------------------------------------------------------
	
	public String getLockReason(String username)
	{
		return getSecurityIssuesAccountByUsername(username).getLockReason();
	}
	
	public Timestamp getUnlockDateByUsername(String username)
	{
		return getSecurityIssuesAccountByUsername(username).getUnlockDate();
	}
		
//-------------------------------------------------A C T I V A T I O N ---------------------------------------------------	
	
	public void sendEmailWithActivationCode(String email, String username)
	{
		SecurityIssues issues = getSecurityIssuesAccountByUsername(username);
		String emailTextContent = "Activate Your account by typing this code into activation window:  "+issues.getActivationCode();
		
		//send email
	//	mail.send(email, "Activate Your Account", emailTextContent);
		System.out.println(emailTextContent);
	}
	
	
	private void resetActivationCode(String username)
	{
		session = this.sessionFactory.getCurrentSession();
		SecurityIssues issues = getSecurityIssuesAccountByUsername(username);
		issues.setActivationCode(generateActivationAndUnlockCode());
		session.update(issues);
	}
	
	
	public void sendAgainEmailWithActivationCode(String email, String username)
	{
	    resetActivationCode(username);
	    sendEmailWithActivationCode(email, username);
	}
	
	
	public void acceptActivationCodeAndEnableAccount(String code)
	{		 
     	    session = this.sessionFactory.getCurrentSession();
			Query<SecurityIssues> query = session.createQuery("from SecurityIssues S WHERE S.activationCode=:code",SecurityIssues.class)
			.setParameter("code",code);
			
			SecurityIssues issue = (SecurityIssues) query.list().get(0);
			issue.setActivationCode(null);
			session.update(issue);
			
			UserAccount userAccount = userDAO.getUserAccount((issue.getUsername()));
			userAccount.setEnabled(true);
			session.update(userAccount);
	}
	
	
//-----------------------------------L O G I N    A T T E M P T S    L I M I T-----------------------------------------------
	
	
	public void increaseFailedLoginAttemptsNumberAndLockIfEqualsFive(String username)
	{
		 SecurityIssues issues = getSecurityIssuesAccountByUsername(username);
				
		 issues.setNumberOfLoginFails((byte)(issues.getNumberOfLoginFails()+1));
		 
		 if(issues.getNumberOfLoginFails()==5) 
	     {
			 userDAO.getUserAccount(username).setNotLocked(false); 
			 issues.setLockReason(MAX_ATTEMPTS_REASON);
			 issues.setUnlockCode(generateActivationAndUnlockCode());
		 }
		 
		 this.session.update(issues);
	}
	
	
	
	
	public void sendEmailWithUnlockCodeAfterSecurityLockup(String email, String username)
	{
		resetUnlockCode(username);
		SecurityIssues issue = getSecurityIssuesAccountByUsername(username);
		String lockReason = issue.getLockReason();
		if(lockReason!=null && lockReason.equals(MAX_ATTEMPTS_REASON))
		{
			String emailTextContent = "Your account has been locked due to maximum login attempts. "
			+ "Type this code to proper field to unlock Your account:  "+issue.getUnlockCode();
			//send email
		//	mail.send(email, "Unlock Your Account", emailTextContent);
			System.out.println(emailTextContent);
		}
		
    }
	
	
	private void resetUnlockCode(String username)
	{
		session = this.sessionFactory.getCurrentSession();
		SecurityIssues issues = getSecurityIssuesAccountByUsername(username);
		issues.setUnlockCode(generateActivationAndUnlockCode());
		session.update(issues);
	}
	
	public void sendAgainEmailWithUnlockCodeAfterSecurityLockup(String email, String username)
	{
		resetUnlockCode(username);
		sendEmailWithUnlockCodeAfterSecurityLockup(email, username);
	}
	
	
	
	public void resetFailedLoginAttemptsAndUnlockAccount(String code)
	{		
   	        session = this.sessionFactory.getCurrentSession();
			Query<?> query = session.createQuery("from SecurityIssues S WHERE S.unlockCode=:code")
			.setParameter("code",code);
			
			SecurityIssues issue = (SecurityIssues) query.list().get(0);
			if(issue.getLockReason().equals(MAX_ATTEMPTS_REASON))
			{
			issue.setUnlockCode(null);
			issue.setLockReason(null);
			issue.setNumberOfLoginFails((byte)0);
			session.update(issue);
			
			UserAccount userAccount = userDAO.getUserAccount((issue.getUsername()));
			userAccount.setNotLocked(true);
			session.update(userAccount);
			}
	}
	
	 
//-------------------------R E S E T    P A S S W O R D ---------------------------------------------------------------------

	
	
	public void sendEmailWithPasswordResetCode(String email, String username)
	{
		resetPasswordResetCode(username);
		SecurityIssues issue = getSecurityIssuesAccountByUsername(username);
		String code = issue.getResetPasswordCode();

	    String emailTextContent = "Type this code to proper field to reset Your password:  "+code;
			//send email
	  //  mail.send(email, "Reset Your password", emailTextContent);
			System.out.println(emailTextContent);
    }
	
	
	private void resetPasswordResetCode(String username)
	{
		session = this.sessionFactory.getCurrentSession();
		SecurityIssues issues = getSecurityIssuesAccountByUsername(username);
		issues.setResetPasswordCode(generateResetPasswordCode());
		session.update(issues);
	}
	
	public void sendAgainEmailWithPasswordResetCode(String email, String username)
	{
		resetPasswordResetCode(username);
		sendEmailWithUnlockCodeAfterSecurityLockup(email, username);
	}
	
	
	
	public void resetPassword(String password, String code)
	{
		session = this.sessionFactory.getCurrentSession();
		
		SecurityIssues issue = session.createQuery("from SecurityIssues S WHERE S.resetPasswordCode=:code", SecurityIssues.class)
		.setParameter("code",code).list().get(0);
		
		issue.setResetPasswordCode(null);
		session.update(issue);
		
		UserAccount userAccount = userDAO.getUserAccount((issue.getUsername()));
		userAccount.setPassword(password);
		session.update(userAccount);

	}
}
