package com.app.web.social.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.web.social.repository.*;
import com.app.web.social.model.*;
import com.app.web.social.service.IMessagesService;
import com.app.web.social.model.temp.LockAccount;

@Service
@Transactional
public class AdminService implements IAdminService
{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SecurityIssuesRepository securityIssuesRepository;
	
	@Autowired
	private FriendsRepository friendsRepository;
	
	@Autowired
	private ProfileRepository profileRepository;
	
	@Autowired
	private IMessagesService messagesService;
	
	@Autowired
	private SessionRegistry sessionRegistry;
	
	public List<String> getActiveUsersFromSessionRegistry()
	{
		 return sessionRegistry.getAllPrincipals().stream()
			      .filter(u -> !sessionRegistry.getAllSessions(u, false).isEmpty())
			      .map(Object::toString)
			      .collect(Collectors.toList());
	}
	
	public Page<UserAccount> getUsersList(int pageNumber)
	{
		final PageRequest pageRequest =  PageRequest.of(pageNumber-1, 10);
		return userRepository.findAll(pageRequest);
	}
	
	public Long countUsers()
	{
	   return userRepository.count();
	}
	
	
	public List<UserAccount> getUsersList()
	{
		return userRepository.findAll();
	}
	
	public void accountSelfUnlockAfterLockTimeout(SecurityIssues issue)
	{
		issue.setUnlockDate(null);
		issue.setLockReason(null);
		issue.setNumberOfLoginFails((byte)0);
		
		UserAccount userAccount = userRepository.findByUsername(issue.getUsername());
		userAccount.setNotLocked(true);
		securityIssuesRepository.save(issue);
		userRepository.save(userAccount);
	}
	
	public void lockUser(LockAccount lockAccount)
	{
        UserAccount userAccount = userRepository.findById(lockAccount.getId()).get();
		
		if(userAccount.getRole().equals("ROLE USER"))
		{
		  userAccount.setNotLocked(false);
		
		  SecurityIssues issue = securityIssuesRepository.findByUsername(userAccount.getUsername());
		  issue.setLockReason(lockAccount.getLockReason());
		
		  if(lockAccount.getLockTime()==0L) 
		  {
			issue.setUnlockDate(null);
		  }
		  else 
		  {
		    issue.setUnlockDate( new Timestamp( System.currentTimeMillis() + 86400000L*lockAccount.getLockTime() ) );
		  }
		securityIssuesRepository.save(issue);
		userRepository.save(userAccount);
		}
	}
	
	public void unlockUser(long id)
	{
		   UserAccount userAccount = userRepository.findById(id).get();
		   
		   if(userAccount.getRole().equals("ROLE USER"))
		   {
		   userAccount.setNotLocked(true);
		   
		   SecurityIssues issue = securityIssuesRepository.findByUsername(userAccount.getUsername());
		   issue.setLockReason(null);
		   issue.setUnlockDate(null);
		   securityIssuesRepository.save(issue);
		   userRepository.save(userAccount);
		   }
	}
	
	
	public void deleteUser(long id) 
	{	
		UserAccount userAccount = userRepository.findById(id).get();
		if(userAccount.getRole().equals("ROLE USER"))
		{ 
		  String nickname = userAccount.getNickname();
		
		  messagesService.removeAllMessages(nickname);
		  
		  userRepository.delete(userAccount);
		  		 		  
		  friendsRepository.deleteById(nickname);
		  
		  profileRepository.deleteById(nickname);
		  		    
	   }
	}
	
}
