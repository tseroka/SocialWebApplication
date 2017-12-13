package com.app.web.social.dao;

import java.util.List;

import com.app.web.social.model.PrivateMessage;

public interface MessagesDAO 
{
	public final static String REMOVAL_SIGN="#";

	public List<PrivateMessage> getInbox(String nickname);
	
	public List<PrivateMessage> getOutbox(String nickname);

	public PrivateMessage getMessage(Long messageId);
	
	public String removeMessage(Long messageId);
	
	public void sendMessage(PrivateMessage message);

	public boolean isMessageSendingAllowed(List<String> recipients);
	
	
}
