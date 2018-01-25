package com.app.web.social.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.app.web.social.model.Profile;
import com.app.web.social.service.ProfileService;
import com.app.web.social.service.FriendsService;

@Controller
@RequestMapping(value="/profile/")
public class ProfileController {
	
	 
	
	@Autowired 
	private ProfileService profileService;
	
	@Autowired 
	private FriendsService friendsService;
	
	/**  --------------------------------VIEW------------------------------------------------------ */
	 
	     @RequestMapping(value="view/{nickname}", method=RequestMethod.GET )  
         public ModelAndView viewProfile(@PathVariable String nickname)
	     { 
		   Profile profile = profileService.getProfileByNickname(nickname);
		   if( profile!=null ) 
		   {
		   ModelAndView model = new ModelAndView("profile/view-profile","profile",profile);
		   model.addObject("isFriend",friendsService.isFriend(nickname));
		   model.addObject("isInvited",friendsService.isInvited(nickname));
		   return model;
		   }
		   
		   return new ModelAndView("redirect:/404");
         }  
 
	     @RequestMapping(value="/yourProfile", method=RequestMethod.GET )  
         public ModelAndView viewProfile()
	     { 
		   Profile profile = profileService.getAuthenticatedProfile();
		   return new ModelAndView("profile/view-profile","profile",profile);
         }  
	
	    @RequestMapping(value="view/all/", method=RequestMethod.GET)
	    public ModelAndView viewProfiles()
	    {
	    	List<Profile> profiles = profileService.getProfilesList();
			return new ModelAndView("profile/view-profiles","profiles",profiles);	
	    }

	  
	 //--------------------------------EDIT------------------------------------------------------ 
	
	    @RequestMapping(value="edit" , method = RequestMethod.GET)  
	    public ModelAndView edit()
	    {  
		  Profile profile = profileService.getAuthenticatedProfile();
          return new ModelAndView("profile/edit-profile","profile", profile);
	    }  
	  
	    
	    @RequestMapping(value="edit/save", method = RequestMethod.POST)  
	    public ModelAndView editSave(@ModelAttribute("profile") Profile profile)
	    {  
	    	profile.setNickname(profileService.getAuthenticatedUserNickname());
	    	profileService.editProfile(profile);  
	        return new ModelAndView("redirect:/home");  
	    }  
        
	
	    

	  

}
