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

        List<String> oneRecipient = new ArrayList<String>(); oneRecipient.add(nickname);
		Session session = this.sessionFactory.openSession();
			
		Query<?> query = session.createQuery("from PrivateMessage Pm where (:oneRecipient) in Pm.messageRecipients")
				.setParameter("oneRecipient",oneRecipient);
		
		List<PrivateMessage> inbox = (List<PrivateMessage>)query.list(); 
		
		for(PrivateMessage currentMessage : inbox)
		{
		//	if( currentMessage.getMessageRecipients().contains(nickname) )
			//{   
			    inbox.remove(currentMessage);
				String sender = currentMessage.getMessageSender();
				currentMessage.setMessageSender(removeSignOfRemoval(sender));
				inbox.add(currentMessage); 
			//}
		} 
		return inbox;       
	    }
	
	
	@SuppressWarnings("unchecked")
	public List<PrivateMessage> getOutbox(String nickname) {
		Session session = this.sessionFactory.openSession();
		String hql = "from PrivateMessage P where P.messageSender =:msgSender";
		Query<?> query = session.createQuery(hql).setParameter("msgSender",nickname);
		List<PrivateMessage> messages = (List<PrivateMessage>)query.list(); 
		
		for(PrivateMessage message : messages)
		{
			List<String> recipients = message.getMessageRecipients();
			message.setMessageRecipients(removeSignOfRemovalFromRecipients(recipients));
		}
		return messages;
	}
	
	
	public PrivateMessage getMessage(Long messageId)
	{
		Session session = this.sessionFactory.openSession();		
		PrivateMessage message = (PrivateMessage) session.load(PrivateMessage.class, messageId);
		String sender = message.getMessageSender();
		List<String> recipients = message.getMessageRecipients();
		message.setMessageSender(removeSignOfRemoval(sender));
		message.setMessageRecipients(removeSignOfRemovalFromRecipients(recipients));
		return message;
	}
	
	
	//------------------------------------REMOVING HELP METHODS ----------------------------------------------
	private boolean checkIsRemoved(String sender)
	{
		return sender.substring(sender.length()-1,sender.length()).equals(REMOVAL_SIGN);
	}
	
	private boolean checkAreRecipientsRemoved(List<String> recipients)
	{
	    	for(String recipient : recipients)
	    	{
	    		if(!checkIsRemoved(recipient)) return false;
	    	}
	    	return true;
	}
	
	private String removeSignOfRemoval(String nickname)
	{
		if(checkIsRemoved(nickname)) return nickname.substring(0,nickname.length()-1) ;
		return nickname;
	}
	
	private List<String> removeSignOfRemovalFromRecipients(List<String> recipients)
	{
		List<String> recipientsWithoutRemovalSign = new ArrayList<String>();
		for(String recipient : recipients) 
		{
			recipientsWithoutRemovalSign.add(removeSignOfRemoval(recipient));
		}
		return recipientsWithoutRemovalSign; 
	}
	//--------------------------------------------------------------------------------------------------------------
 
    
	public String removeMessage(Long messageId)
	{
		Session session = this.sessionFactory.getCurrentSession();		
		PrivateMessage message = (PrivateMessage) session.load(PrivateMessage.class, messageId);
		String redirect="";
		String sender = message.getMessageSender();
		List<String> recipients = message.getMessageRecipients();
		String remover = profileDAO.getAuthenticatedUserNickname();

		if(sender.equals(remover)) 
		    {
		      sender=sender+REMOVAL_SIGN;
		      message.setMessageSender(sender);
		      redirect="/profile/messages/outbox";
			}
		
		else if(recipients.contains(remover))
			{
			recipients.remove(remover);
			recipients.add(remover+REMOVAL_SIGN);
			message.setMessageRecipients(recipients);
			redirect="/profile/messages/inbox";
			}		
		else redirect="/SocialWebApplication/403";
		
		if( checkIsRemoved(message.getMessageSender()) && checkAreRecipientsRemoved(message.getMessageRecipients()) )
		session.remove(message);
		
		else session.update(message);
	   
		return redirect;
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
