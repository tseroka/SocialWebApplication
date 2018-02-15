package com.app.web.social.controller;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.GetMapping;

import com.app.web.social.service.IFriendsService;

@RequestMapping(value="/profile/friends/")
@Controller
public class FriendsController 
{
    
	@Autowired
	private IFriendsService friendsService;
	
	@GetMapping("viewFriends")
	public ModelAndView viewFriends(@CookieValue(value = "visitedProfiles", defaultValue = "") String cookieValue) throws IOException
	{		
		List<String> friendsList = friendsService.getFriendsList();
		ModelAndView model = new ModelAndView("friends/view-friends","friendsList",friendsList);
		boolean suggest = false;
		if(!cookieValue.equals(",") && !cookieValue.equals(""))
		{
			Set<String> suggestedFriends = friendsService.suggestFriends(cookieValue);
			suggest = true;
			model.addObject("suggestedFriends", suggestedFriends);
		}
		model.addObject("suggest", suggest);
		
		return model;
	}
	
	
	@GetMapping("viewSentInvitations")
	public ModelAndView viewSentInvitations()
	{
		List<String> sentInvitations = friendsService.getSentInvitations();
		return new ModelAndView("friends/view-sentInvitations","sentInvitationsList",sentInvitations);
	}
	
	
	@GetMapping("viewReceivedInvitations")
	public ModelAndView viewReceivedInvitations()
	{
	    List<String> receivedInvitations = 	friendsService.getReceivedInvitations();
		return new ModelAndView("friends/view-receivedInvitations","receivedInvitationsList",receivedInvitations);
	}
	
	@GetMapping("invitation/accept={accepted}")
	public String acceptInvitation(@PathVariable String accepted)
	{
		friendsService.acceptInvitationToFriendsList(accepted);
		return "redirect:/profile/friends/viewFriends";
	}
	 
	
	@GetMapping("invitation/decline={declined}")
	public String declineInvitation(@PathVariable String declined)
	{
		friendsService.declineInvitationToFriendsList(declined);
		return "redirect:/profile/friends/viewReceivedInvitations";
	}
	
	
	@GetMapping("invite={invited}")
	public String inviteToFriends(@PathVariable String invited)
	{
		friendsService.addToFriendsList(invited);
		return "redirect:/profile/view/"+invited;
	}	
	
	@GetMapping("remove={removed}")
	public ModelAndView removeFromFriendsList(@PathVariable String removed)
	{
		friendsService.removeFromFriendsList(removed);		
		return new ModelAndView("redirect:/profile/friends/viewFriends");
	}
}
