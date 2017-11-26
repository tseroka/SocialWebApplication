package com.app.web.social.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

	@RequestMapping(value = {"/home","/"} , method = RequestMethod.GET)
	public ModelAndView defaultPage() 
	{
	  return new ModelAndView("home");
	}
	

	@RequestMapping(value = "/about" , method = RequestMethod.GET)
	public ModelAndView aboutPage() 
	{
		return new ModelAndView("about");
	}
	
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied() 
	{
	 return new ModelAndView("403");
    }
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) 
	{
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null)
	    {    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return "redirect:/home";
	}
	
}
