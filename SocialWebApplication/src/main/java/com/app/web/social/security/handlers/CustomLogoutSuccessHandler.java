package com.app.web.social.security.handlers;

import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Service;

@Service
public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler 
{

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException 
	{	
		  HttpSession session = request.getSession();
		  
		  response.setStatus(HttpServletResponse.SC_OK);
		  System.out.println("Current date: "+new Timestamp(System.currentTimeMillis())+", last accessed date: "+new Timestamp(session.getLastAccessedTime()));
	      if( ( System.currentTimeMillis()-session.getLastAccessedTime() ) >= (1000L*60L*1L) )
	      {
	    	  System.out.println("TIMEOUT");
	         response.sendRedirect("/login?error=expired");
	      } 
	      else {
	    	  System.out.println("no timeout");
	    	  response.sendRedirect("/login");	
	      }
	      
	      super.onLogoutSuccess(request, response, authentication);
   }
  
}
