package com.app.web.social.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.app.web.social.service.SocialWebAppUserDetailsService;
import com.app.web.social.security.handlers.CustomAuthenticationFailureHandler;
import com.app.web.social.security.handlers.CustomAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter 
{
     
	@Autowired
	private CustomAuthenticationFailureHandler customFailureHandler;
	
	@Autowired
	private CustomAuthenticationSuccessHandler customSuccessHandler;
	
	@Autowired
	private SocialWebAppUserDetailsService socialWebAppUserDetailsService;
	

	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception 
	{
       auth.userDetailsService(socialWebAppUserDetailsService).passwordEncoder(passwordEncoder());
    }

	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}

	  
    @Override
	protected void configure(HttpSecurity http) throws Exception 
    {

	  http.authorizeRequests()
	  .antMatchers("/").permitAll()
      .antMatchers("/login").permitAll()
      .antMatchers("/registration").permitAll()
      .antMatchers("/profile/**","/search/**","/user/**").hasAnyAuthority("ROLE ADMIN", "ROLE USER")
	 .antMatchers("/admin/**").hasAuthority("ROLE ADMIN")
	 .and()
		  .formLogin().loginPage("/login").loginProcessingUrl("/loginProcess").
		  successHandler(customSuccessHandler).failureHandler(customFailureHandler)
		  .usernameParameter("username").passwordParameter("password")
		.and()
		  .logout().logoutUrl("/logout").logoutSuccessUrl("/home")
		.and()
		  .exceptionHandling().accessDeniedPage("/403")
		.and()
		  .csrf();
	  
	  http.sessionManagement().maximumSessions(1).expiredUrl("/login?error=expired").maxSessionsPreventsLogin(true).
	  and().invalidSessionUrl("/login?error=expired");
		
	}
	
 
} 