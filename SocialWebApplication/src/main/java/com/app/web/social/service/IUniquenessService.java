package com.app.web.social.service;

import com.app.web.social.model.UserAccount;

public interface IUniquenessService {

	public boolean isUsernameNotBusy(String username);
	
	public boolean isNicknameNotBusy(String nickname);
	
	public boolean isEmailNotBusy(String email);
	
	public boolean isPasswordUsernameEmailNicknameAreNotTheSame(UserAccount userAccount);
}
