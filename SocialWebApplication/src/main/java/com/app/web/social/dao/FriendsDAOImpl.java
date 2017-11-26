package com.app.web.social.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.web.social.model.Friends;

@Repository
@Transactional
public class FriendsDAOImpl implements FriendsDAO
{
	@Autowired 
	ProfileDAO profileDAO;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	public void addToFriendsList(String nickname)
	{
		Session session = this.sessionFactory.getCurrentSession();
		
		String sender = profileDAO.getAuthenticatedUserNickname();
		
		Friends friends = (Friends) session.load(Friends.class,nickname);
		friends.getInvitationsReceived().add(sender);
		
		Friends adding= (Friends) session.load(Friends.class,sender);
		adding.getInvitationsSent().add(nickname);
		
		session.update(friends);
		session.update(adding);
	}
	
	
	
	public void acceptInvitationToFriendsList(String nickname)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String acceptorName = profileDAO.getAuthenticatedUserNickname();
		
		Friends acceptor = (Friends) session.load(Friends.class,acceptorName);
		acceptor.getFriends().add(nickname);
		acceptor.getInvitationsReceived().remove(nickname);
		
		Friends sender = (Friends) session.load(Friends.class,nickname);
		sender.getInvitationsSent().remove(acceptorName);
		sender.getFriends().add(acceptorName);
		
		session.update(acceptor);
		session.update(sender);
		
	}
	
	
	
	public void declineInvitationToFriendsList(String nickname)
	{
		String declinerName = profileDAO.getAuthenticatedUserNickname();
		
		Session session = this.sessionFactory.getCurrentSession();
		
		Friends decliner = (Friends) session.load(Friends.class,declinerName);
		decliner.getInvitationsReceived().remove(nickname);
		
		Friends rejected = (Friends) session.load(Friends.class,nickname);
		rejected.getInvitationsSent().add(rejected.getInvitationsSent().indexOf(declinerName),declinerName+"(declined)");
		
		session.update(decliner);
		session.update(rejected);
	}
	
	
	
	public void removeFromFriendsList(String nickname) 
	{
		Session session = this.sessionFactory.getCurrentSession();
		
		String removerName = profileDAO.getAuthenticatedUserNickname();
		
		Friends remover = (Friends) session.load(Friends.class,removerName);
		remover.getFriends().remove(nickname);
		
		Friends removed = (Friends) session.load(Friends.class,nickname);
		removed.getFriends().remove(removerName);
		
		session.update(remover);
	    session.update(removed);
	}
	
	
	
	public List<String> getReceivedInvitations()
	{
		Session session = this.sessionFactory.getCurrentSession();
		return ((Friends) session.load(Friends.class,profileDAO.getAuthenticatedUserNickname())).getInvitationsReceived();
	}
	
	
	
	public List<String> getSentInvitations()
	{
		Session session = this.sessionFactory.getCurrentSession();
		return ((Friends) session.load(Friends.class,profileDAO.getAuthenticatedUserNickname())).getInvitationsSent();
	}
	
	
	
	public List<String> getFriends()
	{
		Session session = this.sessionFactory.getCurrentSession();
		return ((Friends) session.load(Friends.class,profileDAO.getAuthenticatedUserNickname())).getFriends();
	}
	
	
	
	public boolean isFriend(String nickname)
	{
		Session session = this.sessionFactory.getCurrentSession();
		return ((Friends)session.load(Friends.class,nickname)).getFriends().contains(profileDAO.getAuthenticatedUserNickname());
	}
	
}
