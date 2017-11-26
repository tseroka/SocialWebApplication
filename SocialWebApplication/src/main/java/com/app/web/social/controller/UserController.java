package com.app.web.social.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.app.web.social.model.UserAccount;
import com.app.web.social.service.UserService;


@Controller
@RequestMapping(value="/user/")
public class UserController {
	
@Autowired
private UserService userService;
	

	@RequestMapping(value="view", method=RequestMethod.GET )  
    public ModelAndView viewAccount()
	{   
		UserAccount userAccount = userService.getUserAccount( userService.getAuthenticatedUserUsername() );
		return new ModelAndView("view-account","user", userAccount);
    }  
	
}
