package com.app.web.social.service;

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
	
	public SecurityIssues getSecurityIssuesAccountByEmail(String email)
	{
		return getSecurityIssuesAccountByUsername(userService.getUserByEmail(email).getUsername());	
	}
	
	
	public SecurityIssues getAuthenticatedSecurityIssues()
	{
		return getSecurityIssuesAccountByUsername(userService.getAuthenticatedUserUsername());
	}
	
	public void saveSecurityIssuesAccount(SecurityIssues issue)
	{
		securityIssuesRepository.save(issue);
	}
	
	public void loginSuccess(String username)
	{
		SecurityIssues issue = getSecurityIssuesAccountByUsername(username);
		issue.setNumberOfLoginFails((byte)0);			
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

	
	public void sendEmailWithActivationCode(String email)
	{
		if(userRepository.existsByEmail(email))
		{
		SecurityIssues issue = getSecurityIssuesAccountByEmail(email);
		String code = issue.getActivationCode();
		String emailTextContent = "Welcome, "+issue.getUsername()+". Activate Your account by typing this code into activation window:  "+code;
		
		EmailMessage message = new EmailMessage(email, "Account activation", emailTextContent);
		
		emailService.sendEmail(message);
		}
	}
	
	private boolean resetActivationCode(String email)
	{
		if(userRepository.existsByEmail(email))
		{
		SecurityIssues issue = getSecurityIssuesAccountByEmail(email);
		issue.setActivationCode(generateActivationAndUnlockCode());
		issue.setTimeToExpire();
		securityIssuesRepository.saveAndFlush(issue);
		return true;
		}
		else return false;
	}
	
	public void sendAgainEmailWithActivationCode(String email)
	{
	      if(resetActivationCode(email))
	      {
	         sendEmailWithActivationCode(email);
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
		
		 if(issue!=null)
		 {
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
		 
	}
	
	private boolean resetUnlockCode(String email)
	{
		if(userRepository.existsByEmail(email))
		{
		  SecurityIssues issue = getSecurityIssuesAccountByEmail(email);
		
		  if(MAX_ATTEMPTS_REASON.equals(issue.getLockReason()))
		  {
		    issue.setUnlockCode(generateActivationAndUnlockCode());
		    issue.setTimeToExpire();
		  }
	        securityIssuesRepository.saveAndFlush(issue);
	        return true;
		}
		else return false;
	}
	
    public void sendEmailWithUnlockCodeAfterSecurityLockup(String email) 
    {
    	if(resetUnlockCode(email))
    	{
    	 SecurityIssues issue = getSecurityIssuesAccountByEmail(email);
		 String unlockCode = issue.getUnlockCode();
		 if(unlockCode!=null)
		 {
			String emailTextContent = issue.getUsername()+", Your account has been locked due to maximum login attempts. "
			+ "Type this code to proper field to unlock Your account:  "+unlockCode;
		
			EmailMessage message = new EmailMessage(email, "Unlock account", emailTextContent);	
			emailService.sendEmail(message);
		 }	
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
	
	
	
	public void accountSelfUnlockAfterLockTimeout(SecurityIssues issue)
	{
		issue.setUnlockDate(null);
		issue.setLockReason(null);
		issue.setNumberOfLoginFails((byte)0);
		
		UserAccount userAccount = userRepository.findByUsername(issue.getUsername());

		securityIssuesRepository.save(issue);
		userAccount.setNotLocked(true);
		userRepository.save(userAccount);
	}
	 
	
	public void sendEmailWithPasswordResetCode(String email)
	{
		if(resetPasswordResetCode(email))
	    {
			    SecurityIssues issue = getSecurityIssuesAccountByEmail(email);
			    
				String emailTextContent = issue.getUsername()+ ", type this code to proper field to reset Your password:  "+issue.getResetPasswordCode();	
				
				EmailMessage message = new EmailMessage(email, "Password reset", emailTextContent);
				
				emailService.sendEmail(message);
		}
	}
	
	private boolean resetPasswordResetCode(String email)
	{
		if(userRepository.existsByEmail(email))
		{
		SecurityIssues issue = getSecurityIssuesAccountByEmail(email);
		issue.setResetPasswordCode(generateResetPasswordCode());
		issue.setTimeToExpire();
		securityIssuesRepository.saveAndFlush(issue);
		return true;
		}
		else return false;
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
				securityIssuesRepository.saveAndFlush(issue);
				userRepository.saveAndFlush(userAccount);
				}
				else 
				{
					return false;
				}
			
	    return isCodeValid;
	}

	
}