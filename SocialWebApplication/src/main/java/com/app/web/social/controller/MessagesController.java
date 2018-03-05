package com.app.web.social.controller;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.data.domain.Page;
import org.springframework.util.FileCopyUtils;

import com.app.web.social.model.PrivateMessage;
import com.app.web.social.model.Attachment;
import com.app.web.social.service.IProfileService;
import com.app.web.social.service.IMessagesService;


@RequestMapping(value="/profile/messages/")
@Controller
public class MessagesController {

	
	  @Autowired
	  private IMessagesService messagesService;
	  
	  @Autowired
	  private IProfileService profileService;
	  
	 
	   @GetMapping("send/recipient={recipient}")
	   public ModelAndView sendMessageFromProfileViewButton(@PathVariable String recipient)
	   {   
		   PrivateMessage message = new PrivateMessage();
		   if(!recipient.equals(""))
		   {
		   List<String> recipients = Arrays.asList(recipient);		   
		   message.setMessageRecipients(recipients);
		   }	
	       return new ModelAndView("messages/sendMessage","message", message);

	   }
	    
	   
	   @PostMapping("sendProcessing")
	   public ModelAndView sendingMessageProcessing(@ModelAttribute("message") PrivateMessage message)
	   throws IOException
	   {    
		    if
		     (           
			     messagesService.isMessageSendingAllowed(message.getMessageRecipients())
		     ) 
		     {  
			     if(!messagesService.prepareAttachmentsAndValidateIfNotEmpty(message))
			     {
			    	 return new ModelAndView("messages/sendMessage","sendingNotAllowed", "You can upload up to 5 files with 20MB total size, .exe extension is not allowed.");
			     }
			     else 
			     {
			        messagesService.sendMessage(message); 
	                return new ModelAndView("redirect:outbox");
			     }
		     }
		  return new ModelAndView("messages/sendMessage","sendingNotAllowed", "Sending not allowed (recipient doesn't exist or doesn't permit to send message).");
		 
	   }  
	    
	   @GetMapping("download")
	   public void downloadAttachment(@RequestParam(name = "msg", required = true) Long messageId,
	   @RequestParam(name = "att", required = true) Long attachmentId, HttpServletResponse response)
	   throws IOException
	   {
		   Attachment attachment = messagesService.getAttachment(attachmentId);
		   if(messagesService.isDownloadingAllowed(attachment, messageId))
		   {
			    response.setContentType(attachment.getFileType());
		        response.setContentLength(attachment.getFileContent().length);
		        response.setHeader("Content-Disposition","attachment; filename=\"" + attachment.getFileName() +"\"");
		        FileCopyUtils.copy(attachment.getFileContent(), response.getOutputStream());
		   }			
		   else 
		   {
			   attachment = null;
			   response.sendRedirect("/403");
		   }		   
	   }
	    
	   @PostMapping("remove/{id}")
	   public ModelAndView removeMessage(@PathVariable Long id)
	   {
		   return new ModelAndView("redirect:"+messagesService.removeMessage(id));
	   }
	      
	    @GetMapping("inbox")
	    public ModelAndView getInbox(@RequestParam(name = "page", defaultValue = "1") String page)
	    {
	    	Page<PrivateMessage> inbox = messagesService.getInbox(Integer.parseInt(page));
	    	return new ModelAndView("messages/inbox","inboxMessages",inbox).addObject("endpage",messagesService.countInboxMessages()/10+1);
	    }
	   
	  
	  
	    @GetMapping("outbox")
	    public ModelAndView getOutbox(@RequestParam(name = "page", defaultValue = "1") String page)
	    {
	    	Page<PrivateMessage> outbox = messagesService.getOutbox(Integer.parseInt(page));
	    	return new ModelAndView("messages/outbox","outboxMessages",outbox).addObject("endpage",messagesService.countOutboxMessages()/10+1);
	    }
	    
	    @GetMapping("globalMessages")
	    public ModelAndView getGlobalMessages(@RequestParam(name = "page", defaultValue = "1") String page)
	    {
	    	Page<PrivateMessage> globalMessages = messagesService.getGlobalMessages(Integer.parseInt(page));
	    	return new ModelAndView("messages/globalMessages","globalMessages",globalMessages);
	    }
	    
	    
	   
	    
	    
	    @GetMapping("inbox/{id}")
	    public ModelAndView getInMessage(@PathVariable Long id )
	    {
	    	String nickname = profileService.getAuthenticatedUserNickname();
	    	PrivateMessage message = messagesService.getMessage(id);
	    	if(  message.getMessageRecipients().contains(nickname) || message.getMessageRecipients().contains("ALL") ) 
	    	{
	    		return new ModelAndView("messages/inMessage","message",message);
	    	}
	    	
	    	else { message=null; }
	    	return new ModelAndView("redirect:/403");
	    }
	    
	    
	    
	    @GetMapping("outbox/{id}")
	    public ModelAndView getOutMessage(@PathVariable Long id )
	    {
	    	String nickname = profileService.getAuthenticatedUserNickname();
	    	PrivateMessage message = messagesService.getMessage(id);
	    	if(  nickname.equals(message.getMessageSender()) ) 
	    	{ 
	    		return new ModelAndView("messages/outMessage","message",message);
	    	}
	    	
	    	else { message=null; }
	    	return new ModelAndView("redirect:/403");
	    }
	       
}
