package service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.app.web.social.config.DatabaseConfig;
import com.app.web.social.config.Initializer;
import com.app.web.social.config.MvcConfig;
import com.app.web.social.security.SecurityConfig;
import com.app.web.social.service.IUserService;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(classes = {MvcConfig.class,Initializer.class,DatabaseConfig.class ,SecurityConfig.class})
@WebAppConfiguration
@Transactional
public class MixedServiceTest {
	
	
	@Autowired
	private IUserService userService;

	     @Test
	     public void getUserBySomethingTest() {            
	        assertEquals("Invalid user loaded",62L, userService.getUserAccount("testingUser").getId());
	        assertEquals("Invalid user loaded",65L, userService.getUserAccount("sampleusertest").getId());
	        assertEquals("Invalid user loaded",null, userService.getUserAccount("noSuchUser"));
	        assertEquals("Invalid user loaded",null, userService.getUserByEmail("noEmail@mail.pl"));
	        assertEquals("Invalid user loaded","testingUser", userService.getUserByEmail("testmail@mail.pl").getUsername());
	        
	    } 
	
}