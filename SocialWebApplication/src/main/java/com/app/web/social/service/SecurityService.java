package com.app.web.social.service;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Timestamp;

import com.app.web.social.model.SecurityIssues;
import com.app.web.social.model.UserAccount;
import com.app.web.social.model.temp.EmailMessage;
import com.app.web.social.repository.SecurityIssuesRepository;
import com.app.web.social.repository.UserRepository;
import com.app.web.social.utilities.EmailService;

@Service
@Transactional
public class SecurityService implements ISecurityService 
{
   
	@Autowired
	private SecurityIssuesRepository securityIssuesRepository;
	
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired
	private IUserService userService;
	
	@Autowired 
	private EmailService emailService;
	
	@Autowired
	private PasswordEncoder encoder;
	
	public SecurityIssues getSecurityIssuesAccountByUsername(String username)
	{
		return securityIssuesRepository.findByUsername(username);
	}
	
	//public SecurityIssues getSecurityIssuesById(long id)
	//{
	//	return securityIssuesRepository.findById(id).get();
	//}
	
	public SecurityIssues getAuthenticatedSecurityIssues()
	{
		return getSecurityIssuesAccountByUsername(userService.getAuthenticatedUserUsername());
	}
	
	public void saveSecurityIssuesAccount(SecurityIssues issue)
	{
		securityIssuesRepository.saveAndFlush(issue);
	}
	
	public void loginSuccess(String username) throws UnknownHostException
	{
		SecurityIssues issue = getSecurityIssuesAccountByUsername(username);
		issue.setNumberOfLoginFails((byte)0);		
		issue.setLastLoginDate(new Timestamp(System.currentTimeMillis()));
		
		String ip = InetAddress.getLocalHost().toString();
		
		issue.setLastIPAddress(ip);
		issue.addIPAddress(ip);
		
		securityIssuesRepository.save(issue);
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
    	return userService.getUserByEmail(email).getUsername().equals(username);
    }

	
	
	
	
	
	public void sendEmailWithActivationCode(String email, String username)
	{
		String code = getSecurityIssuesAccountByUsername(username).getActivationCode();
		String emailTextContent = "Welcome, "+username+". Activate Your account by typing this code into activation window:  "+code;
		
		EmailMessage message = new EmailMessage(email, "Account activation", emailTextContent);
		
		emailService.sendEmail(message);
	}
	
	private void resetActivationCode(String username)
	{
		SecurityIssues issue = getSecurityIssuesAccountByUsername(username);
		issue.setActivationCode(generateActivationAndUnlockCode());
		issue.setTimeToExpire();
		securityIssuesRepository.save(issue);
	}
	
	public void sendAgainEmailWithActivationCode(String email, String username)
	{
		if(isEmailAndUsernameMatching(email,username))
		{
	      resetActivationCode(username);
	      sendEmailWithActivationCode(email, username);
		}
	}
	
	public boolean acceptActivationCodeAndEnableAccount(String code)
	{
                boolean isCodeValid = securityIssuesRepository.existsByActivationCode(code);
				if(isCodeValid)					
				{
			      SecurityIssues issue = securityIssuesRepository.findByActivationCode(code);
				  if(issue.codeNotExpired())
				  {
				  issue.setActivationCode(null);
				  issue.setCodeExpirationDate(null);
				
				  UserAccount userAccount = userService.getUserAccount((issue.getUsername()));
				  userAccount.setEnabled(true);
				  securityIssuesRepository.save(issue);
				  userRepository.save(userAccount);
				  }
				    else 
				  {
				  	return false;
				  }
				  
				 }
					 
				return isCodeValid;
			
	}
	
	
	
	
	
