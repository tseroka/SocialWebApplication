package com.app.web.social.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.web.social.dao.MessagesDAO;
import com.app.web.social.model.PrivateMessage;
import com.app.web.social.model.Attachment;

@Service
public class MessagesService {

	@Autowired
	private MessagesDAO messagesDAO;
	
	public List<PrivateMessage> getInbox()
	{
		return this.messagesDAO.getInbox();
	}
	
	public List<PrivateMessage> getOutbox()
	{
		return this.messagesDAO.getOutbox();
	}
	
	public List<PrivateMessage> getGlobalMessages()
	{
		return this.messagesDAO.getGlobalMessages();
	}
	
	public PrivateMessage getMessage(Long messageId)
	{
		return this.messagesDAO.getMessage(messageId);
	}
	
	public Attachment getAttachment(Long attachmentId)
	{
		return this.messagesDAO.getAttachment(attachmentId);
	}
	
	public void downloadAttachment(Attachment attachment)
	{
		this.messagesDAO.downloadAttachment(attachment);
	}
	
	public boolean isDownloadingAllowed(Attachment attachment, Long messageId)
	{
	   return this.messagesDAO.isDownloadingAllowed(attachment, messageId);	
	}
	
	public String removeMessage(Long messageId)
	{
		return this.messagesDAO.removeMessage(messageId);
	}
	
	public void sendMessage(PrivateMessage message)
	{
		this.messagesDAO.sendMessage(message);
	}

	public boolean isMessageSendingAllowed(List<String> recipients) 
	{
	    return this.messagesDAO.isMessageSendingAllowed(recipients);
	}
	
}
