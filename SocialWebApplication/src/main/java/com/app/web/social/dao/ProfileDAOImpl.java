package com.app.web.social.dao;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Path;

import org.hibernate.Session;
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
    	
    	Session session = getSession();    	
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<String> criteriaQuery = builder.createQuery(String.class); 	    	
        Root<Profile> root = criteriaQuery.from(Profile.class);  	
        
      	List<Predicate> predicates = new ArrayList<>();
      	
      	if(!"".equals(sex) ) 
      	{
      		 predicates.add(builder.equal(root.get("sex"), sex ));
      	}
      	
      	if(!"".equals(city))
      	{
      		predicates.add(builder.equal(root.get("city"), city ));      		
      	}
      	
      	if(!emptyInterests.equals(interests))
      	{
      	//	Expression<String> interestsExpression = root.as(interests);
      		//Expression<String> interestsExpression = root.get("interests");
      	//	Predicate predicate = ((CriteriaBuilder) interests).in(root.get("interests"));
    //  		predicates.add(predicate);
      

      	//	Expression<Boolean> interestsExpression = root.get("interests").in(interests);
      		//Predicate interestsPredicate = interestsExpression.in(interests);
      //		predicates.add(interestsExpression);
      		//predicates.add(builder.in(root.get("interests"), interests ));  
      		// Expression<String> exp2 = root.get("interests");
      	  //  Predicate p2 = exp2.in(interests);
      	  //  predicates.add(p2);
      //	Expression<List<String> interestsExpression = builder.parameter(String.class);
      	
       		// Expression<List<String>> profileInterests = root.get("interests");
      //	Predicate i1 = interestsExpression.in(profileInterests);
      	
      //	Path<String> profileInterests = root.<List<String>> get("interests");
      //   interests.in(profileInterests);
      		
      	//	interests;//List<String> parentList = Arrays.asList(new String[]{"John", "Raj"}); 

      	//	Expression<String> parentExpression = root.get("interests");
      	//	Predicate parentPredicate = parentExpression.in(interests);
      	//	predicates.add(parentPredicate);
      		for(String interest : interests)
      		{
      		predicates.add(root.get("interests").in(interest));  
      		}
      	}
      	 
      
      //	Predicate [] predicatesArr = predicates.toArray(new Predicate[predicates.size()]); 
      	//root.get("interests").in(interests);
      	
      	criteriaQuery.select(root.<String>get("nickname")).where(predicates.toArray(new Predicate[]{}));
    //  	System.out.println(session.createQuery(criteriaQuery).getSingleResult());
      	return session.createQuery(criteriaQuery).list();  //ClassCastException
    }
    
}