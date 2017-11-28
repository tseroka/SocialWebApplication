package com.app.web.social.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name="users",  uniqueConstraints={ @UniqueConstraint( columnNames={"user_id"} ) }     )
@Valid
public class UserAccount implements Serializable {
	

	private static final long serialVersionUID = -7400604230107519063L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_id", nullable=false, unique=true, length=12)
	private Integer id;
	
//	@Size(min=6, max=25, message="Username must be 6-25 characters length")
//	@Pattern(regexp="^[a-zA-Z0-9]{6,25}$", message="Invalid username input.")
	@Column(name="username", nullable=false, unique=true, length=25)
	private String username;
	
//	@Size(min=8, max=40, message="Password must be 8-40 characters length")
//	@Pattern(regexp="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message="Invalid password input.")
	@Column(name="password", nullable=false, unique=false, length=100)
    private String password;
	
//	@Size(min=3, max=25, message="Nickname must be 3-25 characters length")
//	@Pattern(regexp="^[a-zA-Z\\\\\\\\s]{3,25}$", message="Invalid nickname input")
	@Column(name="nickname", nullable=false, unique=true, length=25)
    private String nickname;
	
//	@Pattern(regexp="^([_a-zA-Z0-9-]+(\\\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\\\.[a-zA-Z0-9-]+)*(\\\\.[a-zA-Z]{1,6}))?$", message="Invalid email input")
	@Column(name="email", nullable=false, unique=true, length=40)
    private String email;
	
	@Column(name="country", nullable=true, unique=false, length=40)
    private String country;
	
	@Column(name="role", nullable=false, unique=false, length=25)
	private String role="ROLE USER";
	
	@Column(name="enabled", nullable=false, unique=false, length=1)
	private boolean enabled=true;
    
	@OneToOne
	@JoinColumn(name="nickname")
	private Profile profile;
    

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
	
	public Profile getProfile() {
			return profile;
		}

    public void setProfile(Profile profile) {
			this.profile = profile;
		}
}

