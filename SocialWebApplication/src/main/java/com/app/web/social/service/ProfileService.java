package com.app.web.social.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.web.social.dao.ProfileDAO;
import com.app.web.social.model.Profile;

@Service
public class ProfileService {

	@Autowired
	private ProfileDAO profileDAO;
	
	    public List<Profile> getProfilesList()
	    {
		    return this.profileDAO.getProfilesList();
        }
		
		public Profile getProfileByNickname(String nickname)
		{
			return this.profileDAO.getProfileByNickname(nickname);
		}
		
		public String getAuthenticatedUserNickname()
		{
			return this.profileDAO.getAuthenticatedUserNickname();
		}
		
		public void editProfile(Profile profile)
		{
		    this.profileDAO.editProfile(profile);
		}
		
		public List<String> searchProfiles(String sex, String city, List<String> intersests)
		{
			return this.profileDAO.searchProfiles(sex, city, intersests);
		}
		

}
