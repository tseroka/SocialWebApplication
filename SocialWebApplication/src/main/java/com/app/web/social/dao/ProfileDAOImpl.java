package com.app.web.social.dao;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.web.social.model.Profile;

@Repository
@Transactional
public class ProfileDAOImpl implements ProfileDAO {

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	public Profile getProfileByNickname(String nickname) {
		Session session = this.sessionFactory.openSession();		
		Profile profile = (Profile) session.load(Profile.class, nickname);
		return profile;
	}
   
	public String getAuthenticatedUserNickname()
	{
	    return userDAO.getUserAccount( userDAO.getAuthenticatedUserUsername()).getNickname();
	}
	
	 
 
	public void editProfile (Profile profile) 
	{
		Session session = this.sessionFactory.getCurrentSession();
	    session.update(profile);
	}
	
	
	
    
	@SuppressWarnings("unchecked")
	public List<Profile> getProfilesList()
    {
    	Session session = this.sessionFactory.getCurrentSession();
		Query<Profile> query=session.createQuery("from Profile p where p.allowSearching=true");     
	    return (List<Profile>)query.list(); 
    }
    
	
    public List<Profile> searchProfiles(String sex, String city, List<String> interests)
    {   
     /**boolean skipCity = city.equals(""); boolean skipSex = sex.equals(""); boolean skipInterests = interests.get(0).equals("");
    	List<Profile> allProfiles = getProfilesList();
     	List<Profile> findedProfiles = new ArrayList<Profile>(); */
    	
    	List<String> emptyInterests = new ArrayList<String>(); emptyInterests.add("");
    	Session session = this.sessionFactory.getCurrentSession();
    	
		@SuppressWarnings("unchecked")
		Query<Profile> query=session.createQuery
		("from Profile p where ( p.allowSearching=true AND (p.sex:=sex OR sex='') AND (p.city:=city OR city='') AND"
				+ "( (:interests) in p.interests) OR p.interests:=emptyInterests ) ");
		
    	query.setParameter("sex",sex).setParameter("city",city).setParameter("interests",interests)
    	.setParameter("emptyInterests",emptyInterests);
        
    	List<Profile> searchResults =(List<Profile>) query.list();
        
    	/**for(int i=0;i<allProfiles.size();i++)
    	{  
    		      Profile currentProfile = allProfiles.get(i);

    			  if
    		      (    
    		    		 ( currentProfile.getCity().equals(city) || skipCity ) &&
    				     ( currentProfile.getSex().equals(sex) || skipSex ) &&
    				     ( currentProfile.getInterests().containsAll(interests) || skipInterests )
    			  )     
    		                   findedProfiles.add( currentProfile ); 
        } */
    			
		return searchResults;   	
    }


}