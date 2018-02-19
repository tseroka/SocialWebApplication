package com.app.web.social.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.app.web.social.service.IUserService;
import com.app.web.social.service.IAdminService;

@Controller
public class MainController {

	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IAdminService adminService;
	
	@GetMapping("/")
	public String defaultPage() 
	{
	  return "redirect:login";
	}
	
	@GetMapping("/home")
	public ModelAndView homePage() 
	{       
	  return new ModelAndView("home","usersOnline",adminService.getActiveUsersFromSessionRegistry().size());
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
	
	@GetMapping("/logout")
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) 
	{
	    if (userService.isAuthenticated())
	    {   
	      SecurityContextLogoutHandler logout = new SecurityContextLogoutHandler();
	      logout.setInvalidateHttpSession(false);
	      logout.logout(request, response, SecurityContextHolder.getContext().getAuthentication());
	    }
	    return "redirect:/login";
	}
	
}
