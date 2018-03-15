package service;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

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
import com.app.web.social.model.ShortenedLink;
import com.app.web.social.service.IShorteningService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MvcConfig.class, Initializer.class, DatabaseConfig.class })
@WebAppConfiguration
@Transactional
public class ShorteningLinksTest {
	
	@Autowired 
	private IShorteningService shorteningService;

	 @Test
	 public void anyFormOfSubmitedUrlShouldReturnValidUrl(){   
		List<String> inputUrls = Arrays.asList("oracle.com","https://spring.io","www.google.com");
		
		List<String> expectedPreparedUrls = Arrays.asList("http://oracle.com","https://spring.io","http://www.google.com");
		
		  for(String inputUrl : inputUrls){
			  ShortenedLink shortened = new ShortenedLink(inputUrl);
			  shorteningService.shortenLink(shortened);
			  String shortUrl = shortened.getShortenedUrl();
			
			  String currentExpectedPreparedUrl = expectedPreparedUrls.get( inputUrls.indexOf(inputUrl) );
			
			  assertEquals("Invalid url preparation", currentExpectedPreparedUrl, shortened.getUrl() );
			  assertEquals("Invalid shortening code generation", currentExpectedPreparedUrl, shorteningService.getFullLink(shortUrl) );	     
		 }
	 }

	
}
