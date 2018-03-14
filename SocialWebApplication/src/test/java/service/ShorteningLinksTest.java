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
	public void anyFormOfSubmitedUrlShouldReturnValidUrl()
	{
		ShortenedLink shortened1 = new ShortenedLink("oracle.com");
		shorteningService.shortenLink(shortened1);
		String shortUrl1 = shortened1.getShortenedUrl();
		
		ShortenedLink shortened2 = new ShortenedLink("https://spring.io");
		shorteningService.shortenLink(shortened2);
		String shortUrl2 = shortened2.getShortenedUrl();
		
		
		ShortenedLink shortened3 = new ShortenedLink("www.google.com");
		shorteningService.shortenLink(shortened3);
		String shortUrl3 = shortened3.getShortenedUrl();
	
		
		assertEquals("Invalid shortening","http://oracle.com", shortened1.getUrl() );
		assertEquals("Invalid shortening code generation","http://oracle.com" , shorteningService.getFullLink(shortUrl1) );
		
		assertEquals("Invalid shortening","https://spring.io", shortened2.getUrl() );
		assertEquals("Invalid shortening code generation","https://spring.io" , shorteningService.getFullLink(shortUrl2) );
		
		assertEquals("Invalid shortening","http://www.google.com", shortened3.getUrl() );
		assertEquals("Invalid shortening code generation","http://www.google.com" , shorteningService.getFullLink(shortUrl3) );
	}
	
	
	
}
