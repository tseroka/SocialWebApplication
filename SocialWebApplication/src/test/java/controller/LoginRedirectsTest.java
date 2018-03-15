package controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import static utilities.TestUserAccountActions.registerTestUser;
import static utilities.TestUserAccountActions.loginTestUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.*;
import org.junit.runner.RunWith;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.FilterChainProxy;

import com.app.web.social.config.MvcConfig;
import com.app.web.social.model.UserAccount;
import com.app.web.social.config.Initializer;
import com.app.web.social.config.DatabaseConfig;
import com.app.web.social.security.SecurityConfig;

import com.app.web.social.service.IUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {MvcConfig.class,Initializer.class,DatabaseConfig.class ,SecurityConfig.class})
public class LoginRedirectsTest {
	    	 
	    @Autowired
	    private WebApplicationContext webContext;
	    
	    private MockMvc mockMvc;

	    @Autowired
	    private FilterChainProxy springSecurityFilterChain;
	    
	    @Autowired
		private IUserService userService;
	    
	    private String username = "usernameExample2";
	    private String password = "passwordExample3";
	    
	    @Before
	    public void setup() throws Exception {
	        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.webContext)
	        .addFilters(springSecurityFilterChain);
	        
	        this.mockMvc = builder.build();	        
	        	        
	    	UserAccount user = new UserAccount(username,password,"example31@mail.pl"
	    			,"exampleNicknameee","USA");	    	
	        this.mockMvc.perform(registerTestUser(user));
	        
	    }	 	 	 	    	    
	    
	    @Test
	    public void loginWithNotEnabledUserShouldRedirectToErrorPage() throws Exception {	    	
	    		    		        
	    	MockHttpServletRequestBuilder loginWithNotEnabledUserBuilder = loginTestUser(username, password);	  
	        this.mockMvc.perform(loginWithNotEnabledUserBuilder)
	        .andExpect(status().isFound()).andExpect(redirectedUrl("/login?error=activate"));	        
	    }

	    @Test
	    public void loginWithEnabledUserShouldRedirectHomePage() throws Exception {
	    	  UserAccount userAccount =  userService.getUserAccount(username);
		      userAccount.setEnabled(true);
		      userService.editUser(userAccount);
		        	        
		      MockHttpServletRequestBuilder loginWithEnabledUserBuilder = loginTestUser(username, password);
		      this.mockMvc.perform(loginWithEnabledUserBuilder)
		      .andExpect(status().isFound()).andExpect(redirectedUrl("/home"));
	    }

	    @Test
	    public void tryingToAccessAdminPageWithoutLoggingShouldRedirectToLoginPage() throws Exception {
	    	MockHttpServletRequestBuilder builder = get("/admin/view-users");
	    	 this.mockMvc.perform(builder).andExpect(status().isFound())
		     .andExpect(redirectedUrl("http://localhost/login"));	    			
	    }
	    	    
	   
}