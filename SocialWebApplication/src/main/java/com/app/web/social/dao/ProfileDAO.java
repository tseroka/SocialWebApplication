package com.app.web.social.dao;

import java.util.List;

import com.app.web.social.model.PrivateMessage;
import com.app.web.social.model.Profile;

public interface ProfileDAO {
	
	
	
	public Profile getProfileByNickname(String nickname);
	
	public String getAuthenticatedUserNickname();
	
	
	public void editProfile(Profile profile);
	
	
	public List<Profile> getProfilesList();
	
	public List<Profile> searchProfiles(String sex, String city, List<String> intersests);
	
	
}
