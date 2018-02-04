package com.app.web.social.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.web.social.dao.FriendsDAO;
import com.app.web.social.model.Friends;

@Service
public class FriendsService 
{
	@Autowired
	private FriendsDAO friendsDAO;
	
	public Friends getAuthenticatedFriendsProfile()
	{
		return this.friendsDAO.getAuthenticatedFriendsProfile();
	}
	
	public Friends getFriendsProfileByNickname(String nickname)
	{
		return this.friendsDAO.getFriendsProfileByNickname(nickname);
	}	
	
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
	
	public List<String> getFriendsList()
	{
		return this.friendsDAO.getFriendsList();
	}
	
	public boolean isFriend(String nickname)
	{
		return this.friendsDAO.isFriend(nickname);
	}
	
	public boolean isInvited(String nickname)
	{
		return this.friendsDAO.isInvited(nickname);
	}
	
	public Set<String> suggestFriends(String cookieValue)
	{
		return this.friendsDAO.suggestFriends(cookieValue);
	}
}
