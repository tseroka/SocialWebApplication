package com.app.web.social.service;

import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

import com.app.web.social.dao.SecurityDAO;
import com.app.web.social.model.SecurityIssues;

@Service
public class SecurityService 
{
   
	@Autowired
	private SecurityDAO securityDAO;
	
	
	public SecurityIssues getSecurityIssuesAccountByUsername(String username)
	{
		return this.securityDAO.getSecurityIssuesAccountByUsername(username);
	}
	
	
	
	public void loginSuccess(String username) throws UnknownHostException
	{
		this.securityDAO.loginSuccess(username);
	}
	
	
	
	public String generateActivationAndUnlockCode()
	{
		return this.securityDAO.generateActivationAndUnlockCode();
	}
	
	public String generateResetPasswordCode()
	{
		return this.securityDAO.generateResetPasswordCode();
	}

	
	public String getLockReason(String username)
	{
		return this.securityDAO.getLockReason(username);
	}
	
	public Timestamp getUnlockDateByUsername(String username)
	{
		return this.securityDAO.getUnlockDateByUsername(username);
	}
	
	
	
	public void sendEmailWithActivationCode(String email, String username)
	{
	   this.securityDAO.sendEmailWithActivationCode(email, username);	
	}
	
	public void sendAgainEmailWithActivationCode(String email, String username)
	{
		this.securityDAO.sendAgainEmailWithActivationCode(email, username);
	}
	
	public void acceptActivationCodeAndEnableAccount(String username)
	{
		this.securityDAO.acceptActivationCodeAndEnableAccount(username);
	}
	
	
	
	
	
	public void increaseFailedLoginAttemptsNumberAndLockIfEqualsFive(String username)
	{
		this.securityDAO.increaseFailedLoginAttemptsNumberAndLockIfEqualsFive(username);
	}
	
    public void sendEmailWithUnlockCodeAfterSecurityLockup(String email, String username) 
    {
       this.securityDAO.sendEmailWithUnlockCodeAfterSecurityLockup(email, username);	
    }
	
	public void sendAgainEmailWithUnlockCodeAfterSecurityLockup(String email, String username)
	{
		this.securityDAO.sendAgainEmailWithUnlockCodeAfterSecurityLockup(email, username);
	}
	
	public void resetFailedLoginAttemptsAndUnlockAccount(String username)
	{
	    this.securityDAO.resetFailedLoginAttemptsAndUnlockAccount(username);
	}
	
	
	
	
	 
	
	public void sendEmailWithPasswordResetCode(String email, String username)
	{
		this.securityDAO.sendEmailWithPasswordResetCode(email, username);
	}
	
	public void sendAgainEmailWithPasswordResetCode(String email, String username)
	{
		this.securityDAO.sendAgainEmailWithPasswordResetCode(email, username);
	}
	
	public void resetPassword(String password, String code)
	{
		this.securityDAO.resetPassword(password, code);
	}
	
}
