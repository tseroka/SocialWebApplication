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

@Repository
@Transactional
public class MessagesDAOImpl extends SuperDAO<Long, PrivateMessage>  implements MessagesDAO, InputCorrectness {

	@Autowired
	private ProfileDAO profileDAO;
	
	@Autowired
	private FriendsDAO friendsDAO;
	
	@Autowired
	private Uniqueness uniqueness;
	
	
	 
	public List<PrivateMessage> getInbox() 
	{		
        List<String> oneRecipient = new ArrayList<String>(); oneRecipient.add(profileDAO.getAuthenticatedUserNickname());
			
		Query<PrivateMessage> query = getSession().createQuery("from PrivateMessage Pm WHERE  (:oneRecipient) in Pm.messageRecipients ORDER BY Pm.sentDate desc",PrivateMessage.class)
				.setParameter("oneRecipient",oneRecipient);
				
		return removeSignOfRemovalFromSenderAndReturnMessagesList( query.list() );
	 }
	
	
	public List<PrivateMessage> getOutbox() 
	{
		Query<PrivateMessage> query = getSession().createQuery("from PrivateMessage Pm WHERE Pm.messageSender =:msgSender ORDER BY Pm.sentDate desc ",PrivateMessage.class)
				.setParameter("msgSender",profileDAO.getAuthenticatedUserNickname());
	
		return returnMessagesListWithRemovedSignOfRemovalFromRecipients( query.list() );
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
		String sender = message.getMessageSender();
		List<String> recipients = message.getMessageRecipients();
		message.setMessageSender(removeSignOfRemoval(sender));
		message.setMessageRecipients(removeSignOfRemovalFromRecipients(recipients));
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
		
		else update(message);
		
		return redirect;
	}

	
	public void sendMessage(PrivateMessage message)
	{
		message.setSentDate(new Timestamp(System.currentTimeMillis()));
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
