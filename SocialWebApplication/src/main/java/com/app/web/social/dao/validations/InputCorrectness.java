package com.app.web.social.dao.validations;

public interface InputCorrectness
{
    public static final String USERNAME_VALIDATION_REGEX = "^[a-zA-Z0-9]{8,25}$";
	
    public static final String PASSWORD_VALIDATION_REGEX = "((?=.*[a-z])(?=.*d)(?=.*[@#$%])(?=.*[A-Z]).{6,50})";
	
	public static final String NICKNAME_VALIDATION_REGEX = "^[a-zA-Z]{4,25}$";
	
	public static final String EMAIL_VALIDATION_REGEX = "^[\\w!#$%&’*+/=\\-?^_`{|}~]+(\\.[\\w!#$%&’*+/=\\-?^_`{|}~]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
	
	public static final String COUNTRY_VALIDATION_REGEX = "^[a-zA-Z]{2,40}$";

}

