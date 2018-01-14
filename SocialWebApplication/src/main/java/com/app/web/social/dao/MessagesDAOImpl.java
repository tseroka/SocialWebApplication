package com.app.web.social.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import java.sql.Timestamp;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.app.web.social.model.PrivateMessage;
import com.app.web.social.model.Attachment;
import com.app.web.social.dao.validations.Uniqueness;
import com.app.web.social.dao.validations.InputCorrectness;

@Repository
@Transactional
public class MessagesDAOImpl implements MessagesDAO, InputCorrectness {

	@Autowired
	private ProfileDAO profileDAO;
	
	@Autowired
	private FriendsDAO friendsDAO;
	
	@Autowired
	private Uniqueness uniqueness;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	 
	public List<PrivateMessage> getInbox() 
	{		
        List<String> oneRecipient = new ArrayList<String>(); oneRecipient.add(profileDAO.getAuthenticatedUserNickname());
			
		Query<PrivateMessage> query = this.sessionFactory.getCurrentSession().createQuery("from PrivateMessage Pm WHERE  (:oneRecipient) in Pm.messageRecipients ORDER BY Pm.sentDate desc",PrivateMessage.class)
				.setParameter("oneRecipient",oneRecipient);
				
		return removeSignOfRemovalFromSenderAndReturnMessagesList( (List<PrivateMessage>)query.list() );
	 }
	
	
	public List<PrivateMessage> getOutbox() 
	{
		Query<PrivateMessage> query = this.sessionFactory.getCurrentSession().createQuery("from PrivateMessage Pm WHERE Pm.messageSender =:msgSender ORDER BY Pm.sentDate desc ",PrivateMessage.class)
				.setParameter("msgSender",profileDAO.getAuthenticatedUserNickname());
	
		return returnMessagesListWithRemovedSignOfRemovalFromRecipients( query.list() );
	}
	
	public List<PrivateMessage> getGlobalMessages() 
	{		
		List<String> all = new ArrayList<String>(); all.add("ALL");
			
		Query<PrivateMessage> query = this.sessionFactory.getCurrentSession().createQuery("from PrivateMessage Pm WHERE (:all) in Pm.messageRecipients  ORDER BY Pm.sentDate desc",PrivateMessage.class)
				.setParameter("all",all);
				
	   return (List<PrivateMessage>)query.list();
	 }
	
	
	
	public PrivateMessage getMessage(Long messageId)
	{
		Session session = this.sessionFactory.getCurrentSession();		
		PrivateMessage message = session.load(PrivateMessage.class, messageId);
		String sender = message.getMessageSender();
		List<String> recipients = message.getMessageRecipients();
		message.setMessageSender(removeSignOfRemoval(sender));
		message.setMessageRecipients(removeSignOfRemovalFromRecipients(recipients));
		return message;
	}
	
	public Attachment getAttachment(Long attachmentId)
	{
		return (Attachment) this.sessionFactory.getCurrentSession().load(Attachment.class, attachmentId);	
	}
	
	public boolean isDownloadingAllowed(Attachment attachment, Long messageId)
	{
		String nickname = profileDAO.getAuthenticatedUserNickname();
		PrivateMessage message = getMessage(messageId);
		return ((message.getMessageSender().equals(nickname) || message.getMessageRecipients().contains(nickname)) && message.getAttachments().iterator().next().getAttachmentId().equals(attachment.getAttachmentId()));
	}
	
	public void downloadAttachment(Attachment attachment)
	{
		
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
	
	private List<PrivateMessage> removeSignOfRemovalFromSenderAndReturnMessagesList(List<PrivateMessage> messages)
	{
		List<PrivateMessage> inbox = new ArrayList<PrivateMessage>();
		for(PrivateMessage currentMessage : messages)
		{
				String sender = currentMessage.getMessageSender();
				currentMessage.setMessageSender(removeSignOfRemoval(sender));
				inbox.add(currentMessage); 
		}
		messages = null;
		return inbox; 
	}
	
	private List<PrivateMessage> returnMessagesListWithRemovedSignOfRemovalFromRecipients(List<PrivateMessage> messages)
	{
		for(PrivateMessage message : messages)
		{
			List<String> recipients = message.getMessageRecipients();
			message.setMessageRecipients(removeSignOfRemovalFromRecipients(recipients));
		}
		return messages;
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
		session.remove(message);
		
		else session.update(message);
	   
		return redirect;
	}
	
	public void sendMessage(PrivateMessage message)
	{
		Session session = this.sessionFactory.getCurrentSession();
		message.setSentDate(new Timestamp(System.currentTimeMillis()));
		session.persist(message);	
		Set<Attachment> attachments = message.getAttachments();
		if(!message.getAttachments().isEmpty())
		{
			for(Attachment attachment : attachments)
			{
				session.persist(attachment);
			}
		}
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
