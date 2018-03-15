package controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import static utilities.TestUserAccountActions.registerTestUser;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.app.web.social.config.DatabaseConfig;
import com.app.web.social.config.Initializer;
import com.app.web.social.config.MvcConfig;
import com.app.web.social.model.Friends;
import com.app.web.social.model.Profile;
import com.app.web.social.model.SecurityIssues;
import com.app.web.social.model.UserAccount;
import com.app.web.social.security.SecurityConfig;
import com.app.web.social.service.IFriendsService;
import com.app.web.social.service.IProfileService;
import com.app.web.social.service.ISecurityService;
import com.app.web.social.service.IUserService;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {MvcConfig.class,Initializer.class,DatabaseConfig.class ,SecurityConfig.class})
public class RegistrationTest {	    
	 
	    @Autowired
	    private WebApplicationContext webContext;
	    
	    private MockMvc mockMvc;

	    @Autowired
	    private FilterChainProxy springSecurityFilterChain;
	    
	    @Autowired
		private IUserService userService;
	    @Autowired
	 	private ISecurityService securityService;
	    @Autowired
	 	private IProfileService profileService;
	    @Autowired
	 	private IFriendsService friendsService;
	    
	    @Before
	    public void setup() throws Exception {
	        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.webContext)
	        .addFilters(springSecurityFilterChain);
	        
	        this.mockMvc = builder.build();
	       
	    }
	    
	    @Test
	    public void performingRegisterShouldRegisterUserWithCorrectProperies() throws Exception
	    {
	    	  String username = "usernameExample";
	    	  String nickname = "exampleNickname";
	    	  UserAccount user = new UserAccount(username,"passwordExample1","example@mail.pl"
	    			,nickname,"USA");
	    	
	          this.mockMvc.perform(registerTestUser(user)).andExpect(view().name("account/activate"));
	    	
	    	  UserAccount userAccount = userService.getUserAccount(username);
	          assertNotNull("UserAccount is null", userAccount);
	        	        
	          assertTrue("Password not hashed", userAccount.getPassword().length()>50);
	          assertEquals("Invalid role", "ROLE_USER", userAccount.getRole());
	          assertEquals("Invalid enabled status", false, userAccount.isEnabled());
	          assertEquals("Invalid not locked status", true, userAccount.isNotLocked());
	        
	          SecurityIssues issues = securityService.getSecurityIssuesAccountByUsername(userAccount.getUsername());
	          assertNotNull("SecurityIssues is null", issues);
	          	      
	          String activationCode = issues.getActivationCode();
	          assertNotNull("Activation code is null",issues.getActivationCode());
	          assertTrue("Invalid code generated", activationCode.length()==20);
	        	
	          Profile profile = profileService.getProfileByNickname(userAccount.getNickname());
	          assertNotNull("Profile is null", profile);
	          assertEquals("Invalid nickname registered",nickname,profile.getNickname());

	          Friends friends = friendsService.getFriendsProfileByNickname(userAccount.getNickname());
	          assertNotNull("Friends profile is null", friends);
              assertEquals("Invalid nickname registered",nickname, friends.getNickname());
	          
	          
	        
	    }
	    
	    
	    @Test
	    public void performingRegisterWithForbiddenInputsShouldNotBeSuccessful() throws Exception {	
	    	
	    	String username = "usernameExample1";
	    	UserAccount user = new UserAccount(username,"passwordExample2","examplmail.pl"
	    			,"exampleNickname1","USA");
	        this.mockMvc.perform(registerTestUser(user)).andExpect(view().name("register"));
	        
	        assertNull("UserAccount with forbidden inputs registered", userService.getUserAccount(username));
	    }
	 
}