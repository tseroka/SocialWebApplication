package com.app.web.social.security;

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

@Service
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler 
{

  private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

  public CustomAuthenticationFailureHandler() 
  {
     super();
  }
 
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
     throws IOException, ServletException 
  {
    String message = "";

    if(exception instanceof UsernameNotFoundException) { message = "credentials"; }
    
    else if(exception instanceof AuthenticationCredentialsNotFoundException) { message = "credentials"; }
    
    else if(exception instanceof DisabledException) { message = "disabled"; }
    
    else if(exception instanceof LockedException) { message = "locked"; }
    
    else if(exception instanceof BadCredentialsException) { message = "credentials"; }
    
    else { message = exception.getMessage(); }
    
     redirectStrategy.sendRedirect(request, response, "/login?error="+message);
  }

}