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
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.app.web.social.service.SecurityService;
import com.app.web.social.service.AdminService;
import com.app.web.social.model.SecurityIssues;

@Service
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler 
{

  private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

  @Autowired
  private SecurityService securityService;
  
  @Autowired
  private AdminService adminService;

  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
     throws IOException, ServletException 
  {
    String message = "";

    String username = request.getParameter("username");
    
    if(exception instanceof UsernameNotFoundException) { message = "credentials"; }
    
    else if(exception instanceof AuthenticationCredentialsNotFoundException) { message = "credentials"; }
    
    else if(exception instanceof DisabledException) { message = "activate"; }
    
    else if(exception instanceof LockedException) 
    {
    	SecurityIssues issue = securityService.getSecurityIssuesAccountByUsername(username);
        message = "locked-"+issue.getLockReason();
        
        if(issue.getUnlockDate()!=null && issue.isLockTimeElapsed() ) 
        {
        	adminService.accountSelfUnlockAfterLockTimeout(issue);
        	message = "locked-end";
        //	request.getSession(true).setAttribute("unlockDate",issue.getUnlockDate());
        //	 request.setAttribute("unlockDate", issue.getUnlockDate());
        }
        
        response.sendRedirect("/SocialWebApplication/login?error="+message+"&kjhubvJHbt="+username);
    }
    
    else if(exception instanceof BadCredentialsException) 
    { 
    	message = "credentials"; 
    	try 
    	{
    	securityService.increaseFailedLoginAttemptsNumberAndLockIfEqualsFive(username);
    	}
    	catch(IndexOutOfBoundsException ex) {}
    }
    
    else { message = exception.getMessage(); }
    
    // redirectStrategy.sendRedirect(request, response, "/login?error="+message);
     response.sendRedirect("/SocialWebApplication/login?error="+message);
     
  }

}