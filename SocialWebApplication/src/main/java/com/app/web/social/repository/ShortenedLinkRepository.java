package com.app.web.social.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.web.social.model.ShortenedLink;

@Repository
public interface ShortenedLinkRepository extends JpaRepository<ShortenedLink,Long>{
	
	ShortenedLink findByShortenedUrl(String shortenedUrl);

	boolean existsByShortenedUrl(String shortenedUrl);
}
