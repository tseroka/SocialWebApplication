package com.app.web.social.dao;

import java.util.ArrayList;
import java.util.List;

import com.app.web.social.model.PrivateMessage;
import com.app.web.social.model.Attachment;


public interface MessagesDAO 
{
	public final static String REMOVAL_SIGN="#";

	public List<PrivateMessage> getInbox(int pageNumber);
	public Long countInboxMessages();
	
	public List<PrivateMessage> getOutbox(int pageNumber);
	public Long countOutboxMessages();
	
	public List<PrivateMessage> getGlobalMessages();
	
	public PrivateMessage getMessage(Long messageId);
	
	public Attachment getAttachment(Long attachmentId);
	
	public boolean isDownloadingAllowed(Attachment attachment, Long messageId);
	 
	public String removeMessage(Long messageId);
	
	public void removeAllMessages(String nickname);
	
	public void sendMessage(PrivateMessage message);

	public boolean isMessageSendingAllowed(List<String> recipients);
	
	
	
	
	
	
	
	//--------------------- D E F A U L T     R E M O V I N G     H E L P     M E T H O D S -------------------------------------------------
	
	default boolean checkIsRemoved(String sender)
	{
		return sender.substring(sender.length()-1,sender.length()).equals(REMOVAL_SIGN);
	}
	
	default boolean checkAreRecipientsRemoved(List<String> recipients)
	{
	    	for(String recipient : recipients)
	    	{
	    		if(!checkIsRemoved(recipient)) return false;
	    	}
	    	return true;
	}
	
	default String removeSignOfRemoval(String nickname)
	{
		if(checkIsRemoved(nickname)) return nickname.substring(0,nickname.length()-1) ;
		return nickname;
	}
	
	default List<PrivateMessage> removeSignOfRemovalFromSenderAndReturnMessagesList(List<PrivateMessage> messages)
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
	
	default List<PrivateMessage> returnMessagesListWithRemovedSignOfRemovalFromRecipients(List<PrivateMessage> messages)
	{
		for(PrivateMessage message : messages)
		{
			List<String> recipients = message.getMessageRecipients();
			message.setMessageRecipients(removeSignOfRemovalFromRecipients(recipients));
		}
		return messages;
	}
	
	default List<String> removeSignOfRemovalFromRecipients(List<String> recipients)
	{
		List<String> recipientsWithoutRemovalSign = new ArrayList<String>();
		for(String recipient : recipients) 
		{
			recipientsWithoutRemovalSign.add(removeSignOfRemoval(recipient));
		}
		return recipientsWithoutRemovalSign; 
	}
	
	
	
	
	
}
