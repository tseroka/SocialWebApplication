package com.app.web.social.model.temp;

public class SecurityIssuesFormHandler 
{
	
	private String email;
	
	private String code;
	
	private String newPassword;
	
	private String newPasswordRepeat;
	
	public SecurityIssuesFormHandler()
	{
		
	}

	public SecurityIssuesFormHandler(String email, String code) 
	{
		this.email = email;
		this.code = code;
	}

	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getNewPasswordRepeat() {
		return newPasswordRepeat;
	}

	public void setNewPasswordRepeat(String newPasswordRepeat) {
		this.newPasswordRepeat = newPasswordRepeat;
	}
	
	
	
	
}
