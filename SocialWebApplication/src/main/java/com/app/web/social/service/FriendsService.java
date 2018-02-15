package com.app.web.social.service;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.web.social.repository.FriendsRepository;
import com.app.web.social.model.Friends;

@Service
@Transactional
public class FriendsService implements IFriendsService
{
	@Autowired
	private FriendsRepository friendsRepository;
	
	@Autowired
	private IProfileService profileService;
	
	public Friends getAuthenticatedFriendsProfile()
	{
		return getFriendsProfileByNickname(profileService.getAuthenticatedUserNickname());
	}
	
	public boolean isProfileExist(String nickname)
	{
	    return friendsRepository.existsById(nickname);	
	}
	
	public Friends getFriendsProfileByNickname(String nickname)
	{
		return friendsRepository.findById(nickname).get();
	}	
	
	public void addToFriendsList(String getterNickname)
	{		
        String senderName = profileService.getAuthenticatedUserNickname();
		
		Friends getter = getFriendsProfileByNickname(getterNickname);
		getter.getInvitationsReceived().add(senderName);
		friendsRepository.save(getter);
		
		Friends sender = getFriendsProfileByNickname(senderName);
		sender.getInvitationsSent().add(getterNickname);
		friendsRepository.save(sender);
	}
		
    public void acceptInvitationToFriendsList(String nickname)
    {
        String acceptorName = profileService.getAuthenticatedUserNickname();
		
		Friends acceptor = getFriendsProfileByNickname(acceptorName);
		
		if(acceptor.getInvitationsReceived().contains(nickname))
		{
		  acceptor.getFriends().add(nickname);
		  acceptor.getInvitationsReceived().remove(nickname);
		  friendsRepository.save(acceptor);
		
	      Friends sender = getFriendsProfileByNickname(nickname);
		  sender.getInvitationsSent().remove(acceptorName);
		  sender.getFriends().add(acceptorName);
		  friendsRepository.save(sender);
		}
    }
		
    public void declineInvitationToFriendsList(String nickname)
    {
        String declinerName = profileService.getAuthenticatedUserNickname();
		
		Friends decliner = getFriendsProfileByNickname(declinerName);
		decliner.getInvitationsReceived().remove(nickname);
		friendsRepository.save(decliner);
		
		Friends rejected = getFriendsProfileByNickname(nickname);
		rejected.getInvitationsSent().remove(declinerName);
		friendsRepository.save(rejected);
    }
    
    public void removeFromFriendsList(String nickname)
    {
        String removerName = profileService.getAuthenticatedUserNickname();
		
		Friends remover = getFriendsProfileByNickname(removerName);
		remover.getFriends().remove(nickname);
		friendsRepository.save(remover);
		
		Friends removed = getFriendsProfileByNickname(nickname);
		removed.getFriends().remove(removerName);
		friendsRepository.save(removed);
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
			 if(!friends.isEmpty() && !isFriend(profile)) 
				 {
				   String suggestion = friends.get(rand.nextInt(friends.size()));
				   if(!isFriend(suggestion))
				   {
					  suggestions.add(suggestion);
				   }
				 }
		 }
		 
		 return suggestions;
	}
	

}
