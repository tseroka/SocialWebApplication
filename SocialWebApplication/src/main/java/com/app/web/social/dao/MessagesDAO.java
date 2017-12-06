package com.app.web.social.dao;

import java.util.List;

import com.app.web.social.model.PrivateMessage;

public interface MessagesDAO 
{

	public List<PrivateMessage> getInbox(String nickname);
	
	public List<PrivateMessage> getOutbox(String nickname);

	public PrivateMessage getMessage(Long messageId);
	
	public void removeMessage(PrivateMessage message);
	
	public void sendMessage(PrivateMessage message);

	public boolean isMessageSendingAllowed(List<String> recipients);
}
