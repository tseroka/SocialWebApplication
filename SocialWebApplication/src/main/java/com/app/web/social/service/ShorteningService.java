package com.app.web.social.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.web.social.model.ShortenedLink;
import com.app.web.social.repository.ShortenedLinkRepository;

@Service
@Transactional
public class ShorteningService implements IShorteningService {

	@Autowired
	private ShortenedLinkRepository shortenedLinkRepository;
	
    public void shortenLink(ShortenedLink shortenedLink) {
    	shortenedLink.setUrl( prepareSubmitedUrl( shortenedLink.getUrl() ) );
    	shortenedLink.setShortenedUrl(generateShortenedLink());
    	shortenedLinkRepository.save(shortenedLink);
    }
	
	public String getFullLink(String shortenedUrl) {
		return shortenedLinkRepository.findByShortenedUrl(shortenedUrl).getUrl();
	}
	
	public boolean isShortenedUrlExist(String shortenedUrl){
		return shortenedLinkRepository.existsByShortenedUrl(shortenedUrl);
	}
	
}
