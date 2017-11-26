package com.app.web.social.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.app.web.social.service.SocialWebAppUserDetailsService;

@Controller
public class LoginController {
    
	@Autowired 
	SocialWebAppUserDetailsService details;
	
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public ModelAndView login()
	{   
		return new ModelAndView("login");
	}
	

}

