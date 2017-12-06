package com.app.web.social.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.web.social.model.PrivateMessage;

@Repository
@Transactional
public class MessagesDAOImpl implements MessagesDAO {

	@Autowired
	private ProfileDAO profileDAO;
	
	@Autowired
	private FriendsDAO friendsDAO;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	public List<PrivateMessage> getInbox(String nickname) {

        List<PrivateMessage> inbox = new ArrayList<PrivateMessage>();
		
		Session session = this.sessionFactory.openSession();
		
		//List<String> rec = new ArrayList<String>();
	//	rec.add(nickname);
		Query<?> query=session.createQuery("from PrivateMessage");
		//query.setParameter("recipient",rec);
        
		
		List<PrivateMessage> messages = (List<PrivateMessage>)query.list(); 
		
		for(int i=0;i<messages.size();i++)
		{
			PrivateMessage currentMessage = messages.get(i);
			if( currentMessage.getMessageRecipients().contains(nickname) )
				 inbox.add(currentMessage);
		} 
		return inbox;       
		//return (List<PrivateMessage>) query.list();
	    }
	
	
	@SuppressWarnings("unchecked")
	public List<PrivateMessage> getOutbox(String nickname) {
		Session session = this.sessionFactory.openSession();
		String hql = "from PrivateMessage P where P.messageSender =:msgSender";
		Query<?> query = session.createQuery(hql).setParameter("msgSender",nickname);
		return (List<PrivateMessage>)query.list(); 
	}
	
	public PrivateMessage getMessage(Long messageId)
	{
		Session session = this.sessionFactory.openSession();		
		PrivateMessage message = (PrivateMessage) session.load(PrivateMessage.class, messageId);
		return message;
	}
	
	public void removeMessage(PrivateMessage message)
	{
		String sender = message.getMessageSender();
		List<String> recipients = message.getMessageRecipients();
		String remover = profileDAO.getAuthenticatedUserNickname();

		if(sender.equals(remover)) message.setMessageSender("");
		
		else if(recipients.contains(remover))
			{
			recipients.remove(remover);
			message.setMessageRecipients(recipients);
			}
		
		Session session = this.sessionFactory.getCurrentSession();		
		
		if(message.getMessageSender().equals("") && message.getMessageRecipients().isEmpty() )
		session.remove(message);
		
		else session.update(message);
			
	}
	
	public void sendMessage(PrivateMessage message)
	{
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(message);	
	}	
	
	public boolean isMessageSendingAllowed(List<String> recipients)
	{
		for(String recipient : recipients)
		{
		  if(!(friendsDAO.isFriend(recipient)|| profileDAO.getProfileByNickname(recipient).getAllowEveryoneToSendMessage()))
			return false;
		}
		
		return true;
	}
}
