package com.app.web.social.dao;

import java.util.List;

public interface FriendsDAO 
{

	public void addToFriendsList(String nickname);
		
	public void acceptInvitationToFriendsList(String nickname);
		
	public void declineInvitationToFriendsList(String nickname);
	
	public void removeFromFriendsList(String nickname);
	
	public List<String> getReceivedInvitations();
	
	public List<String> getSentInvitations();
	
	public List<String> getFriends();

	public boolean isFriend(String nickname);
	
}
