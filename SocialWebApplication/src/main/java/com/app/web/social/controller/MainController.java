package com.app.web.social.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.app.web.social.service.UserService;

@Controller
public class MainController {

	
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/")
	public String defaultPage() 
	{
	  return "redirect:login";
	}
	
	@GetMapping("/home")
	public String homePage() 
	{
	  return "home";
	}

	@GetMapping("/about")
	public String aboutPage() 
	{
		return "static/about";
	}
	
	@GetMapping("/403")
	public String accesssDenied() 
	{
	    return "static/403";
    }
	
	@GetMapping("/404")
	public String notFound() 
	{
	    return "static/404";
    }
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) 
	{
	    if (userService.isAuthenticated())
	    {   
	    	userService.clearSession();
	        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
	    }
	    return "redirect:/";
	}
	
}
