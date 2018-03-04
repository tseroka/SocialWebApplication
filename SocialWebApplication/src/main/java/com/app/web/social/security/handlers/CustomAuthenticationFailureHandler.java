package com.app.web.social.security.handlers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.app.web.social.service.ISecurityService;
import com.app.web.social.model.SecurityIssues;

@Service
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler 
{

  @Autowired
  private ISecurityService securityService;
  

  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
     throws IOException, ServletException 
  {
    StringBuffer message = new StringBuffer();

    String username = request.getParameter("username");
    
    if(exception instanceof UsernameNotFoundException) { message.append("credentials"); }
    
    else if(exception instanceof AuthenticationCredentialsNotFoundException) { message.append("credentials"); }
    
    else if(exception instanceof DisabledException) { message.append("activate"); }
    
    else if(exception instanceof LockedException) 
    {
    	SecurityIssues issue = securityService.getSecurityIssuesAccountByUsername(username);
    	   
        if(issue.getUnlockDate()!=null && issue.isLockTimeElapsed() ) 
        {
        	securityService.accountSelfUnlockAfterLockTimeout(issue);
        	message.append("locked-end");
        } 
        else 
        {
         if(issue.getNumberOfLoginFails()==5)
         {
         message.append("locked-"+issue.getLockReason());
         }
         else
         {
        	message.append("locked-"+issue.getLockReason()+"&kjhubvJHbt="+username);
         }
        }
    }
    
    else if(exception instanceof BadCredentialsException) 
    { 
    	message.append("credentials"); 
    	securityService.increaseFailedLoginAttemptsNumberAndLockIfEqualsFive(username);
    }
    
    else if(exception instanceof SessionAuthenticationException)
    {
    	message.append("max-sessions");
    }
    
    else { message.append(exception.getMessage()); }
    
    response.sendRedirect("/login?error="+message.toString());
     
  }

}