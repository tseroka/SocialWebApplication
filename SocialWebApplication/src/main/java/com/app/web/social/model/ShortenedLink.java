package com.app.web.social.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="shortLinks",  uniqueConstraints={ @UniqueConstraint( columnNames= {"id","shortened_url" } ) }     )
public class ShortenedLink {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
	@Column(name="id", nullable=false, unique=true, length=12)
	private long id;
	
	@Column(name="url", nullable=false, unique=false, length=300)
	private String url;
	
	@Column(name="shortened_url", nullable=false, unique=true, length=5)
	private String shortenedUrl;

	public ShortenedLink(){
		
	}
	
	public ShortenedLink(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getShortenedUrl() {
		return shortenedUrl;
	}

	public void setShortenedUrl(String shortenedUrl) {
		this.shortenedUrl = shortenedUrl;
	}
	
	
	
}
