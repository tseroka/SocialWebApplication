package com.app.web.social.dao;

import java.io.IOException;
import java.util.List;

import com.app.web.social.model.PrivateMessage;
import com.app.web.social.model.PrivateMessageAttachment;
import com.app.web.social.model.FileWrapper;

public interface MessagesDAO 
{
	public final static String REMOVAL_SIGN="#";

	public List<PrivateMessage> getInbox();
	
	public List<PrivateMessage> getOutbox();

	public List<PrivateMessage> getGlobalMessages();
	
	public PrivateMessage getMessage(Long messageId);
	
	public String removeMessage(Long messageId);
	
	public void sendMessage(PrivateMessage message);
	
	public void sendMessage(PrivateMessage message, FileWrapper fileWrapper) throws IOException;

	public void sendMessageAttachment(PrivateMessageAttachment messageAttachment) throws IOException;
	
	public void uploadFile(FileWrapper fileWrapper) throws IOException;
	
	public boolean isMessageSendingAllowed(List<String> recipients);
	
	
}
