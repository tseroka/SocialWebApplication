package com.app.web.social.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.MultipartConfigElement;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.app.web.social.security.SecurityConfig;
import com.app.web.social.security.SessionListener;
 
public class Initializer implements WebApplicationInitializer {
 
    public void onStartup(ServletContext container) throws ServletException {
 
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(MvcConfig.class);
        ctx.register(SecurityConfig.class);
      //  ctx.register(DatabaseConfig.class);
        ctx.setServletContext(container);
        
        container.addListener(new ContextLoaderListener(ctx));
        container.addListener(new SessionListener());
        
        ServletRegistration.Dynamic servlet = container.addServlet("dispatcher", new DispatcherServlet(ctx));
 
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/");
        
    }
    
}