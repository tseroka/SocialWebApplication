package com.app.web.social.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.web.social.repository.UserRepository;
import com.app.web.social.model.UserAccount;

@Service
@Transactional
public class UniquenessService implements IUniquenessService
{
	@Autowired
	private UserRepository userRepository;
	
	public boolean isUsernameNotBusy(String username)
	{
		return !userRepository.existsByUsername(username);
	}
	
	public boolean isNicknameNotBusy(String nickname) 
	{
		return !userRepository.existsByNickname(nickname);
	}
	
	public boolean isEmailNotBusy(String email) 
	{
		return !userRepository.existsByEmail(email);
	}
	
	public boolean isPasswordUsernameEmailNicknameAreNotTheSame(UserAccount userAccount)
	{
		String password = userAccount.getPassword();    String username = userAccount.getUsername();
        String email = userAccount.getEmail().split("@")[0];     String nickname = userAccount.getNickname();
    	return 
    	        (
    			!(username.equals(password)) && 
    			!(email.equals(password)) &&
    			!(nickname.equals(password))  &&
    			!(username.equals(nickname)) &&
    			!(username.equals(email)) &&
    			!(nickname.equals(email))
    			);
	}

}
