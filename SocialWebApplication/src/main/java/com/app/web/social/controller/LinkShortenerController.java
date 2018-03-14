package com.app.web.social.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.app.web.social.model.ShortenedLink;
import com.app.web.social.service.IShorteningService;

@Controller
public class LinkShortenerController {

	@Autowired
	private IShorteningService shorteningService;
	
	@GetMapping("/short")
	public ModelAndView getShorteningForm()
	{
		return new ModelAndView("link-shortening","shortenedLink",new ShortenedLink());
	}
	
	@PostMapping("/shorteningProcessing")
	public String shorteningProcessing(@ModelAttribute("shortenedLink") ShortenedLink shortenedLink, 
			RedirectAttributes attributes)
	{
		shorteningService.shortenLink(shortenedLink);
		attributes.addFlashAttribute("shortUrl", shortenedLink.getShortenedUrl());
		attributes.addFlashAttribute("isShortened",true);
		return "redirect:short";
	}
	
	@GetMapping("/short/{shortUrl}")
	public String redirectToFullUrl(@PathVariable("shortUrl") String shortUrl)
	{
		if(shorteningService.isShortenedUrlExist(shortUrl)) {
        String fullUrl = shorteningService.getFullLink(shortUrl);
		return "redirect:"+fullUrl;
		}
		else return "redirect:/404";
	}
} 
