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

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
     
	
	@Autowired
	private SocialWebAppUserDetailsService socialWebAppUserDetailsService;
	

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(socialWebAppUserDetailsService).passwordEncoder(passwordEncoder());
	}

	
	@Bean
	public PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}

    @Override
	protected void configure(HttpSecurity http) throws Exception {

	  http.authorizeRequests()
	  .antMatchers("/").permitAll()
      .antMatchers("/login").permitAll()
      .antMatchers("/registration").permitAll()
      .antMatchers("/profile/**","/search/**","/user/**").hasAnyAuthority("ROLE ADMIN", "ROLE USER")
	 .antMatchers("/admin/**").hasAuthority("ROLE ADMIN")
	 .and()
		  .formLogin().loginPage("/login").loginProcessingUrl("/loginProcess").failureUrl("/login")
		  .usernameParameter("username").passwordParameter("password").defaultSuccessUrl("/home")
		.and()
		  .logout().logoutUrl("/logout").logoutSuccessUrl("/home")
		.and()
		  .exceptionHandling().accessDeniedPage("/403")
		.and()
		  .csrf();
	  
		
	}
	
 
} 