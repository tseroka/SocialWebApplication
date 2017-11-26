package com.app.web.social.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.app.web.social.model.converter.StringListConverter;

@Entity
@Table(name="profiles",  uniqueConstraints={ @UniqueConstraint( columnNames={"nickname"} ) }     )
public class Profile {

	
	@Id
	@Column(name="nickname", nullable=false, unique=true, length=25)
	private String nickname;
	
	@Column(name="sex", nullable=true, unique=false, length=1)
	private String sex;
		
	@Column(name="interests", nullable=true, unique=false, length=200)
	@Convert(converter = StringListConverter.class)
	private List<String> interests;
	
	@Column(name="city", nullable=true, unique=false, length=50)
	private String city;

	@Column(name="allowSearching", nullable=false, unique=false, length=1)
	private boolean allowSearching=false;
	
	
	public Profile()
	{
		
	}
	
	public Profile(String nickname, String sex, List<String> interests, String city, boolean allowSearching)
	{
		this.nickname=nickname;
		this.sex=sex;
		this.interests=interests;
		this.city=city;
		this.allowSearching=allowSearching; 
	}
	
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public List<String> getInterests() {
		return interests;
	}
	public void setInterests(List<String> interests) {
		this.interests = interests;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public boolean getAllowSearching() {
		return allowSearching;
	}
	public void setAllowSearching(boolean allowSearching) {
		this.allowSearching = allowSearching;
	}
	
	
}
