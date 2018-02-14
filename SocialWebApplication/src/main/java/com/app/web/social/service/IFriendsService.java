package com.app.web.social.service;

import java.util.List;
import java.util.Set;

import com.app.web.social.model.Friends;

public interface IFriendsService {

    public Friends getAuthenticatedFriendsProfile();
	
    public boolean isProfileExist(String nickname);
    
	public Friends getFriendsProfileByNickname(String nickname);

	public void addToFriendsList(String nickname);
		
	public void acceptInvitationToFriendsList(String nickname);
		
	public void declineInvitationToFriendsList(String nickname);
	
	public void removeFromFriendsList(String nickname);
	
	public List<String> getReceivedInvitations();
	
	public List<String> getSentInvitations();
	
	public List<String> getFriendsList();

	public boolean isFriend(String nickname);
	 
	public boolean isInvited(String nickname);
	
	public Set<String> suggestFriends(String cookieValue);
	
}
