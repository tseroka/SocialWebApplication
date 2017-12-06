package com.app.web.social.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.web.social.dao.MessagesDAO;
import com.app.web.social.model.PrivateMessage;

@Service
public class MessagesService {

	@Autowired
	private MessagesDAO messagesDAO;
	
	public List<PrivateMessage> getInbox(String nickname)
	{
		return this.messagesDAO.getInbox(nickname);
	}
	
	public List<PrivateMessage> getOutbox(String nickname)
	{
		return this.messagesDAO.getOutbox(nickname);
	}
	
	public PrivateMessage getMessage(Long messageId)
	{
		return this.messagesDAO.getMessage(messageId);
	}
	
	public void removeMessage(PrivateMessage message)
	{
		this.messagesDAO.removeMessage(message);
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
