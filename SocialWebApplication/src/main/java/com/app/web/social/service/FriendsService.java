package com.app.web.social.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.web.social.dao.FriendsDAO;

@Service
public class FriendsService 
{
	@Autowired
	private FriendsDAO friendsDAO;
	
	public void addToFriendsList(String nickname)
	{
		this.friendsDAO.addToFriendsList(nickname);
	}
		
    public void acceptInvitationToFriendsList(String nickname)
    {
    	this.friendsDAO.acceptInvitationToFriendsList(nickname);
    }
		
    public void declineInvitationToFriendsList(String nickname)
    {
    	this.friendsDAO.declineInvitationToFriendsList(nickname);
    }
    
    public void removeFromFriendsList(String nickname)
    {
    	this.friendsDAO.removeFromFriendsList(nickname);
    }
	
	public List<String> getReceivedInvitations()
	{
		return this.friendsDAO.getReceivedInvitations();
	}
	
	public List<String> getSentInvitations()
	{
		return this.friendsDAO.getSentInvitations();
	}
	
	public List<String> getFriends()
	{
		return this.friendsDAO.getFriends();
	}
	
	public boolean isFriend(String nickname)
	{
		return this.friendsDAO.isFriend(nickname);
	}
}
