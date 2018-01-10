package com.app.web.social.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.app.web.social.service.SecurityService;
import com.app.web.social.service.UserService;

@Controller
public class LoginController {
    
	@Autowired
	private SecurityService securityService;

	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(required = false) String error, HttpServletRequest request, HttpServletResponse response, HttpSession session )
	{   
        
		System.out.println("name: "+SecurityContextHolder.getContext().getAuthentication().getName());
		if(userService.isAuthenticated()) 
		{ 
			return new ModelAndView("redirect:/home") ; 
		}
		
		ModelAndView model = new ModelAndView("login");
		//String errorTwo = error;
		boolean isException = false; 
		
		if(!"".equals(error) && !"expired".equals(error) && error!=null)
		{
			 isException = true;
			 String linkToAction = "";
			
	        if("credentials".equals(error)) 
	        {
	    	   model.addObject("message", "Invalid username or password");
	    	   linkToAction = "Reset password";
	    	 
	        }
	    
	       
	        else if ("activate".equals(error)) 
	        {
	    	  model.addObject("message","Your account hasn't been activated yet");	  
	    	  linkToAction = "Activate account";
	        }
	         
	        
	        else if ("locked-attempts".equals(error))
	        {
	    	   model.addObject("message","Your account has been locked due to maximum failed login attempts. You can unlock it via email by special code."); 
	    	   linkToAction = "Unlock account";
	        }
	    
	        else if ("locked-end".equals(error))
	        {
	    	   model.addObject("message","A lock time of Your account has been elapsed. You can now log in."); 
	        }
	        
	        
	        else if ("locked-badLanguage-perm".equals(error))
	        {
	    	   model.addObject("message","Your account has been permanently locked for bad language."); 
	        }
	        
	        else if ("locked-badLanguage-time".equals(error.split("&")[0]))
	        {
	       //    String username = errorTwo.split("&")[1];
	    	   model.addObject("message","Your account has been locked until for bad language."); 
	           System.out.println("unlock date: "+request.getAttribute("unlockDate"));
	           System.out.println("username: "+request.getAttribute("username"));
	        }
	        
	       
	        model.addObject("linkToAction",linkToAction);
	    
		}
		
		if ("expired".equals(error)) { model.addObject("message", "Session expired");}
		  
		model.addObject("loginActionRedirect", isException);
		
		return model;
	}
	
	

}

