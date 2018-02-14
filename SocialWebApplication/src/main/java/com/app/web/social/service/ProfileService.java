package com.app.web.social.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.web.social.model.Profile;
import com.app.web.social.repository.ProfileRepository;

@Service
@Transactional
public class ProfileService implements IProfileService {

	@Autowired
	private ProfileRepository profileRepository;
	
	@Autowired
	private IUserService userService;
	    
		public Profile getProfileByNickname(String nickname)
		{
			return profileRepository.findById(nickname).get();
		}
		
		public Profile getAuthenticatedProfile()
		{
		    return getProfileByNickname(getAuthenticatedUserNickname());	
		}
		
		public String getAuthenticatedUserNickname()
		{
			return userService.getUserAccount( userService.getAuthenticatedUserUsername()).getNickname();
		}
		
		
		public void editProfile(Profile profile)
		{
		    profileRepository.save(profile);
		}
		
		
		public List<Profile> getProfilesList()
	    {
		    return profileRepository.findAll();
	    }
	
		public List<String> searchProfiles(String sex, String city, List<String> interests)
		{  
			return profileRepository.findProfileBySexAndCityAndInterests(sex, city, interests);
		}
		

}
