package com.app.web.social.dao.validations;

public enum ValidationsRegex 
{

	USERNAME_VALIDATION_REGEX("^[a-zA-Z0-9]{8,25}$"),
	PASSWORD_VALIDATION_REGEX("((?=.*[a-z])(?=.*d)(?=.*[@#$%])(?=.*[A-Z]).{8,40})"),
	NICKNAME_VALIDATION_REGEX("^[a-zA-Z]{4,25}$"),
	EMAIL_VALIDATION_REGEX("^[\\w!#$%&’*+/=\\-?^_`{|}~]+(\\.[\\w!#$%&’*+/=\\-?^_`{|}~]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$");
	
	private String pattern;

    ValidationsRegex(String pattern) {
        this.pattern = pattern;
    }

    public String pattern() {
        return pattern;
    }
}