package com.app.web.social.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.GetMapping;
import com.app.web.social.service.ISecurityService;
import com.app.web.social.service.IUserService;

@Controller
public class LoginController {
    
	@Autowired
	private ISecurityService securityService;

	@Autowired
	private IUserService userService;
	
	@GetMapping("/login")                                                                                   //username
	public ModelAndView login(@RequestParam(name="error", required = false) String error, @RequestParam(name="kjhubvJHbt", required=false) String username)
	{   
        

		if(userService.isAuthenticated()) 
		{ 
			return new ModelAndView("redirect:/home") ; 
		}
	
		 
		ModelAndView model = new ModelAndView("login");

		boolean isException = false; 
		
		if(error!=null && !"expired".equals(error))
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
	        
	        else if ("locked-badLanguage-time".equals(error))
	        {
	    	   model.addObject("message","Your account has been locked until "+securityService.getUnlockDateByUsername(username)+" for bad language."); 
	        }
	        
	       
	        model.addObject("linkToAction",linkToAction);
	    
		}
		
		if ("expired".equals(error)) { model.addObject("message", "Session expired");}
		  
		model.addObject("loginActionRedirect", isException);
		
		return model;
	}
	
	

}

