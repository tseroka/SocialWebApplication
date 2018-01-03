package com.app.web.social.controller;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.app.web.social.dao.validations.InputCorrectness;
import com.app.web.social.model.temp.EditAccount;
import com.app.web.social.model.UserAccount;
import com.app.web.social.service.UserService;
import com.app.web.social.service.UniquenessService;

@Controller
@RequestMapping(value="/user/")
public class UserController implements InputCorrectness
{
	
    @Autowired
    private UserService userService;
	
    @Autowired
    private UniquenessService uniquenessService;

	@RequestMapping(value="view", method=RequestMethod.GET )  
    public ModelAndView viewAccount()
	{   
		UserAccount userAccount = userService.getUserAccount(userService.getAuthenticatedUserUsername() );
		return new ModelAndView("account/view-account","user", userAccount);
    }  
	
	 @RequestMapping(value="edit" , method = RequestMethod.GET)  
	 public ModelAndView edit()
	 {  
	   return new ModelAndView("account/edit-account","editAccount", new EditAccount() );
	 }  
	 
	 @RequestMapping(value="edit/password" , method = RequestMethod.GET)  
	 public ModelAndView changePassword()
	 {  
	   return new ModelAndView("account/change-password","editAccount",new EditAccount() );
	 }  
	  
	    
	 @RequestMapping(value="edit/save", method = RequestMethod.POST)  
	 public ModelAndView editSave(@ModelAttribute("editAccount") EditAccount editAccount)//, BindingResult results)
	    {   
		    System.out.println("email: "+editAccount.getEmail());
		    ModelAndView model = new ModelAndView("account/edit-account");
	        if(!editAccount.getUsername().equals("") || !editAccount.getEmail().equals("") || !editAccount.getCountry().equals("") )
	        {
	          UserAccount userAccount = userService.getUserAccount( userService.getAuthenticatedUserUsername() );
	          String username = editAccount.getUsername().equals("") ? userAccount.getUsername() : editAccount.getUsername();
	          String email = editAccount.getEmail().equals("") ? userAccount.getEmail() : editAccount.getEmail();
	          String country = editAccount.getCountry().equals("") ? userAccount.getCountry() : editAccount.getCountry();
	          System.out.println("email: "+email);
	         // if(!results.hasErrors())
	          
	  		    if
	  		    (   
	  		       userAccount.getPassword().equals( editAccount.getCurrentPassword() )&&
	  		       !username.equals(email.split("@")[0]) &&
	  		       (uniquenessService.isUsernameNotBusy(username) || username.equals(userAccount.getUsername()))  &&
	  		       (uniquenessService.isEmailNotBusy(email) || email.equals(userAccount.getEmail() ) )  		    		    		    		  		 
	  	         )
	  		     {
	  			 	  			 
	  			   userAccount.setUsername(username);
	  			   userAccount.setEmail(email);
	  			   userAccount.setCountry(country);
		    	   userService.editUser(userAccount);  
		         return model.addObject("message","Account has been edited").addObject("user",userAccount);//new ModelAndView("account/view-account","message","Account has been edited"); 
	  		     } 
	  		  // }
	  		 
	  		  else 
	  		  { 
	  			if( !userAccount.getPassword().equals( editAccount.getCurrentPassword() ) ) model.addObject("invalidPasswordMessage","Please type Your correct current password.");  
	  			
	  			if( username.equals(email.split("@")[0]) ) model.addObject("sameInputsMessage","Username and email must be different");
	  			
	  		    if(!uniquenessService.isUsernameNotBusy(username) && !username.equals(userAccount.getUsername()) ) model.addObject("usernameExistsMessage","Username already exists!");
	  		    
	  		    if(!uniquenessService.isEmailNotBusy(email) && !email.equals(userAccount.getEmail()) ) model.addObject("emailExistsMessage","Email already exists!");
	  		    
	  		  }	
	  			
	  	  return model;  
	    }  
	        
	   

    
	        return model.addObject("noChanges","You didn't type any changes");
	    }	 
	 
	 @RequestMapping(value="edit/password/save", method = RequestMethod.POST)  
	 public ModelAndView changePasswordProcessing(@ModelAttribute("editAccount") EditAccount editAccount)
	 {
		 ModelAndView model = new ModelAndView("account/view-account");
		if( "".equals(editAccount.getCurrentPassword()) || "".equals(editAccount.getRepeatPassword()) )
		{
		   UserAccount userAccount = userService.getUserAccount( userService.getAuthenticatedUserUsername() );
		   if
		   ( 
			 userAccount.getPassword().equals(editAccount.getCurrentPassword()) &&
		     Pattern.matches(PASSWORD_VALIDATION_REGEX, editAccount.getNewPassword() ) &&
		     editAccount.getNewPassword().equals(editAccount.getRepeatPassword())
		   ) 
		   {
		   	   userAccount.setPassword(editAccount.getNewPassword());
			   userService.editUser(userAccount);
		       return new ModelAndView("account/view-account","message","Password has been changed");
		   }
		   else model.addObject("Current password is not correct"); //because for honest user javascript allerts is enough
	     } 
	     return model;
	 }
	
}