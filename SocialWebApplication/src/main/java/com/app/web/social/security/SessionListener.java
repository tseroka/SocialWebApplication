package com.app.web.social.security;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.codahale.metrics.Counter;

public class SessionListener implements HttpSessionListener 
{
	private final Counter counterOfActiveSessions;
	  
	public SessionListener() {
        super();
        counterOfActiveSessions = MetricRegistrySingleton.metrics.counter("sessionCounter");
    }
	
    public void sessionCreated(final HttpSessionEvent event) {
        System.out.println("SessionCounter.sessionCreated");
        event.getSession().setMaxInactiveInterval(10*60);
        counterOfActiveSessions.inc();
        event.getSession().setAttribute("counter",this);
    }

    public void sessionDestroyed(final HttpSessionEvent event) {
        System.out.println("SessionCounter.sessionDestroyed");
        counterOfActiveSessions.dec();
        event.getSession().setAttribute("counter",this);
    }   
    
    public long getNumberOfActiveSessions() {
        return this.counterOfActiveSessions.getCount();
    }
    
}