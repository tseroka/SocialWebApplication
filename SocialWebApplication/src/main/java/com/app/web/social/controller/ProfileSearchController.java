package com.app.web.social.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.app.web.social.model.temp.SearchProfile;
import com.app.web.social.service.IProfileService;

@Controller
@RequestMapping(value="/search")
public class ProfileSearchController {
	
	   @Autowired
	   private IProfileService profileService;

	   
	    @GetMapping
	    public ModelAndView search()
	    {
	    	 return new ModelAndView("profileSearching/search-profiles","command", new SearchProfile() );    	
	    }
	    
	    
	
	    @PostMapping("/goSearch")
	    public ModelAndView goSearch(@ModelAttribute("profile") SearchProfile searchProfile)
	    {  	
	    	return new ModelAndView("redirect:/search/query?sex="+searchProfile.getSearchSex()+"&city="+searchProfile.getSearchCity()+"&interests="+searchProfile.getSearchInterests() );
	    }
	     
	    
	    

	    @GetMapping("/query")
	    public ModelAndView searchProfiles(@RequestParam(name = "sex", defaultValue = "") String sex,
	    		@RequestParam(name = "city", defaultValue = "") String city,
	    		@RequestParam(name = "interests", defaultValue = "") String interestsInput, HttpServletResponse response) throws IOException
	    {   
	    	List<String> interests = new ArrayList<String>();
	    	interests = interestsInput.equals("") ? null :  Arrays.asList(interestsInput.split(",") ) ;
	    	
	    	List<String> findedProfiles = profileService.searchProfiles(sex, city, interests);
	    	
	    	//if(!city.equals("")) CookiesService.addCookie(response, "searchedCity", city, 3600);
	    	
	    	//if(!interestsInput.equals("")) CookiesService.addCookie(response, "searchedInterests", interestsInput, 3600);
	    
	    	if(findedProfiles.isEmpty()) 
	    	{
	    	  return new ModelAndView("profileSearching/no-results");
	    	}
	    	
	    	return new ModelAndView("profileSearching/search-results","findedProfiles",findedProfiles);
	    }
 
}
