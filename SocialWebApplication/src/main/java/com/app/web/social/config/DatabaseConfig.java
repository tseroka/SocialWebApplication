package com.app.web.social.config;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import org.hibernate.SessionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration 
@EnableTransactionManagement
@PropertySource("classpath:database.properties")
public class DatabaseConfig {

	
	  @Autowired
      private Environment environment;	
    
	  @Bean
      public HibernateTemplate hibernateTemplate() 
	  {
      return new HibernateTemplate(getSessionFactory());
      }   
	  
	  @Bean
      public SessionFactory getSessionFactory() 
	  {
		   LocalSessionFactoryBean lsfb = new LocalSessionFactoryBean();
		   lsfb.setDataSource(getDataSource());
		   lsfb.setPackagesToScan("com.app.web.social.model");
		   lsfb.setHibernateProperties(getHibernateProperties());
		   try 
		   {
			 lsfb.afterPropertiesSet();
		   } 
		   catch (IOException e) 
		   {
			  e.printStackTrace();
		   }
		   return lsfb.getObject();
	 }
	  
	 @Bean
     public DataSource getDataSource() 
	 {
		  BasicDataSource dataSource = new BasicDataSource();
	      dataSource.setDriverClassName(environment.getProperty("database.driverClassName"));
	      dataSource.setUrl(environment.getProperty("database.url"));
 	      dataSource.setUsername(environment.getProperty("database.username"));
		  dataSource.setPassword(environment.getProperty("database.password"));
		  return dataSource;
     }
	  
		
	private Properties getHibernateProperties() 
	{
          Properties properties = new Properties();
          properties.put("hibernate.dialect", environment.getProperty("hibernate.dialect"));
          properties.put("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));
          properties.put("hibernate.format_sql", environment.getProperty("hibernate.format_sql"));
          return properties;        
    }	
		 
	@Bean
	public HibernateTransactionManager hibernateTransactionManager()
	{
		  return new HibernateTransactionManager(getSessionFactory());
    }
	  
}
