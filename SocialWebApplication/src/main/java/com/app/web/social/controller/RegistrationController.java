package com.app.web.social.controller;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.app.web.social.model.UserAccount;
import com.app.web.social.service.UserService;
import com.app.web.social.service.ValidationsService;


@Controller
public class RegistrationController {
	
  @Autowired
  private UserService userService;
  
  @Autowired
  private ValidationsService validationsService;
  
 
  @RequestMapping(value = "/register", method = RequestMethod.GET)
  public ModelAndView showRegister() 
  {
    return new ModelAndView("register", "user", new UserAccount() ); 
  }

  @RequestMapping(value = "/registerProcess", method = RequestMethod.POST)
  public ModelAndView registerUser(HttpServletRequest request, HttpServletResponse response,
      @ModelAttribute("user") UserAccount userAccount)
  {
	  /**
	if( !results.hasErrors() ) 
	{
       if( validationsService.validateRegistration(userAccount) )
       {
       userService.registerUser(userAccount);
       return new ModelAndView("redirect:/login");  
       }
	} */
	if( validationsService.validateRegistration(userAccount) )
    {
      userService.registerUser(userAccount);
      return new ModelAndView("redirect:/login");  
    }
    else userAccount=null;
    return new ModelAndView("register","message","Invalid inputs in form fields");
  }
  
}
