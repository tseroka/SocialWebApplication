package com.app.web.social.security;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class SpringSecurityInitializer extends AbstractSecurityWebApplicationInitializer 
{

	@Override
	protected boolean enableHttpSessionEventPublisher() 
	{
	   return true;
	}
	
}