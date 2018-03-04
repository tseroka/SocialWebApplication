package com.app.web.social.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;

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
	

	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}

	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() 
	{
	    return new HttpSessionEventPublisher();
	}
	
	@Bean
	public SessionRegistry sessionRegistry() 
	{
	    return new SessionRegistryImpl();
	}
	
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception 
	{
       auth.userDetailsService(socialWebAppUserDetailsService).passwordEncoder(passwordEncoder());
    }
	
    @Override
	protected void configure(HttpSecurity http) throws Exception 
    {

	  http.authorizeRequests()
	  .antMatchers("/","/login","/registration").permitAll()
      .antMatchers("/home","/profile/**","/search/**","/user/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
	  .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
	  .and()
	     .requiresChannel().anyRequest().requiresSecure()
	  .and()
		  .formLogin().loginPage("/login").loginProcessingUrl("/loginProcess").
		  successHandler(customSuccessHandler).failureHandler(customFailureHandler)
		  .usernameParameter("username").passwordParameter("password")
		.and()
		  .logout().logoutUrl("/logout").logoutSuccessUrl("/login").deleteCookies("JSESSIONID").invalidateHttpSession(true)
		.and()
		  .exceptionHandling().accessDeniedPage("/403")
		.and()
		  .csrf()
		.and()
		  .sessionManagement().sessionFixation().migrateSession().maximumSessions(1).sessionRegistry(sessionRegistry())
		  .maxSessionsPreventsLogin(true).expiredUrl("/login?error=expired")
		   .and()
		     .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).invalidSessionUrl("/login?error=expired");  		 
	}
	
 
} 