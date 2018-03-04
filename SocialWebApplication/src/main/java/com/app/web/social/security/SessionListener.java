package com.app.web.social.security;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener 
{
	
    public void sessionCreated(final HttpSessionEvent event) 
    {
        event.getSession().setMaxInactiveInterval(10*60);  
    }
 
    public void sessionDestroyed(final HttpSessionEvent event) 
    {

    }   

}