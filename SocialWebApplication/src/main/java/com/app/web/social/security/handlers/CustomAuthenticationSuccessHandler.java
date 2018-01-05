package com.app.web.social.security.handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import com.app.web.social.service.SecurityService;

@Service
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler
{
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Autowired
	private SecurityService securityService;
	  
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
		     throws IOException, ServletException
    {	
    
        securityService.loginSuccess(authentication.getName());
        
        response.setStatus(HttpServletResponse.SC_OK);
 
        redirectStrategy.sendRedirect(request, response, "/home");
    }

}
