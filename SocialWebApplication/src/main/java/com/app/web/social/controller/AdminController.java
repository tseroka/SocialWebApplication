package com.app.web.social.controller;

import com.app.web.social.model.PrivateMessage;
import com.app.web.social.model.UserAccount;
import com.app.web.social.service.UserService;
import com.app.web.social.service.MessagesService;
import com.app.web.social.service.AdminService;
import com.app.web.social.model.temp.LockAccount;

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
    private AdminService adminService;
	
	@Autowired
	private MessagesService messagesService;
	 
	@RequestMapping(method = RequestMethod.GET)
	public String adminPage() 
	{
       return "admin/admin";
	}
	 
	@RequestMapping(value = " view-users", method = RequestMethod.GET)
	public ModelAndView viewUsers()
	{ 
		List<UserAccount> usersList = userService.getUsersList();
	    return new ModelAndView("admin/view-users","listUser",usersList);  
	}
	
	
	@RequestMapping(value = "lockUser/{id}", method = RequestMethod.GET)  
    public ModelAndView getLockUserAccountForm(@PathVariable long id)
	{   
		LockAccount lockAccount = new LockAccount();
		lockAccount.setId(id);
        return new ModelAndView("admin/lockForm","lockAccount",lockAccount);  
    }  
	
	
	@RequestMapping(value = "lockProcessing", method = RequestMethod.POST)
	public String lockUser(@ModelAttribute("lockAccount") LockAccount lockAccount)
	{
		adminService.lockUser(lockAccount);
		return "redirect:/admin/view-users";
	}
	

	@RequestMapping(value = "unlockUser/{id}", method = RequestMethod.GET)  
    public String unlockUserAccount(@PathVariable long id){   
		adminService.unlockUser(id);
        return "redirect:/admin/view-users";  
    }  

	
	@RequestMapping(value = "sendGlobalMessage", method = RequestMethod.GET)
	public ModelAndView sendToAll()
	{
	  return new ModelAndView("admin/sendGlobalMessage","message",new PrivateMessage());	
	}
	
	@RequestMapping(value = "sendProcessing", method = RequestMethod.POST)
	public String sendProcessing(@ModelAttribute("message") PrivateMessage message)
	{
		List<String> all = new ArrayList<String>(); all.add("ALL");
		message.setMessageSender("ADMIN");
		message.setMessageRecipients(all);
		messagesService.sendMessage(message);
		return "redirect:/admin"; 
	}
}
