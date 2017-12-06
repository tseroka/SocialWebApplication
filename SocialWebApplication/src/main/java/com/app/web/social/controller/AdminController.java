package com.app.web.social.controller;

import com.app.web.social.model.UserAccount;
import com.app.web.social.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    
	@Autowired
    private UserService userService;
	
	 
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView adminPage() 
	{
       return new ModelAndView("admin/admin");
	}
	 
	@RequestMapping(value="view-users", method = RequestMethod.GET)
	public ModelAndView viewUsers()
	{
		    List<UserAccount> listUser =userService.getUsersList();  
	        return new ModelAndView("admin/view-users","listUser",listUser);  
	}
	
	@RequestMapping(value="disableUser/{id}")  
    public ModelAndView disableUser(@PathVariable Integer id){   
		userService.disableUser(id);
        return new ModelAndView("redirect:/admin/view-users");  
    }  

	@RequestMapping(value="enableUser/{id}")  
    public ModelAndView enableUser(@PathVariable Integer id){   
		userService.enableUser(id);
        return new ModelAndView("redirect:/admin/view-users");  
    }  
	
}
