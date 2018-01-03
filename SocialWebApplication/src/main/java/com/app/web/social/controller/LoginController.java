package com.app.web.social.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
    

	
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(required = false) String error )
	{   
		ModelAndView model = new ModelAndView("login");
	    if("credentials".equals(error)) model.addObject("message", "Invalid username or password");
	    else if ("expired".equals(error)) model.addObject("message", "Session expired");
	    else if ("locked".equals(error)) model.addObject("message","Your account has been locked by administrator");
	    else if ("disabled".equals(error)) model.addObject("message","Your account hasn't been activated yet");
		return model;
	}
	

}

