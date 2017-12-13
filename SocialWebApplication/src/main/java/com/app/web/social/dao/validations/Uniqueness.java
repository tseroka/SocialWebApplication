package com.app.web.social.dao.validations;

import com.app.web.social.model.UserAccount;

public interface Uniqueness 
{

	public boolean isUsernameNotBusy(String username);
	
	public boolean isNicknameNotBusy(String nickname);
	
	public boolean isEmailNotBusy(String email);
	
	public boolean isPasswordUsernameEmailNicknameAreNotTheSame(UserAccount userAccount);
	
}
