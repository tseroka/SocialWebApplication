package com.app.web.social.service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.app.web.social.model.ShortenedLink;

public interface IShorteningService {

	public static final char[] LETTERS = {'0','1','2','3','4','5','6','7','8','9'		
		    ,'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','R','S','T','U','W','V','X','Y','Z'
		    ,'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','r','s','t','u','w','v','x','y','z'}  ;
	
	public void shortenLink(ShortenedLink shortenedLink);
	
	public String getFullLink(String shortenedUrl);
	
	public boolean isShortenedUrlExist(String shortenedUrl);
	
	public default String generateShortenedLink(){
		StringBuilder builder = new StringBuilder();
		Random rand = new Random();
		int length = LETTERS.length;
		for(int i=0; i<5; ++i)
		{
			builder.append(LETTERS[rand.nextInt(length)]);
		}
		return builder.toString();
	}
	
	public default String prepareSubmitedUrl(String url)
	{
		List<String> splittedUrl = Arrays.asList(url.split("://"));
		String firstPart = splittedUrl.get(0);
		if("http".equals(firstPart) || "https".equals(firstPart) ) return url;
		else return "http://"+url;
	}
}
