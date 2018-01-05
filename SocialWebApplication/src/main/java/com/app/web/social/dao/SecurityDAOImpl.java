package com.app.web.social.dao;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.Random;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.web.social.model.SecurityIssues;
import com.app.web.social.model.UserAccount;

@Repository
@Transactional
public class SecurityDAOImpl implements SecurityDAO
{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private UserDAO userDAO;
	
	Session session = null;
	
	@SuppressWarnings("unchecked")
	public SecurityIssues getSecurityIssuesAccountByUsername(String username)
	{
		 session = this.sessionFactory.getCurrentSession();
		 Query<SecurityIssues> query = this.sessionFactory.getCurrentSession().createQuery("from SecurityIssues S where S.username=:username")
		.setParameter("username",username);
		 return (SecurityIssues) query.list().get(0);
	}
	


	public int generateActivationAndUnlockCode()
	{
		Random random = new Random();
		return random.nextInt(99999999) + 10000000;
	}
	
	public Long generateResetPasswordCode()
	{
		long from = 1000000000000000000L; 
		long to = 8999999999999999999L; 
		Random random = new Random();
		return Long.valueOf( from + ( (long) (random.nextDouble()*(to-from)) ) );
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
		
		System.out.println(ip);
	}

//----------------ACCOUNT LOCK REASON------------------------------------------------------------------
	
	public String getLockReason(String username)
	{
		return getSecurityIssuesAccountByUsername(username).getLockReason();
	}
	
	
	
//-------------------------------------------------A C T I V A T I O N ---------------------------------------------------	
	
	public void sendEmailWithActivationCode(String username)
	{
		SecurityIssues issues = getSecurityIssuesAccountByUsername(username);
		String emailTextContent = "Activate Your account by typing this code into activation window:  "+issues.getActivationCode();
		
		//send email
	}
	
	
	private void resetActivationCode(String username)
	{
		session = this.sessionFactory.getCurrentSession();
		SecurityIssues issues = getSecurityIssuesAccountByUsername(username);
		issues.setActivationCode(generateActivationAndUnlockCode());
		session.update(issues);
	}
	
	
	public void sendAgainEmailWithActivationCode(String username)
	{
	    resetActivationCode(username);
	    sendEmailWithActivationCode(username);
	}
	
	
	public void acceptActivationCodeAndEnableAccount(String username)
	{
		 session = this.sessionFactory.getCurrentSession();
		 UserAccount userAccount = userDAO.getUserAccount(username);
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
		SecurityIssues issue = getSecurityIssuesAccountByUsername(username);
		String lockReason = issue.getLockReason();
		if(lockReason!=null && lockReason.equals(MAX_ATTEMPTS_REASON))
		{
			String emailTextContent = "Your account has been locked due to maximum login attempts. "
			+ "Type this code to proper field to unlock Your account:  "+issue.getUnlockCode();
			//send email
		}
    }
	
	
	
	public void sendAgainEmailWithUnlockCodeAfterSecurityLockup(String email, String username)
	{
		resetUnlockCode(username);
		sendEmailWithUnlockCodeAfterSecurityLockup(email, username);
	}

	
	
	private void resetUnlockCode(String username)
	{
		session = this.sessionFactory.getCurrentSession();
		SecurityIssues issues = getSecurityIssuesAccountByUsername(username);
		issues.setUnlockCode(generateActivationAndUnlockCode());
		session.update(issues);
	}
	
	
	
	public void resetFailedLoginAttemptsAndUnlockAccount(String username)
	{
		userDAO.getUserAccount(username).setNotLocked(true);
		
		SecurityIssues issue = getSecurityIssuesAccountByUsername(username);
		
		issue.setUnlockCode(0);
		issue.setLockReason("Not locked");
		this.sessionFactory.getCurrentSession().update(issue);
	}
	

	
}
