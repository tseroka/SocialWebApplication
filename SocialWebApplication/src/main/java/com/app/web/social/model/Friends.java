package com.app.web.social.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.app.web.social.model.converter.StringListConverter;

@Entity
@Table(name="friends",  uniqueConstraints={ @UniqueConstraint( columnNames={"nickname"} ) }     )
public class Friends implements Serializable
{
	private static final long serialVersionUID = 4655413959805669732L;

	@Id
	@Column(name="nickname", nullable=false, unique=true, length=25)
	private String nickname;
	
	@Column(name="friends", nullable=true, unique=false, length=4000)
	@Convert(converter = StringListConverter.class)
	private List<String> friends;
	
	@Column(name="invitationsSent", nullable=false, unique=false, length=4000)
	@Convert(converter = StringListConverter.class)
	private List<String> invitationsSent;
	
	@Column(name="invitationsReceived", nullable=false, unique=false, length=4000)
	@Convert(converter = StringListConverter.class)
	private List<String> invitationsReceived;

	
	public Friends()
	{
	
	}
     
	public Friends(String nickname, List<String> friends, List<String> invitationsSent, List<String> invitationsReceived)
	{
		this.nickname=nickname;
		this.friends=friends;
		this.invitationsSent=invitationsSent;
		this.invitationsReceived=invitationsReceived;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public List<String> getFriends() {
		return friends;
	}

	public void setFriends(List<String> friends) {
		this.friends = friends;
	}

	public List<String> getInvitationsSent() {
		return invitationsSent;
	}

	public void setInvitationsSent(List<String> invitationsSent) {
		this.invitationsSent = invitationsSent;
	}

	public List<String> getInvitationsReceived() {
		return invitationsReceived;
	}

	public void setInvitationsReceived(List<String> invitationsReceived) {
		this.invitationsReceived = invitationsReceived;
	}
	
	
}
