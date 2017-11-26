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
	
	@RequestMapping(value="viewFriends", method=RequestMethod.GET)
	public ModelAndView viewFriends()
	{
		return new ModelAndView("view-friends","friendsList",friendsService.getFriends());
	}
	
	@RequestMapping(value="remove={removed}", method=RequestMethod.POST)
	public ModelAndView removeFromFriendsList(@PathVariable String removed)
	{
		friendsService.removeFromFriendsList(removed);
			
		return new ModelAndView("redirect:viewFriends");
	}
	
	@RequestMapping(value="viewSentInvitations", method=RequestMethod.GET)
	public ModelAndView viewSentInvitations()
	{
		ModelAndView model = new ModelAndView("view-invitations","sentInvitationsList",friendsService.getSentInvitations());
		model.addObject("sent/received","Sent invitations");
		model.addObject("from/to","Sent to");
		return model;
	}
	
	@RequestMapping(value="viewReceivedInvitations", method=RequestMethod.GET)
	public ModelAndView viewReceivedInvitations()
	{
		ModelAndView model = new ModelAndView("view-invitations","receivedInvitationsList",friendsService.getReceivedInvitations());
		model.addObject("sent/received","Received invitations");
		model.addObject("from/to","Received from");
		return model;
	}
	
	@RequestMapping(value="invite/{invited}", method=RequestMethod.POST)
	public ModelAndView inviteToFriends(@PathVariable String invited)
	{
		friendsService.addToFriendsList(invited);
			
		return new ModelAndView("redirect:invitationsSentList");
	}
	
	
}
