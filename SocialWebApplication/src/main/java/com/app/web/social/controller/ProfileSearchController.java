package com.app.web.social.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


import com.app.web.social.model.Profile;
import com.app.web.social.model.SearchProfile;
import com.app.web.social.service.ProfileService;

@Controller
@RequestMapping(value="/search")
public class ProfileSearchController {
	
	   @Autowired
	   private ProfileService profileService;
	
	
	
	    @RequestMapping
	    public ModelAndView search()
	    {
	    	 return new ModelAndView("search-profiles","command", new SearchProfile() );    	
	    }
	    
	    
	
	    @RequestMapping(value="/goSearch", method = RequestMethod.POST)
	    public ModelAndView goSearch(@ModelAttribute("profile") SearchProfile searchProfile)
	    {  	
return new ModelAndView("redirect:/search/sex="+searchProfile.getSearchSex()+"/city="+searchProfile.getSearchCity()+"/interests="+searchProfile.getSearchInterests() );
	    }
	    
	    
	    

	    @RequestMapping(value="/sex={sex}/city={city}/interests={interestsInput}")
	    public ModelAndView searchProfiles(@PathVariable String sex, @PathVariable String city, @PathVariable
	    		String interestsInput)
	    {   
	    	List<String> interests = Arrays.asList((interestsInput).split(","));
	    	
	    	List<Profile> findedProfiles = profileService.searchProfiles(sex, city, interests);
	    	
	    	if(findedProfiles.isEmpty()) 
	    	{
	    	  return new ModelAndView("no-results");
	    	}
	    	
	    	return new ModelAndView("search-results","findedProfiles",findedProfiles);
	    }

}
