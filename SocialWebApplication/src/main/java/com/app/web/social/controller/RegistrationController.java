package com.app.web.social.controller;

import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.app.web.social.model.UserAccount;
import com.app.web.social.model.temp.Activate;
import com.app.web.social.service.UserService;
import com.app.web.social.service.SecurityService;
import com.app.web.social.service.UniquenessService;

@Controller
public class RegistrationController {
	
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
  public ModelAndView registerUser(HttpServletRequest request, HttpServletResponse response, HttpSession session,
      @ModelAttribute("user") @Valid UserAccount userAccount, BindingResult results) throws UnknownHostException
  {
	  ModelAndView model = new ModelAndView("register");
	  
	  if(!results.hasErrors()) 
	  {
		 if
		 (
		     userAccount.getPassword().equals(userAccount.getRepeatPassword()) &&
		     uniquenessService.isPasswordUsernameEmailNicknameAreNotTheSame(userAccount) &&
		     uniquenessService.isUsernameNotBusy(userAccount.getUsername()) &&
		     uniquenessService.isEmailNotBusy(userAccount.getEmail()) &&
		     uniquenessService.isNicknameNotBusy(userAccount.getNickname())
		    		  		 
	     )
		   {
			 userService.registerUser(userAccount);
			 model.setViewName("account/activate"); model.addObject("registered","You have successfuly registered. Now You need to activate Your account by code sent on Your email address");
			 session.setAttribute("username",userAccount.getUsername());
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
  
  
  
  
   @RequestMapping(value="/activate", method=RequestMethod.GET)
   public ModelAndView getActivateForm()
   {
	   return new ModelAndView("account/activate","activate", new Activate());
   }
   
   @RequestMapping(value="/activateProcessing", method=RequestMethod.POST)
   public ModelAndView activationProcessiong(HttpSession session, @ModelAttribute("activate") Activate activate )
   {
	   String username = session.getAttribute("username").toString();
	   activate.setUsername(username);
	   
	   
	   if(activate.getActivationCode() == securityService.getSecurityIssuesAccountByUsername(username).getActivationCode())
	   {
	   securityService.acceptActivationCodeAndEnableAccount(username);
	   return new ModelAndView("redirect:/login","message","Account activated. You can now log in");
	   }
	   
	   return new ModelAndView("redirect:/activate","message","Wrong activation code");
   }
   
   @RequestMapping(value="/send-activation-again", method=RequestMethod.GET)
   public ModelAndView sendAgain(HttpSession session)
   {
	   securityService.sendAgainEmailWithActivationCode(session.getAttribute("username").toString());
	   return new ModelAndView("redirect:activate");
   }
}