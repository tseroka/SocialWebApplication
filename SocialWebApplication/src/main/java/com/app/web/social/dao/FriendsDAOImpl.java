package com.app.web.social.dao;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.web.social.model.Friends;

@Repository
@Transactional
public class FriendsDAOImpl extends SuperDAO<String, Friends> implements FriendsDAO
{
	@Autowired 
	private ProfileDAO profileDAO;
	
	public Friends getAuthenticatedFriendsProfile()
	{
		return loadEntityByPrimaryKey(profileDAO.getAuthenticatedUserNickname());
	}
	
	public Friends getFriendsProfileByNickname(String nickname)
	{
		return loadEntityByPrimaryKey(nickname);
	}
	
	
	public void addToFriendsList(String getterNickname)
	{		
		String senderName = profileDAO.getAuthenticatedUserNickname();
		
		Friends getter = loadEntityByPrimaryKey(getterNickname);
		getter.getInvitationsReceived().add(senderName);
		update(getter);
		
		Friends sender = loadEntityByPrimaryKey(senderName);
		sender.getInvitationsSent().add(getterNickname);
		update(sender);
	}
	
		
	public void acceptInvitationToFriendsList(String nickname)
	{
		String acceptorName = profileDAO.getAuthenticatedUserNickname();
		
		Friends acceptor = loadEntityByPrimaryKey(acceptorName);
		
		if(acceptor.getInvitationsReceived().contains(nickname))
		{
		  acceptor.getFriends().add(nickname);
		  acceptor.getInvitationsReceived().remove(nickname);
		  update(acceptor);
		
	      Friends sender = loadEntityByPrimaryKey(nickname);
		  sender.getInvitationsSent().remove(acceptorName);
		  sender.getFriends().add(acceptorName);
		  update(sender);
		}
	}
	
		
    public void declineInvitationToFriendsList(String nickname)
	{
		String declinerName = profileDAO.getAuthenticatedUserNickname();
		
		Friends decliner = loadEntityByPrimaryKey(declinerName);
		decliner.getInvitationsReceived().remove(nickname);
		update(decliner);
		
		Friends rejected = loadEntityByPrimaryKey(nickname);
		rejected.getInvitationsSent().remove(declinerName);
		update(rejected);
	}
	
	
	public void removeFromFriendsList(String nickname) 
	{
		String removerName = profileDAO.getAuthenticatedUserNickname();
		
		Friends remover = loadEntityByPrimaryKey(removerName);
		remover.getFriends().remove(nickname);
		update(remover);
		
		Friends removed = loadEntityByPrimaryKey(nickname);
		removed.getFriends().remove(removerName);
	    update(removed);
	}
	
	
	
	public List<String> getReceivedInvitations()
	{
		return getAuthenticatedFriendsProfile().getInvitationsReceived();
	}
	
		
	public List<String> getSentInvitations()
	{
		return getAuthenticatedFriendsProfile().getInvitationsSent();
	}
	
	
	public List<String> getFriendsList()
	{
		return getAuthenticatedFriendsProfile().getFriends();
	}
	
	
	public boolean isFriend(String nickname)
	{
		return getFriendsList().contains(nickname);
	}
	
	
	public boolean isInvited(String nickname)
	{
		return getSentInvitations().contains(nickname);
	}
	
	public Set<String> suggestFriends(String cookieValue)
	{
		 Random rand = new Random();
		 String[] visitedProfiles = cookieValue.split(",");
		 int length = visitedProfiles.length;
		 Set<String> randomProfiles = new HashSet<>();
		 
		 for(int i=0; i <= (length/2+1) || i <4; i++)
		 {
			 String randomProfile = visitedProfiles[rand.nextInt(length)];
			 if(!"".equals(randomProfile)) randomProfiles.add(randomProfile);
		 }
		 
		 Set<String> suggestions = new HashSet<>();
		 
		 for(String profile : randomProfiles)
		 {
			 List<String> friends = getFriendsProfileByNickname(profile).getFriends();
			 if(!friends.isEmpty() && !isFriend(profile)) suggestions.add(friends.get(rand.nextInt(friends.size())));
		 }
		 
		 return suggestions;
	}
	
}
