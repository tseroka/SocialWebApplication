package com.app.web.social.dao;

import java.util.List;

import com.app.web.social.model.Friends;

public interface FriendsDAO 
{

	public Friends getAuthenticatedFriendsProfile();
	
	public void addToFriendsList(String nickname);
		
	public void acceptInvitationToFriendsList(String nickname);
		
	public void declineInvitationToFriendsList(String nickname);
	
	public void removeFromFriendsList(String nickname);
	
	public List<String> getReceivedInvitations();
	
	public List<String> getSentInvitations();
	
	public List<String> getFriendsList();

	public boolean isFriend(String nickname);
	 
	public boolean isInvited(String nickname);
}
