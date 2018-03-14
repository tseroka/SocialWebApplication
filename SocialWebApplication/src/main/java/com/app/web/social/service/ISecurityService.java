package com.app.web.social.service;

import java.sql.Timestamp;
import java.util.Random;

import com.app.web.social.model.SecurityIssues;

public interface ISecurityService {

public static final String MAX_ATTEMPTS_REASON = "attempts";
	
	public static final char[] ALL_CHARACTERS = {'#','$','!','@','%','^','*','-','0','1','2','3','4','5','6','7','8','9'		
    ,'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','R','S','T','U','W','V','X','Y','Z'
    ,'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','r','s','t','u','w','v','x','y','z'} ;
	
	
	
	public SecurityIssues getSecurityIssuesAccountByUsername(String username);
	
	public SecurityIssues getAuthenticatedSecurityIssues();
	
	public void saveSecurityIssuesAccount(SecurityIssues issue);
	
	public void loginSuccess(String username);
	
	
//-------------------------- R A N D O M    S E C U R I T Y    C O D E S ------------------------------------	
	
	public default String generateActivationAndUnlockCode() 
	{
		return generateRandomCode(20);
	}
	
	public default String generateResetPasswordCode()
	{
		return generateRandomCode(30);
	}
	
	public default String generateRandomCode(int length)
	{
		StringBuilder builder = new StringBuilder();
		Random rand = new Random();
		
		for(int i=0; i<length; ++i)
		{
			builder.append(ALL_CHARACTERS[rand.nextInt(ALL_CHARACTERS.length)]);
		}
		
		return builder.toString();
	}
	
//---------------------------------------------------------------------------------------------------------------	
	
	
	
	public String getLockReason(String username);
	
	public Timestamp getUnlockDateByUsername(String username);
	
	 
	
	public void sendEmailWithActivationCode(String email);
	
	public void sendAgainEmailWithActivationCode(String email);
	
	public boolean acceptActivationCodeAndEnableAccount(String code);

	
	
	public void increaseFailedLoginAttemptsNumberAndLockIfEqualsFive(String username);
	
	public void sendEmailWithUnlockCodeAfterSecurityLockup(String email);
	
	public boolean resetFailedLoginAttemptsAndUnlockAccount(String code);
	
	public void accountSelfUnlockAfterLockTimeout(SecurityIssues issue);
	
	
	public void sendEmailWithPasswordResetCode(String email);
	
	public boolean resetPassword(String password, String code);
}

