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
	private ProfileDAO profileDAO;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	public void addToFriendsList(String getterNickname)
	{
		Session session = this.sessionFactory.getCurrentSession();
		
		String senderName = profileDAO.getAuthenticatedUserNickname();
		
		Friends getter = (Friends) session.load(Friends.class,getterNickname);
		getter.getInvitationsReceived().add(senderName);
		session.update(getter);
		
		Friends sender = (Friends) session.load(Friends.class,senderName);
		sender.getInvitationsSent().add(getterNickname);
		session.update(sender);
	}
	
	
	
	public void acceptInvitationToFriendsList(String nickname)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String acceptorName = profileDAO.getAuthenticatedUserNickname();
		
		Friends acceptor = (Friends) session.load(Friends.class,acceptorName);
		acceptor.getFriends().add(nickname);
		acceptor.getInvitationsReceived().remove(nickname);
		session.update(acceptor);
		
		Friends sender = (Friends) session.load(Friends.class,nickname);
		sender.getInvitationsSent().remove(acceptorName);
		sender.getFriends().add(acceptorName);
		session.update(sender);
		
	}
	
	
	
	public void declineInvitationToFriendsList(String nickname)
	{
		String declinerName = profileDAO.getAuthenticatedUserNickname();
		
		Session session = this.sessionFactory.getCurrentSession();
		
		Friends decliner = (Friends) session.load(Friends.class,declinerName);
		decliner.getInvitationsReceived().remove(nickname);
		session.update(decliner);
		
		Friends rejected = (Friends) session.load(Friends.class,nickname);
		rejected.getInvitationsSent().remove(declinerName);
		session.update(rejected);
	}
	
	
	
	public void removeFromFriendsList(String nickname) 
	{
		Session session = this.sessionFactory.getCurrentSession();
		
		String removerName = profileDAO.getAuthenticatedUserNickname();
		
		Friends remover = (Friends) session.load(Friends.class,removerName);
		remover.getFriends().remove(nickname);
		session.update(remover);
		
		Friends removed = (Friends) session.load(Friends.class,nickname);
		removed.getFriends().remove(removerName);
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
	
	
	
	public List<String> getFriendsList()
	{
		Session session = this.sessionFactory.getCurrentSession();
		return ((Friends) session.load(Friends.class,profileDAO.getAuthenticatedUserNickname())).getFriends();
	}
	
	
	public boolean isFriend(String nickname)
	{
		Session session = this.sessionFactory.getCurrentSession();
		return ((Friends)session.load(Friends.class,profileDAO.getAuthenticatedUserNickname())).getFriends().contains(nickname);
	}
	
	
	public boolean isInvited(String nickname)
	{
		Session session = this.sessionFactory.getCurrentSession();
		return ((Friends)session.load(Friends.class,profileDAO.getAuthenticatedUserNickname())).getInvitationsSent().contains(nickname);
	}
	
}
