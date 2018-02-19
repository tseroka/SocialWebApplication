package com.app.web.social.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.app.web.social.model.Attachment;
import com.app.web.social.model.PrivateMessage;

public interface IMessagesService {

	public final static String REMOVAL_SIGN="#";

	public Page<PrivateMessage> getInbox(int pageNumber);
	public Long countInboxMessages();
	
	public Page<PrivateMessage> getOutbox(int pageNumber);
	public Long countOutboxMessages();
	
	public Page<PrivateMessage> getGlobalMessages(int pageNumber);
	
	public PrivateMessage getMessage(Long messageId);
	
	public Attachment getAttachment(Long attachmentId);
	
	public boolean isDownloadingAllowed(Attachment attachment, Long messageId);
	 
	public String removeMessage(Long messageId);
	
	public void removeAllMessages(String nickname);
	
	public boolean prepareAttachmentsAndValidateIfNotEmpty(PrivateMessage message);
	
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
		
		default Page<PrivateMessage> removeSignOfRemovalFromSenderAndReturnMessagesList(Page<PrivateMessage> messages)
		{ 
			List<PrivateMessage> inbox = messages.getContent();
			
			for(PrivateMessage currentMessage : inbox)
			{
					String sender = currentMessage.getMessageSender();
					currentMessage.setMessageSender(removeSignOfRemoval(sender));
			}
			return  new PageImpl<PrivateMessage>(inbox);
		}
		 
		default Page<PrivateMessage> returnMessagesListWithRemovedSignOfRemovalFromRecipients(Page<PrivateMessage> messages)
		{
			List<PrivateMessage> outbox = messages.getContent();
			
			for(PrivateMessage currentMessage : outbox)
			{
				List<String> recipients = currentMessage.getMessageRecipients();
				currentMessage.setMessageRecipients(removeSignOfRemovalFromRecipients(recipients));
			} 
			return new PageImpl<PrivateMessage>(outbox);
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
