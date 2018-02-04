package com.app.web.social.dao;

import java.util.Arrays;
import java.util.List;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.web.social.model.Profile;

@Repository
@Transactional
public class ProfileDAOImpl extends SuperDAO<String, Profile> implements ProfileDAO {

	@Autowired
	private UserDAO userDAO;
		
	public static final List<String> emptyInterests = Arrays.asList(new String[]{""});
	
	
	public Profile getProfileByNickname(String nickname) 
	{
		return getEntityByPrimaryKey(nickname);
	}
   
	public Profile getAuthenticatedProfile()
	{
		return getProfileByNickname(getAuthenticatedUserNickname());
	}
	
	public String getAuthenticatedUserNickname()
	{
	    return userDAO.getUserAccount( userDAO.getAuthenticatedUserUsername()).getNickname();
	}
 
	public void editProfile (Profile profile) 
	{
		update(profile);
	}
	

	public List<Profile> getProfilesList()
    {
		return getSession().createQuery("from Profile p where p.allowSearching=true",Profile.class).list();     
    }
    
	
    public List<String> searchProfiles(String sex, String city, List<String> interests)
    {      	 	
      	int skipSex = "".equals(sex) ? 1 : 0;
      	int skipCity = "".equals(city) ? 1 : 0;
      	int skipInterests = emptyInterests.equals(interests) ? 1 : 0;
      	
      	System.out.println("skipSex= "+skipSex+", sex= "+sex);
      	System.out.println("skipCity= "+skipCity+", city= "+city);
      	System.out.println("skipInterests= "+skipInterests+", first interests = "+interests.get(0));
      	
      	Query<String> query = getSession().createQuery("SELECT P.nickname FROM Profile P where "
        + "(  ( P.sex=:sex OR (:skipSex=1 ) )   AND   ( P.city=:city OR (:skipCity=1) )   AND   ( (:interests in P.interests) OR (:skipInterests=1) )  ) ORDER BY P.nickname", String.class).
      	setParameter("sex",sex).setParameter("skipSex",skipSex).setParameter("city",city).setParameter("skipCity",skipCity)
      	.setParameter("interests",interests).setParameter("skipInterests",skipInterests);
      	
      	return query.list();
    }
    
}