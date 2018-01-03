package com.app.web.social.model.temp;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import com.app.web.social.dao.validations.InputCorrectness;

@Valid
public class EditAccount implements InputCorrectness
{
	@Pattern(regexp=USERNAME_VALIDATION_REGEX)
    private String username;
	
	@Pattern(regexp=PASSWORD_VALIDATION_REGEX)
    private String newPassword; 
    
    private String repeatPassword; 
    
    private String currentPassword;
    
    @Pattern(regexp=EMAIL_VALIDATION_REGEX)
    private String email;
    
    @Pattern(regexp=COUNTRY_VALIDATION_REGEX)
    private String country;

    public EditAccount()
    {
    	
    }
    
	public EditAccount(String username, String newPassword, String repeatPassword, String currentPassword, String email,
			String country) 
	{
		this.username = username;
		this.newPassword = newPassword;
		this.repeatPassword = repeatPassword;
		this.currentPassword = currentPassword;
		this.email = email;
		this.country = country;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getRepeatPassword() {
		return repeatPassword;
	}

	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
    
    
	
	
}
