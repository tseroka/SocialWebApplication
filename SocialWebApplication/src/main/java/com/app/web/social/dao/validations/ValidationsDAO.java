package com.app.web.social.dao.validations;

import com.app.web.social.model.UserAccount;

public interface ValidationsDAO 
{
	public boolean validateRegistration(UserAccount userAccount);
}
