package com.app.web.social.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.web.social.dao.MessagesDAO;
import com.app.web.social.model.PrivateMessage;
import com.app.web.social.model.PrivateMessageAttachment;
import com.app.web.social.model.FileWrapper;

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
	
	public String removeMessage(Long messageId)
	{
		return this.messagesDAO.removeMessage(messageId);
	}
	
	public void sendMessage(PrivateMessage message)
	{
		this.messagesDAO.sendMessage(message);
	}
	
	public void sendMessage(PrivateMessage message, FileWrapper fileWrapper) throws IOException
	{
		this.messagesDAO.sendMessage(message, fileWrapper);
	}
	
	public void sendMessageAttachment(PrivateMessageAttachment messageAttachment) throws IOException
	{
		this.messagesDAO.sendMessageAttachment(messageAttachment);
	}
	
	public void uploadFile(FileWrapper fileWrapper) throws IOException
	{
		this.messagesDAO.uploadFile(fileWrapper);
	}
	
	public boolean isMessageSendingAllowed(List<String> recipients) 
	{
	    return this.messagesDAO.isMessageSendingAllowed(recipients);
	}
	
}
