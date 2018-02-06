package com.app.web.social.controller;

import com.app.web.social.model.PrivateMessage;
import com.app.web.social.model.UserAccount;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    
	@Autowired
    private AdminService adminService;
	
	@Autowired
	private MessagesService messagesService;
	 

	@GetMapping("view-active-users")
	public ModelAndView viewUsers()
	{ 		
		List<String> activeUsers = adminService.getActiveUsersFromSessionRegistry();
		activeUsers.forEach(System.out::println);
	    return new ModelAndView("admin/active-users","activeUsers", activeUsers);  
	}
	
	
	@GetMapping("view-users")
	public ModelAndView viewUsers(@RequestParam(name = "page", defaultValue = "1") String page)
	{ 		
		List<UserAccount> usersList = adminService.getUsersList(Integer.parseInt(page));
	    return new ModelAndView("admin/view-users","listUser",usersList).addObject("endpage",adminService.countUsers()/10+1);  
	}
	
	  
	@GetMapping("lockUser/{id}") 
    public ModelAndView getLockUserAccountForm(@PathVariable long id)
	{   
		LockAccount lockAccount = new LockAccount();
		lockAccount.setId(id);
        return new ModelAndView("admin/lockForm","lockAccount",lockAccount);  
    }  
	 
	 
	@RequestMapping(value = "lockProcessing", method = RequestMethod.POST)
	public String lockUser(@ModelAttribute("lockAccount") LockAccount lockAccount)
	{
		if(lockAccount.getLockTime()!=0) lockAccount.setLockReason(lockAccount.getLockReason()+"-time");
		else lockAccount.setLockReason(lockAccount.getLockReason()+"-perm");
		adminService.lockUser(lockAccount);
		return "redirect:/admin/view-users";
	}
	   

	@RequestMapping(value = "unlockUser/{id}", method = RequestMethod.POST)  
    public String unlockUserAccount(@PathVariable long id){   
		adminService.unlockUser(id);
        return "redirect:/admin/view-users";  
    }  

	@RequestMapping(value = "deleteUser/{id}", method = RequestMethod.POST)  
    public String deleteUserAccount(@PathVariable long id){   
		adminService.deleteUser(id);
        return "redirect:/admin/view-users";  
    }  
	
	@GetMapping("sendGlobalMessage")
	public ModelAndView sendToAll()
	{
	  return new ModelAndView("admin/sendGlobalMessage","message",new PrivateMessage());	
	}
	
	@PostMapping("sendProcessing")
	public String sendProcessing(@ModelAttribute("message") PrivateMessage message)
	{
		List<String> all = new ArrayList<String>(); all.add("ALL");
		message.setMessageSender("ADMIN");
		message.setMessageRecipients(all);
		messagesService.sendMessage(message);
		return "redirect:/admin"; 
	}
}
