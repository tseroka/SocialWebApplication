package com.app.web.social.service;

import java.util.List;
import java.util.regex.Pattern;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.app.web.social.repository.MessagesRepository;
import com.app.web.social.repository.AttachmentRepository;
import com.app.web.social.model.PrivateMessage;
import com.app.web.social.model.Attachment;

@Service
@Transactional
public class MessagesService implements IMessagesService, InputCorrectness {

	@Autowired
	private MessagesRepository messagesRepository;
	
	@Autowired
	private AttachmentRepository attachmentRepository;
	
	@Autowired
	private IProfileService profileService;
	
	@Autowired
	private IFriendsService friendsService;
	
	@Autowired
	private IUniquenessService uniqueness;
	
	@Transactional(readOnly=true)
	public Page<PrivateMessage> getInbox(int pageNumber)
	{
		final PageRequest pageRequest =  PageRequest.of(pageNumber-1, 10);
		List<String> recipient = new ArrayList<>(); recipient.add(profileService.getAuthenticatedUserNickname());
		return removeSignOfRemovalFromSenderAndReturnMessagesList(messagesRepository.getInbox(recipient, pageRequest));
	}
	
	
	public Long countInboxMessages()
	{
		List<String> recipient = new ArrayList<>(); recipient.add(profileService.getAuthenticatedUserNickname());
		return messagesRepository.countInboxMessages(recipient);
	}
	
	@Transactional(readOnly=true)
	public Page<PrivateMessage> getOutbox(int pageNumber)
	{
		final PageRequest pageRequest =  PageRequest.of(pageNumber-1, 10);
		return returnMessagesListWithRemovedSignOfRemovalFromRecipients(messagesRepository.getOutbox(profileService.getAuthenticatedUserNickname(), pageRequest));	
	}
	
	public Long countOutboxMessages()
	{
		return messagesRepository.countOutboxMessages(profileService.getAuthenticatedUserNickname());
	}
	
	@Transactional(readOnly=true)
	public Page<PrivateMessage> getGlobalMessages(int pageNumber)
	{
		List<String> recipient = new ArrayList<>(); recipient.add("ALL");
		final PageRequest pageRequest =  PageRequest.of(pageNumber-1, 10);
		return messagesRepository.getInbox(recipient, pageRequest);
	}
	
	@Transactional(readOnly=true)
	public PrivateMessage getMessage(Long messageId)
	{
		PrivateMessage message = messagesRepository.findById(messageId).get();
		if(message.isAnyoneRemoved())
		{
		  String sender = message.getMessageSender();
		  List<String> recipients = message.getMessageRecipients();
		  message.setMessageSender(removeSignOfRemoval(sender));
		  message.setMessageRecipients(removeSignOfRemovalFromRecipients(recipients));
		}
		return message;
	}
	
	public Attachment getAttachment(Long attachmentId)
	{
		return attachmentRepository.findById(attachmentId).get();
	}
	
	public boolean isDownloadingAllowed(Attachment attachment, Long messageId)
	{
		String nickname = profileService.getAuthenticatedUserNickname();
		PrivateMessage message = getMessage(messageId);
		return ((message.getMessageSender().equals(nickname) || message.getMessageRecipients().contains(nickname)) && message.getAttachments().iterator().next().getAttachmentId().equals(attachment.getAttachmentId()));
	}
	
	public String removeMessage(Long messageId)
	{
		PrivateMessage messageToRemove = messagesRepository.findById(messageId).get();
		String redirect="/403";   String sender = messageToRemove.getMessageSender(); 
		List<String> recipients = messageToRemove.getMessageRecipients();   String remover = profileService.getAuthenticatedUserNickname();
      	
        if(sender.equals(remover)) 
		{
		      sender = sender+REMOVAL_SIGN;
		      messageToRemove.setMessageSender(sender);
		      redirect="/profile/messages/outbox";
		      messageToRemove.setAnyoneRemoved(true);
		      messagesRepository.save(messageToRemove);
	    }
		  
		if(recipients.contains(remover))
	    {
			recipients.remove(remover);
			recipients.add(remover+REMOVAL_SIGN);
			messageToRemove.setMessageRecipients(recipients); 
			redirect="/profile/messages/inbox";		
			messageToRemove.setAnyoneRemoved(true);
			messagesRepository.save(messageToRemove);
	    }		
		
		if( checkIsRemoved(messageToRemove.getMessageSender()) && checkAreRecipientsRemoved(messageToRemove.getMessageRecipients()) )
			messagesRepository.delete(messageToRemove);
		
		
		return redirect;
	}
	
	
	public void removeAllMessages(String nickname)
	{
        List<String> nicknameAsList = new ArrayList<String>(); nicknameAsList.add(nickname);
		
		List<PrivateMessage> allMessages = messagesRepository.getAllMessages(nicknameAsList, nickname);
		if(!allMessages.isEmpty())
		{
		  for(PrivateMessage message : allMessages)
		  {
			 removeMessageWhenDeletingAccount(message, nickname);
		  }
		}
	}
	
	private void removeMessageWhenDeletingAccount(PrivateMessage message, String nickname)
	{	
		String sender = message.getMessageSender(); 
		List<String> recipients = message.getMessageRecipients();
      	
        if(sender.equals(nickname)) 
		{
		      sender = sender+REMOVAL_SIGN;
		      message.setMessageSender(sender);
		      message.setAnyoneRemoved(true);
		      messagesRepository.save(message);
	    }
		
		if(recipients.contains(nickname))
	    {
			recipients.remove(nickname);
			recipients.add(nickname+REMOVAL_SIGN);
			message.setMessageRecipients(recipients); 		
			message.setAnyoneRemoved(true);
			messagesRepository.save(message);
	    }		
		
		if( checkIsRemoved(message.getMessageSender()) && checkAreRecipientsRemoved(message.getMessageRecipients()) )
			messagesRepository.delete(message);
		
	}
	
	public void sendMessage(PrivateMessage message)
	{
		messagesRepository.save(message);
	}

	public boolean isMessageSendingAllowed(List<String> recipients) 
	{
		for(String recipient : recipients)
		{
		  if( 
			  !Pattern.matches(NICKNAME_VALIDATION_REGEX, recipient) ||
		      uniqueness.isNicknameNotBusy(recipient) ||
		      !( friendsService.isFriend(recipient)|| profileService.getProfileByNickname(recipient).getAllowEveryoneToSendMessage() )
		    )
			return false;
		}
		
		return true;
	}
	
}
