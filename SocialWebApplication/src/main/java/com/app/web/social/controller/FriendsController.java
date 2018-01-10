package com.app.web.social.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.app.web.social.service.FriendsService;

@RequestMapping(value="/profile/friends/")
@Controller
public class FriendsController 
{
    
	@Autowired
	private FriendsService friendsService;
	
	@RequestMapping(value="viewFriends", method=RequestMethod.GET)
	public ModelAndView viewFriends()
	{
		return new ModelAndView("friends/view-friends","friendsList",friendsService.getFriendsList());
	}
	
	
	@RequestMapping(value="viewSentInvitations", method=RequestMethod.GET)
	public ModelAndView viewSentInvitations()
	{
		return new ModelAndView("friends/view-sentInvitations","sentInvitationsList",friendsService.getSentInvitations());
	}
	
	
	@RequestMapping(value="viewReceivedInvitations", method=RequestMethod.GET)
	public ModelAndView viewReceivedInvitations()
	{
		return new ModelAndView("friends/view-receivedInvitations","receivedInvitationsList",friendsService.getReceivedInvitations());
	}
	
	
	@RequestMapping(value="invitation/accept={accepted}", method=RequestMethod.GET)
	public String acceptInvitation(@PathVariable String accepted)
	{
		friendsService.acceptInvitationToFriendsList(accepted);
		return "redirect:/profile/friends/viewFriends";
	}
	 
	
	@RequestMapping(value="invitation/decline={declined}", method=RequestMethod.GET)
	public String declineInvitation(@PathVariable String declined)
	{
		friendsService.declineInvitationToFriendsList(declined);
		return "redirect:/profile/friends/viewReceivedInvitations";
	}
	
	
	@RequestMapping(value="invite={invited}", method=RequestMethod.GET)
	public String inviteToFriends(@PathVariable String invited)
	{
		friendsService.addToFriendsList(invited);
		return "redirect:/profile/view/"+invited;
	}	
	
	@RequestMapping(value="remove={removed}", method=RequestMethod.GET)
	public ModelAndView removeFromFriendsList(@PathVariable String removed)
	{
		friendsService.removeFromFriendsList(removed);		
		return new ModelAndView("redirect:/profile/friends/viewFriends");
	}
}
