package com.app.web.social.controller;


import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.app.web.social.model.PrivateMessage;
import com.app.web.social.model.PrivateMessageAttachment;
import com.app.web.social.model.FileWrapper;
import com.app.web.social.service.ProfileService;
import com.app.web.social.service.MessagesService;
import com.app.web.social.dao.validations.FileValidator;

@RequestMapping(value="/profile/messages/")
@Controller
public class MessagesController {

	
	  @Autowired
	  private MessagesService messagesService;
	  
	  @Autowired
	  private ProfileService profileService;
	  
	 
	   @RequestMapping(value="send/recipient={recipient}", method = RequestMethod.GET)
	   public ModelAndView sendMessageFromProfileViewButton()//@PathVariable String recipient)
	   {   /**
		   PrivateMessage newMessage = new PrivateMessage();
		   if(!recipient.equals(""))
		   {
		   List<String> recipients = new ArrayList<String>();
		   recipients.add(recipient);		   
		   newMessage.setMessageRecipients(recipients);
		   }	
	     */
		 //  FileWrapper fileWrapper = new FileWrapper();
		  // CommonsMultipartFile fileUpload = new CommonsMultipartFile(null);
		   ModelAndView model = new ModelAndView("messages/sendMessage");
		  // model.addObject("newMessage", newMessage);
		//   model.addObject("fileModel",fileWrapper);
		//   model.addObject("fileUpload",fileUpload);
		   return model;
	   }
	    
	   @RequestMapping(value="send", method = RequestMethod.GET)
	   public ModelAndView getSendView() 
	   {
		   return new ModelAndView("messages/sendMessage");//,"command", new CommonsMultipartFile());
	   }
	   
	   /**
	   @RequestMapping(value="sendProcessing", method = RequestMethod.POST)
	   public ModelAndView sendingMessageProcessing(@ModelAttribute("newMessage") PrivateMessage newMessage)
	   {    
		    if
		     (           
			     messagesService.isMessageSendingAllowed(newMessage.getMessageRecipients())
		     ) 
		     {  
		    	newMessage.setMessageSender( profileService.getAuthenticatedUserNickname() );
		    	
			    if(newMessage.getMessageSubject().equals("")) newMessage.setMessageSubject("No subject");
			   
			  
			    messagesService.sendMessage(newMessage); 
			   
			 
	            return new ModelAndView("redirect:outbox");
		    }
		   return new ModelAndView("messages/sendMessage","sendingNotAllowed", "Sending not allowed (recipient doesn't exist or doesn't permit to send message).");
		 
	   } */
	    
	   @RequestMapping(value="sendProcessing", method = RequestMethod.POST)
	   public ModelAndView sendingMessageProcessing(HttpServletRequest request,
              @RequestParam(value = "fileUpload") CommonsMultipartFile fileUpload) throws IOException
		
	   {   
		   //if (fileUpload != null)
		  // {
			   System.out.println("Saving file: " + fileUpload.getOriginalFilename());
			   PrivateMessageAttachment privateMessageAttachment = new PrivateMessageAttachment();
			   privateMessageAttachment.setAttachmentName(fileUpload.getOriginalFilename());
			   privateMessageAttachment.setFileType(fileUpload.getContentType());
			   privateMessageAttachment.setAttachment(fileUpload.getBytes());
               messagesService.sendMessageAttachment(privateMessageAttachment);      
		  // }
			   
	            return new ModelAndView("redirect:outbox");
	   } 
	   
	  /** @RequestMapping(value="sendProcessing", method = RequestMethod.POST)
	   public ModelAndView sendingMessageProcessing(HttpServletRequest request,
              @ModelAttribute(value = "messageAttachment") PrivateMessageAttachment messageAttachment) throws IOException
		
	   {   
		   //if (fileUpload != null)
		  // {
			   System.out.println("Saving file: " + fileUpload.getOriginalFilename());
			   PrivateMessageAttachment privateMessageAttachment = new PrivateMessageAttachment();
			   privateMessageAttachment.setAttachmentName(fileUpload.getOriginalFilename());
			   privateMessageAttachment.setFileType(fileUpload.getContentType());
			   privateMessageAttachment.setAttachment(fileUpload.getBytes());
               messagesService.sendMessageAttachment(privateMessageAttachment);      
		  // }
			   
	            return new ModelAndView("redirect:outbox");
	   } */
	    
	   @RequestMapping(value="remove/{id}", method = RequestMethod.GET)
	   public ModelAndView removeMessage(@PathVariable Long id)
	   {
		   return new ModelAndView("redirect:"+messagesService.removeMessage(id));
	   }
	     
	    @RequestMapping(value="inbox", method = RequestMethod.GET)
	    public ModelAndView getInbox()
	    {
	    	List<PrivateMessage> inbox = messagesService.getInbox();
	    	return new ModelAndView("messages/inbox","inboxMessages",inbox);
	    }
	   
	  
	  
	    @RequestMapping(value="outbox", method = RequestMethod.GET)
	    public ModelAndView getOutbox()
	    {
	    	List<PrivateMessage> outbox = messagesService.getOutbox();
	    	return new ModelAndView("messages/outbox","outboxMessages",outbox);
	    }
	    
	    @RequestMapping(value="globalMessages", method = RequestMethod.GET)
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
