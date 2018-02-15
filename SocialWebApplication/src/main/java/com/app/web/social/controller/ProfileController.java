package com.app.web.social.controller;

import java.util.List;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.app.web.social.model.Profile;
import com.app.web.social.service.IProfileService;
import com.app.web.social.service.IFriendsService;
import com.app.web.social.utilities.CookiesService;

@Controller
@RequestMapping(value="/profile/")
public class ProfileController {
	
	 
	
	@Autowired 
	private IProfileService profileService;
	
	@Autowired 
	private IFriendsService friendsService;
	
	/**  --------------------------------VIEW------------------------------------------------------ */
	 
	     @GetMapping("view/{nickname}")  
         public ModelAndView viewProfile(@PathVariable String nickname, HttpServletResponse response,
         @CookieValue(value = "visitedProfiles", defaultValue = "") String cookieValue) throws IOException
	     { 
	    	 if(friendsService.isProfileExist(nickname))
		     {
			   Profile profile = profileService.getProfileByNickname(nickname);
			   ModelAndView model = new ModelAndView("profile/view-profile","profile",profile);
			   model.addObject("isFriend",friendsService.isFriend(nickname));
			   model.addObject("isInvited",friendsService.isInvited(nickname));
			   
			   CookiesService.addCookie(response, "visitedProfiles", cookieValue+","+nickname, 3600);
			   
			   return model;
			 }
		   
		   return new ModelAndView("redirect:/404");
         }  
 
	     @GetMapping("/yourProfile")  
         public ModelAndView viewProfile()
	     { 
		   Profile profile = profileService.getAuthenticatedProfile();
		   return new ModelAndView("profile/view-profile","profile",profile);
         }  
	
	    @GetMapping("view/all/")
	    public ModelAndView viewProfiles()
	    {
	    	List<Profile> profiles = profileService.getProfilesList();
			return new ModelAndView("profile/view-profiles","profiles",profiles);	
	    }

	  
	 //--------------------------------EDIT------------------------------------------------------ 
	
	    @GetMapping("edit")  
	    public ModelAndView edit()
	    {  
		  Profile profile = profileService.getAuthenticatedProfile();
          return new ModelAndView("profile/edit-profile","profile", profile);
	    }  
	  
	    
	    @PostMapping(value="edit/save")  
	    public ModelAndView editSave(@ModelAttribute("profile") Profile profile)
	    {  
	    	profile.setNickname(profileService.getAuthenticatedUserNickname());
	    	profileService.editProfile(profile);  
	        return new ModelAndView("redirect:/profile/yourProfile");  
	    }  

}
