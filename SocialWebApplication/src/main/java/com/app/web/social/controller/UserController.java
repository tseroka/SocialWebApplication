package com.app.web.social.controller;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.web.social.service.InputCorrectness;
import com.app.web.social.model.temp.EditAccount;
import com.app.web.social.model.UserAccount;
import com.app.web.social.model.SecurityIssues;
import com.app.web.social.service.IUserService;
import com.app.web.social.service.ISecurityService;
import com.app.web.social.service.IUniquenessService;

@Controller
@RequestMapping(value="/user/")
public class UserController implements InputCorrectness
{
	
    @Autowired
    private IUserService userService;
	
    @Autowired
    private ISecurityService securityService;
    
    @Autowired
    private IUniquenessService uniquenessService;

    
    
	@GetMapping(value="view")  
    public ModelAndView viewAccount()
	{   
		UserAccount userAccount = userService.getAuthenticatedUserAccount();
		return new ModelAndView("account/view-account","user", userAccount);
    }  
	
	//--------------------------------------------EDIT ACCOUNT (BUT NOT PASSWORD)-----------------------------------------
	 @GetMapping("edit")  
	 public ModelAndView edit()
	 {  
	   return new ModelAndView("account/edit-account","editAccount", new EditAccount() );
	 }  
	  
	    
	 @PostMapping("edit/save")  
	 public ModelAndView editSave(@ModelAttribute("editAccount") EditAccount editAccount, RedirectAttributes attributes)
	 {    
		    String username = editAccount.getUsername();
            String email = editAccount.getEmail();
            String country = editAccount.getCountry();  
         
		    ModelAndView model = new ModelAndView("account/edit-account");
	        if(!username.equals("") || !email.equals("") || !country.equals("Unspecified") )
	        {
	          UserAccount userAccount = userService.getAuthenticatedUserAccount();
	          
	          SecurityIssues issue = securityService.getAuthenticatedSecurityIssues();
	                    
	  		    if(userService.checkPassword(editAccount.getCurrentPassword(), userAccount.getPassword()) )
	  		     {
	  			     if(!username.equals("") && Pattern.matches(USERNAME_VALIDATION_REGEX, username) && uniquenessService.isUsernameNotBusy(username)) 
	  			     {
	  				   userAccount.setUsername(username);
	  				   issue.setUsername(username);
	  				   securityService.saveSecurityIssuesAccount(issue);
	  			     }
	  			   
	  			     if(!email.equals("") && Pattern.matches(EMAIL_VALIDATION_REGEX, email) && uniquenessService.isEmailNotBusy(email)) 
	  			     {
	  				   userAccount.setEmail(email);
	  			     }	  		
	  			     
	  			     if(!country.equals("Unspecified"))
	  			     {
	  			       userAccount.setCountry(country);
	  			     }
		    	   userService.editUser(userAccount); 
		    	   
		    	   if(username.equals(userAccount.getUsername()))
		    	   {
		    		 attributes.addFlashAttribute("mddzkfzf","changed");
		    		 return new ModelAndView("redirect:/logout");
		    	   }
		    	   
                   attributes.addFlashAttribute("message","Account has been successfuly edited");
		           return new ModelAndView("redirect:/user/view"); 
	  		    }
	  		 
	  		  else 
	  		  { 
	  			if( !userService.checkPassword(editAccount.getCurrentPassword(), userAccount.getPassword()) ) model.addObject("invalidPasswordMessage","Please type Your correct current password.");  
	  			
	  		    if(!username.equals("") && !uniquenessService.isUsernameNotBusy(username) ) model.addObject("usernameExistsMessage","Username already exists!");
	  		    
	  		    if(!email.equals("") && !uniquenessService.isEmailNotBusy(email) ) model.addObject("emailExistsMessage","Email already exists!");
	  		    
	  		  }	
	  			
	  	   return model;  
	    }  
	        
	      return model.addObject("noChanges","You didn't type any changes");
	  }	
	 
	 

	 
	 
	 //-------------------------------------------CHANGE PASSWORD----------------------------------------------------
	 @GetMapping("edit/password")  
	 public ModelAndView changePassword()
	 {  
	   return new ModelAndView("account/change-password","editAccount", new EditAccount() );
	 }  
	 
	 
	 @PostMapping(value="edit/password/save")  
	 public ModelAndView changePasswordProcessing(@ModelAttribute("editAccount") EditAccount editAccount,  RedirectAttributes attributes)
	 {
		  ModelAndView model = new ModelAndView("account/change-password");
		   
		   UserAccount userAccount = userService.getAuthenticatedUserAccount();
		   if
		   (  
			 userService.checkPassword(editAccount.getCurrentPassword(), userAccount.getPassword()) &&
		     Pattern.matches(PASSWORD_VALIDATION_REGEX, editAccount.getNewPassword() ) &&
		     editAccount.getNewPassword().equals(editAccount.getRepeatPassword())
		   ) 
		   {
			   userService.changePassword(userAccount, editAccount.getNewPassword());
			   
			   attributes.addFlashAttribute("message","Password successfuly changed.");
		       return new ModelAndView("redirect:/user/view");
		   }
		   else model.addObject("invalidPasswordMessage","Current password is not correct");
		   
	     return model;
	 }
	 
	 

	
}