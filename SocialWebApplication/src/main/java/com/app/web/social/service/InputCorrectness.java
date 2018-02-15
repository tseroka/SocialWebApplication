package com.app.web.social.service;

public interface InputCorrectness
{
    public static final String USERNAME_VALIDATION_REGEX = "^[a-zA-Z0-9]{8,25}$";
	
    public static final String PASSWORD_VALIDATION_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,40}$";
	
	public static final String NICKNAME_VALIDATION_REGEX = "^[a-zA-Z]{4,25}$";
	
	public static final String EMAIL_VALIDATION_REGEX = "^[\\w!#$%&’*+/=\\-?^_`{|}~]+(\\.[\\w!#$%&’*+/=\\-?^_`{|}~]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
	

}

  