package com.app.web.social.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.app.web.social.service.FriendsService;
import com.app.web.social.service.ProfileService;

@RequestMapping(value="/profile/friends/")
@Controller
public class FriendsController 
{
    
	@Autowired
	private FriendsService friendsService;
	
	@Autowired 
	private ProfileService profileService;
	
	@RequestMapping(value="invite/{invited}", method=RequestMethod.POST)
	public ModelAndView inviteToFriends(@PathVariable String invited)
	{
		friendsService.addToFriendsList(invited);
			
		return new ModelAndView("redirect:invitationsSentList");
	}
}
