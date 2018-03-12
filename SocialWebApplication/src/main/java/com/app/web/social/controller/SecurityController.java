package com.app.web.social.controller;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.web.social.service.InputCorrectness;
import com.app.web.social.model.temp.SecurityIssuesFormHandler;
import com.app.web.social.service.ISecurityService;

@Controller
@RequestMapping(value = "/exceptions/")
public class SecurityController implements InputCorrectness
{

	@Autowired
	private ISecurityService securityService;

	
	@GetMapping("Reset password")
	public ModelAndView getSendResetPasswordCodeForm()
	{		
		return new ModelAndView("account/exceptions/send-reset-password-code","sendResetPasswordCode", new SecurityIssuesFormHandler());		
	}
	
	 @PostMapping("sendResetPasswordCodeProcessing")
	 public ModelAndView sendPasswordResetCodeProcessiong(@ModelAttribute("sendResetPasswordCode") SecurityIssuesFormHandler  sendResetPasswordCode, RedirectAttributes attributes )
	 {
			String email = sendResetPasswordCode.getEmail(); 
			
			if(Pattern.matches(EMAIL_VALIDATION_REGEX, email))
			{
		         securityService.sendEmailWithPasswordResetCode(email);
		         attributes.addFlashAttribute("message","If submited email exists, 5 minutes valid code to reset password will be send on this email address");
		         return new ModelAndView("redirect:resetPassword/set");
			  
		    }	   
		   return new ModelAndView("redirect:Reset password");
	 }
	 
	 
	
	
	@GetMapping("resetPassword/set")
	public ModelAndView getResetPasswordForm()
	{
		return new ModelAndView("account/exceptions/reset-password", "resetPassword", new SecurityIssuesFormHandler() );
	}
	
	
	 @PostMapping("resetPasswordProcessing")
	 public ModelAndView passwordResetProcessiong(@ModelAttribute("resetPassword") SecurityIssuesFormHandler  resetPassword )
	   {
		   String password = resetPassword.getNewPassword();
		   if(
				   Pattern.matches(PASSWORD_VALIDATION_REGEX, password ) && password.equals(resetPassword.getNewPasswordRepeat()) &&
				   securityService.resetPassword(password, resetPassword.getCode()) ) 
		   {		 
		  	    return new ModelAndView("login","ok","Password changed. You can now log in"); 		     
		   }
		   return new ModelAndView("account/exceptions/reset-password","message","Wrong or expired reset password code");
		   
	   }
	
	
	 
	 
//----------------------------------------- U N L O C K --------------------------------------------------
	
	@GetMapping("Unlock account")
	public ModelAndView getSendUnlockCodeForm()
	{
		return new ModelAndView("account/exceptions/send-unlock-code","sendUnlockCode", new SecurityIssuesFormHandler());
	}

    @PostMapping("sendUnlockCodeProcessing")
	public ModelAndView sendUnlockCodeProcessiong(@ModelAttribute("sendUnlockCode") SecurityIssuesFormHandler  sendUnlockCode, RedirectAttributes attributes )
	{
		String email = sendUnlockCode.getEmail(); 
		if(Pattern.matches(EMAIL_VALIDATION_REGEX, email)) 
		{
		   securityService.sendEmailWithUnlockCodeAfterSecurityLockup(email);
		   
		   attributes.addFlashAttribute("message","If submited email exists, 5 minutes valid code to unlock account will be send on this email address");
		   return new ModelAndView("redirect:unlock");	 
		}
		
		  return new ModelAndView("redirect:Unlock account");
		
	}
	 
	
	
	@GetMapping("unlock")
	public ModelAndView getAccountUnlockForm()
	{
		return new ModelAndView("account/exceptions/unlock", "unlockAccount", new SecurityIssuesFormHandler() );
	}
	
	
	 @PostMapping("unlockProcessing")
	 public ModelAndView accountUnlockProcessiong(@ModelAttribute("unlockAccount") SecurityIssuesFormHandler  unlockAccount, RedirectAttributes attributes )
	   {
           
		 if(securityService.resetFailedLoginAttemptsAndUnlockAccount(unlockAccount.getCode()))
		   {
			   attributes.addFlashAttribute("ok","Account unlocked. You can now log in");
			   return new ModelAndView("redirect:/login"); 
		   }
		   return new ModelAndView("account/exceptions/unlock","message","Wrong or expired unlock code");
		   
	   }
	
	
}
