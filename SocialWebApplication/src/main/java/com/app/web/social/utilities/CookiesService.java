package com.app.web.social.utilities;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class CookiesService 
{

	public static void addCookie(HttpServletResponse response, String name, String value, int maxAge ) throws IOException { 
	    Cookie cookie = new Cookie(name, URLEncoder.encode(value, "UTF-8"));
	    cookie.setMaxAge(maxAge);
	    cookie.setSecure(true); 
	    cookie.setPath("/");
	    response.addCookie(cookie);
	}
	
}
