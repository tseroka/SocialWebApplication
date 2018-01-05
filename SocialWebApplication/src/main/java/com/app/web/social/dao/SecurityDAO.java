package com.app.web.social.dao;

import java.net.UnknownHostException;

import com.app.web.social.model.SecurityIssues;

public interface SecurityDAO 
{
	public static final String MAX_ATTEMPTS_REASON = "attempts";
	
	
	
	public SecurityIssues getSecurityIssuesAccountByUsername(String username);
			
	public void loginSuccess(String username) throws UnknownHostException;
	
	public int generateActivationAndUnlockCode();
	
	public Long generateResetPasswordCode();
	
	public String getLockReason(String username);
	
	
	public void sendEmailWithActivationCode(String username);
	
	
	public void sendAgainEmailWithActivationCode(String username);
	
	
	public void acceptActivationCodeAndEnableAccount(String username);
	
	
	public void increaseFailedLoginAttemptsNumberAndLockIfEqualsFive(String username);
	
	
	public void sendEmailWithUnlockCodeAfterSecurityLockup(String email, String username);
	
	
	public void sendAgainEmailWithUnlockCodeAfterSecurityLockup(String email, String username);
	
	
	public void resetFailedLoginAttemptsAndUnlockAccount(String username);
	

}
