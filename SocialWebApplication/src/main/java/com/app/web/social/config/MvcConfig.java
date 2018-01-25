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
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.app.web.social.security.SecurityConfig;
import com.app.web.social.config.DatabaseConfig;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages="com.app.web.social*")
@EnableTransactionManagement
@Import({ SecurityConfig.class, DatabaseConfig.class  })
public class MvcConfig implements WebMvcConfigurer{

	@Bean("viewResolver")
	public ViewResolver getViewResolver(){ 
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setViewClass(JstlView.class);
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
	  
	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver getCommonsMultipartResolver() {
	    CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
	    multipartResolver.setMaxUploadSize(20971520);   // 20MB
	    multipartResolver.setMaxInMemorySize(1048576);  // 1MB
	    return multipartResolver;
	}
	
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/*").addResourceLocations("/resources/");
		registry.addResourceHandler("/static/*").addResourceLocations("/static/");
	}
	
	
	
}