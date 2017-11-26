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


@RequestMapping(value="/profile/messages/")
@Controller
public class MessagesController {

	
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
		   return new ModelAndView("sendMessage", "newMessage", newMessage );
	   }
	   
	   
	   @RequestMapping(value="sendProcessing", method = RequestMethod.POST)
	   public ModelAndView sendingMessageProcessing(@ModelAttribute("newMessage") PrivateMessage newMessage)
	   {   
		 newMessage.setMessageSender( profileService.getAuthenticatedUserNickname() );
		 
		   if
		   (   
			   !newMessage.getMessageRecipients().isEmpty()
				        && 
			   !newMessage.getMessageSubject().equals("")
		   ) 
		     {  
		        profileService.sendMessage(newMessage); 
		        return new ModelAndView("outbox","outboxMessages",profileService.getInbox( profileService.getAuthenticatedUserNickname()) );
		     }
		   
		   return new ModelAndView("sendMessage","emptyRecipientsOrSubject", "Neither subject or recipients can be empty ");
		 
	   }
	    
	     
	    @RequestMapping(value="inbox")
	    public ModelAndView getInbox()
	    {
	        List<PrivateMessage> inboxMessages = profileService.getInbox( profileService.getAuthenticatedUserNickname() );
	    	return new ModelAndView("inbox","inboxMessages",inboxMessages);
	    }
	   
	  
	  
	    @RequestMapping(value="outbox")
	    public ModelAndView getOutbox()
	    {
	    	List<PrivateMessage> outboxMessages = profileService.getOutbox( profileService.getAuthenticatedUserNickname() );
	    	return new ModelAndView("outbox","outboxMessages",outboxMessages);
	    }
	   
	    
	    
	    @RequestMapping(value="inbox/{id}")
	    public ModelAndView getInMessage(@PathVariable Long id )
	    {
	    	
	    	String nickname = profileService.getAuthenticatedUserNickname();
	    	PrivateMessage message = profileService.getMessage(id);
	    	if(  message.getMessageRecipients().contains(nickname) ) 
	    	{
	    		return new ModelAndView("inMessage","message",message);
	    	}
	    	
	    	else { message=null; }
	    	
	    	return new ModelAndView("403");
	    }
	    
	    
	    
	    @RequestMapping(value="outbox/{id}")
	    public ModelAndView getOutMessage(@PathVariable Long id )
	    {
	    	String nickname = profileService.getAuthenticatedUserNickname();
	    	PrivateMessage message = profileService.getMessage(id);
	    	if(  nickname.equals(message.getMessageSender()) ) 
	    	{ 
	    		return new ModelAndView("outMessage","message",message);
	    	}
	    	
	    	else { message=null; }
	    	return new ModelAndView("403");
	    }
	       
}
