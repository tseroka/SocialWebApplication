package com.app.web.social.dao;

import java.io.IOException;
import java.util.List;

import com.app.web.social.model.PrivateMessage;
import com.app.web.social.model.Attachment;


public interface MessagesDAO 
{
	public final static String REMOVAL_SIGN="#";

	public List<PrivateMessage> getInbox();
	
	public List<PrivateMessage> getOutbox();

	public List<PrivateMessage> getGlobalMessages();
	
	public PrivateMessage getMessage(Long messageId);
	
	public Attachment getAttachment(Long attachmentId);
	
	public boolean isDownloadingAllowed(Attachment attachment, Long messageId);
	
	public void downloadAttachment(Attachment attachment);
	
	public String removeMessage(Long messageId);
	
	public void sendMessage(PrivateMessage message);

	public boolean isMessageSendingAllowed(List<String> recipients);
	
	
}
