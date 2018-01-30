package com.app.web.social.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.app.web.social.model.temp.SearchProfile;
import com.app.web.social.service.ProfileService;

@Controller
@RequestMapping(value="/search")
public class ProfileSearchController {
	
	   @Autowired
	   private ProfileService profileService;
	
	
	
	    @RequestMapping(method = RequestMethod.GET)
	    public ModelAndView search()
	    {
	    	 return new ModelAndView("profileSearching/search-profiles","command", new SearchProfile() );    	
	    }
	    
	    
	
	    @RequestMapping(value = "/goSearch", method = RequestMethod.POST)
	    public ModelAndView goSearch(@ModelAttribute("profile") SearchProfile searchProfile)
	    {  	
	    	return new ModelAndView("redirect:/search/query?sex="+searchProfile.getSearchSex()+"&city="+searchProfile.getSearchCity()+"&interests="+searchProfile.getSearchInterests() );
	    }
	     
	    
	    

	    @RequestMapping(value = "/query")
	    public ModelAndView searchProfiles(@RequestParam(name = "sex", defaultValue = "") String sex,
	    		@RequestParam(name = "city", defaultValue = "") String city,
	    		@RequestParam(name = "interests", defaultValue = "") String interestsInput)
	    {   
	    	List<String> interests = new ArrayList<String>( Arrays.asList(interestsInput.split(",") ) );
	    	
	    	List<String> findedProfiles = profileService.searchProfiles(sex, city, interests);
	    	
	    	interests.forEach(System.out::println);
	    	
	    	if(findedProfiles.isEmpty()) 
	    	{
	    	  return new ModelAndView("profileSearching/no-results");
	    	}
	    	
	    	return new ModelAndView("profileSearching/search-results","findedProfiles",findedProfiles);
	    }
 
}
