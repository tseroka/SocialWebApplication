package com.app.web.social.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.web.social.model.UserAccount;
import com.app.web.social.dao.validations.ValidationsDAO;

@Service
public class ValidationsService 
{
	@Autowired
	private ValidationsDAO validationsDAO;
	
    public boolean validateRegistration(UserAccount userAccount)
    {
	   return this.validationsDAO.validateRegistration(userAccount);
    }

}
