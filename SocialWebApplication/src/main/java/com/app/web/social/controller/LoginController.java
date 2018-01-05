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
		if(!"".equals(error) && !"expired".equals(error))
		{
			 model.addObject("login-action-redirect",true);
			 String linkToAction = "";
			
	        if("credentials".equals(error)) 
	        {
	    	   model.addObject("message", "Invalid username or password");
	    	   linkToAction = "Reset password";
	    	 
	        }
	    
	        else if ("locked-attempts".equals(error))
	        {
	    	   model.addObject("message","Your account has been locked due to maximum failed login attempts. You can unlock it via email by special code."); 
	    	   linkToAction = "Unlock account";
	        }
	    
	        else if ("disabled".equals(error)) 
	        {
	    	  model.addObject("message","Your account hasn't been activated yet");	  
	    	  linkToAction = "Enable account";
	        }
	         
	        model.addObject("linkToAction",linkToAction);
	    
		}
		
		if ("expired".equals(error)) model.addObject("message", "Session expired");
		  
		return model;
	}
	

}

