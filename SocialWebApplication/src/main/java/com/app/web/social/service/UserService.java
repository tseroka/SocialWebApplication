package com.app.web.social.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.web.social.repository.*;
import com.app.web.social.model.UserAccount;
import com.app.web.social.model.Friends;
import com.app.web.social.model.Profile;
import com.app.web.social.model.SecurityIssues;

@Service
@Transactional
public class UserService implements IUserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProfileRepository profileRepository;
	
	@Autowired
	private FriendsRepository friendsRepository;
	
	@Autowired
	private ISecurityService securityService;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	public void registerUser(UserAccount userAccount) 
	{

		List<String> empty = new ArrayList<String>();
						
		profileRepository.save( new Profile(userAccount.getNickname(), "", empty, "", false, false));
		
		friendsRepository.save( new Friends(userAccount.getNickname(), empty, empty , empty));
		
		userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
		userAccount.setCreationDate(new Timestamp(System.currentTimeMillis()));
		
		userRepository.save(userAccount);
		securityService.saveSecurityIssuesAccount(new SecurityIssues(userAccount.getId(),userAccount.getUsername(), userAccount, securityService.generateActivationAndUnlockCode(), null, null, new Timestamp(System.currentTimeMillis()+300000L),(byte) 0, null, null));
			
		securityService.sendEmailWithActivationCode(userAccount.getEmail());
	}

	
	public void editUser(UserAccount userAccount, SecurityIssues issue)
	{
		securityService.saveSecurityIssuesAccount(issue);
		userRepository.save(userAccount);
	}
	
	public void editUser(UserAccount userAccount)
	{
		userRepository.save(userAccount);
	}
	
	public void changePassword(UserAccount userAccount, String rawPassword)
	{
		userAccount.setPassword(passwordEncoder.encode(rawPassword));
		editUser(userAccount);
	}
	
	public boolean checkPassword(String rawPassword, String encodedPassword)
	{
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}
	
	public UserAccount getUserById(long id)
	{
		return userRepository.findById(id).get();
	}
	
	public UserAccount getUserAccount(String username)
	{
		if(userRepository.existsByUsername(username)) return userRepository.findByUsername(username);
				
		else return null;
    }
	
	public UserAccount getUserByNickname(String nickname)
	{
		return userRepository.findByNickname(nickname);
	}
	
	public UserAccount getUserByEmail(String email) 
	{
	    return userRepository.findByEmail(email);
	}
	
	
	public UserAccount getAuthenticatedUserAccount() 
	{
	    return getUserAccount(getAuthenticatedUserUsername());	
	}
		
}
