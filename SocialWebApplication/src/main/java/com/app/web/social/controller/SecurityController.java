package com.app.web.social.controller;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.web.social.dao.validations.InputCorrectness;
import com.app.web.social.model.temp.SecurityIssuesFormHandler;
import com.app.web.social.service.SecurityService;

@Controller
@RequestMapping(value = "/exceptions/")
public class SecurityController implements InputCorrectness
{

	@Autowired
	private SecurityService securityService;

	
	@RequestMapping(value = "Reset password", method = RequestMethod.GET)
	public ModelAndView getSendResetPasswordCodeForm()
	{		
		return new ModelAndView("account/exceptions/send-reset-password-code","sendResetPasswordCode", new SecurityIssuesFormHandler());		
	}
	
	 @RequestMapping(value = "sendResetPasswordCodeProcessing", method = RequestMethod.POST)
	 public ModelAndView sendPasswordResetCodeProcessiong(@ModelAttribute("sendResetPasswordCode") SecurityIssuesFormHandler  sendResetPasswordCode, RedirectAttributes attributes )
	   {
			String email = sendResetPasswordCode.getEmail(); String username = sendResetPasswordCode.getUsername();
			
			if(Pattern.matches(EMAIL_VALIDATION_REGEX, email) && Pattern.matches(USERNAME_VALIDATION_REGEX, username) )
			{
			  try 
			  {  
		      securityService.sendEmailWithPasswordResetCode(email, username);
		      attributes.addFlashAttribute("message","If email and username are valid, code to reset password will be send on this email address");
		      return new ModelAndView("redirect:resetPassword/set");
			  }
			  catch(IndexOutOfBoundsException ex)
			  {
			    	  System.out.println("Unexisting account: "+username);
			  }
		   }	   
		   return new ModelAndView("redirect:Reset password");
	   }
	 
	 
	
	
	@RequestMapping(value = "resetPassword/set", method = RequestMethod.GET)
	public ModelAndView getResetPasswordForm()
	{
		return new ModelAndView("account/exceptions/reset-password", "resetPassword", new SecurityIssuesFormHandler() );
	}
	
	
	 @RequestMapping(value = "resetPasswordProcessing", method = RequestMethod.POST)
	 public ModelAndView passwordResetProcessiong(@ModelAttribute("resetPassword") SecurityIssuesFormHandler  resetPassword )
	   {
           String password = resetPassword.getNewPassword();
		   try
		   {   if( Pattern.matches(PASSWORD_VALIDATION_REGEX, password ) && password.equals(resetPassword.getNewPasswordRepeat()))
		       {		       
			     securityService.resetPassword(password, resetPassword.getCode());
		       }
		   }
		   catch(IndexOutOfBoundsException ex)
		   {
			   return new ModelAndView("account/exceptions/reset-password","message","Wrong reset password code");
		   }
		
		   return new ModelAndView("login","ok","Password changed. You can now log in");
		   
	   }
	
	
	 
	 
//----------------------------------------- U N L O C K --------------------------------------------------
	
	@RequestMapping(value = "Unlock account", method=RequestMethod.GET)
	public ModelAndView getSendUnlockCodeForm()
	{
		return new ModelAndView("account/exceptions/send-unlock-code","sendUnlockCode", new SecurityIssuesFormHandler());
	}

    @RequestMapping(value = "sendUnlockCodeProcessing", method=RequestMethod.POST)
	public ModelAndView sendUnlockCodeProcessiong(@ModelAttribute("sendUnlockCode") SecurityIssuesFormHandler  sendUnlockCode, RedirectAttributes attributes )
	{
		String email = sendUnlockCode.getEmail(); String username = sendUnlockCode.getUsername();
		
		if(Pattern.matches(EMAIL_VALIDATION_REGEX, email) && Pattern.matches(USERNAME_VALIDATION_REGEX, username) )
		{
		   try
		   {
		   securityService.sendEmailWithUnlockCodeAfterSecurityLockup(email, username);
		   
		   attributes.addFlashAttribute("message","If email and username are valid, code to unlock account will be send on this email address");
		   return new ModelAndView("redirect:unlock") ;	 
		   }
		   catch(IndexOutOfBoundsException ex)
		   {
			   System.out.println("Unexisting account: "+username);
		   }
		}
		
		  return new ModelAndView("redirect:Unlock account");
		
	}
	 
	
	
	@RequestMapping(value="unlock", method=RequestMethod.GET)
	public ModelAndView getAccountUnlockForm()
	{
		return new ModelAndView("account/exceptions/unlock", "unlockAccount", new SecurityIssuesFormHandler() );
	}
	
	
	 @RequestMapping(value="unlockProcessing", method=RequestMethod.POST)
	 public ModelAndView accountUnlockProcessiong(@ModelAttribute("unlockAccount") SecurityIssuesFormHandler  unlockAccount )
	   {
           
		   try
		   {   	       		       
			     securityService.resetFailedLoginAttemptsAndUnlockAccount(unlockAccount.getCode());	      
		   }
		   catch(IndexOutOfBoundsException ex)
		   {
			   return new ModelAndView("account/exceptions/unlock","message","Wrong unlock code");
		   }
		
		   return new ModelAndView("login","ok","Account unlocked. You can now log in");
		   
	   }
	
	
}
