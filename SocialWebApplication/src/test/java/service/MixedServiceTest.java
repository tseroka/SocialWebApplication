package service;

import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.app.web.social.config.DatabaseConfig;
import com.app.web.social.config.Initializer;
import com.app.web.social.config.MvcConfig;
import com.app.web.social.service.IUserService;
import com.app.web.social.service.IProfileService;
import com.app.web.social.model.UserAccount;
import com.app.web.social.repository.ProfileRepository;
import com.app.web.social.repository.UserRepository;
import com.app.web.social.model.Profile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MvcConfig.class, Initializer.class, DatabaseConfig.class })
@WebAppConfiguration
@Transactional
public class MixedServiceTest {

	@Autowired
	private IUserService userService;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private IProfileService profileService;
	@Autowired
	private ProfileRepository profileRepository;


	
    @Before
	public void mockDatabaseRecords() {
		userRepository.save(new UserAccount(1L, "usernameExample", "passwordExample1", "example@mail.pl",
				"exampleNickname", "USA", new Timestamp(System.currentTimeMillis()), "ROLE_USER", true, true));
		
		profileRepository.save(new Profile("exampleNickname", "", Arrays.asList("Java,Spring"), "Warsaw", true, true));
		profileRepository.save(new Profile("anotherNickname", "", Arrays.asList("Kotlin"), "London", true, true));
		
	}


	@Test(expected = DataIntegrityViolationException.class)
	public void nullCreationDateShouldCauseException() {
		userRepository.save(new UserAccount(315L, "usernameExample13", "passwordExample1", "example21@mail.pl",
				"exampledaNickname", "USA", null, "ROLE_USER", true, true));
	}


	@Test
	public void getUserByUniquePropertyShouldReturnThisUniqueUser() {
		assertEquals("Invalid user loaded", "exampleNickname", userService.getUserAccount("usernameExample").getNickname());
		assertEquals("Invalid user loaded", "usernameExample", userService.getUserByEmail("example@mail.pl").getUsername());
	}

	@Test
	public void getAllProfilesShouldReturnCorrectNumberOfProfiles() {
		assertEquals("Incorrect number of profiles loaded", 2, profileService.getProfilesList().size());
	}

	@Test
	public void searchProfileByPropertyShouldReturnAllProfilesWithThatProperty() {
		List<String> searchResults = profileService.searchProfiles("", "Warsaw", null);
		assertEquals("Invalid searching", "exampleNickname", searchResults.get(0));
		assertEquals("Invalid searching",1, searchResults.size());
	}

	@Test
	public void searchProfileByNotExistingValuesOfSearchingCriteriaShouldReturnEmptyList() {
		assertEquals("Invalid searching", true, profileService.searchProfiles("", "Chicago", null).isEmpty());
		assertEquals("Invalid searching", true, profileService.searchProfiles("", "", Arrays.asList("football")).isEmpty());
	}

	@Test
	public void notExistingPropertiesShouldGetNullEntity() {
		assertEquals("Invalid user loaded", null, userService.getUserAccount("notSuchUsername"));
		assertEquals("Invalid user loaded", null, userService.getUserByEmail("noSuchEmail@mail.pl"));
	}

	@Test
	public void notExtistingPrimaryKeysShouldGetEmptyOptional() {
		assertEquals("Invalid user loaded", Optional.empty(), userRepository.findById(313L));
		assertEquals("Invalid profile loaded", Optional.empty(), profileRepository.findById("noSuchNickname"));
	}
}