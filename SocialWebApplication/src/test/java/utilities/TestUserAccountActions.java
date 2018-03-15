package utilities;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.app.web.social.model.UserAccount;

public class TestUserAccountActions {

	public static MockHttpServletRequestBuilder registerTestUser(UserAccount userAccount) {	
		return post("/registerProcess")
    			.param("username", userAccount.getUsername())
    			.param("password", userAccount.getPassword())
    			.param("repeatPassword", userAccount.getPassword())
    			.param("email", userAccount.getEmail())
    			.param("nickname", userAccount.getNickname())
    			.param("country", userAccount.getCountry())   			
    			.flashAttr("user", new UserAccount()).with(csrf());
	}
	
	public static MockHttpServletRequestBuilder loginTestUser(String username, String password) {		
		return post("/loginProcess")
    	       .param("username", username)
               .param("password", password)
               .with(csrf());
	}
	
}
