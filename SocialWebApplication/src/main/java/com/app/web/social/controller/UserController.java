package com.app.web.social.controller;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.web.social.dao.validations.InputCorrectness;
import com.app.web.social.model.temp.EditAccount;
import com.app.web.social.model.UserAccount;
import com.app.web.social.model.SecurityIssues;
import com.app.web.social.service.UserService;
import com.app.web.social.service.SecurityService;
import com.app.web.social.service.UniquenessService;

@Controller
@RequestMapping(value="/user/")
public class UserController implements InputCorrectness
{
	
    @Autowired
    private UserService userService;
	
    @Autowired
    private SecurityService securityService;
    
    @Autowired
    private UniquenessService uniquenessService;

    
    
	@RequestMapping(value="view", method=RequestMethod.GET )  
    public ModelAndView viewAccount()
	{   
		return new ModelAndView("account/view-account","user", userService.getUserAccount( userService.getAuthenticatedUserUsername() ));
    }  
	
	//--------------------------------------------EDIT ACCOUNT (BUT NOT PASSWORD)-----------------------------------------
	 @RequestMapping(value="edit" , method = RequestMethod.GET)  
	 public ModelAndView edit()
	 {  
	   return new ModelAndView("account/edit-account","editAccount", new EditAccount() );
	 }  
	  
	    
	 @RequestMapping(value="edit/save", method = RequestMethod.POST)  
	 public ModelAndView editSave(@ModelAttribute("editAccount") EditAccount editAccount, RedirectAttributes attributes)
	    {   
		    System.out.println("email: "+editAccount.getEmail());
		    ModelAndView model = new ModelAndView("account/edit-account");
	        if(!editAccount.getUsername().equals("") || !editAccount.getEmail().equals("") || !editAccount.getCountry().equals("") )
	        {
	          UserAccount userAccount = userService.getUserAccount( userService.getAuthenticatedUserUsername() );
	          
	          SecurityIssues issue = securityService.getSecurityIssuesAccountByUsername(userService.getAuthenticatedUserUsername() );
	          
	          String username = editAccount.getUsername().equals("") ? userAccount.getUsername() : editAccount.getUsername();
	          String email = editAccount.getEmail().equals("") ? userAccount.getEmail() : editAccount.getEmail();
	          String country = editAccount.getCountry().equals("") ? userAccount.getCountry() : editAccount.getCountry();
	          System.out.println("email: "+email);
	        
	          
	  		    if
	  		    (   
	  		       userAccount.getPassword().equals( editAccount.getCurrentPassword() )&&
	  		       validateEditAccount(username, email, country) &&
	  		       !username.equals(email.split("@")[0]) &&
	  		       (uniquenessService.isUsernameNotBusy(username) || username.equals(userAccount.getUsername()))  &&
	  		       (uniquenessService.isEmailNotBusy(email) || email.equals(userAccount.getEmail() ) )  		    		    		    		  		 
	  	         )
	  		     {
	  			 	  			 
	  			   userAccount.setUsername(username);
	  			   userAccount.setEmail(email);
	  			   userAccount.setCountry(country);
	  			   issue.setUsername(username);
		    	   userService.editUser(userAccount, issue); 
                   attributes.addFlashAttribute("message","Account has been successfuly edited");
		           return new ModelAndView("redirect:/view"); 
	  		     } 
	  		 
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
	 
	 private boolean validateEditAccount(String username, String email, String country)
	 {
		 return 
	     (
	    		 Pattern.matches(USERNAME_VALIDATION_REGEX, username) &&
	    		 Pattern.matches(EMAIL_VALIDATION_REGEX, email) &&
	    		 Pattern.matches(COUNTRY_VALIDATION_REGEX, country)
	     );
	 }
	 
	 
	 //-------------------------------------------CHANGE PASSWORD----------------------------------------------------
	 @RequestMapping(value="edit/password" , method = RequestMethod.GET)  
	 public ModelAndView changePassword()
	 {  
	   return new ModelAndView("account/change-password","editAccount",new EditAccount() );
	 }  
	 
	 
	 @RequestMapping(value="edit/password/save", method = RequestMethod.POST)  
	 public ModelAndView changePasswordProcessing(@ModelAttribute("editAccount") EditAccount editAccount,  RedirectAttributes attributes)
	 {
		  ModelAndView model = new ModelAndView("account/edit-account");
		  System.out.println("editAccount.getCurrentPassword()" + editAccount.getCurrentPassword() );
		  System.out.println("editAccount.getRepeatPassword()" + editAccount.getRepeatPassword() );
		   

		   UserAccount userAccount = userService.getUserAccount( userService.getAuthenticatedUserUsername() );
		   if
		   ( 
			 userAccount.getPassword().equals(editAccount.getCurrentPassword()) &&
		     Pattern.matches(PASSWORD_VALIDATION_REGEX, editAccount.getNewPassword() ) &&
		     editAccount.getNewPassword().equals(editAccount.getRepeatPassword())
		   ) 
		   {
			   System.out.println("password:" + userAccount.getPassword() );
			   System.out.println("new password:" + editAccount.getNewPassword() );
		   	   userAccount.setPassword(editAccount.getNewPassword());
			   userService.editUser(userAccount);
			   
			   attributes.addFlashAttribute("message","Password successfuly changed.");
		       return new ModelAndView("redirect:/view");
		   }
		   else model.addObject("message","Current password is not correct"); //because for honest user javascript allerts is enough
		   
	     return model;
	 }
	 
	 

	
}