package com.app.web.social.dao;

import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.Random;

import com.app.web.social.model.SecurityIssues;

public interface SecurityDAO 
{
	public static final String MAX_ATTEMPTS_REASON = "attempts";
	
	public static final char[] ALL_CHARACTERS = {'#','$','!','@','%','^','*','?','-','+','0','1','2','3','4','5','6','7','8','9'		
    ,'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','R','S','T','U','W','V','X','Y','Z'} ;
	
	
	
	public SecurityIssues getSecurityIssuesAccountByUsername(String username);
			
	
	
	public void loginSuccess(String username) throws UnknownHostException;
	
	
//-------------------------- R A N D O M    S E C U R I T Y    C O D E S ------------------------------------	
	
	public default String generateActivationAndUnlockCode() 
	{
		return generateRandomCode(20);
	}
	
	public default String generateResetPasswordCode()
	{
		return generateRandomCode(30);
	}
	
	private String generateRandomCode(int length)
	{
		StringBuilder builder = new StringBuilder();
		Random rand = new Random();
		
		for(int i=0; i<length; i++)
		{
			builder.append(ALL_CHARACTERS[rand.nextInt(ALL_CHARACTERS.length)]);
		}
		
		return builder.toString();
	}
	
//---------------------------------------------------------------------------------------------------------------	
	
	
	
	public String getLockReason(String username);
	
	public Timestamp getUnlockDateByUsername(String username);
	
	 
	
	public void sendEmailWithActivationCode(String email, String username);
	
	public void sendAgainEmailWithActivationCode(String email, String username);
	
	public void acceptActivationCodeAndEnableAccount(String username);
	
	public void increaseFailedLoginAttemptsNumberAndLockIfEqualsFive(String username);
	
	public void sendEmailWithUnlockCodeAfterSecurityLockup(String email, String username);
	
	public void sendAgainEmailWithUnlockCodeAfterSecurityLockup(String email, String username);
	
	public void resetFailedLoginAttemptsAndUnlockAccount(String username);
	
	
	
	public void sendEmailWithPasswordResetCode(String email, String username);
	
	public void sendAgainEmailWithPasswordResetCode(String email, String username);
	
	public void resetPassword(String password, String code);
	
	

}
