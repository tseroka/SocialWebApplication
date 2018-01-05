package com.app.web.social.model.temp;

public class Activate 
{

	private String username;
	
	private int activationCode;
	
	public Activate()
	{
		
	}

	public Activate(String username, int activationCode) 
	{
		this.username = username;
		this.activationCode = activationCode;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getActivationCode() {
		return activationCode;
	}

	public void setActivationCode(int activationCode) {
		this.activationCode = activationCode;
	}
	
	
	
}
