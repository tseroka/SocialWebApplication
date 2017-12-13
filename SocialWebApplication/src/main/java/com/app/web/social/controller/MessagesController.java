package com.app.web.social.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


import com.app.web.social.model.PrivateMessage;
import com.app.web.social.service.ProfileService;
import com.app.web.social.service.MessagesService;


@RequestMapping(value="/profile/messages/")
@Controller
public class MessagesController {

	
	  @Autowired
	  private MessagesService messagesService;
	  
	  @Autowired
	  private ProfileService profileService;
	  
	  /**
	  @RequestMapping(value="send")
	   public ModelAndView sendMessage()
	   {   
		   return new ModelAndView("sendMessage", "newMessage", new PrivateMessage() );
	   } */
	   
	  
	   @RequestMapping(value="send/recipient={recipient}")
	   public ModelAndView sendMessageFromProfileViewButton(@PathVariable String recipient)
	   {   
		   PrivateMessage newMessage = new PrivateMessage();
		   if(recipient.equals("") || recipient==null);
		   else 
		   {
		   List<String> recipients = new ArrayList<String>();
		   recipients.add(recipient);		   
		   newMessage.setMessageRecipients(recipients);
		   }	   
		   return new ModelAndView("messages/sendMessage", "newMessage", newMessage );
	   }
	   
	   
	   @RequestMapping(value="sendProcessing", method = RequestMethod.POST)
	   public ModelAndView sendingMessageProcessing(@ModelAttribute("newMessage") PrivateMessage newMessage)
	   {   
		 newMessage.setMessageSender( profileService.getAuthenticatedUserNickname() );
		   List<String> recipients = newMessage.getMessageRecipients();
		   if
		   (   
			   !recipients.isEmpty()
				        && 
			   !newMessage.getMessageSubject().equals("")
			            &&
			            messagesService.isMessageSendingAllowed(recipients)
		  
		   ) 
		     {  
			   messagesService.sendMessage(newMessage); 
		        return new ModelAndView("redirect:outbox");
		     }
		   
		   return new ModelAndView("messages/sendMessage","emptyRecipientsOrSubject", "Neither subject or recipients can be empty or sending recipient(s) not allowed.");
		 
	   }
	    
	   @RequestMapping(value="remove/{id}")
	   public ModelAndView removeMessage(@PathVariable Long id)
	   {
		   return new ModelAndView("redirect:"+messagesService.removeMessage(id));
	   }
	     
	    @RequestMapping(value="inbox")
	    public ModelAndView getInbox()
	    {
	    	return new ModelAndView("messages/inbox","inboxMessages",messagesService.getInbox( profileService.getAuthenticatedUserNickname()) );
	    }
	   
	  
	  
	    @RequestMapping(value="outbox")
	    public ModelAndView getOutbox()
	    {
	    	List<PrivateMessage> outboxMessages = messagesService.getOutbox( profileService.getAuthenticatedUserNickname() );
	    	return new ModelAndView("messages/outbox","outboxMessages",outboxMessages);
	    }
	   
	    
	    
	    @RequestMapping(value="inbox/{id}")
	    public ModelAndView getInMessage(@PathVariable Long id )
	    {
	    	
	    	String nickname = profileService.getAuthenticatedUserNickname();
	    	PrivateMessage message = messagesService.getMessage(id);
	    	if(  message.getMessageRecipients().contains(nickname) ) 
	    	{
	    		return new ModelAndView("messages/inMessage","message",message);
	    	}
	    	
	    	else { message=null; }
	    	
	    	return new ModelAndView("403");
	    }
	    
	    
	    
	    @RequestMapping(value="outbox/{id}")
	    public ModelAndView getOutMessage(@PathVariable Long id )
	    {
	    	String nickname = profileService.getAuthenticatedUserNickname();
	    	PrivateMessage message = messagesService.getMessage(id);
	    	if(  nickname.equals(message.getMessageSender()) ) 
	    	{ 
	    		return new ModelAndView("messages/outMessage","message",message);
	    	}
	    	
	    	else { message=null; }
	    	return new ModelAndView("403");
	    }
	       
}
