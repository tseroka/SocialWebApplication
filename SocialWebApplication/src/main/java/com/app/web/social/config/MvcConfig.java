package com.app.web.social.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.app.web.social.security.SecurityConfig;
import com.app.web.social.config.DatabaseConfig;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages="com.app.web.social*")
@EnableTransactionManagement
@Import({ SecurityConfig.class, DatabaseConfig.class  })
public class MvcConfig implements WebMvcConfigurer{


	@Bean
	public ViewResolver getViewResolver(){ 
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setViewClass(JstlView.class);
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
	
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

}