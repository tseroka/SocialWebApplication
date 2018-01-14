package com.app.web.social.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.app.web.social.service.UserService;

@Controller
public class MainController {

	
	@Autowired
	private UserService userService;
	
	
	@RequestMapping(value = {"/home","/"} , method = RequestMethod.GET)
	public String defaultPage() 
	{
	  return "home";
	}
	

	@RequestMapping(value = "/about" , method = RequestMethod.GET)
	public String aboutPage() 
	{
		return "about";
	}
	
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String accesssDenied() 
	{
	    return "403";
    }
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) 
	{
	    if (userService.isAuthenticated())
	    {   
	    	userService.clearSession();
	        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
	    }
	    return "redirect:/home";
	}
	
}
