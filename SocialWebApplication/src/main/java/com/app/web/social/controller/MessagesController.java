package com.app.web.social.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.app.web.social.model.PrivateMessage;
import com.app.web.social.model.Attachment;
import com.app.web.social.service.ProfileService;
import com.app.web.social.service.MessagesService;


@RequestMapping(value="/profile/messages/")
@Controller
public class MessagesController {

	
	  @Autowired
	  private MessagesService messagesService;
	  
	  @Autowired
	  private ProfileService profileService;
	  
	 
	   @RequestMapping(value="send/recipient={recipient}", method = RequestMethod.GET)
	   public ModelAndView sendMessageFromProfileViewButton(@PathVariable String recipient)
	   {   
		   PrivateMessage message = new PrivateMessage();
		   if(!recipient.equals(""))
		   {
		   List<String> recipients = new ArrayList<String>();
		   recipients.add(recipient);		   
		   message.setMessageRecipients(recipients);
		   }	
	       return new ModelAndView("messages/sendMessage","message", message);

	   }
	    
	   
	   @RequestMapping(value="sendProcessing", method = RequestMethod.POST)
	   public ModelAndView sendingMessageProcessing(@ModelAttribute("message") PrivateMessage message)
	   throws IOException
	   {    
		    if
		     (           
			     messagesService.isMessageSendingAllowed(message.getMessageRecipients())
		     ) 
		     {  
		    	message.setMessageSender( profileService.getAuthenticatedUserNickname() );
		    	
			    if(message.getMessageSubject().equals("")) message.setMessageSubject("No subject");
			   
			    CommonsMultipartFile fileUpload = message.getFileUpload();
	  		    if(fileUpload!=null)
			    {
	  		    message.setIsAnyAttachment(true);
	  		    
			    Attachment attachment = new Attachment();	    
                attachment.setFileName(fileUpload.getOriginalFilename());          
                attachment.setFileType(fileUpload.getContentType());
                attachment.setFileContent(fileUpload.getBytes());
                attachment.setMessage(message);
                
                Set<Attachment> attachments = new HashSet<>(); attachments.add(attachment);
                message.setAttachments(attachments);
			    } 
			    messagesService.sendMessage(message); 
			    
			   
	            return new ModelAndView("redirect:outbox");
		    }
		   return new ModelAndView("messages/sendMessage","sendingNotAllowed", "Sending not allowed (recipient doesn't exist or doesn't permit to send message).");
		 
	   }  
	    
	   @RequestMapping(value = "download", method = RequestMethod.GET)
	   public String downloadAttachment(@RequestParam(name = "msg", required = true) Long messageId,
	   @RequestParam(name = "att", required = true) Long attachmentId	)
	   {
		   Attachment attachment = messagesService.getAttachment(attachmentId);
		   
		   if(messagesService.isDownloadingAllowed(attachment, messageId))
		   {
			   messagesService.downloadAttachment(attachment);
			   System.out.println("Downloading attachment...");
		   }
		   else
		   {
			   attachment = null;
			   return "redirect:/403";
		   }
		   return "redirect:inbox/"+messageId;
	   }
	    
	   @RequestMapping(value = "remove/{id}", method = RequestMethod.GET)
	   public ModelAndView removeMessage(@PathVariable Long id)
	   {
		   return new ModelAndView("redirect:"+messagesService.removeMessage(id));
	   }
	     
	    @RequestMapping(value = "inbox", method = RequestMethod.GET)
	    public ModelAndView getInbox()
	    {
	    	List<PrivateMessage> inbox = messagesService.getInbox();
	    	return new ModelAndView("messages/inbox","inboxMessages",inbox);
	    }
	   
	  
	  
	    @RequestMapping(value = "outbox", method = RequestMethod.GET)
	    public ModelAndView getOutbox()
	    {
	    	List<PrivateMessage> outbox = messagesService.getOutbox();
	    	return new ModelAndView("messages/outbox","outboxMessages",outbox);
	    }
	    
	    @RequestMapping(value = "globalMessages", method = RequestMethod.GET)
	    public ModelAndView getGlobalMessages()
	    {
	    	List<PrivateMessage> globalMessages = messagesService.getGlobalMessages();
	    	return new ModelAndView("messages/globalMessages","globalMessages",globalMessages);
	    }
	    
	    
	   
	    
	    
	    @RequestMapping(value="inbox/{id}", method = RequestMethod.GET)
	    public ModelAndView getInMessage(@PathVariable Long id )
	    {
	    	
	    	String nickname = profileService.getAuthenticatedUserNickname();
	    	PrivateMessage message = messagesService.getMessage(id);
	    	if(  message.getMessageRecipients().contains(nickname) || message.getMessageRecipients().contains("ALL") ) 
	    	{
	    		return new ModelAndView("messages/inMessage","message",message);
	    	}
	    	
	    	else { message=null; }
	    	
	    	return new ModelAndView("403");
	    }
	    
	    
	    
	    @RequestMapping(value="outbox/{id}", method = RequestMethod.GET)
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
