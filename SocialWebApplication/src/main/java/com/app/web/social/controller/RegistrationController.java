package com.app.web.social.controller;

import java.net.UnknownHostException;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.web.social.service.InputCorrectness;
import com.app.web.social.model.UserAccount;
import com.app.web.social.model.temp.SecurityIssuesFormHandler ;
import com.app.web.social.service.IUserService;
import com.app.web.social.service.ISecurityService;
import com.app.web.social.service.IUniquenessService;

@Controller
public class RegistrationController implements InputCorrectness {
	
  @Autowired
  private IUserService userService;
  
  @Autowired
  private ISecurityService securityService;
  
  @Autowired
  private IUniquenessService uniquenessService;
  
 
  @GetMapping("/register")
  public ModelAndView getRegisterForm() 
  { 
	if(!userService.isAuthenticated())
	{
    return new ModelAndView("register", "user", new UserAccount() ); 
	}
	else return new ModelAndView("redirect:/home");
  }

  @PostMapping("/registerProcess")
  public ModelAndView registerUser(@ModelAttribute("user") @Valid UserAccount userAccount, BindingResult results) 
		  throws UnknownHostException
  {
	  ModelAndView model = new ModelAndView("register");
	  
	  if(!results.hasErrors() && Pattern.matches(PASSWORD_VALIDATION_REGEX, userAccount.getPassword())) 
	  {
		 if
		 (   
		     !userAccount.getUsername().equals("anonymousUser") &&
		     userAccount.getPassword().equals(userAccount.getRepeatPassword()) &&
		     //uniquenessService.isPasswordUsernameEmailNicknameAreNotTheSame(userAccount) &&
		     uniquenessService.isUsernameNotBusy(userAccount.getUsername()) &&
		     uniquenessService.isEmailNotBusy(userAccount.getEmail()) &&
		     uniquenessService.isNicknameNotBusy(userAccount.getNickname())
		     		  		 
	     )
		   {
			 userService.registerUser(userAccount);
			 model.setViewName("account/activate"); model.addObject("registered","You have successfuly registered. Now You need to activate Your account by 5 minutes valid code sent on Your email address");
		     model.addObject("activate", new SecurityIssuesFormHandler());
		   } 
		 
		 else 
		 {
					    
			//if(!uniquenessService.isPasswordUsernameEmailNicknameAreNotTheSame(userAccount)) model.addObject("sameInputsMessage","Username, password, email and nickname must be different!");
			
		    if(!uniquenessService.isUsernameNotBusy(userAccount.getUsername())) model.addObject("usernameExistsMessage","Username already exists!");
		    
		    if(!uniquenessService.isEmailNotBusy(userAccount.getEmail())) model.addObject("emailExistsMessage","Email already exists!");
		    
		    if(!uniquenessService.isNicknameNotBusy(userAccount.getNickname())) model.addObject("nicknameExistsMessage","Nickname already exists!");
		 }	
			
	  }
	  return model;  
   }
  
  
  
  
   @GetMapping("/exceptions/Activate account")
   public ModelAndView getActivateForm()
   {
	   return new ModelAndView("account/activate","activate", new SecurityIssuesFormHandler ());
   }
   
   @PostMapping("/activateProcessing")
   public ModelAndView activationProcessiong(@ModelAttribute("activate") SecurityIssuesFormHandler  activate )
   {
	   if(securityService.acceptActivationCodeAndEnableAccount(activate.getCode()))
	   {
		   return new ModelAndView("/login","ok","Account activated. You can now log in.");   
	   }
	   return new ModelAndView("account/activate","message","Wrong or expired activation code");
   }
   
   
   
   //-----------------------------S E N D    C O D E    A G A I N --------------------------------------
   @GetMapping("/sendActivationCodeAgain")
   public ModelAndView getSendActivationAgainForm()
   {
	   return new ModelAndView("account/activate-send-again","send-activation-code-again", new SecurityIssuesFormHandler() );
   }
   
   @PostMapping("/sendActivationCodeAgainProcessing")
   public ModelAndView sendAgain(@ModelAttribute("send-activation-code-again") SecurityIssuesFormHandler sendAgain, RedirectAttributes attributes )
   {
	   String email = sendAgain.getEmail();
	   
	   if(Pattern.matches(EMAIL_VALIDATION_REGEX, email))
	   {    
		   securityService.sendAgainEmailWithActivationCode(email);
		   attributes.addFlashAttribute("message","If submited email exists, 5 minutes valid code to activate account will be send on this email address");
		   return new ModelAndView("redirect:/exceptions/Activate account");
	   }
	   
	   return new ModelAndView("redirect:sendActivationCodeAgain");
   }
}