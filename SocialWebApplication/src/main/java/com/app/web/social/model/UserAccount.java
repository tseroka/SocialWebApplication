package com.app.web.social.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import com.app.web.social.dao.validations.InputCorrectness;

@Entity
@Table(name="users",  uniqueConstraints={ @UniqueConstraint( columnNames={"user_id", "username", "nickname", "email"} ) }     )
@Valid
public class UserAccount implements Serializable, InputCorrectness {
	
	private static final long serialVersionUID = -7400604230107519063L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_id", nullable=false, unique=true, length=12)
	private Integer id;

	@Column(name="username", nullable=false, unique=true, length=25)
	@Pattern(regexp=USERNAME_VALIDATION_REGEX)
	private String username;
	
	@Column(name="password", nullable=false, unique=false, length=100)
    @Pattern(regexp=PASSWORD_VALIDATION_REGEX)
    private String password;
	
	@Column(name="nickname", nullable=false, unique=true, length=25)
	@Pattern(regexp=NICKNAME_VALIDATION_REGEX)
    private String nickname;
	
	@Column(name="email", nullable=false, unique=true, length=40)
    @Pattern(regexp=EMAIL_VALIDATION_REGEX)
    private String email;
	
	@Column(name="country", nullable=true, unique=false, length=40)
	@Pattern(regexp=COUNTRY_VALIDATION_REGEX)
    private String country;
	
	@Column(name="role", nullable=false, unique=false, length=25)
	private String role="ROLE USER";
	
	@Column(name="enabled", nullable=false, unique=false, length=1)
	private boolean enabled=true;
  
	

	public UserAccount()
    {
    	
    }
    
    public UserAccount (Integer id, String username, String password, String email, String nickname, String country,
    		String role, boolean enabled)
    {
    	this.id = id;
    	this.username = username;
    	this.password = password;
    	this.email = email;
    	this.nickname = nickname;
    	this.country = country;
    	this.role = role;
    	this.enabled = enabled;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String login) {
		this.username = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	

}

