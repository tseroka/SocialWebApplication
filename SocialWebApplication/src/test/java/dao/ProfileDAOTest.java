package dao;

import com.app.web.social.service.ProfileService;
import com.app.web.social.service.UserService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import javax.servlet.ServletContext;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(classes = { com.app.web.social.config.MvcConfig.class,com.app.web.social.config.Initializer.class, com.app.web.social.config.DatabaseConfig.class})
@WebAppConfiguration
public class ProfileDAOTest {
	
	@Autowired
	ServletContext servletContext;
	
	@Autowired
	private ProfileService profileService;
	
	@Autowired
	private UserService userService;
	
	     @Test
	     @Transactional
	     public void getProfileByNicknameTest() { 
       
	        assertEquals("Invalid profile loaded","sebix", profileService.getProfileByNickname("sebix").getNickname());
	       
	     
	    }

	     @Test
	     @Transactional
	     public void getUserByUsernameTest() { 
       
	        assertEquals("Invalid user loaded",Long.valueOf(7), Long.valueOf(userService.getUserAccount("sebabanan").getId()));
	        assertNotSame("Invalid user loaded",8, userService.getUserAccount("sebabanan").getId());
	     
	    } 
	
}
