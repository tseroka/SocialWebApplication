package com.app.web.social.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import java.sql.Timestamp;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.web.social.model.PrivateMessage;
import com.app.web.social.model.Attachment;
import com.app.web.social.dao.validations.Uniqueness;
import com.app.web.social.dao.validations.InputCorrectness;
import com.app.web.social.dao.SuperDAO;

@Repository
@Transactional
public class MessagesDAOImpl extends SuperDAO<Long, PrivateMessage>  implements MessagesDAO, InputCorrectness {

	@Autowired
	private ProfileDAO profileDAO;
	
	@Autowired
	private FriendsDAO friendsDAO;
	
	@Autowired
	private Uniqueness uniqueness;
	
	
	 
	public List<PrivateMessage> getInbox(int pageNumber) 
	{		
		int pageSize = 10;
        List<String> oneRecipient = new ArrayList<String>(); oneRecipient.add(profileDAO.getAuthenticatedUserNickname());
			
		Query<PrivateMessage> query = getSession().createQuery("from PrivateMessage Pm WHERE  (:oneRecipient) in Pm.messageRecipients ORDER BY Pm.sentDate desc",PrivateMessage.class)
				.setFirstResult((pageNumber - 1) * pageSize).setMaxResults(pageSize)
				.setParameter("oneRecipient",oneRecipient);
				
		return removeSignOfRemovalFromSenderAndReturnMessagesList( query.list() );
	 }
	
	 public Long countInboxMessages()
	 {      
	        List<String> oneRecipient = new ArrayList<String>(); oneRecipient.add(profileDAO.getAuthenticatedUserNickname());
	    	return getSession().createQuery("select count(Pm.id) from PrivateMessage Pm WHERE  (:oneRecipient) in Pm.messageRecipients", Long.class)
	    			.setParameter("oneRecipient",oneRecipient).uniqueResult();
	 }
	    
	
	public List<PrivateMessage> getOutbox(int pageNumber) 
	{
		int pageSize = 10;
		Query<PrivateMessage> query = getSession().createQuery("from PrivateMessage Pm WHERE Pm.messageSender =:msgSender ORDER BY Pm.sentDate desc ",PrivateMessage.class)
				.setFirstResult((pageNumber - 1) * pageSize).setMaxResults(pageSize)
				.setParameter("msgSender",profileDAO.getAuthenticatedUserNickname());
	
		return returnMessagesListWithRemovedSignOfRemovalFromRecipients( query.list() );
	}
	
	 public Long countOutboxMessages()
	 {      
			return getSession().createQuery("select count(Pm.id) from PrivateMessage Pm WHERE Pm.messageSender =:msgSender ORDER BY Pm.sentDate desc ",Long.class)
			.setParameter("msgSender",profileDAO.getAuthenticatedUserNickname()).uniqueResult();
	 }
	    
	 
	public List<PrivateMessage> getGlobalMessages() 
	{		
		List<String> all = new ArrayList<String>(); all.add("ALL");
			
		Query<PrivateMessage> query = getSession().createQuery("from PrivateMessage Pm WHERE (:all) in Pm.messageRecipients  ORDER BY Pm.sentDate desc",PrivateMessage.class)
				.setParameter("all",all);
				
	   return query.list();
	 }
	
	
	
	public PrivateMessage getMessage(Long messageId)
	{	
		PrivateMessage message = loadEntityByPrimaryKey(messageId);
		if(message.isAnyoneRemoved())
		{
		  String sender = message.getMessageSender();
		  List<String> recipients = message.getMessageRecipients();
		  message.setMessageSender(removeSignOfRemoval(sender));
		  message.setMessageRecipients(removeSignOfRemovalFromRecipients(recipients));
		}
		return message;
	}
	
	public Attachment getAttachment(Long attachmentId)
	{
		return getSession().get(Attachment.class, attachmentId);	
	}
	
	public boolean isDownloadingAllowed(Attachment attachment, Long messageId)
	{
		String nickname = profileDAO.getAuthenticatedUserNickname();
		PrivateMessage message = getMessage(messageId);
		return ((message.getMessageSender().equals(nickname) || message.getMessageRecipients().contains(nickname)) && message.getAttachments().iterator().next().getAttachmentId().equals(attachment.getAttachmentId()));
	}

	public String removeMessage(Long messageId)
	{	
		PrivateMessage message = loadEntityByPrimaryKey(messageId);
		String redirect="";   String sender = message.getMessageSender(); 
		List<String> recipients = message.getMessageRecipients();   String remover = profileDAO.getAuthenticatedUserNickname();
      	
        if(sender.equals(remover)) 
		{
		      sender = sender+REMOVAL_SIGN;
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
		else redirect="/403";
		
		if( checkIsRemoved(message.getMessageSender()) && checkAreRecipientsRemoved(message.getMessageRecipients()) )
		   remove(message);
		
		else 
		{   
			message.setAnyoneRemoved(true);
			update(message);
		}
		
		return redirect;
	}

	public void removeAllMessages(String nickname)
	{
		List<String> oneRecipient = new ArrayList<String>(); oneRecipient.add(nickname);
		Query<PrivateMessage> inboxQuery = getSession().createQuery("from PrivateMessage Pm WHERE  (:oneRecipient) in Pm.messageRecipients ORDER BY Pm.sentDate desc",PrivateMessage.class)
				.setParameter("oneRecipient",oneRecipient);
		
		List<PrivateMessage> inbox = inboxQuery.list();
		
		Query<PrivateMessage> outboxQuery = getSession().createQuery("from PrivateMessage Pm WHERE Pm.messageSender =:msgSender ORDER BY Pm.sentDate desc ",PrivateMessage.class)
				.setParameter("msgSender",nickname);
		
		List<PrivateMessage> outbox = outboxQuery.list();
		
		for(PrivateMessage message : inbox)
		{
			removeMessageWhenDeletingAccount(message, nickname);
		}
		
		for(PrivateMessage message : outbox)
		{
			removeMessageWhenDeletingAccount(message, nickname);
		}
	}
	
	
	private void removeMessageWhenDeletingAccount(PrivateMessage message, String nickname)
	{	
		String sender = message.getMessageSender(); 
		List<String> recipients = message.getMessageRecipients();
      	
        if(sender.equals(nickname)) 
		{
		      sender = sender+REMOVAL_SIGN;
		      message.setMessageSender(sender);
	    }
		
		else if(recipients.contains(nickname))
	    {
			recipients.remove(nickname);
			recipients.add(nickname+REMOVAL_SIGN);
			message.setMessageRecipients(recipients); 		
	    }		
		
		if( checkIsRemoved(message.getMessageSender()) && checkAreRecipientsRemoved(message.getMessageRecipients()) )
		   remove(message);
		
		else 
		{   
			message.setAnyoneRemoved(true);
			update(message);
		}		
	}

	public void sendMessage(PrivateMessage message)
	{
		message.setSentDate(new Timestamp(System.currentTimeMillis()));
		message.setAnyoneRemoved(false);
		persist(message);	
	}
 
	public boolean isMessageSendingAllowed(List<String> recipients)
	{
		for(String recipient : recipients)
		{
		  if( 
			  !Pattern.matches(NICKNAME_VALIDATION_REGEX, recipient) ||
		      uniqueness.isNicknameNotBusy(recipient) ||
		      !( friendsDAO.isFriend(recipient)|| profileDAO.getProfileByNickname(recipient).getAllowEveryoneToSendMessage() )
		    )
			return false;
		}
		
		return true;
	}
}
