package com.app.web.social.controller;

import java.net.UnknownHostException;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.app.web.social.dao.validations.InputCorrectness;
import com.app.web.social.model.UserAccount;
import com.app.web.social.model.temp.SecurityIssuesFormHandler ;
import com.app.web.social.service.UserService;
import com.app.web.social.service.SecurityService;
import com.app.web.social.service.UniquenessService;

@Controller
public class RegistrationController implements InputCorrectness {
	
  @Autowired
  private UserService userService;
  
  @Autowired
  private SecurityService securityService;
  
  @Autowired
  private UniquenessService uniquenessService;
  
 
  @RequestMapping(value = "/register", method = RequestMethod.GET)
  public ModelAndView getRegisterForm() 
  {
    return new ModelAndView("register", "user", new UserAccount() ); 
  }

  @RequestMapping(value = "/registerProcess", method = RequestMethod.POST)
  public ModelAndView registerUser(@ModelAttribute("user") @Valid UserAccount userAccount, BindingResult results) 
		  throws UnknownHostException
  {
	  ModelAndView model = new ModelAndView("register");
	  
	  if(!results.hasErrors()) 
	  {
		 if
		 (   
		     !userAccount.getUsername().equals("anonymousUser") &&
		     userAccount.getPassword().equals(userAccount.getRepeatPassword()) &&
		     uniquenessService.isPasswordUsernameEmailNicknameAreNotTheSame(userAccount) &&
		     uniquenessService.isUsernameNotBusy(userAccount.getUsername()) &&
		     uniquenessService.isEmailNotBusy(userAccount.getEmail()) &&
		     uniquenessService.isNicknameNotBusy(userAccount.getNickname())
		     		  		 
	     )
		   {
			 userService.registerUser(userAccount);
			 model.setViewName("account/activate"); model.addObject("registered","You have successfuly registered. Now You need to activate Your account by code sent on Your email address");
		     model.addObject("activate", new SecurityIssuesFormHandler());
		   } 
		 
		 else 
		 {
					    
			if(!uniquenessService.isPasswordUsernameEmailNicknameAreNotTheSame(userAccount)) model.addObject("sameInputsMessage","Username, password, email and nickname must be different!");
			
		    if(!uniquenessService.isUsernameNotBusy(userAccount.getUsername())) model.addObject("usernameExistsMessage","Username already exists!");
		    
		    if(!uniquenessService.isEmailNotBusy(userAccount.getEmail())) model.addObject("emailExistsMessage","Email already exists!");
		    
		    if(!uniquenessService.isNicknameNotBusy(userAccount.getNickname())) model.addObject("nicknameExistsMessage","Nickname already exists!");
		 }	
			
	  }
	  return model;  
   }
  
  
  
  
   @RequestMapping(value="/exceptions/Activate account", method=RequestMethod.GET)
   public ModelAndView getActivateForm()
   {
	   return new ModelAndView("account/activate","activate", new SecurityIssuesFormHandler ());
   }
   
   @RequestMapping(value="/activateProcessing", method=RequestMethod.POST)
   public ModelAndView activationProcessiong(@ModelAttribute("activate") SecurityIssuesFormHandler  activate )
   {
	   try
	   {
	   securityService.acceptActivationCodeAndEnableAccount(activate.getCode());
	   }
	   catch(IndexOutOfBoundsException ex)
	   {
		   return new ModelAndView("account/activate","message","Wrong activation code");
	   }
	   
	   return new ModelAndView("/login","ok","Account activated. You can now log in.");
   }
   
   
   
   //-----------------------------S E N D    C O D E    A G A I N --------------------------------------
   @RequestMapping(value="/sendActivationCodeAgain", method=RequestMethod.GET)
   public ModelAndView getSendActivationAgainForm()
   {
	   return new ModelAndView("account/activate-send-again","send-activation-code-again", new SecurityIssuesFormHandler ());
   }
   
   @RequestMapping(value="/sendActivationCodeAgainProcessing", method=RequestMethod.POST)
   public ModelAndView sendAgain(@ModelAttribute("send-activation-code-again") SecurityIssuesFormHandler sendAgain )
   {
	   String email = sendAgain.getEmail(); String username = sendAgain.getUsername();
	   
	   if(Pattern.matches(EMAIL_VALIDATION_REGEX, email) && Pattern.matches(USERNAME_VALIDATION_REGEX, username) )
	   {
	      try 
	      {  
		   securityService.sendAgainEmailWithActivationCode(email, username);
		   return new ModelAndView("account/activate","message","If email and username are valid, activation code will be send again");
	      }
	      catch(IndexOutOfBoundsException ex)
	      {
	    	  System.out.println("Unexisting account: "+username);
	      }
	   }
	   
	   return new ModelAndView("redirect:sendActivationCodeAgain");
   }
}