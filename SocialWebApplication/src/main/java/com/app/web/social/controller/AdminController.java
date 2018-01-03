package com.app.web.social.controller;

import com.app.web.social.model.PrivateMessage;
import com.app.web.social.model.UserAccount;
import com.app.web.social.service.UserService;
import com.app.web.social.service.MessagesService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    
	@Autowired
    private UserService userService;
	
	@Autowired
	private MessagesService messagesService;
	 
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView adminPage() 
	{
       return new ModelAndView("admin/admin");
	}
	 
	@RequestMapping(value="view-users", method = RequestMethod.GET)
	public ModelAndView viewUsers()
	{
		    List<UserAccount> listUser =userService.getUsersList();  
	        return new ModelAndView("admin/view-users","listUser",listUser);  
	}
	
	@RequestMapping(value="lockUser/{id}")  
    public ModelAndView lockUser(@PathVariable long id){   
		userService.lockUser(id);
        return new ModelAndView("redirect:/admin/view-users");  
    }  

	@RequestMapping(value="unlockUser/{id}")  
    public ModelAndView unlockUser(@PathVariable long id){   
		userService.unlockUser(id);
        return new ModelAndView("redirect:/admin/view-users");  
    }  

	@RequestMapping(value="sendGlobalMessage", method = RequestMethod.GET)
	public ModelAndView sendToAll()
	{
	  return new ModelAndView("admin/sendGlobalMessage","message",new PrivateMessage());	
	}
	
	@RequestMapping(value="sendProcessing", method = RequestMethod.POST)
	public ModelAndView sendProcessing(@ModelAttribute("message") PrivateMessage message)
	{
		List<String> all = new ArrayList<String>(); all.add("ALL");
		message.setMessageSender("ADMIN");
		message.setMessageRecipients(all);
		messagesService.sendMessage(message);
		return new ModelAndView("redirect:/admin"); 
	}
}
