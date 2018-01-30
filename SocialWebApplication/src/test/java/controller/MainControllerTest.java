package controller;

import javax.servlet.ServletContext;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

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

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(classes = { com.app.web.social.config.MvcConfig.class,com.app.web.social.config.Initializer.class, com.app.web.social.config.DatabaseConfig.class , com.app.web.social.security.SecurityConfig.class})
@WebAppConfiguration
public class MainControllerTest {

	    @SuppressWarnings("unused")
		@Autowired
	    private ServletContext servletContext;
	
	    @Autowired
	    private WebApplicationContext webContext;
	    private MockMvc mockMvc;

	    @Before
	    public void setup() {
	        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.webContext);
	        this.mockMvc = builder.build();
	    }
	    
	    @Test
	    public void testMainController() throws Exception {
	        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/");
	        this.mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk())
	        .andExpect(MockMvcResultMatchers.view().name("static/home"));
	     }
	 
	    
}
