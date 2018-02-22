package controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.*;
import org.junit.runner.RunWith;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.FilterChainProxy;

import com.app.web.social.config.MvcConfig;
import com.app.web.social.config.Initializer;
import com.app.web.social.config.DatabaseConfig;
import com.app.web.social.security.SecurityConfig;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(classes = {MvcConfig.class,Initializer.class,DatabaseConfig.class ,SecurityConfig.class})
@WebAppConfiguration
public class ControllersTest {
	    
	    @Autowired
	    private WebApplicationContext webContext;
	    private MockMvc mockMvc;

	    @Autowired
	    private FilterChainProxy springSecurityFilterChain;
	    
	    @Before
	    public void setup() {
	        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.webContext).addFilter(springSecurityFilterChain);
	        this.mockMvc = builder.build();
	    }
	 
	    
	    @Test
	    public void mainPageRedirectTest() throws Exception {
	        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/");
	        this.mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isFound())
	        .andExpect(MockMvcResultMatchers.view().name("redirect:login"));
	     }
	    
	    @Test
	    public void testLogin() throws Exception {
	    	MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/loginProcess")
	    			.param("username", "testingUser")
	                .param("password", "dadaeDRT123#$")
	                .with(csrf());
	        this.mockMvc.perform(builder).andExpect(status().isFound());
	    }

	    @Test
	    @WithMockUser(roles="ADMIN")
	    public void testAdminPathIsAvailableToAdminRole() throws Exception {
	    	MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("admin/view-users");
	    	mockMvc.perform(builder).andExpect(view().name("login"))
	    			.andExpect(status().isOk()); 
	    }
	    
	    @Test
	    @WithMockUser(roles="USER")
	    public void accessDeniedTest() throws Exception {
	    	MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("admin/view-users");
	    	mockMvc.perform(builder).andExpect(view().name("403"))
	    			.andExpect(status().isFound()); 
	    }
	    
	 
	    
}