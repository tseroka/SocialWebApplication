package com.app.web.social.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/exceptions")
public class SecurityController 
{

	
	@RequestMapping(value="Reset password", method=RequestMethod.GET)
	public ModelAndView sendResetPasswordCodeForm()
	{
		return new ModelAndView("account/exceptions/reset-pasword");
	}
	
	
	
	
	@RequestMapping(value="Unlock account", method=RequestMethod.GET)
	public ModelAndView sendUnlockCodeForm()
	{
		return new ModelAndView("account/exceptions/send-unlock-code");
	}
	
	
	
	
	@RequestMapping(value="Enable account", method=RequestMethod.GET)
	public ModelAndView sendEnableCodeForm()
	{
		return new ModelAndView("account/exceptions/send-activation-code");
	}
	
	
}
