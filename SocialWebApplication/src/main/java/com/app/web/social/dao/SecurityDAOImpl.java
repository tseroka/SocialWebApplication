package com.app.web.social.dao;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.web.social.model.SecurityIssues;
import com.app.web.social.model.UserAccount;
import com.app.web.social.utilities.CodeExpiredException;
//import com.app.web.social.utilities.EmailUtility;

@Repository
@Transactional
public class SecurityDAOImpl extends SuperDAO<Long, SecurityIssues> implements SecurityDAO
{
	@Autowired
	private UserDAO userDAO;
	
//	@Autowired 
//	private EmailUtility mail;
		
	public SecurityIssues getSecurityIssuesAccountByUsername(String username)
	{	 
		 return getSession().createQuery("from SecurityIssues S where S.username=:username",SecurityIssues.class)
		.setParameter("username",username).list().get(0);
	}
	
	public SecurityIssues getSecurityIssuesById(long id)
	{
		return getEntityByPrimaryKey(id);
	}
	
	public SecurityIssues getAuthenticatedSecurityIssues()
	{
		return getSecurityIssuesAccountByUsername(userDAO.getAuthenticatedUserUsername());
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
		
		update(issue);
	}


	
	public String getLockReason(String username)
	{
		return getSecurityIssuesAccountByUsername(username).getLockReason();
	}
	
	public Timestamp getUnlockDateByUsername(String username)
	{
		return getSecurityIssuesAccountByUsername(username).getUnlockDate();
	}
		
    private boolean isEmailAndUsernameMatching(String email, String username)
    {
    	return userDAO.getUserByEmail(email).getUsername().equals(username);
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
		SecurityIssues issue = getSecurityIssuesAccountByUsername(username);
		issue.setActivationCode(generateActivationAndUnlockCode());
		issue.setTimeToExpire();
		update(issue);
	}
	
	
	public void sendAgainEmailWithActivationCode(String email, String username)
	{
		if(isEmailAndUsernameMatching(email,username))
		{
	      resetActivationCode(username);
	      sendEmailWithActivationCode(email, username);
		}
	}
	
	
	public void acceptActivationCodeAndEnableAccount(String code) throws CodeExpiredException
	{		 
			Query<SecurityIssues> query = getSession().createQuery("from SecurityIssues S WHERE S.activationCode=:code",SecurityIssues.class)
			.setParameter("code",code);
			
			SecurityIssues issue = query.list().get(0);
			if(issue.codeNotExpired())
			{
			issue.setActivationCode(null);
			issue.setCodeExpirationDate(null);
			
			UserAccount userAccount = userDAO.getUserAccount((issue.getUsername()));
			userAccount.setEnabled(true);
			update(issue);
			}
			else 
			{
				throw new CodeExpiredException("Code expired");
			}
			
	}
	
	
//-----------------------------------L O G I N    A T T E M P T S    L I M I T-----------------------------------------------
	
	
	public void increaseFailedLoginAttemptsNumberAndLockIfEqualsFive(String username)
	{
		 SecurityIssues issue = getSecurityIssuesAccountByUsername(username);
				
		 issue.setNumberOfLoginFails((byte)(issue.getNumberOfLoginFails()+1));
		 
		 if(issue.getNumberOfLoginFails()==5) 
	     {
			 UserAccount userAccount = userDAO.getUserAccount(username);
			 userAccount.setNotLocked(false); 
			 issue.setLockReason(MAX_ATTEMPTS_REASON);
		 }
		 update(issue);
	}
	
	
	
	
	public void sendEmailWithUnlockCodeAfterSecurityLockup(String email, String username)
	{
		resetUnlockCode(username);
		String unlockCode = getSecurityIssuesAccountByUsername(username).getUnlockCode();
		if(unlockCode!=null && isEmailAndUsernameMatching(email,username) )
		{
			String emailTextContent = "Your account has been locked due to maximum login attempts. "
			+ "Type this code to proper field to unlock Your account:  "+unlockCode;
			//send email
		//	mail.send(email, "Unlock Your Account", emailTextContent);
			System.out.println(emailTextContent);
		}
		
    }
	
	
	private void resetUnlockCode(String username)
	{
		SecurityIssues issue = getSecurityIssuesAccountByUsername(username);
		
		if(MAX_ATTEMPTS_REASON.equals(issue.getLockReason()))
		{
		   issue.setUnlockCode(generateActivationAndUnlockCode());
		   issue.setTimeToExpire();
		}
		update(issue);
	}
	
	
	public void resetFailedLoginAttemptsAndUnlockAccount(String code) throws CodeExpiredException
	{		
			Query<SecurityIssues> query = getSession().createQuery("from SecurityIssues S WHERE S.unlockCode=:code", SecurityIssues.class )
			.setParameter("code",code);
			
			SecurityIssues issue = query.list().get(0);
			if(issue.getLockReason().equals(MAX_ATTEMPTS_REASON) && issue.codeNotExpired() )
			{
			   if(issue.codeNotExpired())
			   {
			   issue.setUnlockCode(null);
			   issue.setLockReason(null);
			   issue.setNumberOfLoginFails((byte)0);
			   issue.setCodeExpirationDate(null);
			
			   UserAccount userAccount = userDAO.getUserAccount((issue.getUsername()));
			   userAccount.setNotLocked(true);
			   update(issue);
			   }
			   else 
			   {
					throw new CodeExpiredException("Code expired");
			   }
			}
	}
	
	 
//-------------------------R E S E T    P A S S W O R D ---------------------------------------------------------------------

	
	
	public void sendEmailWithPasswordResetCode(String email, String username)
	{
		resetPasswordResetCode(username);
		if(isEmailAndUsernameMatching(email,username))
			{
				String emailTextContent = "Type this code to proper field to reset Your password:  "+getSecurityIssuesAccountByUsername(username).getResetPasswordCode();
				//send email
			//	mail.send(email, "Unlock Your Account", emailTextContent);
				System.out.println(emailTextContent);
			}
    }
	
	
	private void resetPasswordResetCode(String username)
	{
		SecurityIssues issue = getSecurityIssuesAccountByUsername(username);
		issue.setResetPasswordCode(generateResetPasswordCode());
		issue.setTimeToExpire();
		update(issue);
	}
	
	
	
	
	public void resetPassword(String password, String code) throws CodeExpiredException
	{
		SecurityIssues issue = getSession().createQuery("from SecurityIssues S WHERE S.resetPasswordCode=:code", SecurityIssues.class)
		.setParameter("code",code).list().get(0);
		
		if(issue.codeNotExpired())
		{
		issue.setResetPasswordCode(null);
		issue.setCodeExpirationDate(null);
		
		UserAccount userAccount = userDAO.getUserAccount((issue.getUsername()));
		userAccount.setPassword(password);
		update(issue);
		}
		else 
		{
			throw new CodeExpiredException("Code expired");
		}
	}
}
