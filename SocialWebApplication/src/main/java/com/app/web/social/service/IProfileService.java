package com.app.web.social.service;

import java.util.List;

import com.app.web.social.model.Profile;

public interface IProfileService {
	
	public Profile getProfileByNickname(String nickname);
	
	public Profile getAuthenticatedProfile();
	
	public String getAuthenticatedUserNickname();

	
	public void editProfile(Profile profile);
	
	
	public List<Profile> getProfilesList();
	
	public List<String> searchProfiles(String sex, String city, List<String> interests);

}
