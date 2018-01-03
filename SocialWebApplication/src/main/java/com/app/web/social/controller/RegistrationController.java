package com.app.web.social.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.app.web.social.model.UserAccount;
import com.app.web.social.service.UserService;
import com.app.web.social.service.UniquenessService;

@Controller
public class RegistrationController {
	
  @Autowired
  private UserService userService;
  
  @Autowired
  private UniquenessService uniquenessService;
  
 
  @RequestMapping(value = "/register", method = RequestMethod.GET)
  public ModelAndView showRegister() 
  {
    return new ModelAndView("register", "user", new UserAccount() ); 
  }

  @RequestMapping(value = "/registerProcess", method = RequestMethod.POST)
  public ModelAndView registerUser(HttpServletRequest request, HttpServletResponse response,
      @ModelAttribute("user") @Valid UserAccount userAccount, BindingResult results)
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
			 model.setViewName("login"); model.addObject("registered","You have successfuly registered.");
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
}