	public void increaseFailedLoginAttemptsNumberAndLockIfEqualsFive(String username)
	{
		 SecurityIssues issue = getSecurityIssuesAccountByUsername(username);
		
		 issue.setNumberOfLoginFails((byte)(issue.getNumberOfLoginFails()+1));
		 
		 if(issue.getNumberOfLoginFails()==5) 
	     {
			 UserAccount userAccount = userService.getUserAccount(username);
			 userAccount.setNotLocked(false); 
			 issue.setLockReason(MAX_ATTEMPTS_REASON);
			 userRepository.save(userAccount);
		 }
		 securityIssuesRepository.save(issue);
		 
	}
	
	private void resetUnlockCode(String username)
	{
		SecurityIssues issue = getSecurityIssuesAccountByUsername(username);
		
		if(MAX_ATTEMPTS_REASON.equals(issue.getLockReason()))
		{
		   issue.setUnlockCode(generateActivationAndUnlockCode());
		   issue.setTimeToExpire();
		}
	  securityIssuesRepository.save(issue);
	}
	
    public void sendEmailWithUnlockCodeAfterSecurityLockup(String email, String username) 
    {
    	resetUnlockCode(username);
		String unlockCode = getSecurityIssuesAccountByUsername(username).getUnlockCode();
		if(unlockCode!=null && isEmailAndUsernameMatching(email,username) )
		{
			String emailTextContent = username+", Your account has been locked due to maximum login attempts. "
			+ "Type this code to proper field to unlock Your account:  "+unlockCode;
		
			EmailMessage message = new EmailMessage(email, "Unlock account", emailTextContent);	
			emailService.sendEmail(message);
		}	
    }
	
	public boolean resetFailedLoginAttemptsAndUnlockAccount(String code)
	{
		boolean isCodeValid = securityIssuesRepository.existsByUnlockCode(code);
				
				if(isCodeValid) {
			       SecurityIssues issue = securityIssuesRepository.findByUnlockCode(code);
				if(issue.getLockReason().equals(MAX_ATTEMPTS_REASON) && issue.codeNotExpired() )
				{
				   if(issue.codeNotExpired())
				   {
				   issue.setUnlockCode(null);
				   issue.setLockReason(null);
				   issue.setNumberOfLoginFails((byte)0);
				   issue.setCodeExpirationDate(null);
				
				   UserAccount userAccount = userService.getUserAccount((issue.getUsername()));
				   userAccount.setNotLocked(true);
				   securityIssuesRepository.save(issue);
				   userRepository.save(userAccount);
				   }
				   else 
				   {
						return false; 
				   }
				}
			}
		 return isCodeValid;
	}
	
	
	
	
	 
	
	public void sendEmailWithPasswordResetCode(String email, String username)
	{
		resetPasswordResetCode(username);
		if(isEmailAndUsernameMatching(email,username))
			{
				String emailTextContent = username+ ", type this code to proper field to reset Your password:  "+getSecurityIssuesAccountByUsername(username).getResetPasswordCode();	
				
				EmailMessage message = new EmailMessage(email, "Password reset", emailTextContent);
				
				emailService.sendEmail(message);
			}
	}
	
	private void resetPasswordResetCode(String username)
	{
		SecurityIssues issue = getSecurityIssuesAccountByUsername(username);
		issue.setResetPasswordCode(generateResetPasswordCode());
		issue.setTimeToExpire();
		securityIssuesRepository.save(issue);
	}
	
	public boolean resetPassword(String password, String code)
	{
		boolean isCodeValid = securityIssuesRepository.existsByResetPasswordCode(code);
				
				if(isCodeValid)
				{
			    SecurityIssues issue = securityIssuesRepository.findByResetPasswordCode(code);
				issue.setResetPasswordCode(null);
				issue.setCodeExpirationDate(null);
				
				UserAccount userAccount = userService.getUserAccount((issue.getUsername()));
				userAccount.setPassword(encoder.encode(password));
				securityIssuesRepository.save(issue);
				userRepository.save(userAccount);
				}
				else 
				{
					return false;
				}
			
	    return isCodeValid;
	}
	
}
