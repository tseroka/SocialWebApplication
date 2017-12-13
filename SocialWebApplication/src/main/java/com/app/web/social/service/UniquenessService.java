package com.app.web.social.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.web.social.model.UserAccount;
import com.app.web.social.dao.validations.Uniqueness;

@Service
public class UniquenessService 
{
	@Autowired
	private Uniqueness uniqueness;
	
	public boolean isUsernameNotBusy(String username)
	{
		return this.uniqueness.isUsernameNotBusy(username);
	}
	
	public boolean isNicknameNotBusy(String nickname) 
	{
		return this.uniqueness.isNicknameNotBusy(nickname);
	}
	
	public boolean isEmailNotBusy(String email) 
	{
		return this.uniqueness.isEmailNotBusy(email);
	}
	
	public boolean isPasswordUsernameEmailNicknameAreNotTheSame(UserAccount userAccount)
	{
		return this.uniqueness.isPasswordUsernameEmailNicknameAreNotTheSame(userAccount);
	}

}
